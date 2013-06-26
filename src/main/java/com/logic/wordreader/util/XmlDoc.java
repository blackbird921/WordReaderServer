package com.logic.wordreader.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlDoc {

    public static String getTextByElementPath(String xml, String path) {
        String text = null;
        InputStream is = null;
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {

            if (path == null || path.length() == 0) {
                return null;
            }
            String[] pathArray = path.split("/");

            is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            document = saxReader.read(is);
            Element element = document.getRootElement();

            for (String elementName : pathArray) {
                element = element.element(elementName);
            }
            text = element.getText();

        } catch (Exception e) {

        } finally {
            if (document != null) {
                document.clearContent();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }

        return text;
    }

    public static String getTextByRootElement(String xml) {
        String text = null;
        InputStream is = null;
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {

            is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            document = saxReader.read(is);
            Element root = document.getRootElement();
            text = root.getText();

        } catch (Exception e) {

        } finally {
            if (document != null) {
                document.clearContent();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }

        return text;
    }

    public static Element getElementByPath(String xml, String path) {
        InputStream is = null;
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {

            if (path == null || path.length() == 0) {
                return null;
            }
            String[] pathArray = path.split("/");

            is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            document = saxReader.read(is);
            Element element = document.getRootElement();

            for (String elementName : pathArray) {
                element = element.element(elementName);
                if (element == null) {
                    break;
                }
            }
            return element;

        } catch (Exception e) {

        } finally {
            if (document != null) {
                document.clearContent();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }

        return null;
    }

    public static Element getElementByPath(Element element, String path) {
        Element childElement = element;

        try {
            if (path == null || path.length() == 0) {
                return null;
            }
            String[] pathArray = path.split("/");

            for (String elementName : pathArray) {
                childElement = childElement.element(elementName);
                if (childElement == null) {
                    break;
                }
            }

        } catch (Exception e) {

            childElement = null;
        }

        return childElement;
    }

    public static String getChildElementText(Element element, String elementName) {

        String text = null;

        Element childElement = element.element(elementName);
        if (childElement != null) {
            text = childElement.getText();
        }

        return text;
    }

    public static void main(String[] args){
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<TransportContainer>" +
                "<Data>4Wg5JKDIRarOluxMtc81Z3hlBLCa8yV4hivj2ARX+G6qQFk5bB3m28h9Tw+B3C4KfOY54CoHBfac/JKOYH8Korg0tSUVMWQJasVn9M+hSbwVTbDYwir4njqNTfxxOY9UTH3Ujk6Gbzw+OZ5Ut8H9Ghy293JV/qnAp/rEAnK5H1AoZfgSDC+WTjeJPk6n0gj3f7oVqM8wzL3qH/UWU2YIAnA7kS6bpc5JoIwHPwLoq7Edv1u9zfjjwFo1qFXGp+7yjIOwckB1J6PEDlt/GB5YcNA2pvnIEmsbADJ+E50QhgdJxocCxnnQCQ29H4KfSU9/UenAk2K5nTyCGIa2d9vD5fP7/nm/I4JjlYv+u0F15WCxZNU6nmzbCSV3dYkT+5gNj4OrutwQxofzAvk7fOkE9hpQOVdZQMpjbhT1qbn8U7AFjuU6V3NMyY+tzNWTyTJ4cZlV0OrxsvTmj7PGdBDSYk8vWNc0bKFLNOQgpfDtwCyqN6Otje5j7+QxRt5OK4XzqbtmbaqbFMlvtUCpkpnaFiQRkUXmTb3Ry6N/fIpMHjYy77P26wGSi84Ju3GeWKOtkSpxOft6aq15lwWynsSfwmLJ/ITrgrNZiJIQ+IUOYQrN50U6cQxHThT9D5mGhQc6v93cTCcM4COHIfqY+6YIwHj5TQ97zbTI2/Fcr5kHQOczvU8pYc4lYHf+975vjozQkIDYU87AVXs=</Data>" +
                "</TransportContainer>";
        String responseData = XmlDoc.getTextByElementPath(xml, "Data");
        System.out.println(responseData);
    }
}
