/*
 * @(#)JavaView.java	1.01 09/03/17
 */

import java.awt.*;

import javax.swing.event.DocumentEvent;
import javax.swing.text.*;

/**
 * Sample JavaCC compiler
 * 
 * @author Atlantis Mao
 * @version 1.01 03/17/09
 */

public class JavaView extends PlainView {
	public JavaView(Element elem) {
		super(elem);
	}

	/**
	 * Undefined changedUpdate(DocumentEvent e, Shape a, ViewFactory f)
	 */
	 
	/**
	 * Undefined drawSelectedText(Graphics g, int x, int y, int p0, int p1)
	 */
	
	protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1)
			throws BadLocationException {
		
		Document doc = getDocument();
		Segment segment = new Segment();
		Segment token = getLineBuffer();
		doc.getText(p0, p1 - p0, segment);
		int count = p1 - p0;
		int left = 0;
		int state = 0;
		int elementIndex = doc.getDefaultRootElement().getElementIndex(p0);
		AttributeSet lineAttributes = doc.getDefaultRootElement().getElement(
				elementIndex).getAttributes();
		if (lineAttributes.isDefined("inComment"))
			state = 4;
		for (int i = 0; i < count; i++) {
			if (state == 0) {
				if (Character.isLetter(segment.array[i + segment.offset])
						&& Character.isLowerCase(segment.array[i
								+ segment.offset])) {
					g.setColor(textColor);
					g.setFont(new Font("Courier New", Font.PLAIN, 14));
					
					doc.getText(p0 + left, i - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i;
					state = 1;
				} else if (segment.array[i + segment.offset] == '/') {
					g.setColor(textColor);
					g.setFont(new Font("Courier New", Font.PLAIN, 14));
					doc.getText(p0 + left, i - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i;
					state = 2;
				} else if (segment.array[i + segment.offset] == '"') {
					g.setColor(textColor);
					g.setFont(new Font("Courier New", Font.PLAIN, 14));
					doc.getText(p0 + left, i - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i;
					state = 3;
				}
				continue;
			}
			if (state == 1) {
				if (!Character.isLetter(segment.array[i + segment.offset])) {
					doc.getText(p0 + left, i - left, token);
					if (Keywords.isKeyword(token)) {
						g.setColor(keywordColor);
						g.setFont(new Font("Courier New", Font.BOLD, 14));
					}
						
					else {
						g.setColor(textColor);
						g.setFont(new Font("Courier New", Font.PLAIN, 14));
					}
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i;
					state = 0;
					if (segment.array[i + segment.offset] == '/')
						state = 2;
					else if (segment.array[i + segment.offset] == '"')
						state = 3;
				}
				continue;
			}
			if (state == 2) {
				if (segment.array[i + segment.offset] == '/')
					break;
				if (segment.array[i + segment.offset] == '*')
					state = 4;
				else
					state = 0;
			} else if (state == 4) {
				if (i > 0 && segment.array[i + segment.offset] == '/'
						&& segment.array[(i + segment.offset) - 1] == '*') {
					doc.getText(p0 + left, (i + 1) - left, token);
					g.setColor(commentColor);
					g.setFont(new Font("Courier New", Font.PLAIN, 14));
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i + 1;
					state = 0;
				}
			} else if (state == 3 && segment.array[i + segment.offset] == '"') {
				doc.getText(p0 + left, (i + 1) - left, token);
				g.setColor(stringColor);
				g.setFont(new Font("Courier New", Font.PLAIN, 14));
				x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
				left = i + 1;
				state = 0;
			}
		}
		doc.getText(p0 + left, p1 - p0 - left, token);
		if (state == 1) {
			if (Keywords.isKeyword(token)) {
				g.setColor(keywordColor);
				g.setFont(new Font("Courier New", Font.BOLD, 14));
			}
			else {
				g.setColor(textColor);
				g.setFont(new Font("Courier New", Font.PLAIN, 14));
			}
		} else if (state == 3) {
			g.setColor(stringColor);
			g.setFont(new Font("Courier New", Font.PLAIN, 14));
		}
		else if (state == 2 && p1 - p0 - left > 1) {
			g.setColor(commentColor);
			g.setFont(new Font("Courier New", Font.PLAIN, 14));
		}
		else if (state == 4) {
			g.setColor(commentColor);
			g.setFont(new Font("Courier New", Font.PLAIN, 14));
		}
		else {
			g.setColor(textColor);
			g.setFont(new Font("Courier New", Font.PLAIN, 14));
		}
		x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
		return x;
	}

	protected int getTabSize() {
		return 4;
	}

	/**
	 * Undefined insertUpdate(DocumentEvent e, Shape a, ViewFactory f)
	 */
	 
	/**
	 * Undefined removeUpdate(DocumentEvent e, Shape a, ViewFactory f)
	 */
	
	public static final Color commentColor = new Color(63, 127, 95);
	public static final Color stringColor = new Color(42, 0, 255);
	public static final Color variableColor = new Color(42, 0, 255);
	public static final Color keywordColor = new Color(140, 0, 60);
	public static final Color textColor = new Color(0, 0, 0);
	public static final int TEXT = 0;
	public static final int KEYWORD = 1;
	public static final int COMMENT = 2;
	public static final int STRING = 3;
	public static final int VARIABLE = 5;
	public static final int MULTILINECOMMENT = 4;
}