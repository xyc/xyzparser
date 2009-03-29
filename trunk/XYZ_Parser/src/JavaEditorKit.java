/*
 * @(#)JavaEditorKit.java	1.01 09/03/17
 */

import java.io.*;
import javax.swing.Action;
import javax.swing.text.*;

/**
 * Sample JavaCC compiler
 * 
 * @author Atlantis Mao
 * @version 1.01 03/17/09
 */

public class JavaEditorKit extends DefaultEditorKit {
	public JavaEditorKit() {
	}

	public Object clone() {
		return super.clone();
	}

	public Caret createCaret() {
		return super.createCaret();
	}

	public Document createDefaultDocument() {
		JavaDocument doc = new JavaDocument();
		return doc;
	}

	public Action[] getActions() {
		return super.getActions();
	}

	public String getContentType() {
		return "text/java";
	}

	public ViewFactory getViewFactory() {
		return new JavaViewFactory();
	}

	public void read(InputStream in, Document doc, int pos) throws IOException,
			BadLocationException {
		super.read(in, doc, pos);
	}

	public void read(Reader in, Document doc, int pos) throws IOException,
			BadLocationException {
		super.read(in, doc, pos);
	}

	public void write(OutputStream out, Document doc, int pos, int len)
			throws IOException, BadLocationException {
		super.write(out, doc, pos, len);
	}

	public void write(Writer out, Document doc, int pos, int len)
			throws IOException, BadLocationException {
		super.write(out, doc, pos, len);
	}
}
