package com.retirement.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.io.FileOutputStream;



public class FileIO {
	@SuppressWarnings("unused")
	public static BufferedReader getReader(String name) {
		BufferedReader in = null;
		try {
			File aFile = new File(name);
			in = new BufferedReader(new FileReader(aFile));
			if (aFile == null) {
				throw new IllegalArgumentException("File should not be null.");
			}
			if (!aFile.exists()) {
				throw new FileNotFoundException("File does not exist: " + aFile);
			}
			if (!aFile.isFile()) {
				throw new IllegalArgumentException(
						"Should not be a directory: " + aFile);
			}
		} catch (FileNotFoundException e) {
			System.out.println(name + " file not found!");
		}
		return in;
	}
	public static boolean fileExists(String name){
		boolean bResult=false;
		File aFile = new File(name);
		if (aFile.exists()) bResult=true; 
		return bResult;
	}
	
	public static String readLine(BufferedReader in) {
		String[] data;
		String line = "";
		try {
			line = in.readLine();
		} catch (IOException e) {
			System.out.println("I/O Error");
			System.exit(0);
		}
		if (line == null)
			return null;
		else {
			data = line.split("\t");
			return data[0];
		}

	}

	public static void writeFile(File fname, StringBuffer sb) throws IOException {
		FileWriter fw = new FileWriter(fname);
		BufferedWriter bwr = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bwr);
		pw.print(sb);

		pw.close();
		System.out.println("Content of String written to File.");

	}
	public static ArrayList<String> listDIR(String strWorkDir) {
		return FileIO.listDIR(strWorkDir, ".");
	}
	public static ArrayList<String> listDIR(String strPath, String strMask){
		ArrayList<String> results = new ArrayList<String>();


		File[] files = new File(strPath).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 

		for (File file : files) {
		    if (file.isFile()&& (file.getName().contains(strMask))) {
		        results.add(file.getName());
		    }
		}
		return results;
	}
	@SuppressWarnings({ "resource" })
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }
	 
	    FileChannel origin = null;
	    FileChannel destination = null;
	    try {
	        origin = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	 
	        long count = 0;
	        long size = origin.size();              
	        while((count += destination.transferFrom(origin, count, size-count))<size);
	    }
	    finally {
	        if(origin != null) {
	            origin.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}


}
