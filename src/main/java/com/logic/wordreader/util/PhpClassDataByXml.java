package com.logic.wordreader.util;

public class PhpClassDataByXml {
    public String name;
    public Boolean isObject;
    public Boolean isArray;

    public PhpClassDataByXml(String name, Boolean object, Boolean isArray) {
        this.name = name;
        this.isObject = object;
        this.isArray = isArray;
    }
}