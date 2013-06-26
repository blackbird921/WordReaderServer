package com.logic.wordreader.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by IntelliJ IDEA.
 * User: w
 * Date: 5/18/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class PinYinTest {
    public static String getPiYin(String src) {

        HanyuPinyinOutputFormat outFormat = new HanyuPinyinOutputFormat();
        outFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        outFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            return PinyinHelper.toHanyuPinyinString(src, outFormat, ",");
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
            return src;
        }
    }

    public static void main(String[] args) {
        System.out.println(getPiYin("今天"));
        System.out.println(getPiYin("一见钟情"));
        System.out.println(getPiYin("一百分"));
        System.out.println(getPiYin("曾经"));
        System.out.println(getPiYin("曾国藩"));

    }
}
