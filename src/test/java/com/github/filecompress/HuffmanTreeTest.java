package com.github.filecompress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTreeTest {

    @Test
    void createHuffmanTree() {
    }

    @Test
    void testCreateHuffmanTree() {
    }

    @Test
    void getWPL() {
    }

    @Test
    void encoding() {
    }

    @Test
    void huffmanTreeNodeDataToCode() {
        File testFile = new File("C:\\Users\\ASUS\\Desktop\\hello\\file-compress\\src\\main\\resources\\test.txt");
        List<Node> list = FileHelper.fileToHuffmanNodeList(testFile);
        Node root = HuffmanTree.createHuffmanTree(list);
        Map<Character, String> map = HuffmanTree.huffmanTreeNodeDataToCode(root);
        String s = FileHelper.fileToCodeString(testFile, map);
        // ABBACCCAAABBDDE
        // 111010110000001111111010011011010
        Assertions.assertEquals(s, "111010110000001111111010011011010");
        FileHelper.writeHuffmanCodeToFile(s);
        String s2 = FileHelper.readHuffmanFileToString();
        Assertions.assertEquals(s, s2);
    }
}