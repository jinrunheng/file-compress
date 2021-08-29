package com.github.filecompress;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        File testFile = new File("src/main/resources/Harry Potter and the Sorcerer's Stone.txt");
        List<Node> list = FileHelper.fileToHuffmanNodeList(testFile);
        Node root = HuffmanTree.createHuffmanTree(list);
        Map<Character, String> map = HuffmanTree.huffmanTreeNodeDataToCode(root);
        String s = FileHelper.fileToCodeString(testFile, map);

        FileHelper.writeHuffmanCodeToFile(s);
        String s2 = FileHelper.readHuffmanFileToString();

        Map<String, Character> swapMap = FileHelper.swapMapKV(map);
        File newFile = new File("src/main/resources/Harry Potter and the Sorcerer's Stone2.txt");
        FileHelper.codeStringToFile(newFile,s2,swapMap);
    }
}
