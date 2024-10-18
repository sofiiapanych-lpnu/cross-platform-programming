package com.example.task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    String text;
    String fileName = "input.txt";

    public Task2(String fileName) {
        this.fileName = fileName;
    }

    public boolean readTextFromFile() {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            text = contentBuilder.toString();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> SelectAllTrio() {
        List<String> trios = new ArrayList<>();

        String regex = "[А-ЩЬЮЯЄІЇҐ][А-ЩЬЮЯЄІЇҐа-щьюяєіїґ']*\\.*\\s+[А-ЩЬЮЯЄІЇҐ][А-ЩЬЮЯЄІЇҐа-щьюяєіїґ']*\\.*" +
                "\\s+[А-ЩЬЮЯЄІЇҐ][А-ЩЬЮЯЄІЇҐа-щьюяєіїґ']*\\.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println("Found: " + matcher.group());
            trios.add(matcher.group());
        }

        return trios;
    }


}