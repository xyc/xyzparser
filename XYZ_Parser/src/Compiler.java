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
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.*;

import parser.*;

/**
 * Sample JavaCC compiler
 * 
 * @author Atlantis Mao
 * @version 1.01 03/17/09
 */

public class Compiler extends JPanel {

	private XYZParser parser;
	private static ResourceBundle resources;
	private final static String EXIT_AFTER_PAINT = new String("-exit");
	private static boolean exitAfterFirstPaint;
	private int i=0;

	static {
		try {
			resources = ResourceBundle.getBundle("resources.Notepad", Locale
					.getDefault());
		} catch (MissingResourceException mre) {
			System.err.println("resources/Notepad.properties not found");
			System.exit(1);
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
	public void parse1(InputStream stream){
		try {
			if(parser==null)
				parser = new XYZParser(stream);
			else{
				XYZParser.ReInit(stream);
			}

			parser.lexicalAnalyze();
			
			stream.close();

			tipPanel.setLayout(new BorderLayout());

			consoleArea.setForeground(new Color(255, 0, 0));
			consoleArea.setFont(new Font("Courier New", Font.PLAIN, 14));
			consoleArea.append("there are " + parser.errors.size()
					+ " errors" + "\n");

			for (int i = 0; i < parser.errors.size(); i++) {
				JLabel tipLabel = new JLabel();
				tipLabel.setIcon(new ImageIcon(
						"src\\resources\\error_tsk.gif"));
				tipLabel.setBounds(0, 15 * (parser.errors.get(i).getErrorLine() - 1), 20, 20);
				tipLabel.setToolTipText(parser.errors.get(i)
						.getErrorMessage());
				tipPanel.add(tipLabel);

				consoleArea.removeAll();
				consoleArea.append("Error " + (i + 1) + " line "
						+ parser.errors.get(i).getErrorLine() + ": ");
				consoleArea.append(", Column "
						+ parser.errors.get(i).getErrorColumn() + "\n");
				consoleArea.append("\t errorMessage: "
						+ parser.errors.get(i).getErrorMessage() + "\n");

			}
			
			consoleArea.append("end" + "\n");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void parse() {
		try {
			FileInputStream stream = new FileInputStream(file);

			
			if(parser==null)
				parser = new XYZParser(stream);
			else{
				XYZParser.ReInit(stream);
			}

			parser.lexicalAnalyze();
			
			stream.close();

			tipPanel.setLayout(new BorderLayout());

			consoleArea.setForeground(new Color(255, 0, 0));
			consoleArea.setFont(new Font("Courier New", Font.PLAIN, 14));
			consoleArea.append("there are " + parser.errors.size()
					+ " errors" + "\n");

			for (int i = 0; i < parser.errors.size(); i++) {
				JLabel tipLabel = new JLabel();
				tipLabel.setIcon(new ImageIcon(
						"src\\resources\\error_tsk.gif"));
				tipLabel.setBounds(0, 15 * (parser.errors.get(i).getErrorLine() - 1), 20, 20);
				tipLabel.setToolTipText(parser.errors.get(i)
						.getErrorMessage());
				tipPanel.add(tipLabel);

				consoleArea.removeAll();
				consoleArea.append("Error: " + (i + 1) + " line "
						+ parser.errors.get(i).getErrorLine());
				consoleArea.append(", Column "
						+ parser.errors.get(i).getErrorColumn() + "\n");
				consoleArea.append("\t errorMessage: "
						+ parser.errors.get(i).getErrorMessage() + "\n");

			}
			
			consoleArea.append("end" + "\n");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Compiler() {
		super(true);

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.err.println("Error loading L&F: " + e);
		}

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
		editor.setPreferredSize(new Dimension(930, 500));
		editor.addKeyListener(new KeyHandler());

		lineNumPane = new JTextArea();
		lineNumPane.setEditable(false);
		lineNumPane.setForeground(null);
		lineNumPane.setBackground(new Color(200, 200, 240));
		lineNumPane.setFont(new Font("Courier New", Font.PLAIN, 13));

		JPanel lowLevelPane = new JPanel();
		lowLevelPane.setLayout(new BorderLayout());
		lowLevelPane.add(tipPanel, BorderLayout.CENTER);
		lowLevelPane.add(editor, BorderLayout.EAST);
		lowLevelPane.add(lineNumPane, BorderLayout.WEST);

		outputPane.add("Console", new JScrollPane(consoleArea));
		outputPane.add("I/O Message", new JScrollPane(IOArea));
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setPreferredSize(new Dimension(300, 500));
		JSplitPane splitPane_1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				panel_1, new JScrollPane(lowLevelPane));
		JSplitPane splitPane_2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				splitPane_1, outputPane);
		splitPane_1.setContinuousLayout(true);
		splitPane_1.setOneTouchExpandable(true);
		splitPane_2.setContinuousLayout(true);
		splitPane_2.setOneTouchExpandable(true);
		add("North", createToolbar());
		add("Center", splitPane_2);
		add("South", createStatusbar());

		
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

			JFrame frame = new JFrame();
			//frame.addKeyListener(new Key());
			ImageIcon logoImage = new ImageIcon("src\\resources\\logo.png");
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
		JMenuItem mi = new JMenuItem(getResourceString(cmd + labelSuffix));
		URL url = getResource(cmd + imageSuffix);
		if (url != null) {
			mi.setHorizontalTextPosition(JButton.RIGHT);
			mi.setIcon(new ImageIcon(url));
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

	// private int lineCount;
	public File file;
	public JTabbedPane outputPane = new JTabbedPane();
	public JPanel tipPanel = new JPanel();
	public JTextArea consoleArea = new JTextArea();
	public JTextArea IOArea = new JTextArea();
	private JTextArea lineNumPane;
	private JEditorPane editor;
	private Hashtable commands;
	private Hashtable menuItems;
	private JMenuBar menubar;
	private JToolBar toolbar;
	private JComponent status;
	private JFrame elementTreeFrame;
	protected ElementTreePanel elementTreePanel;

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
						InputStream stream = new ByteArrayInputStream(editor.getText().getBytes());

						
						if(parser==null)
							parser = new XYZParser(stream);
						else{
							XYZParser.ReInit(stream);
						}

						parser.lexicalAnalyze();
						
						stream.close();

						tipPanel.setLayout(new BorderLayout());

						consoleArea.setForeground(new Color(255, 0, 0));
						consoleArea.setFont(new Font("Courier New", Font.PLAIN, 14));
						consoleArea.append("there are " + parser.errors.size()
								+ " errors" + "\n");

						for (int i = 0; i < parser.errors.size(); i++) {
							JLabel tipLabel = new JLabel();
							tipLabel.setIcon(new ImageIcon(
									"src\\resources\\error_tsk.gif"));
							tipLabel.setBounds(0, 15 * (parser.errors.get(i).getErrorLine() - 1), 20, 20);
							tipLabel.setToolTipText(parser.errors.get(i)
									.getErrorMessage());
							tipPanel.add(tipLabel);

							consoleArea.removeAll();
							consoleArea.append("Error: " + (i + 1) + " line "
									+ parser.errors.get(i).getErrorLine());
							consoleArea.append(", Column "
									+ parser.errors.get(i).getErrorColumn() + "\n");
							consoleArea.append("\t errorMessage: "
									+ parser.errors.get(i).getErrorMessage() + "\n");

						}
						
						consoleArea.append("end" + "\n");

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
			new SaveAction(), new ExitAction(), new ShowElementTreeAction(),
			undoAction, redoAction, new LineNumAction(), new OutputAction(),
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
			JFileChooser chooser = new JFileChooser();
			int ret = chooser.showOpenDialog(frame);

			if (ret != JFileChooser.APPROVE_OPTION) {
				return;
			}

			file = chooser.getSelectedFile();
			parse();

			if (file.isFile() && file.canRead()) {
				Document oldDoc = getEditor().getDocument();
				if (oldDoc != null) {
					oldDoc.removeUndoableEditListener(undoHandler);
				}
				if (elementTreePanel != null) {
					elementTreePanel.setEditor(null);
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
			JFileChooser chooser = new JFileChooser();
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
			Document oldDoc = getEditor().getDocument();
			if (oldDoc != null) {
				oldDoc.removeUndoableEditListener(undoHandler);
				// oldDoc.addDocumentListener(documentListener);
			}
			getEditor().setDocument(new PlainDocument());
			getEditor().getDocument().addUndoableEditListener(undoHandler);
			// getEditor().getDocument().addDocumentListener(documentListener);
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

	/**
	 * Action that brings up a JFrame with a JTree showing the structure of the
	 * document.
	 */
	class ShowElementTreeAction extends AbstractAction {

		ShowElementTreeAction() {
			super(showElementTreeAction);
		}

		ShowElementTreeAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e) {
			if (elementTreeFrame == null) {
				// Create a frame containing an instance of
				// ElementTreePanel.
				try {
					String title = resources.getString("ElementTreeFrameTitle");
					elementTreeFrame = new JFrame(title);
				} catch (MissingResourceException mre) {
					elementTreeFrame = new JFrame();
				}

				elementTreeFrame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent weeee) {
						elementTreeFrame.setVisible(false);
					}
				});
				Container fContentPane = elementTreeFrame.getContentPane();

				fContentPane.setLayout(new BorderLayout());
				elementTreePanel = new ElementTreePanel(getEditor());
				fContentPane.add(elementTreePanel);
				elementTreeFrame.pack();
			}
			elementTreeFrame.show();
		}
	}

	class LineNumAction extends AbstractAction {
		LineNumAction() {
			super(lineNumAction);
		}

		LineNumAction(String nm) {
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
				status.removeAll();
				JProgressBar progress = new JProgressBar();
				progress.setMinimum(0);
				progress.setMaximum((int) f.length());
				status.add(progress);
				status.revalidate();

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
			status.removeAll();
			status.revalidate();

			resetUndoManager();

			if (elementTreePanel != null) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						elementTreePanel.setEditor(getEditor());
					}
				});
			}

			int lineCount;
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
				status.removeAll();
				JProgressBar progress = new JProgressBar();
				progress.setMinimum(0);
				progress.setMaximum((int) doc.getLength());
				status.add(progress);
				status.revalidate();

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
			status.removeAll();
			status.revalidate();
		}
	}
}
