package com.github.filecompress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileHelper {

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
}
