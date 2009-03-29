/*
 * @(#)JavaViewFactory.java	1.01 09/03/17
 */

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * Sample JavaCC compiler
 * 
 * @author Atlantis Mao
 * @version 1.01 03/17/09
 */

public class JavaViewFactory implements ViewFactory {
	public JavaViewFactory() {
	}

	public View create(Element elem) {
		return new JavaView(elem);
	}
}
