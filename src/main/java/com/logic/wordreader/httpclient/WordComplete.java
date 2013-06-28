package com.logic.wordreader.httpclient;

/**
 * Created by IntelliJ IDEA.
 * User: w
 * Date: 6/29/13
 * Time: 2:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class WordComplete {
    private int id;
    private String chinese;
    private String pinyinUtf8="";
    private String pinyinNumber="";
    private String bookKey = "";
    private int grade;
    private int gradeTerm;
    private int chapter;
    private int page;
    private int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getPinyinUtf8() {
        return pinyinUtf8;
    }

    public void setPinyinUtf8(String pinyinUtf8) {
        this.pinyinUtf8 = pinyinUtf8;
    }

    public String getPinyinNumber() {
        return pinyinNumber;
    }

    public void setPinyinNumber(String pinyinNumber) {
        this.pinyinNumber = pinyinNumber;
    }

    public String getBookKey() {
        return bookKey;
    }

    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGradeTerm() {
        return gradeTerm;
    }

    public void setGradeTerm(int gradeTerm) {
        this.gradeTerm = gradeTerm;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return
                chinese +
                        "," + pinyinUtf8 +
                        "," + pinyinNumber +
                        "," + bookKey +
                        "," + grade +
                        "," + gradeTerm +
                        "," + chapter +
                        "," + page +
                        "," + level;
    }
}
