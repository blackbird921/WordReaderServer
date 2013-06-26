package com.logic.wordreader.util;

import java.util.ArrayList;
import java.util.List;

public class PhpClass {
    private String name;
    private String classDesc;
    private List<PhpField> values = new ArrayList<PhpField>();

    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhpField> getValues() {
        return values;
    }

    public void setValues(List<PhpField> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return name + ',' + values+'\n';
    }
}