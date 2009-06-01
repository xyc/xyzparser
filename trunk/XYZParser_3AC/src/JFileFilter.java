/*
 * @(#)JSFileFilte.java	1.01 09/03/17
 */

import java.io.*;

import javax.swing.filechooser.FileFilter;

/**
 * Sample JavaCC compiler
 * 
 * @version 1.01 03/17/09
 */

public class JFileFilter extends FileFilter {
	private static final boolean ONE = true;
	private String fileExt;
	private String[] fileExts;
	private boolean type = false;
	private String description;
	private int length;
	private String extension;

	/**
	 * 构造函数 参数：filesExtsIn 允许的文件后缀。 参数：description 对文件后缀的描述。
	 */
	public JFileFilter(String[] filesExtsIn, String description) {
		if (filesExtsIn.length == 1) {
			type = ONE;
			fileExt = filesExtsIn[0];
		} else {
			fileExts = filesExtsIn;
			length = fileExts.length;
		}
		this.description = description;
	}

	/**
	 * 根据后缀检查文件是否允许显示。
	 */
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		extension = getExtension(f);
		if (extension != null) {
			if (type)
				return check(fileExt);
			else {
				for (int i = 0; i < length; i++) {
					if (check(fileExts[i]))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * 如果后缀和 in 相同，则返回true；否则返回false。 参数： in 允许的后缀名。 参数： 如果后缀和 in
	 * 相同，则返回true；否则返回false。
	 */
	private boolean check(String in) {
		return extension.equalsIgnoreCase(in);
	}

	/**
	 * 返回文件关于文件后缀的描述。
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 获取文件的后缀。 file 要过滤的文件。 文件后缀。
	 */
	private String getExtension(File file) {
		String filename = file.getName();
		int length = filename.length();
		int i = filename.lastIndexOf('.');
		if (i > 0 && i < length - 1)
			return filename.substring(i + 1).toLowerCase();
		return null;
	}
}