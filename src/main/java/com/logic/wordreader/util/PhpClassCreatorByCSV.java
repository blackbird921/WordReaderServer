package com.logic.wordreader.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhpClassCreatorByCSV {
    private static final String OUTPUT_PATH = "D:/prj-mobile/GenisysMobileProcessor/GenisysMobileProcessor/application/models/MobileProcessor/ORCC/Ofx/";
//    private static final String OUTPUT_PATH = "d:/prj-anx/i2k_report/docs/temp/Ofx/";

    public static void main(String argv[]) {
        String dirStr = "D:/prj-mobile/GenisysMobileProcessor/GenisysMobileProcessor/docs/";
        File dir = new File(dirStr);
//        runFile(dirStr + "orcc_api_billpay_request.txt");
        runFile(dirStr + "orcc_api_billpay_response.txt");
    }





    public static String getFriendlyName(String s) {
        String result = s;
        for (String key : Constant.names.keySet()) {
            if (result != null) {
                result = result.replace(key, Constant.names.get(key));
            }
        }

        return result;
    }

    public static List<PhpClass> readCsv(String fileName) throws Exception {

        List<PhpClass> classes = new ArrayList<PhpClass>();
        BufferedReader CSVFile = new BufferedReader(new FileReader(fileName));

        String dataRow = CSVFile.readLine(); // Read first line.
        System.out.println(dataRow);
        List<PhpField> fields = new ArrayList<PhpField>();
        while (dataRow != null) {
            PhpField field = new PhpField();
            String[] dataArray = dataRow.split("\t");
            for (int i = 0; i < dataArray.length; i++) {
                if (i == 0) {
                    field.setClassName(dataArray[i].trim());
                } else if (i == 1) {
                    field.setClassDesc(dataArray[i].trim());
                } else if (i == 2) {
                    field.setName(dataArray[i].trim());
                } else if (i == 3) {
                    field.setType(dataArray[i].trim());
                } else if (i == 4) {
                    field.setValidation(dataArray[i].trim());
                } else if (i == 5) {
                    field.setDesc(dataArray[i].replaceAll("\"", "")
                            .replaceAll("See below for structure.", "").trim()
                    );
                } else if (i == 6) {
                    field.setValues(dataArray[i].trim());
                }
            }
            fields.add(field);
            dataRow = CSVFile.readLine(); // Read next line of data.
            System.out.println(dataRow);
        }
        // Close the file once all data has been read.
        CSVFile.close();

        System.out.println(fields);
        System.out.println();
        System.out.println();

        Map<String, List<PhpField>> categorizedFields = new HashMap<String, List<PhpField>>();
        for (PhpField field : fields) {
            if (!categorizedFields.keySet().contains(field.getClassName())) {
                List<PhpField> cat = new ArrayList<PhpField>();
                categorizedFields.put(field.getClassName(), cat);
            }
            categorizedFields.get(field.getClassName()).add(field);
        }

        for (String className : categorizedFields.keySet()) {
            PhpClass php = new PhpClass();
            php.setName(className);
            for (PhpField field : categorizedFields.get(className)) {
                if (field.getClassDesc() != null && field.getClassDesc().length() > 0) {
                    php.setClassDesc(field.getClassDesc());
                }
            }
            php.setValues(categorizedFields.get(className));
            classes.add(php);
        }

        System.out.println(classes);
        System.out.println();

        return classes;

    } //main()

    public static void runFile(String fileName) {
        try {
            if (fileName.contains("response") || fileName.contains("Response") || fileName.contains("RESPONSE")) {
                writeFile(readCsv(fileName), true);
            } else {
                writeFile(readCsv(fileName), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExtends(String className) {
        String e = "";
        if (className.toUpperCase().contains("SYNCRQ") && !className.toUpperCase().contains("BASESYNC")) {
            e = " extends Application_Model_MobileProcessor_ORCC_Ofx_Common_BaseSyncRq";
        } else if (className.toUpperCase().contains("SYNCRS") && !className.toUpperCase().contains("BASESYNC")) {
            e = " extends Application_Model_MobileProcessor_ORCC_Ofx_Common_BaseSyncRs";
        }
        return e;
    }

    public static void writeFile(List<PhpClass> phps, boolean isResponse) {
        try {

            for (PhpClass php : phps) {
                String className = getFriendlyName(php.getName());
                // Create file
                String outputPath = OUTPUT_PATH;
                if (php.getName() != null && php.getName().startsWith("ORCC")) {
                    outputPath += "ORCC/";
                }

                String subPath = "";
                String subPackageName = "";
                if (!isTop(className)) {
                    if (!outputPath.contains("Ofx/ORCC")) {
                        subPath = "Common/";
                        subPackageName = "Common_";
                    }
                }
                System.out.println(outputPath);
                System.out.println(subPath);
                if (className == null) {
                    continue;
                }
                BufferedWriter out = new BufferedWriter(new FileWriter(outputPath + subPath + className.replaceAll("ORCC.", "") + ".php", false));
                String extend = getExtends(className);
                String s = "";
                s += "<?php\n";
                if (php.getClassDesc() != null) {
                    s += "/**";
                    s += "\n\t* " + php.getClassDesc();
                    s += "\n\t*/";
                }

                String packagePrefix = "Application_Model_MobileProcessor_ORCC_Ofx_" + subPackageName;

                s += "\nclass " + packagePrefix + getFriendlyName(className) + extend + " {\n";
                for (PhpField field : php.getValues()) {
                    s += "\n\t/**";
                    s += "\n\t* " + field.getDesc() == null ? "" : field.getDesc();
                    s += "\n\t* ";
                    if (field.getValues() != null && field.getValues().size() > 0) {
                        s += "\n\t* " + field.getValues();
                        s += "\n\t* ";
                    }
                    s += "\n\t* " + field.getType() + " " + field.getValidation();
                    s += "\n\t*/";
                    s += "\n\tpublic $" + field.getName() + ";\n";
                }

                //Create empty constructor
                s += "\n\tfunction __construct() {\n";
                boolean hasArray = false;
                if (isResponse) {
                for (PhpField field : php.getValues()) {
                    if (field != null && field.getValidation() != null && field.getValidation().trim().equalsIgnoreCase("0 to many")) {
                        s += "\t\t\t$this->" + field.getName() + " = array();\n";
                        hasArray = true;
                    } else if (field != null && field.getType() != null && field.getType().equalsIgnoreCase("Aggregate")) {
                        s += "\t\t\t$this->" + field.getName() + " = new " + wrapCommon(packagePrefix, getFriendlyName(field.getName())) + "();\n";
                    }
                }
                }
                s += "\n\t}\n";

                if (hasArray) {
                    s += "\n\tpublic function addToArray($name, $value = null){";
                    for (PhpField field : php.getValues()) {
                        if (field != null && field.getValidation() != null && field.getValidation().trim().equalsIgnoreCase("0 to many")) {
                            s += "\n\t\tif ($name == '" + field.getName() + "') {";
                            s += "\n\t\t\tif (isset ( $value )) {";
                            s += "\n\t\t\t\tarray_push($this->" + field.getName() + ", $value);";
                            if (field != null && field.getType() != null && field.getType().equalsIgnoreCase("Aggregate")) {
                                s += "\n\t\t\t} else {";
                                s += "\n\t\t\t\tarray_push($this->" + field.getName() + ", new " + wrapCommon(packagePrefix, getFriendlyName(field.getName())) + "());";
                            }
                            s += "\n\t\t\t}";
                            s += "\n\t\t}";
                        }
                    }
                    s += "\n\t}";

                    s += "\n\tpublic function createNewArrayItem(){";
                    for (PhpField field : php.getValues()) {
                        if (field != null && field.getValidation() != null && field.getValidation().trim().equalsIgnoreCase("0 to many")) {
                            s += "\n\t\treturn new "+wrapCommon(packagePrefix, getFriendlyName(field.getName()))+"();";
                        }
                    }
                    s += "\n\t}";
                }


                List<String> choices = new ArrayList<String>();
                for (PhpField field : php.getValues()) {
                    if (field != null && field.getValidation() != null && field.getValidation().equalsIgnoreCase("Choice")) {
                        choices.add(field.getName());
                    }
                }

                if (!isResponse) {
                    //Create creators for sub classes
                    for (PhpField field : php.getValues()) {
                        if (field != null && field.getType() != null && field.getType().equalsIgnoreCase("Aggregate")) {
                            String methodName = "create";
                            String commentsAdditional = "";
                            if (choices.contains(field.getName())) {
                                commentsAdditional = "\n\t* <br>This method creates " + field.getName() + " and set off other choices.";
                                methodName = "choose";
                            }
//                            System.out.println(getFriendlyName(field.getName()));

                            s += "\t/**";
                            s += "\n\t* " + field.getDesc() == null ? "" : field.getDesc();
                            ;
                            s += commentsAdditional;
                            s += "\n\t*/";
                            s += "\n\tpublic function " + methodName + getFriendlyName(field.getName()) + "() {\n";
                            s += "\t\tif(!is_object($this->" + field.getName() + ")){\n";
                            s += "\t\t\t$this->" + field.getName() + " = new " + wrapCommon(packagePrefix, getFriendlyName(field.getName())) + "();\n";
                            if (choices.contains(field.getName())) {
                                for (String c : choices) {
                                    if (!c.equalsIgnoreCase(field.getName())) {
                                        s += "\t\t\t$this->" + c + " = null;\n";
                                    }
                                }
                            }
                            s += "\t\t}\n";
                            s += "\t\treturn $this->" + field.getName() + ";\n";
                            s += "\t}\n";
                        }
                    }
                }

                //Create validate() method
                s += "\n\tpublic function validate() {";
                for (PhpField field : php.getValues()) {
                    if (field != null && field.getValidation() != null && field.getValidation().equalsIgnoreCase("Mandatory")) {
                        s += "\n\t\t" + "if(!isset($this->" + field.getName() + ")){";
                        s += "\n\t\t\t" + "return \"" + className + "->" + field.getName() + "\";";
                        if (field.getType().equalsIgnoreCase("Aggregate")) {
                            s += "\n\t\t" + "}else if(method_exists($this->" + field.getName() + ", 'validate') ){";
                            s += "\n\t\t\t" + "$result =  $this->" + field.getName() + "->validate();";
                            s += "\n\t\t\t" + "if($result!=\"\"){";
                            s += "\n\t\t\t\t" + "return $result;";
                            s += "\n\t\t\t" + "}";
                        }
                        s += "\n\t\t" + "}";
                    }

                    if (field != null && field.getValidation() != null && field.getValidation().equalsIgnoreCase("Choice") && field.getType().equalsIgnoreCase("Aggregate")) {
                        if (field != null && field.getType() != null && field.getType().equalsIgnoreCase("Aggregate")) {
                            s += "\n\t\t" + "if(isset($this->" + field.getName() + ") && method_exists($this->" + field.getName() + ", 'validate')){";
                            s += "\n\t\t\t" + "$result =  $this->" + field.getName() + "->validate();";
                            s += "\n\t\t\t" + "if($result!=\"\"){";
                            s += "\n\t\t\t\t" + "return $result;";
                            s += "\n\t\t\t" + "}";
                            s += "\n\t\t" + "}";
                        }
                    }

                }
                if (choices.size() > 0) {
                    s += "\n\t\t" + "if(";
                    for (String c : choices) {
                        s += "!isset($this->" + c + ")";
                        if (choices.indexOf(c) != choices.size() - 1) {
                            s += " && ";
                        }
                    }
                    s += "){";
                    s += "\n\t\t\t" + "return \"" + className + "->";
                    for (String c : choices) {
                        s += c;
                        if (choices.indexOf(c) != choices.size() - 1) {
                            s += "|";
                        }
                    }
                    s += "\";";
                    s += "\n\t\t" + "}";
                }

//                for (PhpField field : php.getValues()) {
//                    if()
//                }
                //end of validate

                s += "\n\t\treturn \"\";";
                s += "\n\t}\n";


                s += "\t\n}\n\n?>";
                s = s.replace("ORCC.", "ORCC_");
                out.write(s);
                //Close the output stream
                out.close();
            }
        } catch (
                Exception e
                )

        {//Catch exception if any
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }

    }

    private static String wrapCommon(String prefix, String n) {
        String result = n;
        if (!isTop(result) && !prefix.contains("Common") && !n.contains("Common")) {
            result = "Common_" + result;
        }
        return (prefix + result).replaceAll("Common_ORCC", "ORCC");
    }

    private static boolean isTop(String n) {
        System.out.println("xxxxxxxxxxxxxxx" + n);
        boolean is = false;
        for (String s : Constant.top) {
            if (n != null && n.toUpperCase().contains(s) && !n.toUpperCase().contains("ORCC")) {
                System.out.println(true);
                return true;
            }
        }
        return is;
    }
}