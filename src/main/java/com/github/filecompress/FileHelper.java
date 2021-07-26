package com.github.filecompress;

import javafx.beans.binding.StringBinding;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHelper {

    // 每次传进去的字符串都是8个字符长度，刚好能够表示一个byte
    public static byte encode(String s) {
        int a = 0;
        for (int i = 0; i < 8; i++) {
            char ch = s.charAt(i);
            a = a << 1;
            if (ch == '1') {
                a = a | 0x1;
            }
        }
        return (byte) a;
    }


    public static String readHuffmanFileToString() throws Exception {
        File f = new File("test.huffman");
        FileInputStream fs = new FileInputStream(f);
        byte[] buffer = new byte[1024];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        byte z = (byte) fs.read();
        while ((len = fs.read(buffer)) != -1) {
            for (int i = 0; i < len; i++) {
                sb.append(decode(buffer[i]));
            }
        }
        fs.close();
        return sb.substring(0, sb.length() - z);
    }

    // 上一步的逆操作
    public static String decode(byte b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append((b & (0x1 << (7 - i))) > 0 ? '1' : '0');
        }
        return sb.toString();
    }

    public static void compressFile(String s) throws Exception {
        writeHuffmanCodeToFile(s);
    }

    public static void writeHuffmanCodeToFile(String s) throws Exception {
        // 因为huffman编码字符串不总是8个字符的倍数，那么我们不足8时补0，并记录我们到底补了几个。
        // 我们把补位数放在文件的第一个字节
        int z = 8 - s.length() % 8;
        if (z == 8) {
            z = 0;
        }
        byte[] buffer = new byte[1024];
        buffer[0] = (byte) z;
        int pos = 1, nBytes = (int) (Math.ceil(s.length() / ((double) 8)));
        File f = new File("test.huffman");
        FileOutputStream os = new FileOutputStream(f);
        for (int i = 0; i < nBytes; i++) {
            String ss;
            if (s.length() >= (i + 1) * 8) {
                ss = s.substring(i * 8, (i + 1) * 8);
            } else {
                ss = s.substring(i * 8);
                while (ss.length() < 8) {
                    ss = new StringBuilder(ss).append('0').toString();
                }
            }
            buffer[pos] = encode(ss);
            pos++;
            if (pos == 1024) {
                os.write(buffer);
                pos = 0;
            }
        }
        if (pos > 0) {
            os.write(buffer, 0, pos);
        }
        os.close();
    }

    public static Map<Character, Integer> countFrequencyOfEachCharacterInFile(File file) {
        Map<Character, Integer> map = new HashMap<>();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1];
            while (bis.read(buffer) != -1) {
                char c = (char) buffer[0];
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String readFile(File file, Map<Character, String> map) {
        StringBuilder sb = new StringBuilder();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1];
            while (bis.read(buffer) != -1) {
                char c = (char) buffer[0];
                if (map.containsKey(c)) {
                    sb.append(map.get(c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void decompressFile(File compressFile,File decompressFile){

    }

    public static void writeFile(String s, Map<Character, String> map, File compressFile) {
        try (FileOutputStream fos = new FileOutputStream(compressFile)) {
            int len = s.length();//获取01串的长度
            String temp = ""; //临时存放段的01字符串
            for (int i = 0; i < len; i++) {
                temp += s.charAt(i);
                if (map.containsKey(temp)) {
                    fos.write(map.get(temp).charAt(0)); //一个字符的字符串转字符然后写出
                    temp = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
