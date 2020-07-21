package com.gonggongjohn.eok.client.manual;

import com.gonggongjohn.eok.EOK;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

final class DocumentParser {

    private final Document document = new Document();
    private final StyleHolder currentGlobalStyle = new StyleHolder();

    Document parse(ResourceLocation location) throws IOException, ParserConfigurationException, SAXException {
        InputStream stream;
        try {
            stream = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(stream);
        return parseDocument(document);
    }

    private Document parseDocument(org.w3c.dom.Document xmlDocument) {
        org.w3c.dom.Element rootElement = xmlDocument.getDocumentElement();
        if (!rootElement.getTagName().equals("document"))   // 没有document根元素，返回空文档
            return this.document;

        NodeList rootElementList = rootElement.getChildNodes();
        for (int i = 0; i < rootElementList.getLength(); i++) {
            Node node = rootElementList.item(i);
            if (node instanceof Element) {
                parseDocumentRootElement((Element) node);
            } else if (node instanceof Text) {
                appendText(node.getTextContent());
            } else {
                EOK.getLogger().warn("Unknown element: {} {}", node.getClass().getName(), node.toString());
            }
        }
        return this.document;
    }

    private void parseDocumentRootElement(Element element) {
        switch (element.getTagName()) {
            case "style":
                applyStyleStringToStyleHolder(this.currentGlobalStyle, element.getTextContent().trim());
                break;
            case "text":
                StyleHolder styleHolder = this.currentGlobalStyle;
                if (element.hasAttribute("style")) {

                }
        }
    }

    private void appendText(String text) {

    }

    private void applyStyleStringToStyleHolder(StyleHolder holder, String style) {

    }
}
