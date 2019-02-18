package org.tony.chenjy.fulltext_search_service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.*;

public class TikaTest {
    public static void main(String[] args) throws IOException, TikaException {
        // Assume sample.txt is in your current directory
        File file = new File("E:\\data\\Project\\git\\FullTextSearchService\\src\\test\\resources\\attachment\\Jane Eyre - en.docx");

        // Instantiating Tika facade class
        Tika tika = new Tika();
        String filecontent = tika.parseToString(file);
        System.out.println("Extracted Content: " + filecontent.substring(100));
    }
}
