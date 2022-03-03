package com.callumrodgers.tabela.xml;

import com.callumrodgers.tabela.util.ResourceLoader;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XMLParser {

    private static DocumentBuilder builder;

    static {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        dbf.setIgnoringComments(true);
        try {
            builder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Document parse(String filePath) throws IOException, SAXException {
        return builder.parse(ResourceLoader.getAsStream(filePath));
    }
}
