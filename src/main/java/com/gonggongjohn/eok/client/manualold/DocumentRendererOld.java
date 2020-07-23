package com.gonggongjohn.eok.client.manualold;

import com.github.zi_jing.cuckoolib.client.gui.Colors;
import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import com.github.zi_jing.cuckoolib.util.DataUtils;
import com.github.zi_jing.cuckoolib.util.TextUtil;
import com.github.zi_jing.cuckoolib.util.math.ColorRGB;
import com.gonggongjohn.eok.EOK;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 用来读取并显示EOK文档。<br><br>
 * It is used for reading and displaying EOK documents.<br><br>
 * <p>
 * 类结构较复杂，下面给出快捷导航列表：
 * 默认构造器-{@link #DocumentRendererOld(int, int, int, int, int, int, String)}
 * 加载外部文档时使用的构造器-{@link #DocumentRendererOld(int, int, int, int, int, int, File)}
 * 文档解析器初始化函数-{@link #init()}
 * 文档读取函数-{@link #read()}
 * </p>
 */
@SuppressWarnings({"IfStatementWithIdenticalBranches", "ConstantConditions"})
@SideOnly(Side.CLIENT)
public class DocumentRendererOld {
    public static final String localManualPath = "local_manual" + File.separator;
    private static final Map<String, Predicate<String[]>> tokenMap = new HashMap<>();
    public final boolean isDocumentExternal;
    private final List<Page> pages = new ArrayList<>();
    private final List<Element> elements = new ArrayList<>();
    private final GuiDocumentLoading loadingScreen = new GuiDocumentLoading();
    protected int width;
    protected int height;
    protected Tessellator tessellator;
    protected BufferBuilder bufferBuilder;
    private int org1X;
    private int org1Y;
    private int org2X;
    private int org2Y;
    private Logger logger;
    private int lineNumber;
    private ResourceLocation documentLocation;
    private File documentFile;
    private Document documentIn;
    private boolean available;
    private GuiScreen parentGui = null;

    public DocumentRendererOld(int org1X, int org1Y, int org2X, int org2Y, int width, int height, String documentPath) {
        this.isDocumentExternal = false;
        this.documentLocation = new ResourceLocation(documentPath);
        this.constructorImpl(org1X, org1Y, org2X, org2Y, width, height);
    }

    public DocumentRendererOld(int org1X, int org1Y, int org2X, int org2Y, int width, int height, File documentFile) {
        this.isDocumentExternal = true;
        this.documentFile = documentFile;
        this.constructorImpl(org1X, org1Y, org2X, org2Y, width, height);
    }

    private static BufferedReader getResource(ResourceLocation location) throws IOException {
        return new BufferedReader(new InputStreamReader(DataUtils.getResource(location), StandardCharsets.UTF_8));
    }

    private void constructorImpl(int org1X, int org1Y, int org2X, int org2Y, int width, int height) {
        this.org1X = org1X;
        this.org1Y = org1Y;
        this.org2X = org2X;
        this.org2Y = org2Y;
        this.width = width;
        this.height = height;
        logger = EOK.getLogger();
        this.tessellator = Tessellator.getInstance();
        this.bufferBuilder = Tessellator.getInstance().getBuffer();
        init();
        if (!(available = read())) {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("eok.message.documentrenderer.error")), false);
        } else {
            available = true;
        }
        this.closeLoadingGui();
    }

    public boolean isAvailable() {
        return available;
    }

    public int getPages() {
        if (documentIn == null)
            return 0;
        return documentIn.pages.size();
    }

    public void removePages() {
        if (documentIn != null) {
            documentIn.remove();
        }
    }

    private void init() {
        tokenMap.put("center", this::addCenteredText);
        tokenMap.put("end_of_page", this::addEndOfPageMark);
        tokenMap.put("comment", (s) -> true);
        tokenMap.put("image", this::addImage);
        tokenMap.put("drawline", this::addLine);
        tokenMap.put("hyperlink", this::addHyperLink);
    }

    public boolean reload(boolean isExternal, String path) {
        this.removePages();
        if (!isExternal) {
            this.documentLocation = new ResourceLocation(path);
        } else {
            this.documentFile = new File(path);
        }
        if (!(available = read())) {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("eok.message.documentrenderer.error")), false);
        }
        this.closeLoadingGui();
        return this.available;
    }

    public boolean read() {
        this.displayLoadingGui();
        this.loadingScreen.setProgress(0, I18n.format("eok.message.documentrenderer.loadingscreen.status0"));
        pages.clear();
        elements.clear();
        available = false;
        this.removePages();
        lineNumber = 0;
        long startTime = System.currentTimeMillis();
        if (this.width < 20 || this.height < 20) {
            this.error(I18n.format("eok.manual.error.text_area_too_small"));
            return false;
        }
        if (!isDocumentExternal) {
            logger.info("Loading document {}", documentLocation.toString());
            this.loadingScreen.setProgress(5, I18n.format("eok.message.documentrenderer.loadingscreen.status1", documentLocation.toString()));
        } else {
            logger.info("Loading document {}", documentFile.getAbsolutePath());
            this.loadingScreen.setProgress(5, I18n.format("eok.message.documentrenderer.loadingscreen.status1", documentFile.getAbsolutePath()));
        }
        lineNumber = 1;
        BufferedReader reader;
        List<String> lineList = new ArrayList<>();
        String[] orgLines;
        try {
            if (!isDocumentExternal) {
                reader = getResource(documentLocation);
                String line;
                while ((line = reader.readLine()) != null) {
                    lineList.add(line);
                }
                orgLines = lineList.toArray(new String[0]);
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(documentFile), StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    lineList.add(line);
                }
                orgLines = lineList.toArray(new String[0]);
            }
        } catch (Exception e) {
            if (!isDocumentExternal) {
                this.error(I18n.format("eok.manual.error.cant_read_document_file", this.documentLocation.toString()));
            } else {
                this.error(I18n.format("eok.manual.error.cant_read_document_file", this.documentFile.getPath()));
            }
            this.error(e.toString());
            e.printStackTrace();
            return false;
        }
        logger.info("Parsing document structure");
        for (String line : orgLines) {
            if (!processLine(line)) {
                return false;
            }
            lineNumber++;
        }
        logger.info("Building pages");
        if (!buildDocument()) return false;
        logger.info("Loading completed in {}ms.", System.currentTimeMillis() - startTime);
        if (documentIn.pages.size() == 0) {
            this.warning(I18n.format("eok.manual.msg.document_empty"));
            return false;
        }
        return true;
    }

    private boolean processLine(String line) {
        try {
            if (line.startsWith("`!") && line.endsWith("`")) {
                String statement = line.substring(2, line.length() - 1);    // 索引从0开始，并且要去掉最后一个字符，所以还要减1
                if (statement.indexOf(' ') == -1) {    // statement with no arguments
                    this.loadingScreen.setProgress(40, I18n.format("eok.message.documentrenderer.loadingscreen.status2", this.lineNumber, statement));
                    if (tokenMap.containsKey(statement)) {
                        if (!tokenMap.get(statement).test(new String[0])) {
                            appendErrText(line);
                        }
                    } else {
                        this.warning(I18n.format("eok.manual.error.invalid_token", statement));
                    }
                } else {
                    String token = statement.substring(0, statement.indexOf(' '));
                    String args = statement.substring(statement.indexOf(' ') + 1);
                    this.loadingScreen.setProgress(40, I18n.format("eok.message.documentrenderer.loadingscreen.status2", this.lineNumber, token));
                    if (tokenMap.containsKey(token)) {
                        if (args.indexOf(' ') == -1) {    // statement with only one argument
                            if (!tokenMap.get(token).test(new String[]{args})) {
                                appendErrText(line);
                            }
                        } else {    // statement with multiple arguments
                            String[] argArray = args.split(" ");
                            if (!tokenMap.get(token).test(argArray)) {
                                appendErrText(line);
                            }
                        }
                    } else {
                        this.warning(I18n.format("eok.manual.error.invalid_token", statement));
                    }

                }
            } else {
                this.loadingScreen.setProgress(40, I18n.format("eok.message.documentrenderer.loadingscreen.status2", this.lineNumber, ""));
                appendText(line);
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing line \"{}\" (Line {})", line, lineNumber);
            this.error(I18n.format("eok.manual.error.error_processing_statement"));
            this.error(e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean buildDocument() {
        logger.info("buildDocument: Loaded {} elements.", elements.size());
        int currentY = 0;
        List<Element> currentElements = new ArrayList<>();
        int idx = 0;
        int total = elements.size();
        for (Element element : elements) {
            idx++;
            this.loadingScreen.setProgress(60 + (int) ((float) idx / (float) total * 40F), I18n.format("eok.message.documentrenderer.loadingscreen.status3", idx, total));
            if (element.getHeight() > this.height) {
                this.error(I18n.format("eok.manual.error.element_too_large", this.width, this.height));
                return false;
            }
            if (element instanceof ElementEndOfPage) {
                pages.add(new Page(new ArrayList<>(currentElements)));
                currentElements.clear();
                currentY = 0;
                continue;
            }
            if (currentY + element.getHeight() >= this.height) {
                pages.add(new Page(new ArrayList<>(currentElements)));
                currentElements.clear();
                currentY = 0;
                currentElements.add(element);
                currentY += element.getHeight();
            } else {
                currentElements.add(element);
                currentY += element.getHeight();
            }
        }
        if (!currentElements.isEmpty()) {
            pages.add(new Page(new ArrayList<>(currentElements)));
            currentElements.clear();
        }
        documentIn = new Document(new ArrayList<>(pages));
        logger.info("buildDocument: Built {} pages.", pages.size());
        return true;
    }

    public void draw(int pageIndex, DocumentSide side, int offsetX, int offsetY) {
        if (!available) {
            if (side == DocumentSide.LEFT) {
                GLUtils.drawString(I18n.format("eok.manual.error"), offsetX + org1X, offsetY + org1Y, Colors.DEFAULT_BLACK);
            }
            return;
        }
        int originX;
        int originY;
        switch (side) {
            case LEFT:
                originX = offsetX + org1X;
                originY = offsetY + org1Y;
                break;
            case RIGHT:
                originX = offsetX + org2X;
                originY = offsetY + org2Y;
                break;
            default:    // never reaches, but it is necessary
                originX = 0;
                originY = 0;
                break;
        }
        GLUtils.pushMatrix();
        this.documentIn.pages.get(pageIndex).draw(originX, originY, this);
        GLUtils.popMatrix();
        GLUtils.drawCenteredString(String.valueOf(pageIndex + 1), originX + width / 2, originY + height + 5, Colors.DEFAULT_BLACK);
    }

    public void onMouseClick(int mouseX, int mouseY, int mouseButton, int pageIndex) {
        if (this.pages == null)
            return;
        if (this.pages.size() == 0)
            return;
        for (Element e : this.pages.get(pageIndex).elements) {
            e.onClicked(this);
        }
    }

    protected void error(String msg) {
        if (!this.isDocumentExternal) {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("eok.manual.msg.error", this.documentLocation.toString(), this.lineNumber, msg)), false);
            this.logger.error(I18n.format("eok.manual.msg.error", this.documentLocation.toString(), this.lineNumber, msg));
        } else {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("eok.manual.msg.error", this.documentFile.getPath(), this.lineNumber, msg)), false);
            this.logger.error(I18n.format("eok.manual.msg.error", this.documentFile.getPath(), this.lineNumber, msg));
        }
    }

    protected void warning(String msg) {
        if (!this.isDocumentExternal) {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("eok.manual.msg.warning", this.documentLocation.toString(), this.lineNumber, msg)), false);
            this.logger.error(I18n.format("eok.manual.msg.warning", this.documentLocation.toString(), this.lineNumber, msg));
        } else {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("eok.manual.msg.warning", this.documentFile.getPath(), this.lineNumber, msg)), false);
            this.logger.error(I18n.format("eok.manual.msg.warning", this.documentFile.getPath(), this.lineNumber, msg));
        }
    }

    private void displayLoadingGui() {
        this.loadingScreen.reset();
        this.parentGui = Minecraft.getMinecraft().currentScreen;
        this.loadingScreen.initGui();
        Minecraft.getMinecraft().currentScreen = this.loadingScreen;
    }

    private void closeLoadingGui() {
        Minecraft.getMinecraft().currentScreen = this.parentGui;
    }

    private void appendErrText(String str) {
        appendText("§c§l" + I18n.format("eok.manual.text.error") + ":" + str);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  以下几个方法用于解析元素，由processLine()调用
    ///////////////////////////////////////////////////////////////////////////

    private void appendText(String str) {
        String output = TextUtil.expandTextStyleMark(str);
        List<String> lines = TextUtil.trimStringToWidthWithoutStyleMarks(output, this.width);
        for (String line : lines) {
            elements.add(new ElementTextLine(line));
        }
    }

    /* {text:string} */
    private boolean addCenteredText(String[] args) {
        if (args.length != 1) {
            this.warning(I18n.format("eok.manual.error.illegal_arguments", 1));
            return false;
        }
        elements.add(new ElementCenteredText(args[0]));
        return true;
    }

    @SuppressWarnings("SameReturnValue")
    private boolean addEndOfPageMark(String[] args) {
        elements.add(new ElementEndOfPage());
        return true;
    }

    /* {path:string} */
    private boolean addImage(String[] args) {
        if (args.length != 1) {
            this.warning(I18n.format("eok.manual.error.illegal_arguments", 1));
            return false;
        }
        try {
            this.loadingScreen.setProgress(this.loadingScreen.getProgress(), I18n.format("eok.message.documentrenderer.loadingscreen.status2.readfile", args[0]));
            /*if (!isDocumentExternal) {
                elements.add(new Element.Image(new ResourceLocation(args[0]), this.width, this.height));
            } else {
                ResourceLocation location = new ResourceLocation(args[0]);
                File file = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + DocumentRenderer.localManualPath + location.getResourceDomain() + File.separator + location.getResourcePath());
                if (file.exists()) {
                    elements.add(new Element.Image(file, this.width, this.height));
                } else {
                    logger.error("File not found: {}", file.getAbsolutePath());
                    throw new FileNotFoundException(String.format("File not found: %s", file.getAbsolutePath()));
                }
            }*/
            // 不再支持读取文件系统中的图像
            elements.add(new ElementImage(new ResourceLocation(args[0]), this.width, this.height));
        } catch (Exception e) {
            this.warning(I18n.format("eok.manual.error.syntax_error"));
            this.warning(e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /* {x1:int, y1:int, x2:int, y2:int, width:int, color:string[#XXXXXX]} */
    private boolean addLine(String[] args) {
        if (args.length != 6) {
            this.warning(I18n.format("eok.manual.error.illegal_arguments", 6));
            return false;
        }
        try {
            String hex = args[5];
            ColorRGB rgb = GLUtils.hexStringToRGB(hex);
            int x1 = Integer.parseInt(args[0]);
            int y1 = Integer.parseInt(args[1]);
            int x2 = Integer.parseInt(args[2]);
            int y2 = Integer.parseInt(args[3]);
            int width = Integer.parseInt(args[4]);
            int r = rgb.getR();
            int g = rgb.getG();
            int b = rgb.getB();
            elements.add(new ElementLine(x1, y1, x2, y2, width, r, g, b));
        } catch (NumberFormatException e) {
            this.warning(I18n.format("eok.manual.error.syntax_error"));
            this.warning(e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /* {text:string, link:string[domain:path]} */
    private boolean addHyperLink(String[] args) {
        if (args.length != 2) {
            this.warning(I18n.format("eok.manual.error.illegal_arguments", 2));
            return false;
        }
        try {
            if (!isDocumentExternal) {
                Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(args[1]));    // 判断文档是否存在，不存在会抛出异常
                elements.add(new ElementHyperLink(args[0], args[1], false));
            } else {
                ResourceLocation location = new ResourceLocation(args[1]);
                String link = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + DocumentRendererOld.localManualPath + location.getResourceDomain() + File.separator + location.getResourcePath();
                File document = new File(link);
                if (!document.exists()) throw new FileNotFoundException("Can't find document file" + args[0]);
                elements.add(new ElementHyperLink(args[0], link, true));
            }
        } catch (Exception e) {
            this.error(I18n.format("eok.manual.error.cant_read_document_file", args[0]));
            this.error(e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public enum DocumentSide {
        LEFT, RIGHT
    }

}
