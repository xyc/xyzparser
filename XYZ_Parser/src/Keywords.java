/*
 * @(#)Keywords.java	1.01 09/03/17
 */

import javax.swing.text.Segment;

/**
 * Sample JavaCC compiler
 * 
 * @author Atlantis Mao
 * @version 1.01 03/17/09
 */

public class Keywords {
	public Keywords() {
	}

	public static boolean isKeyword(Segment seg) {
		boolean match = false;
		for (int i = 0; !match && i < keywords.length; i++)
			if (seg.count == keywords[i].length()) {
				match = true;
				for (int j = 0; match && j < seg.count; j++)
					if (seg.array[seg.offset + j] != keywords[i].charAt(j))
						match = false;

			}

		return match;
	}

	public static final String keywords[] = { "abstract", "boolean", "break",
			"byte", "case", "catch", "char", "class", "const", "continue",
			"default", "do", "double", "else", "extends", "final", "finally",
			"float", "for", "goto", "if", "implements", "import", "instanceof",
			"int", "interface", "long", "native", "new", "package", "private",
			"protected", "public", "return", "short", "static", "strictfp",
			"super", "switch", "synchronized", "this", "throw", "throws",
			"transient", "try", "void", "volatile", "while", "true", "false" };
}