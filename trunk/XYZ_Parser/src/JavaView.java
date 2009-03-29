/*
 * @(#)JavaView.java	1.01 09/03/17
 */

import java.awt.*;

import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.text.*;
import parser.XYZParserConstants;
import parser.XYZParser;
import parser.Token;
/**
 * Sample JavaCC compiler
 * 
 * @author Atlantis Mao
 * @version 1.01 03/17/09
 */

public class JavaView extends PlainView {
	//public int text[][] = {{2,5,1},{7,9,2},{14,95,1}};
	public int key_num = 3;
	
	public JavaView(Element elem) {
		super(elem);
	}

    /**
     * Get the color of a token.
     */
    private Color getTokenColor(Token t){
        switch(t.kind){
            case XYZParserConstants.KEYWORD:
                return keywordColor;
            case XYZParserConstants.COMMENT:
                return commentColor;
            case XYZParserConstants.INTLITERAL:
            case XYZParserConstants.REALLITERAL:
            	return variableColor;
            case XYZParserConstants.OPER:
            	return operColor;
        }
        return textColor;
    }

	/**
	 * Undefined changedUpdate(DocumentEvent e, Shape a, ViewFactory f)
	 */
	 
	/**
	 * Undefined drawSelectedText(Graphics g, int x, int y, int p0, int p1)
	 */
	
	protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1)
			throws BadLocationException {
        //∏ﬂ¡¡token
		if(p1==0)
			return x;
		ArrayList<Token> tokens = XYZParser.getTokens();
		key_num = tokens.size();

		Document doc = getDocument();
		Segment token = getLineBuffer();
		Segment segment = new Segment();
		doc.getText(p0, p1 - p0, segment);

		int key_start;
		boolean isLeft = true;
		boolean key_end = false;
		int i = 0;
		/*find the start*/
		while(true){
			if(isLeft){
				if(tokens.get(i).beginOffset<=p0){
					isLeft = false;
					continue;
				}
				else{
					break;
				}
			}
			else{
				if(tokens.get(i).endOffset<p0){
					isLeft = true;
					if(i+1<=key_num-1){
						i++;
						continue;
					}
					else{
						key_end = true;
						break;
					}
				}
				else{
					break;
				}
			}
		}
		key_start = i;
		i = p0;
		int key_now = key_start;
		while(true){
			if(key_end){
				doc.getText(i, p1 - i, token);
				g.setColor(colors[0]);
				x = Utilities.drawTabbedText(token, x, y, g, this, i);
				return x;
			}
			if(isLeft){
				if(tokens.get(key_now).beginOffset < p1){
					doc.getText(i, tokens.get(key_now).beginOffset - i, token);
					g.setColor(colors[0]);
					x = Utilities.drawTabbedText(token, x, y, g, this, i);
					i = tokens.get(key_now).beginOffset;
					isLeft = false;
					continue;
				}
				else{
					doc.getText(i, p1 - i, token);
					g.setColor(colors[0]);
					x = Utilities.drawTabbedText(token, x, y, g, this, i);
					key_start = key_now;
					isLeft = true;
					return x;
				}	
			}
			else{
				if(tokens.get(key_now).endOffset < p1){
					doc.getText(i, tokens.get(key_now).endOffset - i + 1, token);
					g.setColor(getTokenColor(tokens.get(key_now)));
					x = Utilities.drawTabbedText(token, x, y, g, this, i);
					i = tokens.get(key_now).endOffset + 1;
					isLeft = true;
					if(key_now < key_num - 1)
						key_now++;
					else
						key_end = true;
					continue;
				}
				else{
					doc.getText(i, p1 - i, token);
					g.setColor(getTokenColor(tokens.get(key_now)));
					x = Utilities.drawTabbedText(token, x, y, g, this, i);
					isLeft = false;
					key_start = key_now;
					return x;
				}
			}
		}
		
		/*if(key_start == key_end){
			if(text[key_start][0] > p0){
				left = text[key_start][0] - p0;
				g.setColor(colors[0]);
				doc.getText(p0, left, token);
				x = Utilities.drawTabbedText(token, x, y, g, this, p0);
				
				if(text[i][1]<=p1 && i == key_num - 1){
					g.setColor(colors[text[key_start][2]]);
					doc.getText(p0 + left, text[key_start][1] - text[key_start][0] + 1, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left); 
					left = text[key_start][1] - text[key_start][0] + 1;

					g.setColor(colors[0]);
					doc.getText(p0 + left, p1 - p0 - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left); 
					return x;
				}
				else{
					g.setColor(colors[text[key_start][2]]);
					doc.getText(p0 + left, p1 - p0 - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left); 
					return x;
				}
			}
			else{
				if(i == key_num - 1){
					g.setColor(colors[text[key_start][2]]);
					doc.getText(p0 + left, text[key_start][1] - p0 + 1, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left); 
					left = text[key_start][1] - p0 + 1;

					g.setColor(colors[0]);
					doc.getText(p0 + left, p1 - p0 - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left); 
					return x;
				}
				else{
					g.setColor(colors[text[key_start][2]]);
					doc.getText(p0, p1 - p0, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0);
					return x;
				}
			}
		}
		if(text[key_start][0] > p0){
			left = text[key_start][0] - p0;
			g.setColor(colors[0]);
			doc.getText(p0, left, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0);
			
			g.setColor(colors[text[key_start][2]]);
			doc.getText(p0 + left, text[key_start][1] - text[key_start][0] + 1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
			left += text[key_start][1] - text[key_start][0] + 1;
		}
		else{
			g.setColor(colors[text[key_start][2]]);
			doc.getText(p0, text[key_start][1] - p0 + 1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0);

			left = text[key_start][1] - p0 + 1;
		}
		for(i = key_start + 1; i<key_end; i++){
			g.setColor(colors[0]);
			doc.getText(p0 + left, text[i][0] - text[i-1][1] + 1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
			left += text[i][0] - text[i-1][1] + 1;
			
			g.setColor(colors[text[key_start][2]]);
			doc.getText(p0 + left, text[i][1] - text[i][0] + 1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
			left += text[i][1] - text[i][0] + 1;
		}
		if(p1 <= text[i][0]){
			g.setColor(colors[0]);
			doc.getText(p0 + left, p1 - text[i-1][1] - 1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
			return x;
		}
		else{
			g.setColor(colors[0]);
			doc.getText(p0 + left, text[i][0] - text[i-1][1] + 1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
			left = text[i][0] - text[i-1][1] + 1;
			
			g.setColor(colors[text[i][2]]);
			doc.getText(p0 + left, p1 - text[i][0] - 1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
			return x;
		}*/
		/*if(p0<5){
			g.setColor(textColor);
			doc.getText(p0, 4, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0);
			
			if(p1>8){
			g.setColor(keywordColor);
			doc.getText(p0+5, 2, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0+5);
			

			g.setColor(textColor);
			doc.getText(p0+8, p1-1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0+8);
			}
			else{

				g.setColor(keywordColor);
				doc.getText(p0+5, p1-1, token);
				x = Utilities.drawTabbedText(token, x, y, g, this, p0+5);
				
			}
		}
		else if(p0<8){
			if(p1>8){
				g.setColor(keywordColor);
				doc.getText(p0, p0+7, token);
				x = Utilities.drawTabbedText(token, x, y, g, this, p0);
				

				g.setColor(textColor);
				doc.getText(p0+8, p1-1, token);
				x = Utilities.drawTabbedText(token, x, y, g, this, p0+8);
				}
				else{

					g.setColor(keywordColor);
					doc.getText(p0, p1-1, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0);
					
				}
		}
		else{
			g.setColor(textColor);
			doc.getText(p0, p1-1, token);
			x = Utilities.drawTabbedText(token, x, y, g, this, p0);
			
		}*/
		/*int count = p1 - p0;
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
					
					doc.getText(p0 + left, i - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i;
					state = 1;
				} else if (segment.array[i + segment.offset] == '/') {
					g.setColor(textColor);
					doc.getText(p0 + left, i - left, token);
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i;
					state = 2;
				} else if (segment.array[i + segment.offset] == '"') {
					g.setColor(textColor);
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
					if (Keywords.isKeyword(token))
						g.setColor(keywordColor);
					else
						g.setColor(textColor);
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
					x = Utilities.drawTabbedText(token, x, y, g, this, p0
							+ left);
					left = i + 1;
					state = 0;
				}
			} else if (state == 3 && segment.array[i + segment.offset] == '"') {
				doc.getText(p0 + left, (i + 1) - left, token);
				g.setColor(stringColor);
				x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
				left = i + 1;
				state = 0;
			}
		}
		doc.getText(p0 + left, p1 - p0 - left, token);
		if (state == 1) {
			if (Keywords.isKeyword(token))
				g.setColor(keywordColor);
			else
				g.setColor(textColor);
		} else if (state == 3)
			g.setColor(stringColor);
		else if (state == 2 && p1 - p0 - left > 1)
			g.setColor(commentColor);
		else if (state == 4)
			g.setColor(commentColor);
		else
			g.setColor(textColor);
		x = Utilities.drawTabbedText(token, x, y, g, this, p0 + left);
		return x;*/
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
	public static final Color operColor = new Color(248, 113, 2);
	public static final Color variableColor = new Color(255, 0, 0);
	public static final Color keywordColor = new Color(180, 0, 85);
	public static final Color textColor = new Color(0, 0, 0);
	public static final Color colors[] = {textColor, keywordColor, commentColor};
	public static final int TEXT = 0;
	public static final int KEYWORD = 1;
	public static final int COMMENT = 2;
	public static final int STRING = 3;
	public static final int VARIABLE = 5;
	public static final int MULTILINECOMMENT = 4;
}