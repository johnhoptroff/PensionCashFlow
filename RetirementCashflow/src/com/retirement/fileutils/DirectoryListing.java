package com.retirement.fileutils;

import java.io.File;
import java.util.ArrayList;

public class DirectoryListing {

	public static ArrayList<String> getFiles(String strWorkDir) {
		ArrayList<String> alFiles = new ArrayList<String>();
		File f = null;
		String[] paths;

		try {

			// create new file
			f = new File(strWorkDir);
			// array of files and directory
			paths = f.list();

			// for each name in the path array
			for (String path : paths) {
				if (path.contains(".dat")) //change this to the extension needed.
					alFiles.add(path);
			}

		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
		return alFiles;
	}

}
