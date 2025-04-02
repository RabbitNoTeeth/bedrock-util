package com.github.rabbitnoteeth.bedrock.util;

import com.github.rabbitnoteeth.bedrock.util.exception.XmlException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlUtils {

    private XmlUtils() {
    }

    public static Document parse(String xmlStr) throws XmlException {
        try (StringReader sr = new StringReader(xmlStr)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(sr);
            return builder.parse(is);
        } catch (Throwable e) {
            throw new XmlException(e);
        }
    }

}
