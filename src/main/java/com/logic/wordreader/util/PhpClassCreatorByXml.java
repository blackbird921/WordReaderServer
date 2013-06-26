package com.logic.wordreader.util;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PhpClassCreatorByXml {
    private static final String INPUT_PATH = "D:/prj-mobile/Genisys_Server_Tool/docs/MobileApi";
//            private static final String OUTPUT_PATH = "D:/prj-mobile/Genisys_Server_Tool/docs/MobileApi_php/";
    private static final String OUTPUT_PATH = "D:/prj-mobile/GenisysMobileProcessor/application/models/MobileProcessor/MobileApi/";
    private static final String OUTPUT__WRAPPER_PATH = OUTPUT_PATH + "wrapper";
    public static final String ROOT_ELEMENT = "MobileGatewayResponse";
    public static final List<String> wrappers = new ArrayList<String>();
    public static final String CLASS_NAME_PREFIX = "Application_Model_MobileProcessor_MobileApi_";
    public static final String CLASS_NAME__WRAPPER_PREFIX = CLASS_NAME_PREFIX + "Wrapper_";

    static {
//        wrappers.add("AccountInformation");
//        wrappers.add("Services");
//        wrappers.add("MobileBillPayRegistration");
//        wrappers.add("MobileBillPay");
//        wrappers.add("MobileBillPayPayee");
//        wrappers.add("MobileBillPayPayment");
    }


    public static void main(String argv[]) throws Exception{
        String x =
                "<?xml version=\"1.0\"?>\n" +
                        "<info>\n" +
                        "<entry\n" +
                        "   kind=\"dir\"\n" +
                        "   path=\".\"\n" +
                        "   revision=\"27152\">\n" +
                        "<url>svn://216.91.145.250/svnrepos/genisys/GenisysAndroid</url>\n" +
                        "<repository>\n" +
                        "<root>svn://216.91.145.250/svnrepos</root>\n" +
                        "<uuid>c206318d-2511-4e8f-8be4-020f6273d4a3</uuid>\n" +
                        "</repository>\n" +
                        "<wc-info>\n" +
                        "<schedule>normal</schedule>\n" +
                        "<depth>infinity</depth>\n" +
                        "</wc-info>\n" +
                        "<commit\n" +
                        "   revision=\"27144\">\n" +
                        "<author>anna.geng@logicsolutions.com</author>\n" +
                        "<date>2013-05-16T10:29:21.769934Z</date>\n" +
                        "</commit>\n" +
                        "</entry>\n" +
                        "</info>\n" +
                        "";

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(new ByteArrayInputStream(x.getBytes("UTF-8")));
        NodeList books = doc.getElementsByTagName("entry");
        for (int i = 0; i < books.getLength(); i++) {
            Element book = (Element) books.item(i);
//            Node title = book.getElementsByTagName("logentry").item(0);
            String revision = book.getAttribute("revision");
//            System.out.println(book.getTextContent());
            System.out.println(revision);
            if (revision != null) {
                break;
            }
        }

//        String dirStr = INPUT_PATH + "/";
//        File dir = new File(dirStr);
//        for (File child : dir.listFiles()) {
//            if (!child.getName().startsWith(".")) {
//                runFile(dirStr + child.getName());
//            }
//        }
    }

    public static String getFriendlyName(String s) {
        String result = s;
        for (String key : Constant.names.keySet()) {
            result = result.replace(key, Constant.names.get(key));
        }
        return result;
    }

    public static void runFile(String fileName) {
        try {

            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName(ROOT_ELEMENT);

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                System.out.println("xxxxxxxxxxxxxx" + nNode.getNodeName());
                printNode(nNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExtends(String className) {
        String e = "";
        if (className.contains("SYNCRQ")) {
            e = " extends " + CLASS_NAME_PREFIX + "_BaseSyncRq";
        }
        return e;
    }

    public static void writeFile(String fileName, String className, List<PhpClassDataByXml> childrenClassNames, boolean isTopWrapper) {
        try {
            // Create file
            String path = OUTPUT_PATH;
            String prefix = CLASS_NAME_PREFIX;
            if (isTopWrapper) {
                path = OUTPUT__WRAPPER_PATH;
                prefix = CLASS_NAME__WRAPPER_PREFIX;
            }
            FileWriter fstream = new FileWriter(path + "/" + getFriendlyName(fileName).replaceAll("ORCC_", "") + ".php", false);
            BufferedWriter out = new BufferedWriter(fstream);
            String extend = getExtends(className);
            String s = "";
            s += "<?php\n";


            s += "\nclass " + prefix + getFriendlyName(className) + extend + " {\n";
            for (PhpClassDataByXml key : childrenClassNames) {
                s += "\tpublic $" + key.name + ";\n";
            }

            s += "\n\tfunction __construct() {\n";
            boolean hasArray = false;

            for (PhpClassDataByXml field : childrenClassNames) {
                if (field.isArray) {
                    s += "\t\t$this->" + field.name + " = array();\n";
                }
            }
            s += "\n\t}\n";


            for (PhpClassDataByXml field : childrenClassNames) {
                if (field.isArray) {
                    s += "\n\tpublic function add" + field.name + "(){";
                    s += "\n\t\t$value = new "+prefix+field.name+"();";
                    s += "\n\t\tarray_push($this->"+ field.name + ", $value);";
                    s += "\n\t\treturn $value;";

                    s += "\n\t}";
                }
            }

            for (PhpClassDataByXml key : childrenClassNames) {
                if (key.isObject) {
                    s += "\n\tpublic function get" + getFriendlyName(key.name) + "() {\n";
                    s += "\t\tif(!is_object($this->" + key.name + ")){\n";
                    s += "\t\t\t$this->" + key.name + " = new " + CLASS_NAME_PREFIX + getFriendlyName(key.name) + "();\n";
                    s += "\t\t}\n";
                    s += "\t\treturn $this->" + key.name + ";\n";
                    s += "\t}\n";
                }
            }

//            if (!isTopWrapper) {
//                s += "\n\tpublic function copyValues($value) {\n";
//                s += "\t}\n";
//            }
            s += "\t\n}\n\n?>";
            s = s.replace("ORCC.", "ORCC_");
            out.write(s);
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void printNode(Node node) {
        if (isElement(node)) {
//            System.out.println(node.getNodeName());
            if (isObject(node)) {
                List<PhpClassDataByXml> childrenNames = new ArrayList<PhpClassDataByXml>();
                List<String> childrenNameStr = new ArrayList<String>();
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    Node child = node.getChildNodes().item(i);
                    if (childrenNameStr.contains(child.getNodeName())) {
                        for (PhpClassDataByXml name : childrenNames) {
                            if (name.name.equals(child.getNodeName())) {
                                name.isArray = true;
                                name.isObject = false;
                                break;
                            }
                        }
                    } else {
                        if (isObject(child)) {
                            childrenNames.add(new PhpClassDataByXml(child.getNodeName(), true, false));
                        } else if (isElement(child)) {
                            childrenNames.add(new PhpClassDataByXml(child.getNodeName(), false, false));
                        }
                        childrenNameStr.add(child.getNodeName());
                    }
                }
                if (!node.getNodeName().equalsIgnoreCase(ROOT_ELEMENT)) {
                    writeFile(node.getNodeName().replace("ORCC.", "ORCC_"), node.getNodeName(), childrenNames, wrappers.contains(node.getNodeName()));
                }
            }
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                printNode(node.getChildNodes().item(i));
            }
        }
    }

    public static boolean isObject(Node node) {
        return node.getChildNodes().getLength() > 1;
    }


    public static boolean isElement(Node node) {
        return node.getChildNodes().getLength() > 0;
    }
}