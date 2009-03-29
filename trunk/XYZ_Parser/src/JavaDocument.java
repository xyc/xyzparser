import javax.swing.text.*;

public class JavaDocument extends PlainDocument {
	public boolean needsRedraw;
	private boolean editing;

	public JavaDocument() {
		needsRedraw = false;
		editing = false;
	}

	protected JavaDocument(javax.swing.text.AbstractDocument.Content c) {
		super(c);
		needsRedraw = false;
		editing = false;
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if (!editing) {
			super.insertString(offs, str, a);
			return;
		}
		if (str.equals("}")) {
			if (getText(offs - 1, 1).equals("\t")) {
				super.remove(offs - 1, 1);
				super.insertString(offs - 1, str, a);
			} else {
				super.insertString(offs, str, a);
			}
		} else if (str.equals("\n")) {
			int elementIndex = getDefaultRootElement().getElementIndex(offs);
			Element element = getDefaultRootElement().getElement(elementIndex);
			int startOffset = element.getStartOffset();
			int endOffset = element.getEndOffset();
			String elementText = null;
			elementText = getText(startOffset, endOffset - startOffset);
			int tabCount;
			for (tabCount = 0; elementText.charAt(tabCount) == '\t'; tabCount++)
				;
			if (elementText.indexOf("{") >= 0)
				tabCount++;
			String tabs = "";
			for (int i = 0; i < tabCount; i++)
				tabs = tabs + "\t";
			super.insertString(offs, str + tabs, a);
		} else {
			super.insertString(offs, str, a);
		}
	}

	protected void insertUpdate(
			javax.swing.text.AbstractDocument.DefaultDocumentEvent event,
			AttributeSet attributeSet) {
		int orgChangedIndex = getDefaultRootElement().getElementIndex(
				event.getOffset());
		super.insertUpdate(event, attributeSet);
		Element rootElement = getDefaultRootElement();
		javax.swing.event.DocumentEvent.ElementChange deltas = event
				.getChange(rootElement);
		if (deltas == null) {
			Element changedElement = getDefaultRootElement().getElement(
					orgChangedIndex);
			processMultilineComments(changedElement, false);
		} else {
			Element changedElements[] = deltas.getChildrenAdded();
			if (changedElements == null || changedElements.length == 0)
				log("Unknown insert even, 0 children added.");
			for (int i = 0; i < changedElements.length; i++)
				processMultilineComments(changedElements[i], true);
		}
	}

	public boolean isNeedsRedraw() {
		return needsRedraw;
	}

	private void log(String s) {
		System.out.println(s);
	}

	protected void postRemoveUpdate(
			javax.swing.text.AbstractDocument.DefaultDocumentEvent event) {
		int orgChangedIndex = getDefaultRootElement().getElementIndex(
				event.getOffset());
		super.postRemoveUpdate(event);
		Element rootElement = getDefaultRootElement();
		int changedIndex = getDefaultRootElement().getElementIndex(
				event.getOffset());
		Element changedElement = getDefaultRootElement().getElement(
				changedIndex);
		javax.swing.event.DocumentEvent.ElementChange deltas = event
				.getChange(rootElement);
		if (deltas != null)
			processMultilineComments(changedElement, true);
		else
			processMultilineComments(changedElement, false);
	}

	public void processMultilineComments(Element element, boolean isDeltas) {
		int elementIndex = getDefaultRootElement().getElementIndex(
				element.getStartOffset());
		int startOffset = element.getStartOffset();
		int endOffset = element.getEndOffset();
		String elementText = null;
		try {
			elementText = getText(startOffset, endOffset - startOffset);
		} catch (BadLocationException e) {
			log("Error processing updates: " + e);
		}
		boolean followingLineComment = false;
		boolean previousLineComment = false;
		boolean startsComment = false;
		boolean endsComment = false;
		MutableAttributeSet a = (MutableAttributeSet) element.getAttributes();
		if (a.isDefined("inComment"))
			previousLineComment = true;
		else if (isDeltas) {
			int lastElementIndex = elementIndex - 1;
			if (lastElementIndex >= 0) {
				javax.swing.text.AbstractDocument.AbstractElement lastElement = (javax.swing.text.AbstractDocument.AbstractElement) getDefaultRootElement()
						.getElement(lastElementIndex);
				if (!lastElement.isDefined("endsComment")
						&& lastElement.isDefined("inComment")
						|| lastElement.isDefined("startsComment")) {
					a.addAttribute("inComment", "inComment");
					previousLineComment = true;
				}
			}
		}
		followingLineComment = previousLineComment;
		int cIndex = elementText.indexOf("//");
		int eIndex;
		int sIndex;
		if (cIndex >= 0) {
			eIndex = elementText.lastIndexOf("*/", cIndex);
			sIndex = elementText.lastIndexOf("/*", cIndex);
		} else {
			eIndex = elementText.lastIndexOf("*/");
			sIndex = elementText.lastIndexOf("/*");
		}
		if (eIndex > sIndex) {
			followingLineComment = false;
			endsComment = true;
			startsComment = false;
		} else if (sIndex > eIndex) {
			followingLineComment = true;
			startsComment = true;
			endsComment = false;
		}
		if (followingLineComment) {
			if (startsComment) {
				if (!a.isDefined("startsComment")) {
					a.addAttribute("startsComment", "startsComment");
					setFollowingLinesCommentFlag(startOffset, true);
				}
			} else if (a.isDefined("startsComment"))
				a.removeAttribute("startsComment");
			if (a.isDefined("endsComment")) {
				a.removeAttribute("endsComment");
				setFollowingLinesCommentFlag(startOffset, true);
			} else if (isDeltas)
				setFollowingLinesCommentFlag(startOffset, true);
		} else {
			if (endsComment) {
				if (!a.isDefined("endsComment")) {
					a.addAttribute("endsComment", "endsComment");
					setFollowingLinesCommentFlag(startOffset, false);
				}
			} else if (a.isDefined("endsComment"))
				a.removeAttribute("endsComment");
			if (a.isDefined("startsComment")) {
				a.removeAttribute("startsComment");
				setFollowingLinesCommentFlag(startOffset, false);
			} else if (isDeltas)
				setFollowingLinesCommentFlag(startOffset, false);
		}
	}

	public void setFollowingLinesCommentFlag(int offset, boolean commentFlag) {
		int elementIndex = getDefaultRootElement().getElementIndex(offset);
		elementIndex++;
		for (boolean done = false; !done;) {
			Element e = getDefaultRootElement().getElement(elementIndex);
			if (e == null) {
				done = true;
			} else {
				MutableAttributeSet a = (MutableAttributeSet) e.getAttributes();
				if (commentFlag) {
					if (a.isDefined("inComment")) {
						done = true;
					} else {
						a.addAttribute("inComment", "inComment");
						needsRedraw = true;
					}
					if (a.isDefined("endsComment"))
						done = true;
				} else {
					if (!a.isDefined("inComment")) {
						done = true;
					} else {
						a.removeAttribute("inComment");
						needsRedraw = true;
					}
					if (a.isDefined("startsComment"))
						done = true;
				}
				elementIndex++;
			}
		}
	}

	public void setNeedsRedraw(boolean newNeedsRedraw) {
		needsRedraw = newNeedsRedraw;
	}

	public boolean getEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}
}
