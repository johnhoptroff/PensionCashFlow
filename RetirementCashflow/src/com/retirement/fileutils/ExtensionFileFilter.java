package com.retirement.fileutils;

import java.io.File;
import javax.swing.filechooser.*;

public class ExtensionFileFilter extends FileFilter {
	String description;
	String extensions[];

	public ExtensionFileFilter(String description, String extension) {
		this(description, new String[] { extension });
	}

	public ExtensionFileFilter(String description, String extensions[]) {
		if (description == null) {
			this.description = extensions[0] + "{ " + extensions.length + "} ";
		} else {
			this.description = description;
		}
		// convert array to lower case
		// Don't alter original entries
		this.extensions = (String[]) extensions.clone();
		toLower(this.extensions);
	}

	private void toLower(String array[]) {
		for (int i = 0, n = array.length; i < n; i++) {
			array[i] = array[i].toLowerCase();
		}
	}

	@Override
	public String getDescription() {
		return description;
	}
    // ignore case, always accept directories
	//Character before extension must be a full stop	
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}else {
			String path = file.getAbsolutePath().toLowerCase();
			for (int i=0, n = extensions.length;i < n;i++){
				String extension = extensions[i];
				if ((path.endsWith(extension) &&
						(path.charAt(path.length()-extension.length()-1)) == '.')) {
					return true;
				}
			}
		}
		return false;
	}

}
