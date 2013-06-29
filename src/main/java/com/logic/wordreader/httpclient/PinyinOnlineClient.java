package com.logic.wordreader.httpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.logic.wordreader.util.ChineseLibrary;
import com.logic.wordreader.util.PhpClass;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class PinyinOnlineClient {

    public static List<String> readFile(String fileName, String delim, int startTrim) throws Exception {
        BufferedReader fileBuffer = new BufferedReader(new FileReader(fileName));

        String dataRow = fileBuffer.readLine(); // Read first line.
        StringBuilder sb = new StringBuilder(dataRow);
        System.out.println(dataRow);
        while (dataRow != null) {
            dataRow = fileBuffer.readLine(); // Read first line.
            if (dataRow != null) {
                sb.append(dataRow);
                System.out.println(dataRow);
            }
        }
        List<String> words = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(sb.toString().substring(startTrim, sb.toString().length()), delim);
        System.out.println(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken());
        }
        return words;
    }

    public static String getPinyin(String words) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        String responseBody = "";
        try {
            HttpGet httpPost = new HttpGet("http://dianzu.duapp.com/?app=app.pinyin.index&dyz=1&con=" + words);

            System.out.println("executing request " + httpPost.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }

        return responseBody;
    }

    public static void writeFile(String fileName, String content) throws Exception {
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));
        out.write(content);
        //Close the output stream
        out.close();
    }

    public static String getPinyinByBatch(List<String> words, int batchSize, BookInfo bookInfo) throws Exception {
        System.out.println(words);
        String wordStrWithPy = "chinese,pinyinUtf8,pinyinNumber,bookKey,grade,gradeTerm,chapter,page,level(1-5)\n";
        for (int j = 0; j < words.size(); j += batchSize) {
            String wordStr = "";
            for (int i = j; i < j + batchSize && i < words.size(); i++) {
                if (!words.get(i).equalsIgnoreCase("奶奶")) {
                    wordStr += words.get(i) + ",";
                }
            }
            System.out.println(wordStr);
            String pinyinStr = getPinyin(wordStr).replaceAll("ok:", "");
//            String x = "ok:bǎo zhòng ,bǎo hù ,hù shì ,hù lǐ ,chéng xiāng ,cháng chéng ,chéng qiáng ,chéng shì ,shì mín ,dū shì ,qì chē ,qì shuǐ ,qì yóu ,xiān huā ,xīn xiān ,xiān hóng ,yùn dòng ,yùn yòng ,xìng yùn ,xiāo shī , ";
//            String pinyinStr = x.replaceAll("ok:", "");
            List<WordComplete> wordCompleteList = getWordComplete(wordStr, pinyinStr, bookInfo);
            for (WordComplete c : wordCompleteList) {
                wordStrWithPy += c.toString() + "\n";
            }
        }

        return wordStrWithPy;
    }

    public static List<WordComplete> getWordComplete(String wordStr, String pinyinStr, BookInfo bookInfo) {
        List<WordComplete> wordCompletes = new ArrayList<WordComplete>();

        String[] words = wordStr.split(",");
        String[] pinyins = pinyinStr.split(",");
        for (int i = 0; i < words.length; i++) {
            WordComplete wordComplete = new WordComplete();
            wordComplete.setChinese(words[i]);
            wordComplete.setPinyinUtf8(pinyins[i].trim());
            String pinyinNumber = "";
            for(String s: pinyins[i].split("\\s")){
                pinyinNumber+= ChineseLibrary.pinyinUtf8ToNumber(s)+" ";
            }
            wordComplete.setPinyinNumber(pinyinNumber.trim());
            wordComplete.setBookKey(bookInfo.getBookKey());
            wordComplete.setGrade(bookInfo.getGrade());
            wordComplete.setGradeTerm(bookInfo.getGradeTerm());
            wordCompletes.add(wordComplete);
        }

        return wordCompletes;
    }

    public static void main(String[] args) throws Exception {
        List<String> words = readFile("D:\\prj-mobile\\WordReaderServer\\docs\\一年级第二学期识字卡片.txt", " ", 1);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookKey("沪教一年级第二学期");
        bookInfo.setGrade(1);
        bookInfo.setGradeTerm(2);
        String wordStrWithPy = getPinyinByBatch(words, 40, bookInfo);
        writeFile("D:\\prj-mobile\\WordReaderServer\\docs\\一年级第二学期识字卡片_with_pinyin.txt", wordStrWithPy);
//        getPinyin("中国,国家");
    }

}
