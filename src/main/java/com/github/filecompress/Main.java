package com.github.filecompress;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Character, Integer> ret = FileHelper.countFrequencyOfEachCharacterInFile(new File("C:\\Users\\ASUS\\Desktop\\my-book\\file-compress\\src\\main\\resources\\Harry Potter and the Sorcerer's Stone.txt"));
        for (Map.Entry<Character, Integer> entry : ret.entrySet()) {
            System.out.println("key : " + entry.getKey() + " val : " + entry.getValue());
        }

    }


}
