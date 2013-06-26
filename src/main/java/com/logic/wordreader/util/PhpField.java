package com.logic.wordreader.util;

import java.util.ArrayList;
import java.util.List;

public class PhpField {
    private String className;
    private String classDesc;
    private String name;
    private String type;
    private String validation;
    private String desc;
    private List<String> values = new ArrayList<String>();
//    private  boolean isObject;
//    private  boolean isChoice;
//    private  boolean isNumber;
//    private  int getMaxLen;


    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(String values) {
        String[] array = values.split(",");
        for (String s : array) {
            this.values.add(s);
        }
    }

    @Override
    public String toString() {
        return "PhpField{" +
                "className='" + className + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", validation='" + validation + '\'' +
                ", desc='" + desc + '\'' +
                ", values=" + values +
                "}\n";
    }
}