package com.github.filecompress;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHelper {

    private static byte encode(String s) {
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

    /**
     * 读取 Huffman 文件，将二进制文件转换为 Huffman 编码的字符串格式
     *
     * @return
     */
    public static String readHuffmanFileToString() {
        File f = new File("test.huffman");
        try (FileInputStream fs = new FileInputStream(f)) {
            byte[] buffer = new byte[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            byte z = (byte) fs.read();
            while ((len = fs.read(buffer)) != -1) {
                for (int i = 0; i < len; i++) {
                    sb.append(decode(buffer[i]));
                }
            }
            return sb.substring(0, sb.length() - z);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 上一步的逆操作
    private static String decode(byte b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append((b & (0x1 << (7 - i))) > 0 ? '1' : '0');
        }
        return sb.toString();
    }

    /**
     * 将文件 file 转化为 Huffman Code 的字符串形式
     *
     * @param file
     * @param map
     * @return
     */
    public static String fileToCodeString(File file, Map<Character, String> map) {
        StringBuilder sb = new StringBuilder();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1];
            while (bis.read(buffer) != -1) {
                char c = (char) buffer[0];
                sb.append(map.get(c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 将 Huffman 编码对应的字符串格式转换为二进制存储到文件中
     *
     * @param s
     */
    public static void writeHuffmanCodeToFile(String s) {
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
        try (FileOutputStream os = new FileOutputStream(f)) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件内容转化为一个存储所有节点的列表
     *
     * @param file
     * @return
     */
    public static List<Node> fileToHuffmanNodeList(File file) {
        // AAAAABBBBCCCDDE
        Map<Character, Integer> map = countFrequencyOfEachCharInFile(file);
        List<Node> list = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            list.add(new Node(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    private static Map<Character, Integer> countFrequencyOfEachCharInFile(File file) {
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
}
