package eok.client.manual;

import com.ibm.icu.impl.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

final class TestStyleHolder {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void testApplyStyleString() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class classStyleHandler = Class.forName("com.gonggongjohn.eok.client.manual.StyleHandler");
        Method setup = classStyleHandler.getDeclaredMethod("setup");
        setup.setAccessible(true);
        setup.invoke(null);

        Class classStyleHolder = Class.forName("com.gonggongjohn.eok.client.manual.StyleHolder");
        Method applyStyleString = classStyleHolder.getDeclaredMethod("applyStyleString", String.class);
        applyStyleString.setAccessible(true);
        Method equals = classStyleHolder.getDeclaredMethod("equals", Object.class);
        equals.setAccessible(true);
        Field styleProperties = classStyleHolder.getDeclaredField("styleProperties");
        styleProperties.setAccessible(true);
        Constructor constructor = classStyleHolder.getDeclaredConstructor();
        constructor.setAccessible(true);

        Object holder = constructor.newInstance();
        applyStyleString.invoke(holder, "  \n color:white;align  : center;\n  margin-top:100;margin-right:6.75");

        Object answer = constructor.newInstance();
        Map propMap = (Map) styleProperties.get(answer);
        Class classPropertyNumber = Class.forName("com.gonggongjohn.eok.client.manual.PropertyNumber");
        Class classPropertyString = Class.forName("com.gonggongjohn.eok.client.manual.PropertyString");
        Method setNumber = classPropertyNumber.getDeclaredMethod("set", double.class);
        setNumber.setAccessible(true);
        Method setString = classPropertyString.getDeclaredMethod("set", String.class);
        setString.setAccessible(true);
        setString.invoke(propMap.get("color"), "white");
        setString.invoke(propMap.get("align"), "center");
        setNumber.invoke(propMap.get("margin-top"), 100);
        setNumber.invoke(propMap.get("margin-right"), 6.75D);
        boolean result = (boolean) equals.invoke(holder, answer);
        Assert.assrt(result);
    }
}
