package com.github.filecompress;


import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<Character, Integer> ret = FileHelper.countFrequencyOfEachCharacterInFile(new File("C:\\Users\\ASUS\\Desktop\\hello\\file-compress\\src\\main\\resources\\Harry Potter and the Sorcerer's Stone.txt"));
        for (Map.Entry<Character, Integer> entry : ret.entrySet()) {
            // System.out.println("key : " + entry.getKey() + " val : " + entry.getValue());
        }

        // key 作为 e,value 作为 weight 将 Map 转换为节点
        HuffmanTree huffmanTree = new HuffmanTree();
        List<Node> nodes = HuffmanTree.mapToListNode(ret);
        // System.out.println(nodes);
        Node root = HuffmanTree.createHuffmanTree(nodes);
        Map<Character, String> map = huffmanTree.treeToHuffmanCode(root);
        String s = FileHelper.readFile(new File("C:\\Users\\ASUS\\Desktop\\hello\\file-compress\\src\\main\\resources\\Harry Potter and the Sorcerer's Stone.txt"), map);
        System.out.println(s);
        // System.out.println(map);
        FileHelper.compressFile(s);
        String string = FileHelper.readHuffmanFileToString();
        // System.out.println(string);
    }
}
