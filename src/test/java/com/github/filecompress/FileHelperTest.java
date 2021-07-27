package com.github.filecompress;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {

    @Test
    void readHuffmanFileToString() {
    }

    @Test
    void writeHuffmanCodeToFile() {
    }

    @Test
    void fileToHuffmanNodeList() {
        File testFile = new File("C:\\Users\\ASUS\\Desktop\\hello\\file-compress\\src\\main\\resources\\test.txt");
        List<Node> nodes = FileHelper.fileToHuffmanNodeList(testFile);
        System.out.println(nodes);
    }
}