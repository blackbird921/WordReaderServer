package com.logic.wordreader.util;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: w
 * Date: 6/29/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class AudioLibrary {
    static String PATH = "D:\\prj-mobile\\WordReaderServer\\docs\\audio_man\\";
    
    public static void main(String[] args) {
        File file = new File(PATH+"all_pinyin_with_tones_1170-0001.mp3");
        file.renameTo(new File(PATH + "all_pinyin_with_tones_1170-0.mp3"));
    }
}
