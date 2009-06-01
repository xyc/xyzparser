/*
 * @(#)Compiler.java	1.01 09/03/17
 */

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.net.URL;
import java.util.*;

import javax.swing.text.*;
import javax.swing.tree.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.*;

import parser.ParseException;
import parser.XYZParser;
import semantic.ClassDescriptor;
import semantic.FieldDescriptor;
import semantic.LocalDescriptor;
import semantic.MethodDescriptor;
import semantic.ParamDescriptor;
import semantic.ProgramDescriptor;
import semantic.Symbol;

/**
 * Sample JavaCC compiler
 * 
 * @version 1.01 03/17/09
 */

public class Compiler extends JPanel {

	private XYZParser parser;
	private boolean showFinal = false;
	private parser2.XYZParser parserSyntax;
	private static ResourceBundle resources;
	private final static String EXIT_AFTER_PAINT = new String("-exit");
	private static boolean exitAfterFirstPaint;
	private int i = 0;
	ViewClassPanel viewClassPanel;

	static {
		try {
			resources = ResourceBundle.getBundle("resources.Notepad", Locale
					.getDefault());
		} catch (MissingResourceException mre) {
			System.err.println("resources/Notepad.properties not found");
			System.exit(1);
		}

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.err.println("Error loading L&F: " + e);
		}
	}

	public void paintChildren(Graphics g) {
		super.paintChildren(g);
		if (exitAfterFirstPaint) {
			System.exit(0);
		}
	}

	public int getLineOfOffset(int offset) {
		Document doc = editor.getDocument();
		if (offset >= 0 && offset <= doc.getLength()) {
			Element map = doc.getDefaultRootElement();
			return map.getElementIndex(offset);
		} else
			return -1;
	}

	public int getLineStartOffset(int line) {
		Element map = editor.getDocument().getDefaultRootElement();
		if (line >= 0 && line <= map.getElementCount()) {
			Element lineElem = map.getElement(line);
			return lineElem.getStartOffset();
		} else
			return -1;
	}

	public JFileChooser getFileChooser() {
		JFileChooser chooser = new JFileChooser();
		JFileFilter[] filter = {
				new JFileFilter(new String[] { "xyz" }, "xyz源文件 (*.xyz)"),
				new JFileFilter(new String[] { "java" }, "Java源文件 (*.java)"),
				new JFileFilter(new String[] { "txt" }, "文本文件 (*.txt)"), };
		for (int i = 0; i < filter.length; i++)
			chooser.addChoosableFileFilter(filter[i]);
		chooser.setFileFilter(filter[0]);
		return chooser;
	}

	public void setConsoleArea() {

		// tipPanel.removeAll();
		// tipPanel = new JPanel();
		
		tipPanel.setLayout(new BorderLayout());
		tipPanel.removeAll();
		consoleArea.setText("");
		consoleArea.setForeground(new Color(255, 0, 0));
		consoleArea.setFont(new Font("Courier New", Font.PLAIN, 14));
		consoleArea.append("There are " + parser.errors.size() + " errors"
				+ "\n");

		for (int i = 0; i < parser.errors.size(); i++) {
			JLabel tipLabel = new JLabel();
			tipLabel.setIcon(new ImageIcon(
					"src\\resources\\quickfix_error_obj.gif"));
			tipLabel.setBounds(0,
					15 * (parser.errors.get(i).getErrorLine() - 1), 20, 20);
			tipLabel.setToolTipText(parser.errors.get(i).getErrorMessage());

			tipPanel.add(tipLabel);

			consoleArea.append("Error: " + (i + 1) + " line "
					+ parser.errors.get(i).getErrorLine());
			consoleArea.append(", Column "
					+ parser.errors.get(i).getErrorColumn() + "\n");
			consoleArea.append("\t errorMessage: "
					+ parser.errors.get(i).getErrorMessage() + "\n");

		}

		consoleArea.append("end" + "\n");
		// newLowPanel();

	}

	public void setConsoleAreaSyntax() {
		// tipPanel.removeAll();
		tipPanel.setLayout(new BorderLayout());
		consoleArea.setText("");
		consoleArea.setForeground(new Color(255, 0, 0));
		consoleArea.setFont(new Font("Courier New", Font.PLAIN, 14));
		consoleArea.append("There are " + parserSyntax.errors.size()
				+ " errors" + "\n");

		for (int i = 0; i < parserSyntax.errors.size(); i++) {
			JLabel tipLabel = new JLabel();
			tipLabel.setIcon(new ImageIcon(
					"src\\resources\\quickfix_error_obj.gif"));
			tipLabel.setBounds(0, 15 * (parserSyntax.errors.get(i)
					.getErrorLine() - 1), 20, 20);
			tipLabel.setToolTipText(parserSyntax.errors.get(i)
					.getErrorMessage());
			tipPanel.add(tipLabel);

			consoleArea.removeAll();
			consoleArea.append("Error: " + (i + 1) + " line "
					+ parserSyntax.errors.get(i).getErrorLine());
			consoleArea.append(", Column "
					+ parserSyntax.errors.get(i).getErrorColumn() + "\n");
			consoleArea.append("\t errorMessage: "
					+ parserSyntax.errors.get(i).getErrorMessage() + "\n");

		}
		consoleArea.append("end" + "\n");
	}

	public void setWeightArea() {
		weightArea.setText("");
		weightArea.setForeground(new Color(255, 0, 0));
		weightArea.setFont(new Font("Courier New", Font.PLAIN, 14));

		weightArea.append(ast.XYZParser.weightInfo);
	}

	public void setVariableArea() {
		variableArea.setText("");
		variableArea.setForeground(new Color(255, 0, 0));
		variableArea.setFont(new Font("Courier New", Font.PLAIN, 14));

		variableArea.append(ast.XYZParser.undefinedInfo);

	}
	
	public void setRedefinedArea() {
		redefinedArea.setText("");
		redefinedArea.setForeground(new Color(255, 0, 0));
		redefinedArea.setFont(new Font("Courier New", Font.PLAIN, 14));

		redefinedArea.append(ast.XYZParser.redefinedInfo);
		
	}

	public void setDuplicateMethodArea() {
		duplicateMethodArea.setText("");
		duplicateMethodArea.setForeground(new Color(255, 0, 0));
		duplicateMethodArea.setFont(new Font("Courier New", Font.PLAIN, 14));

		duplicateMethodArea.append(ast.XYZParser.duplicateMethodInfo);

	}
	
	public void setMismatchInfoArea() {
		mismatchInfoArea.setText("");
		mismatchInfoArea.setForeground(new Color(255, 0, 0));
		mismatchInfoArea.setFont(new Font("Courier New", Font.PLAIN, 14));

		mismatchInfoArea.append(ast.XYZParser.mismatchInfo);
		
	}
	
	public void setTacInfoArea(){
		tacInfoArea.setText("");
		tacInfoArea.setForeground(new Color(255, 0, 0));
		tacInfoArea.setFont(new Font("Courier New", Font.PLAIN, 14));

		tacInfoArea.append(ast.XYZParser.tacInfo);
	}

	public void parse1(File file) {
		try {
			FileInputStream stream = new FileInputStream(file);
			if (parser == null)
				parser = new XYZParser(stream);
			else {
				// XYZParser.changedReInit(stream);
				XYZParser.ReInit(stream);
				// parser = new XYZParser(stream);
			}
			parser.lexicalAnalyze();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse2(File file) {
		try {
			FileInputStream stream2 = new FileInputStream(file);
			if (parserSyntax == null)
				parserSyntax = new parser2.XYZParser(stream2);
			else {
				parser2.XYZParser.ReInit(stream2);
			}

			if (parser.errors.size() == 0) {
				parserSyntax.getSyntaxInfo();
				// statArea.setText(""); // statArea.append("StatementCount:
				// "+v.getStatementCount());

			}
			stream2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse3(File file) {
		ast.XYZParser.check(file, showFinal);
	}

	public void parse() {
		try {
			parse1(file);
			setConsoleArea();
			// parse2(file);
			// weightArea.setText(parserSyntax.weightInfo);
			// setConsoleAreaSyntax();
			// viewClassPanel.classTree.setContent();

			// Next is semantic check:

			parse3(file);
			setVariableArea();
			setRedefinedArea();
			setDuplicateMethodArea();
			setMismatchInfoArea();
			setTacInfoArea();
			setWeightArea();

			viewClassPanel.classTree.setContentSemantic();
			// setContentSemantic();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Compiler() {
		super(true);

		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout());

		// create the embedded JTextComponent
		editor = createEditor();

		// Add this as a listener for undoable edits.
		editor.getDocument().addUndoableEditListener(undoHandler);

		// install the command table
		commands = new Hashtable();
		Action[] actions = getActions();
		for (int i = 0; i < actions.length; i++) {
			Action a = actions[i];
			commands.put(a.getValue(Action.NAME), a);
		}

		JScrollPane scroller = new JScrollPane();
		JViewport port = scroller.getViewport();
		port.add(editor);
		try {
			String vpFlag = resources.getString("ViewportBackingStore");
			Boolean bs = Boolean.valueOf(vpFlag);
			bs.booleanValue();
		} catch (MissingResourceException mre) {
			// just use the viewport default
		}

		menuItems = new Hashtable();

		JavaEditorKit editorKit = new JavaEditorKit();
		editor.setEditorKit(editorKit);
		//editor.setPreferredSize(new Dimension(920, 500));
		editor.addKeyListener(new KeyHandler());

		lineNumPane = new JTextArea();
		lineNumPane.setEditable(false);
		lineNumPane.setForeground(null);
		lineNumPane.setBackground(new Color(200, 200, 240));
		lineNumPane.setFont(new Font("Courier New", Font.PLAIN, 13));
		lineNumPane.setVisible(false);

		docTabbedPane.setBackground(Color.WHITE);
		Dimension d = new Dimension();
		d.width = 20;
		tipPanel.setPreferredSize(d);
		lowLevelPane.setLayout(new BorderLayout());
		lowLevelPane.add(tipPanel, BorderLayout.WEST);
		lowLevelPane.add(editor, BorderLayout.CENTER);
		//lowLevelPane.add(lineNumPane, BorderLayout.WEST);
		docTabbedPane.addTab("new file", new ImageIcon(
				"src\\resources\\file_obj.gif"), lowLevelPane, "new file");

		outputPane.add("Error Console ", new JScrollPane(consoleArea));
		outputPane.setIconAt(0, new ImageIcon(
				"src\\resources\\console_view.gif"));
		outputPane.add("Weight Info", new JScrollPane(weightArea));
		outputPane.add("Undefined Variables", new JScrollPane(variableArea));
		outputPane.add("Redefined Variables", new JScrollPane(redefinedArea));
		outputPane.add("Depulicate methods", new JScrollPane(duplicateMethodArea));
		outputPane.add("Mismatch Infos", new JScrollPane(mismatchInfoArea));
		outputPane.add("Three-address Infos", new JScrollPane(tacInfoArea));
				
		JTabbedPane viewTabbedPane = new JTabbedPane();
		ViewFilePanel viewFilePanel = new ViewFilePanel();
		viewClassPanel = new ViewClassPanel();
		JPanel viewResourcePanel = new JPanel();
		viewClassPanel.setBackground(Color.WHITE);
		viewResourcePanel.setBackground(Color.WHITE);
		viewTabbedPane.setPreferredSize(new Dimension(300, 500));
		viewTabbedPane.add(viewFilePanel, "File View", 0);
		viewTabbedPane.setIconAt(0, new ImageIcon(
				"src\\resources\\resource_persp.gif"));
		viewTabbedPane.add(viewClassPanel, "Class View", 1);
		viewTabbedPane.setIconAt(1, new ImageIcon(
				"src\\resources\\outline_co.gif"));

		JSplitPane splitPane_1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				viewTabbedPane, docTabbedPane);
		JSplitPane splitPane_2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				splitPane_1, outputPane);
		splitPane_1.setContinuousLayout(true);
		splitPane_1.setOneTouchExpandable(true);
		splitPane_2.setContinuousLayout(true);
		splitPane_2.setOneTouchExpandable(true);
		JStatusBar statusbar = new JStatusBar(this);
		add("North", createToolbar());
		add("Center", splitPane_2);
		add("South", statusbar);
	}

	public static void main(String[] args) {
		try {
			String vers = System.getProperty("java.version");
			if (vers.compareTo("1.1.2") < 0) {
				System.out.println("!!!WARNING: Swing must be run with a "
						+ "1.1.2 or higher version VM!!!");
			}
			if (args.length > 0 && args[0].equals(EXIT_AFTER_PAINT)) {
				exitAfterFirstPaint = true;
			}

			JFrame.setDefaultLookAndFeelDecorated(true);
			JFrame frame = new JFrame();
			// frame.addKeyListener(new Key());
			ImageIcon logoImage = new ImageIcon(
					"src\\resources\\logo\\logo1.png");
			frame.setResizable(true);
			frame.setIconImage(logoImage.getImage());
			frame.setTitle(resources.getString("Title"));
			frame.setBackground(Color.lightGray);
			frame.getContentPane().setLayout(new BorderLayout());
			Compiler compiler = new Compiler();
			frame.getContentPane().add("Center", compiler);
			frame.setJMenuBar(compiler.createMenubar());
			frame.addWindowListener(new AppCloser());
			frame.pack();
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		} catch (Throwable t) {
			System.out.println("uncaught exception: " + t);
			t.printStackTrace();
		}

	}

	/**
	 * Fetch the list of actions supported by this editor. It is implemented to
	 * return the list of actions supported by the embedded JTextComponent
	 * augmented with the actions defined locally.
	 */
	public Action[] getActions() {
		return TextAction.augmentList(editor.getActions(), defaultActions);
	}

	/**
	 * Create an editor to represent the given document.
	 */
	protected JEditorPane createEditor() {
		JEditorPane c = new JEditorPane();
		// c.setDragEnabled(true);
		c.setFont(new Font("Courier New", Font.PLAIN, 13));
		return c;
	}

	/**
	 * Fetch the editor contained in this panel
	 */
	protected JEditorPane getEditor() {
		return editor;
	}

	/**
	 * To shutdown when run as an application. This is a fairly lame
	 * implementation. A more self-respecting implementation would at least
	 * check to see if a save was needed.
	 */
	protected static final class AppCloser extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	/**
	 * Find the hosting frame, for the file-chooser dialog.
	 */
	protected Frame getFrame() {
		for (Container p = getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}

	/**
	 * This is the hook through which all menu items are created. It registers
	 * the result with the menuitem hashtable so that it can be fetched with
	 * getMenuItem().
	 * 
	 * @see #getMenuItem
	 */
	protected JMenuItem createMenuItem(String cmd) {
		char[] ch = new char[1];
		String sr = getResourceString(cmd + labelSuffix);
		sr.getChars(0, 1, ch, 0);
		JMenuItem mi = new JMenuItem(sr, ch[0]);
		URL url = getResource(cmd + imageSuffix);
		if (url != null) {
			mi.setHorizontalTextPosition(JButton.RIGHT);
			mi.setIcon(new ImageIcon(url));
			if (getResourceString(cmd + keyStrokeSuffix) != null) {
				char[] chh = new char[1];
				String srr = getResourceString(cmd + keyStrokeSuffix);
				srr.getChars(0, 1, chh, 0);
				mi.setAccelerator(KeyStroke.getKeyStroke(chh[0],
						InputEvent.CTRL_MASK));
			} else if (sr.equals("Compile"))
				mi.setAccelerator(KeyStroke.getKeyStroke("F7"));
			else if (sr.equals("Run"))
				mi.setAccelerator(KeyStroke.getKeyStroke("F5"));
			else if (sr.equals("Help Contents")) {
				mi.setAccelerator(KeyStroke.getKeyStroke("F1"));
			}
		}
		String astr = getResourceString(cmd + actionSuffix);
		if (astr == null) {
			astr = cmd;
		}
		mi.setActionCommand(astr);
		Action a = getAction(astr);
		if (a != null) {
			mi.addActionListener(a);
			a.addPropertyChangeListener(createActionChangeListener(mi));
			mi.setEnabled(a.isEnabled());
		} else {
			mi.setEnabled(false);
		}
		menuItems.put(cmd, mi);
		return mi;
	}

	/**
	 * Fetch the menu item that was created for the given command.
	 * 
	 * @param cmd
	 *            Name of the action.
	 * @returns item created for the given command or null if one wasn't
	 *          created.
	 */
	protected JMenuItem getMenuItem(String cmd) {
		return (JMenuItem) menuItems.get(cmd);
	}

	protected Action getAction(String cmd) {
		return (Action) commands.get(cmd);
	}

	protected String getResourceString(String nm) {
		String str;
		try {
			str = resources.getString(nm);
		} catch (MissingResourceException mre) {
			str = null;
		}
		return str;
	}

	protected URL getResource(String key) {
		String name = getResourceString(key);
		if (name != null) {
			URL url = this.getClass().getResource(name);
			return url;
		}
		return null;
	}

	protected Container getToolbar() {
		return toolbar;
	}

	protected JMenuBar getMenubar() {
		return menubar;
	}

	/**
	 * Create a status bar
	 */
	protected Component createStatusbar() {
		// need to do something reasonable here
		status = new StatusBar();
		return status;
	}

	/**
	 * Resets the undo manager.
	 */
	protected void resetUndoManager() {
		undo.discardAllEdits();
		undoAction.update();
		redoAction.update();
	}

	/**
	 * Create the toolbar. By default this reads the resource file for the
	 * definition of the toolbar.
	 */
	private Component createToolbar() {
		toolbar = new JToolBar();
		String[] toolKeys = tokenize(getResourceString("toolbar"));
		for (int i = 0; i < toolKeys.length; i++) {
			if (toolKeys[i].equals("-")) {
				toolbar.add(Box.createHorizontalStrut(5));
			} else {
				toolbar.add(createTool(toolKeys[i]));
			}
		}
		toolbar.add(Box.createHorizontalGlue());
		return toolbar;
	}

	/**
	 * Hook through which every toolbar item is created.
	 */
	protected Component createTool(String key) {
		return createToolbarButton(key);
	}

	/**
	 * Create a button to go inside of the toolbar. By default this will load an
	 * image resource. The image filename is relative to the classpath
	 * (including the '.' directory if its a part of the classpath), and may
	 * either be in a JAR file or a separate file.
	 * 
	 * @param key
	 *            The key in the resource file to serve as the basis of lookups.
	 */
	protected JButton createToolbarButton(String key) {
		URL url = getResource(key + imageSuffix);
		JButton b = new JButton(new ImageIcon(url)) {
			public float getAlignmentY() {
				return 0.5f;
			}
		};
		b.setRequestFocusEnabled(false);
		b.setMargin(new Insets(1, 1, 1, 1));

		String astr = getResourceString(key + actionSuffix);
		if (astr == null) {
			astr = key;
		}
		Action a = getAction(astr);
		if (a != null) {
			b.setActionCommand(astr);
			b.addActionListener(a);
		} else {
			b.setEnabled(false);
		}

		String tip = getResourceString(key + tipSuffix);
		if (tip != null) {
			b.setToolTipText(tip);
		}

		return b;
	}

	/**
	 * Take the given string and chop it up into a series of strings on
	 * whitespace boundaries. This is useful for trying to get an array of
	 * strings out of the resource file.
	 */
	protected String[] tokenize(String input) {
		Vector v = new Vector();
		StringTokenizer t = new StringTokenizer(input);
		String cmd[];

		while (t.hasMoreTokens())
			v.addElement(t.nextToken());
		cmd = new String[v.size()];
		for (int i = 0; i < cmd.length; i++)
			cmd[i] = (String) v.elementAt(i);

		return cmd;
	}

	/**
	 * Create the menubar for the app. By default this pulls the definition of
	 * the menu from the associated resource file.
	 */
	protected JMenuBar createMenubar() {
		JMenuItem mi;
		JMenuBar mb = new JMenuBar();

		String[] menuKeys = tokenize(getResourceString("menubar"));
		for (int i = 0; i < menuKeys.length; i++) {
			JMenu m = createMenu(menuKeys[i]);
			if (m != null) {
				mb.add(m);
			}
		}
		this.menubar = mb;
		return mb;
	}

	/**
	 * Create a menu for the app. By default this pulls the definition of the
	 * menu from the associated resource file.
	 */
	protected JMenu createMenu(String key) {
		String[] itemKeys = tokenize(getResourceString(key));
		JMenu menu = new JMenu(getResourceString(key + "Label"));
		char[] ch = new char[1];
		getResourceString(key + "Label").getChars(0, 1, ch, 0);
		menu.setMnemonic(ch[0]);
		for (int i = 0; i < itemKeys.length; i++) {
			if (itemKeys[i].equals("-")) {
				menu.addSeparator();
			} else {
				JMenuItem mi = createMenuItem(itemKeys[i]);
				menu.add(mi);
			}
		}
		return menu;
	}

	// Yarked from JMenu, ideally this would be public.
	protected PropertyChangeListener createActionChangeListener(JMenuItem b) {
		return new ActionChangedListener(b);
	}

	// Yarked from JMenu, ideally this would be public.
	private class ActionChangedListener implements PropertyChangeListener {
		JMenuItem menuItem;

		ActionChangedListener(JMenuItem mi) {
			super();
			this.menuItem = mi;
		}

		public void propertyChange(PropertyChangeEvent e) {
			String propertyName = e.getPropertyName();
			if (e.getPropertyName().equals(Action.NAME)) {
				String text = (String) e.getNewValue();
				menuItem.setText(text);
			} else if (propertyName.equals("enabled")) {
				Boolean enabledState = (Boolean) e.getNewValue();
				menuItem.setEnabled(enabledState.booleanValue());
			}
		}
	}

	public int tabNum = 0;
	public int docSize;
	public int lineCount;
	public File file;
	public JTabbedPane outputPane = new JTabbedPane();
	public JPanel lowLevelPane = new JPanel();
	public JTabbedPane docTabbedPane = new JTabbedPane();
	public JPanel tipPanel = new JPanel();
	public JTextArea weightArea = new JTextArea();
	public JTextArea statArea = new JTextArea();
	public JTextArea consoleArea = new JTextArea();
	public JTextArea IOArea = new JTextArea();
	public JTextArea variableArea = new JTextArea();
	public JTextArea redefinedArea = new JTextArea();
	public JTextArea duplicateMethodArea = new JTextArea();
	public JTextArea mismatchInfoArea = new JTextArea();
	public JTextArea tacInfoArea = new JTextArea();
	private JTextArea lineNumPane;
	private JEditorPane editor;
	private Hashtable commands;
	private Hashtable menuItems;
	private JMenuBar menubar;
	private JToolBar toolbar;
	private JComponent status;
	private JFrame elementTreeFrame;

	protected FileDialog fileDialog;

	// protected DocumentListener documentListener = null;

	/**
	 * Listener for the edits on the current document.
	 */
	protected UndoableEditListener undoHandler = new UndoHandler();

	/** UndoManager that we add edits to. */
	protected UndoManager undo = new UndoManager();

	/**
	 * Suffix applied to the key used in resource file lookups for an image.
	 */
	public static final String imageSuffix = "Image";

	/**
	 * Suffix applied to the key used in resource file lookups for an image.
	 */
	public static final String keyStrokeSuffix = "KeyStroke";

	/**
	 * Suffix applied to the key used in resource file lookups for a label.
	 */
	public static final String labelSuffix = "Label";

	/**
	 * Suffix applied to the key used in resource file lookups for an action.
	 */
	public static final String actionSuffix = "Action";

	/**
	 * Suffix applied to the key used in resource file lookups for tooltip text.
	 */
	public static final String tipSuffix = "Tooltip";

	public static final String openAction = "open";
	public static final String newAction = "new";
	public static final String saveAction = "save";
	public static final String exitAction = "exit";
	public static final String showElementTreeAction = "showElementTree";
	public static final String lineNumAction = "lineNum";
	public static final String fullScreenAction = "fullScreen";
	public static final String outputAction = "output";
	public static final String printAction = "print";
	public static final String compileAction = "compile";
	public static final String runAction = "run";

	class KeyHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {
			try {
				byte[] bytes = editor.getText().getBytes();
				InputStream stream = new ByteArrayInputStream(bytes);
				InputStream stream2 = new ByteArrayInputStream(bytes);

				if (parser == null)
					parser = new XYZParser(stream);
				else {
					// XYZParser.changedReInit(stream);
					XYZParser.ReInit(stream);
					// parser = new XYZParser(stream);
				}

				if (parserSyntax == null)
					parserSyntax = new parser2.XYZParser(stream2);
				else {
					parser2.XYZParser.ReInit(stream2);
				}

				parser.lexicalAnalyze();
				setConsoleArea();
				if (parser.errors.size() == 0) {
					parserSyntax.getSyntaxInfo();

					// statArea.setText("");
					// statArea.append("StatementCount:
					// "+v.getStatementCount());
					weightArea.setText(parserSyntax.weightInfo);
					setConsoleAreaSyntax();

				}
				/*
				 * if (parser.errors.size() == 0) { SimpleNode n =
				 * parserSyntax.parse(); TestVisitor v = new TestVisitor();
				 * InfoVisitor iv = new InfoVisitor(); n.jjtAccept(v, null);
				 * n.jjtAccept(iv, null); System.out.println("StatementCount:" +
				 * v.getStatementCount()); System.out.println("TotalWeight:" +
				 * v.getTotalWeight()); statArea.setText("");
				 * statArea.append(v.getStatementCount() + "");
				 * weightArea.setText(""); weightArea.append(v.getTotalWeight()
				 * + ""); }
				 */
				stream.close();
				stream2.close();

				// System.out.println("Hello");

				// tipPanel.setLayout(new BorderLayout());

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	class UndoHandler implements UndoableEditListener {

		/**
		 * Messaged when the Document has created an edit, the edit is added to
		 * <code>undo</code>, an instance of UndoManager.
		 */
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
			undoAction.update();
			redoAction.update();
		}
	}

	/**
	 * This part is not so useful
	 */
	class StatusBar extends JComponent {

		public StatusBar() {
			super();
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		}

		public void paint(Graphics g) {
			super.paint(g);
		}

	}

	// --- action implementations -----------------------------------

	private UndoAction undoAction = new UndoAction();
	private RedoAction redoAction = new RedoAction();

	/**
	 * Actions defined by the Notepad class
	 */
	private Action[] defaultActions = { new NewAction(), new OpenAction(),
			new SaveAction(), new ExitAction(), undoAction, redoAction,
			new LineNumAction(), new FullScreenAction(), new OutputAction(),
			new PrintAction(), new CompileAction(), new RunAction() };

	class UndoAction extends AbstractAction {
		public UndoAction() {
			super("Undo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.undo();
			} catch (CannotUndoException ex) {
				System.out.println("Unable to undo: " + ex);
				ex.printStackTrace();
			}
			update();
			redoAction.update();
		}

		protected void update() {
			if (undo.canUndo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getUndoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Undo");
			}
		}
	}

	class RedoAction extends AbstractAction {
		public RedoAction() {
			super("Redo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.redo();
			} catch (CannotRedoException ex) {
				System.out.println("Unable to redo: " + ex);
				ex.printStackTrace();
			}
			update();
			undoAction.update();
		}

		protected void update() {
			if (undo.canRedo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getRedoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "Redo");
			}
		}
	}

	class OpenAction extends NewAction {

		OpenAction() {
			super(openAction);
		}

		public void actionPerformed(ActionEvent e) {
			editor.removeAll();
			Frame frame = getFrame();
			JFileChooser chooser = getFileChooser();
			int ret = chooser.showOpenDialog(frame);

			if (ret != JFileChooser.APPROVE_OPTION) {
				return;
			}

			file = chooser.getSelectedFile();
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				int n;
				while ((n = br.read()) != -1) {
					docSize += 2;
				}
			} catch (IOException ee) {

			}

			parse();
			if (file.isFile() && file.canRead()) {
				Document oldDoc = getEditor().getDocument();
				if (oldDoc != null) {
					oldDoc.removeUndoableEditListener(undoHandler);
				}

				frame.setTitle("Java Compiler - " + file.getName());
				Thread loader = new FileLoader(file, editor.getDocument());
				loader.start();
			} else {
				JOptionPane.showMessageDialog(getFrame(),
						"Could not open file: " + file, "Error opening file",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class SaveAction extends AbstractAction {

		SaveAction() {
			super(saveAction);
		}

		public void actionPerformed(ActionEvent e) {
			Frame frame = getFrame();
			JFileChooser chooser = getFileChooser();
			int ret = chooser.showSaveDialog(frame);

			if (ret != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File f = chooser.getSelectedFile();
			frame.setTitle(f.getName());
			Thread saver = new FileSaver(f, editor.getDocument());
			saver.start();
		}
	}

	class NewAction extends AbstractAction {

		NewAction() {
			super(newAction);
		}

		NewAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {
			// docTabbedPane.add("New Doc", new JScrollPane(lowLevelPane));
			Document oldDoc = getEditor().getDocument();
			if (oldDoc != null) {
				oldDoc.removeUndoableEditListener(undoHandler);
			}
			getEditor().setDocument(new PlainDocument());
			getEditor().getDocument().addUndoableEditListener(undoHandler);
			resetUndoManager();
			getFrame().setTitle(resources.getString("Title"));
			revalidate();
		}
	}

	/**
	 * Really lame implementation of an exit command
	 */
	class ExitAction extends AbstractAction {

		ExitAction() {
			super(exitAction);
		}

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	class LineNumAction extends AbstractAction {
		boolean isVisible = false;

		LineNumAction() {
			super(lineNumAction);
		}

		LineNumAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {
			if (isVisible == false) {
				lineNumPane.setVisible(true);
				isVisible = true;
			} else {
				lineNumPane.setVisible(false);
				isVisible = false;
			}
		}
	}

	class FullScreenAction extends AbstractAction {
		FullScreenAction() {
			super(fullScreenAction);
		}

		FullScreenAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {

		}

	}

	class OutputAction extends AbstractAction {
		OutputAction() {
			super(outputAction);
		}

		OutputAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				FileInputStream stream = new FileInputStream(file);
				parser.ReInit(stream);
				parserSyntax.ReInit(new FileInputStream(file));
				parser.outputStatisticsFile();
				stream.close();
				IOArea.setForeground(new Color(0, 0, 255));
				IOArea.setFont(new Font("Courier New", Font.PLAIN, 14));
				IOArea.append(parser.output);
			} catch (ParseException ex) {
				ex.printStackTrace();
			} catch (FileNotFoundException exx) {
				// TODO Auto-generated catch block
				exx.printStackTrace();
			} catch (IOException exxx) {
				// TODO Auto-generated catch block
				exxx.printStackTrace();
			}
		}
	}

	class PrintAction extends AbstractAction {
		PrintAction() {
			super(printAction);
		}

		PrintAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {

		}
	}

	class CompileAction extends AbstractAction {
		CompileAction() {
			super(compileAction);
		}

		CompileAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {
			if(showFinal)
				showFinal = false;
			else
				showFinal = true;
			
			ast.XYZParser.reParse(file, showFinal);
			tacInfoArea.setText(ast.XYZParser.tacInfo);
		}
	}

	class RunAction extends AbstractAction {
		RunAction() {
			super(runAction);
		}

		RunAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {

		}
	}

	/**
	 * Thread to load a file into the text storage model
	 */
	class FileLoader extends Thread {

		FileLoader(File f, Document doc) {
			setPriority(4);
			this.f = f;
			this.doc = doc;
		}

		public void run() {
			try {
				doc.remove(0, doc.getLength());
				// initialize the statusbar
				// status.removeAll();
				JProgressBar progress = new JProgressBar();
				progress.setMinimum(0);
				progress.setMaximum((int) f.length());
				// status.add(progress);
				// status.revalidate();

				// try to start reading
				Reader in = new FileReader(f);
				char[] buff = new char[4096];
				int nch;
				while ((nch = in.read(buff, 0, buff.length)) != -1) {
					doc.insertString(doc.getLength(), new String(buff, 0, nch),
							null);
					progress.setValue(progress.getValue() + nch);
				}
			} catch (IOException e) {
				final String msg = e.getMessage();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane
								.showMessageDialog(getFrame(),
										"Could not open file: " + msg,
										"Error opening file",
										JOptionPane.ERROR_MESSAGE);
					}
				});
			} catch (BadLocationException e) {
				System.err.println(e.getMessage());
			}
			doc.addUndoableEditListener(undoHandler);
			// doc.addDocumentListener(documentListener);
			// we are done... get rid of progressbar
			// status.removeAll();
			// status.revalidate();

			resetUndoManager();

			try {
				lineCount = getLineOfOffset(editor.getDocument().getLength()) + 1;
				String lineStr = " ";
				for (int i = 1; i <= lineCount; i++) {
					lineNumPane.removeAll();
					lineStr = lineStr + i + " \n ";
				}
				lineNumPane.setText(lineStr);
			} catch (Exception ee) {
			}

			docTabbedPane.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int tabNumber = docTabbedPane.getUI().tabForCoordinate(
							docTabbedPane, e.getX(), e.getY());
					if (tabNumber < 0)
						return;
					if (e.getClickCount() == 2) {
						docTabbedPane.remove(tabNumber);
					}
				}
			});
			
			docTabbedPane.add(new JScrollPane(lowLevelPane), file.getName(),
					tabNum);
			docTabbedPane.setToolTipTextAt(tabNum, file.getPath());
			docTabbedPane.setSelectedIndex(tabNum);
			if (file.getName().endsWith(".java")) {
				docTabbedPane.setIconAt(tabNum, new ImageIcon(
						"src\\resources\\java_file_obj.gif"));
			} else {
				docTabbedPane.setIconAt(tabNum, new ImageIcon(
						"src\\resources\\file_obj.gif"));
			}
			tabNum++;
		}

		Document doc;
		File f;
	}

	/**
	 * Thread to save a document to file
	 */
	class FileSaver extends Thread {
		Document doc;
		File f;

		FileSaver(File f, Document doc) {
			setPriority(4);
			this.f = f;
			this.doc = doc;
		}

		public void run() {
			try {
				// initialize the statusbar
				// status.removeAll();
				JProgressBar progress = new JProgressBar();
				progress.setMinimum(0);
				progress.setMaximum((int) doc.getLength());
				// status.add(progress);
				// status.revalidate();

				// start writing
				Writer out = new FileWriter(f);
				Segment text = new Segment();
				text.setPartialReturn(true);
				int charsLeft = doc.getLength();
				int offset = 0;
				while (charsLeft > 0) {
					doc.getText(offset, Math.min(4096, charsLeft), text);
					out.write(text.array, text.offset, text.count);
					charsLeft -= text.count;
					offset += text.count;
					progress.setValue(offset);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				final String msg = e.getMessage();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(getFrame(),
								"Could not save file: " + msg,
								"Error saving file", JOptionPane.ERROR_MESSAGE);
					}
				});
			} catch (BadLocationException e) {
				System.err.println(e.getMessage());
			}
			// we are done... get rid of progressbar
			// status.removeAll();
			// status.revalidate();
		}
	}

	class ViewClassPanel extends JPanel {
		JClassTree classTree;

		ViewClassPanel() {
			classTree = new JClassTree();
			this.setLayout(new BorderLayout());
			this.add(classTree, BorderLayout.CENTER);
		}

		public void updateFileTree(JClassTree tree) {
			this.remove(classTree);
			this.classTree = tree;
			this.add(classTree);
			this.validate();
		}
	}

	class JClassTree extends JPanel implements MouseListener {
		JTree tree;
		DefaultTreeModel treeModel;

		JClassTree() throws HeadlessException {
			init();
			this.setLayout(new GridLayout(1, 1));
			this.add(new JScrollPane(tree));
			tree.setCellRenderer(new ClassTreeRenderer());
			tree.addMouseListener(this);
		}

		public void init() {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("RootInt");
			treeModel = new DefaultTreeModel(root);
			tree = new JTree(treeModel);

		}

		public void setContentSemantic() {
			ProgramDescriptor programDescriptor = ast.XYZParser.programDescriptor;
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("RootSem");

			Iterator<Symbol> ci = programDescriptor.classSymbolTable.symbols
					.keySet().iterator();
			ClassDescriptor cd;
			MethodDescriptor md;
			FieldDescriptor fd;
			LocalDescriptor ld;
			ParamDescriptor pd;
			Iterator<Symbol> mi;
			Iterator<Symbol> pi;
			Iterator<Symbol> li;
			Iterator<Symbol> fi;
			boolean isMainClass = false;
			while (ci.hasNext()) {
				cd = (ClassDescriptor) programDescriptor.classSymbolTable.symbols
						.get((Symbol) ci.next());

				DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(
						cd.getId());

				fi = cd.fieldSymbolTable.symbols.keySet().iterator();
				while (fi.hasNext()) {
					fd = (FieldDescriptor) cd.fieldSymbolTable.symbols
							.get((Symbol) fi.next());
					DefaultMutableTreeNode fNode = new DefaultMutableTreeNode(
							fd.getInfo());
					classNode.add(fNode);
				}
				mi = cd.methodSymbolTable.symbols.keySet().iterator();
				while (mi.hasNext()) {

					md = (MethodDescriptor) cd.methodSymbolTable.symbols
							.get((Symbol) mi.next());
					if (md.getId().equals("main"))
						isMainClass = true;
					String paramName = "";

					pi = md.parameterSymbolTable.symbols.keySet().iterator();
					while (pi.hasNext()) {
						pd = (ParamDescriptor) md.parameterSymbolTable.symbols
								.get((Symbol) pi.next());
						paramName += pd.getInfo() + ", ";
					}
					if (!paramName.trim().equals(""))
						paramName = paramName.substring(0,
								paramName.length() - 2);
					DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(
							md.getId() + "(" + paramName + ")");

					li = md.localSymbolTable.symbols.keySet().iterator();
					while (li.hasNext()) {
						ld = (LocalDescriptor) md.localSymbolTable.symbols
								.get((Symbol) li.next());
						DefaultMutableTreeNode localNode = new DefaultMutableTreeNode(
								ld.getInfo());
						methodNode.add(localNode);
					}
					classNode.add(methodNode);
				}
				if (isMainClass) {
					String name = "MC:" + classNode.toString();
					classNode.setUserObject(name);
					treeModel.insertNodeInto(classNode, root, 0);
					isMainClass = false;
				} else
					root.add(classNode);
			}

			treeModel = new DefaultTreeModel(root);
			tree = new JTree(treeModel);
			// JOptionPane.showMessageDialog(null,"HEllO");
			this.removeAll();
			this.setLayout(new GridLayout(1, 1));
			this.add(new JScrollPane(tree));
			tree.setCellRenderer(new ClassTreeRenderer());
			tree.addMouseListener(this);

		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

	class ClassTreeRenderer extends DefaultTreeCellRenderer {
		ImageIcon projectIcon;
		ImageIcon mainClassIcon;
		ImageIcon classIcon;
		ImageIcon mainMethIcon;
		ImageIcon methIcon;
		ImageIcon varIcon;

		public ClassTreeRenderer() {
			projectIcon = new ImageIcon("src\\resources\\projects.gif");
			mainClassIcon = new ImageIcon("src\\resources\\newclass_wiz.gif");
			classIcon = new ImageIcon("src\\resources\\class_obj.gif");
			mainMethIcon = new ImageIcon("src\\resources\\mainMeth.gif");
			methIcon = new ImageIcon("src\\resources\\methpub_obj.gif");
			varIcon = new ImageIcon("src\\resources\\field_private_obj.gif");
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			if (value instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				if (node.isRoot()) {
					this.setIcon(projectIcon);
				}
				String tmp = (String) (node.toString());
				if (tmp.indexOf("MC:") != -1) {
					this.setIcon(mainClassIcon);
				} else if (tmp.indexOf("main(String)") != -1) {
					this.setIcon(mainMethIcon);
				} else if (tmp.indexOf("(") != -1) {
					this.setIcon(methIcon);
				} else if (tmp.indexOf(";") != -1) {
					this.setIcon(varIcon);
				} else {
					this.setIcon(classIcon);
				}
			}
			return this;
		}
	}

	class ViewFilePanel extends JPanel {
		JSFileTree fileTree;
		File f = new File("XYZParser");

		ViewFilePanel() {
			if (!(f.exists())) {
				f.mkdir();
			}
			fileTree = new JSFileTree(f);
			this.setLayout(new BorderLayout());
			this.add(fileTree, BorderLayout.CENTER);
		}

		public void updateFileTree(JSFileTree tree) {
			this.remove(fileTree);
			this.fileTree = tree;
			this.add(fileTree);
			this.validate();
		}
	}

	class JSFileTree extends JPanel implements MouseListener {
		JTree tree;
		File root;
		PopupMenu popupMenu1;
		PopupMenu popupMenu2;
		MenuItem menuItem1;
		MenuItem menuItem2;

		JSFileTree(File dir) throws HeadlessException {
			this.root = dir;
			this.popupMenu1 = new PopupMenu();
			this.popupMenu2 = new PopupMenu();
			this.menuItem1 = new MenuItem();
			this.menuItem2 = new MenuItem();
			menuItem1.setLabel("Open File");
			menuItem2.setLabel("Open Folder");
			popupMenu1.add(menuItem1);
			popupMenu2.add(menuItem2);
			this.add(popupMenu1);
			this.add(popupMenu2);
			this.setLayout(new GridLayout(1, 1));
			this.add(new JScrollPane(tree = new JTree(buildTreeModel(dir))));
			tree.setCellRenderer(new FileTreeRenderer());
			tree.putClientProperty("JTree.lineStyle", "Angled");
			tree.addMouseListener(this);
		}
		
		private void fileTreeMouseClicked(MouseEvent e) {
			int mods = e.getModifiers();
			if ((mods & InputEvent.BUTTON3_MASK) != 0) {
				popupMenu1.show(tree, e.getX(), e.getY());
			}
		}

		private void dirTreeMouseClicked(MouseEvent e) {
			int mods = e.getModifiers();
			if ((mods & InputEvent.BUTTON3_MASK) != 0) {
				popupMenu2.show(tree, e.getX(), e.getY());
			}
		}

		private TreeModel buildTreeModel(File dir) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(dir);
			walkthrough(dir, root);
			return new DefaultTreeModel(root);
		}

		private void walkthrough(File f, DefaultMutableTreeNode node) {
			for (File fle : f.listFiles()) {
				DefaultMutableTreeNode n = new DefaultMutableTreeNode(fle);
				node.add(n);
				if (fle.isDirectory()) {
					walkthrough(fle, n);
				}
			}
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				try {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					if (!node.isLeaf()) {
						dirTreeMouseClicked(e);
					}
				} catch (Exception ex) {
				}
				try {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					if (node.isLeaf()) {
						fileTreeMouseClicked(e);
					}
				} catch (Exception ex) {
				}
			}
			
			if (e.getClickCount() == 2) {
				try {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					if (node.isLeaf()) {
						file = new File(node.toString());
						if (!file.isDirectory()) {
							try {
								BufferedReader br = new BufferedReader(
										new FileReader(file));
								int n;
								while ((n = br.read()) != -1) {
									docSize += 2;
								}
							} catch (IOException ee) {

							}

							parse();

							if (file.isFile() && file.canRead()) {
								Document oldDoc = getEditor().getDocument();
								if (oldDoc != null) {
									oldDoc
											.removeUndoableEditListener(undoHandler);
								}

								// frame.setTitle("Java Compiler - " +
								// file.getName());
								Thread loader = new FileLoader(file, editor
										.getDocument());
								loader.start();
							} else {
								JOptionPane.showMessageDialog(getFrame(),
										"Could not open file: " + file,
										"Error opening file",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				} catch (Exception ee) {
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

	class FileTreeRenderer extends DefaultTreeCellRenderer {
		ImageIcon projectIcon = new ImageIcon("src\\resources\\projects.gif");
		ImageIcon srcIcon = new ImageIcon(
				"src\\resources\\packagefolder_obj.gif");
		ImageIcon packageIcon = new ImageIcon("src\\resources\\package_obj.gif");
		ImageIcon javafileIcon = new ImageIcon(
				"src\\resources\\java_file_obj.gif");
		ImageIcon fileIcon = new ImageIcon("src\\resources\\file_obj.gif");
		ImageIcon gifpicIcon = new ImageIcon("src\\resources\\gif_pic_obj.jpg");
		ImageIcon jpgpicIcon = new ImageIcon("src\\resources\\jpg_pic_obj.jpg");
		ImageIcon pngpicIcon = new ImageIcon("src\\resources\\png_pic_obj.jpg");

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			this.setOpenIcon(packageIcon);
			this.setClosedIcon(packageIcon);

			JLabel cmp = (JLabel) super.getTreeCellRendererComponent(tree,
					value, sel, expanded, leaf, row, hasFocus);
			if (value instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) value;
				Object obj = n.getUserObject();
				if (obj instanceof File) {
					File objfile = (File) obj;
					cmp.setText(objfile.getName());
					if (objfile.getName().endsWith(".java")) {
						cmp.setIcon(javafileIcon);
					} else if (objfile.getName().endsWith(".txt")) {
						cmp.setIcon(fileIcon);
					} else if (objfile.getName().endsWith(".gif")) {
						cmp.setIcon(gifpicIcon);
					} else if (objfile.getName().endsWith(".jpg")) {
						cmp.setIcon(jpgpicIcon);
					} else if (objfile.getName().endsWith(".png")) {
						cmp.setIcon(pngpicIcon);
					} else if (((File) obj).isFile()) {
						cmp.setIcon(fileIcon);
					} else if (objfile.getName().equals("XYZParser")) {
						cmp.setIcon(projectIcon);
					} else if (objfile.getName().equals("src")) {
						cmp.setIcon(srcIcon);
					}
					// cmp.setForeground(objfile.isDirectory() ? Color.BLUE :
					// Color.BLACK);
				}
			}
			return cmp;
		}
	}

	class JStatusBar extends JPanel implements Runnable {
		Compiler parent;
		JLabel curWorkPane, caretPane, sizePane;
		String curWorkStr, caretStr, sizeStr;
		Thread thread;

		JStatusBar(Compiler parent) {
			try {
				jsInit(parent);
			} catch (Exception e) {
			}
		}

		public void jsInit(Compiler p) {
			this.parent = p;
			curWorkPane = new JLabel("");
			caretPane = new JLabel("");
			sizePane = new JLabel("");
			thread = new Thread(this);

			this.setLayout(new GridLayout(1, 3));
			this.setSize(1280, 20);
			this.add(curWorkPane);
			this.add(caretPane);
			this.add(sizePane);
			thread.start();
		}

		public void run() {
			while (true) {
				try {
					updateStatus();
					thread.sleep(100);
				} catch (Exception e) {
				}
			}
		}

		public void updateStatus() {
			updateCurWorkPane();
			updateCaretPane();
			updateSizePane();
		}

		public void updateCurWorkPane() {
			curWorkStr = " Ready";
			curWorkPane.setText(curWorkStr);
		}

		public void updateCaretPane() {
			try {
				int lineCountt = lineCount;
				int curLine = getLineOfOffset(editor.getCaretPosition()) + 1;
				int curPos = editor.getCaretPosition()
						- getLineStartOffset(curLine - 1);
				caretStr = "Line:  " + curLine + "       Column:  " + curPos
						+ "      Lines:  " + lineCountt;
				caretPane.setText(caretStr);
			} catch (Exception exp) {
				caretPane.setText("");
			}
		}

		public void updateSizePane() {
			try {
				int size = docSize;
				if ((size >= 0) && (size < 1024))
					sizeStr = size + " Byte";
				else if ((size >= 1024) && (size < (1024 * 1024)))
					sizeStr = (((int) ((size / 1024.0f) * 100.0f)) / 100.0f)
							+ " KB";
				else if ((size >= (1024 * 1024))
						&& (size < (1024 * 1024 * 1024)))
					sizeStr = (((int) ((size / 1024.0f / 1024.0f) * 100.0f)) / 100.0f)
							+ " MB";
				else if (size < 0)
					sizeStr = "";
				sizePane.setText("SIZE : " + sizeStr);
			} catch (Exception exp) {
				sizePane.setText("");
			}
		}
	}
}
