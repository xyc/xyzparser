package parser;


public class TokenRecognize {
	private final static Character IllegalTokenArray [] = new Character[] {
			'@','#','$','%','^','~','?','\'','/'};
	//private static ArrayList<Character> illegalTokens = new ArrayList<Character>();
	private final static Character legalTokenArray [] = new Character[] {
		'\t',  '\n',  '\r',  '.',  '!',  '=',  '&',  '<',  '+',  '-',  '*',  
		'{',  '}',  '[',  ']',  '(',  ')',  ',',  ';',  '_',  '/'	};
	
	public TokenRecognize() {
		super();
	}
	
	public static boolean isLegalCharacter(char c) {	
		if (c>='0'&&c<='9' || (c >='A'&& c <='Z')||(c>='a'&&c<='z'))
			return true;
		for (int i = 0; i < legalTokenArray.length; i++) {
			if (c == legalTokenArray[i])
				return true;
		}
		return false;
	}

	public static Character[] getIllegalTokenArray() {
		return IllegalTokenArray;
	}
}
