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
	 * ���캯�� ������filesExtsIn ������ļ���׺�� ������description ���ļ���׺��������
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
	 * ���ݺ�׺����ļ��Ƿ�������ʾ��
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
	 * �����׺�� in ��ͬ���򷵻�true�����򷵻�false�� ������ in ����ĺ�׺���� ������ �����׺�� in
	 * ��ͬ���򷵻�true�����򷵻�false��
	 */
	private boolean check(String in) {
		return extension.equalsIgnoreCase(in);
	}

	/**
	 * �����ļ������ļ���׺��������
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * ��ȡ�ļ��ĺ�׺�� file Ҫ���˵��ļ��� �ļ���׺��
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