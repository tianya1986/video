package com.wolf.cs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MIME_Demo {
	public static void main(String[] args) throws Exception {
		System.out.println(getContentType("F:/test.png"));
		System.out.println(getContentType("F:/2.doc"));
		System.out.println(getContentType("F:/2.csv"));
		System.out.println(getContentType("F:/LiveUpdate.exe"));
		System.out.println(getContentType("F:/1.txt"));
		System.out.println(getContentType("F:/demo.jpg"));
	}
	
	public static String getContentType(String filename){
		String type = null;
		Path path = Paths.get(filename);
		try {
			type = Files.probeContentType(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return type;
	}
}
