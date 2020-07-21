package com.gonggongjohn.eok.client.manual;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 用于存储某个元素的所有样式
 */
final class StyleHolder {

    private final Map<String, Property> styleProperties = new HashMap<>();

    StyleHolder() {
        for (String key : StyleHandler.getRegistry().keySet())
            this.styleProperties.put(key, StyleHandler.getRegistry().get(key).getDefaultStyle());
    }

    boolean hasStyle(String key) {
        return this.styleProperties.containsKey(key);
    }

    double getPropertyDouble(String key) throws InvalidStyleNameException, IncorrectStylePropertyTypeException {
        if (!this.hasStyle(key))
            throw new InvalidStyleNameException(key);
        Property property = this.styleProperties.get(key);
        if (!(property instanceof PropertyNumber))
            throw new IncorrectStylePropertyTypeException("number", "string");
        return ((PropertyNumber) property).get();
    }

    int getPropertyInteger(String key) throws InvalidStyleNameException, IncorrectStylePropertyTypeException {
        return (int) this.getPropertyDouble(key);
    }

    String getPropertyString(String key) throws InvalidStyleNameException, IncorrectStylePropertyTypeException {
        if (!this.hasStyle(key))
            throw new InvalidStyleNameException(key);
        Property property = this.styleProperties.get(key);
        if (!(property instanceof PropertyString))
            throw new IncorrectStylePropertyTypeException("string", "number");
        return ((PropertyString) property).get();
    }

    void setStyle(String key, double value) throws InvalidStyleNameException, IncorrectStylePropertyTypeException {
        if (!this.hasStyle(key))
            throw new InvalidStyleNameException(key);
        Property property = this.styleProperties.get(key);
        if (!(property instanceof PropertyNumber))
            throw new IncorrectStylePropertyTypeException("number", "string");
        ((PropertyNumber) property).set(value);
    }

    void setStyle(String key, String value) throws IncorrectStylePropertyTypeException, InvalidStyleNameException, InvalidStylePropertyValueException {
        if (!this.hasStyle(key))
            throw new InvalidStyleNameException(key);
        if (!StyleHandler.validateStringStyle(key, value))
            throw new InvalidStylePropertyValueException(value, StyleHandler.getAllAcceptedValues(key));
        ((PropertyString) this.styleProperties.get(key)).set(value);
    }

    /**
     * 用于解析&lt;{@code style}块&gt;或{@code style}属性中的一个或多个样式语句，并将其应用到该{@link StyleHolder}中
     *
     * @param styleString 要解析的样式语句
     */
    void applyStyleString(String styleString) throws StyleParsingException {
        String[] statements = StringUtils.split(styleString, ';');
        for (int i = 0; i < statements.length; i++)
            statements[i] = statements[i].trim();
        ArrayList<String> fixedStatements = new ArrayList<>(statements.length);
        for (String str : statements) {
            if (!str.isEmpty())
                fixedStatements.add(str);
        }
        fixedStatements.trimToSize();
        for (String statement : fixedStatements) {
            if (statement.indexOf(':') == -1)
                throw new StyleParsingException(statement, "style name and style property must be separated by \":\"");
            String[] fragments = StringUtils.split(statement, ':');
            for (int i = 0; i < fragments.length; i++)
                fragments[i] = fragments[i].trim();
            if (fragments.length != 2)
                throw new StyleParsingException(statement, "invalid statement");
            if (fragments[0].isEmpty())
                throw new StyleParsingException(statement, "missing style name");
            if (fragments[1].isEmpty())
                throw new StyleParsingException(statement, "missing style property value");
            String styleName = fragments[0];
            String propertyValue = fragments[1];
            if (!this.hasStyle(styleName)) {
                StyleParsingException e = new StyleParsingException(statement, "invalid style name");
                e.initCause(new InvalidStyleNameException(styleName));
                throw e;
            }
            Property property = this.styleProperties.get(styleName);
            if (property instanceof PropertyNumber) {
                try {
                    ((PropertyNumber) property).set(Double.parseDouble(propertyValue));
                } catch (NumberFormatException numberFormatException) {
                    StyleParsingException e = new StyleParsingException(statement, "invalid property value");
                    e.initCause(numberFormatException);
                    throw e;
                }
            } else if (property instanceof PropertyString) {
                boolean valid;
                try {
                    valid = StyleHandler.validateStringStyle(styleName, propertyValue);
                } catch (IncorrectStylePropertyTypeException | InvalidStyleNameException styleException) {
                    StyleParsingException e = new StyleParsingException(statement, "error while validating style property value, this is probably a bug");
                    e.initCause(styleException);
                    throw e;
                }
                if (!valid) {
                    StyleParsingException styleParsingException = new StyleParsingException(statement, "invalid property value");
                    try {
                        styleParsingException.initCause(new InvalidStylePropertyValueException(propertyValue, StyleHandler.getAllAcceptedValues(styleName)));
                    } catch (IncorrectStylePropertyTypeException | InvalidStyleNameException e) {
                        StyleParsingException styleParsingException1 = new StyleParsingException(statement, "error while throwing parsing exception, this is probably a bug");
                        styleParsingException1.initCause(e);
                        styleParsingException.initCause(styleParsingException1);
                        throw styleParsingException;
                    }
                    throw styleParsingException;
                }
                ((PropertyString) property).set(propertyValue);
            } else {
                throw new StyleParsingException(statement, "unexpected property type: " + property.getClass().getName() + " , this is probably a bug");
            }
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof StyleHolder))
            return false;
        return ((StyleHolder) o).styleProperties.equals(this.styleProperties);
    }

}

/**
 * 用于存储单个样式状态
 */
abstract class Property {
    String key;
}

final class PropertyString extends Property {
    private String value;

    PropertyString(String value) {
        this.value = value;
    }

    String get() {
        return this.value;
    }

    void set(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyString that = (PropertyString) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

final class PropertyNumber extends Property {
    private double value;

    PropertyNumber(double value) {
        this.value = value;
    }

    double get() {
        return this.value;
    }

    void set(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyNumber that = (PropertyNumber) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

abstract class StyleRegistryEntry {
    String target;

    abstract Property getDefaultStyle();
}

final class StyleRegistryEntryString extends StyleRegistryEntry {
    final String defaultValue;
    final String[] values;

    /**
     * 构造一个样式注册项
     *
     * @param target       样式被应用到的目标, 全局则为{@code "global"}
     * @param defaultValue 默认值
     * @param values       允许的所有值, 若为空({@code new String[]{}})则为不限
     */
    StyleRegistryEntryString(String target, String defaultValue, String[] values) {
        this.target = target;
        this.defaultValue = defaultValue;
        this.values = values;
    }

    @Override
    Property getDefaultStyle() {
        return new PropertyString(this.defaultValue);
    }
}

final class StyleRegistryEntryNumber extends StyleRegistryEntry {
    final double defaultValue;

    /**
     * 构造一个样式注册项
     *
     * @param target       样式被应用到的目标, 全局则为{@code "global"}
     * @param defaultValue 默认值
     */
    StyleRegistryEntryNumber(String target, double defaultValue) {
        this.target = target;
        this.defaultValue = defaultValue;
    }

    @Override
    Property getDefaultStyle() {
        return new PropertyNumber(this.defaultValue);
    }
}

/**
 * 用于管理所有样式注册项，需要从外部调用{@link #setup()}方法来初始化
 */
final class StyleHandler {

    private static final Map<String, StyleRegistryEntry> REGISTRY = new HashMap<>();

    static void setup() {
        registerStringStyle("global", "color", "black", new String[]{});
        registerStringStyle("global", "align", "none", new String[]{"none", "center", "right"});
        registerNumberStyle("global", "margin-top", 0);
        registerNumberStyle("global", "margin-right", 0);
        registerNumberStyle("global", "margin-bottom", 0);
        registerNumberStyle("global", "margin-left", 0);
        registerNumberStyle("global", "padding-top", 0);
        registerNumberStyle("global", "padding-right", 0);
        registerNumberStyle("global", "padding-bottom", 0);
        registerNumberStyle("global", "padding-left", 0);
        registerNumberStyle("text", "font-size", 9);
        registerNumberStyle("text", "line-spacing", 1);
    }

    private static void registerStringStyle(String target, String name, String defaultValue, String[] values) {
        REGISTRY.put(name, new StyleRegistryEntryString(target, defaultValue, values));
    }

    private static void registerNumberStyle(String target, String name, double defaultValue) {
        REGISTRY.put(name, new StyleRegistryEntryNumber(target, defaultValue));
    }

    /**
     * 验证给定的样式值是否有效(给定的样式值是否为样式所允许的值)
     *
     * @param key   样式名称
     * @param value 样式值
     * @return 结果
     * @throws InvalidStyleNameException           当指定的样式不存在时抛出
     * @throws IncorrectStylePropertyTypeException 当样式类型不是字符串时抛出
     * @throws NullPointerException                当给定值为{@code null}时抛出
     */
    static boolean validateStringStyle(String key, String value) throws InvalidStyleNameException, IncorrectStylePropertyTypeException {
        StyleRegistryEntry entry = REGISTRY.get(key);
        if (value == null)
            throw new NullPointerException("value can't be null");
        if (entry == null)
            throw new InvalidStyleNameException(key);
        if (!(entry instanceof StyleRegistryEntryString))
            throw new IncorrectStylePropertyTypeException("string", "number");
        List<String> allowedValues = Arrays.asList(((StyleRegistryEntryString) entry).values);
        return allowedValues.size() == 0 || allowedValues.contains(value);  // 若StyleRegistryEntryString::values[]为空则允许任意值
    }

    static String[] getAllAcceptedValues(String key) throws InvalidStyleNameException, IncorrectStylePropertyTypeException {
        StyleRegistryEntry entry = REGISTRY.get(key);
        if (entry == null)
            throw new InvalidStyleNameException(key);
        if (!(entry instanceof StyleRegistryEntryString))
            throw new IncorrectStylePropertyTypeException("string", "number");
        return ((StyleRegistryEntryString) entry).values;
    }

    static Map<String, StyleRegistryEntry> getRegistry() {
        return REGISTRY;
    }
}

abstract class StyleException extends Exception {
    StyleException(String cause) {
        super(cause);
    }
}

class InvalidStyleNameException extends StyleException {
    InvalidStyleNameException(String styleName) {
        super("Invalid style name: " + styleName);
    }
}

class InvalidStylePropertyValueException extends StyleException {
    InvalidStylePropertyValueException(String value, String[] allowedValues) {
        super("Invalid style property value: " + value + ". All allowed values are " + Arrays.toString(allowedValues));
    }
}

class IncorrectStylePropertyTypeException extends StyleException {
    IncorrectStylePropertyTypeException(String expectedType, String actualType) {
        super("Incorrect style type. Expected " + expectedType + ", got " + actualType);
    }
}

class StyleParsingException extends StyleException {
    StyleParsingException(String statements, String message) {
        super("Invalid style statement(s): " + message + ": " + statements);
    }
}
