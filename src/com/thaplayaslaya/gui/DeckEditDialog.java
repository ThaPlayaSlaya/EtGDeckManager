package com.thaplayaslaya.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.datastructures.Style;

public class DeckEditDialog extends JDialog {

	private static final long serialVersionUID = -2241892616536917822L;
	private JPanel appearancePanel;
	private JButton backgroundColorButton;
	private JToggleButton boldToggle;
	private JButton cancelButton;
	private JPanel deckBinderSettingsPanel;
	private JButton defaultButton;
	private JButton doneButton;
	private JButton foregroundColorButton;
	private JToggleButton italicToggle;
	private JLabel jLabel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JTabbedPane tabPane;
	private JTextField jTextField1;
	private JLabel nameLabel;
	private JPanel previewPanel;
	private JToggleButton strikethroughToggle;
	private JToggleButton underlineToggle;

	private FontEffectActionListener fontEffectActionListener;
	private AppearanceActionListener appearanceActionListener;

	private Deck originalDeck;
	private Deck newDeck;
	private JTextArea importCodeTextArea;
	private JTextArea deckNotesTextArea;
	private UndoManager undoManager;

	public DeckEditDialog(Deck deck) {
		super(DeckManager.getDeckManagerGUI(), "Edit Deck", true);
		this.originalDeck = deck;
		this.newDeck = deck.clone();
		initComponents();
		setLocation(DeckManager.getDeckManagerGUI().getSmartExternalWindowLocation(this));
		jTextField1.selectAll();
		setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		appearancePanel = new JPanel();
		foregroundColorButton = new JButton();
		backgroundColorButton = new JButton();
		jPanel2 = new JPanel();
		defaultButton = new JButton();
		previewPanel = new JPanel();
		jLabel1 = new JLabel();
		deckBinderSettingsPanel = new JPanel();
		nameLabel = new JLabel();
		jTextField1 = new JTextField();
		boldToggle = new JToggleButton();
		italicToggle = new JToggleButton();
		underlineToggle = new JToggleButton();
		strikethroughToggle = new JToggleButton();
		tabPane = new JTabbedPane();
		jPanel3 = new JPanel();
		doneButton = new JButton();
		cancelButton = new JButton();

		fontEffectActionListener = new FontEffectActionListener(jLabel1, newDeck);
		appearanceActionListener = new AppearanceActionListener(this, jLabel1, newDeck);

		undoManager = new UndoManager();
		importCodeTextArea = new JTextArea(7, 20);
		deckNotesTextArea = new JTextArea();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		importCodeTextArea.setText(newDeck.getImportCode());
		deckNotesTextArea.setText(newDeck.getNotes());

		initTextArea(importCodeTextArea);
		tabPane.addTab("Code", importCodeTextArea);

		initTextArea(deckNotesTextArea);
		JScrollPane scroll = new JScrollPane(deckNotesTextArea);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		tabPane.addTab("Notes", scroll);

		appearancePanel.setBorder(BorderFactory.createTitledBorder("Appearance"));

		foregroundColorButton.setText("Foreground Color");
		foregroundColorButton.addActionListener(appearanceActionListener);

		backgroundColorButton.setText("Background Color");
		backgroundColorButton.addActionListener(appearanceActionListener);

		defaultButton.setText("Default");
		defaultButton.addActionListener(appearanceActionListener);

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
				jPanel2Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(defaultButton)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(defaultButton,
				GroupLayout.Alignment.TRAILING));

		GroupLayout appearancePanelLayout = new GroupLayout(appearancePanel);
		appearancePanel.setLayout(appearancePanelLayout);
		appearancePanelLayout.setHorizontalGroup(appearancePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				appearancePanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								appearancePanelLayout
										.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(backgroundColorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(foregroundColorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jPanel2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addContainerGap()));
		appearancePanelLayout.setVerticalGroup(appearancePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				appearancePanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(foregroundColorButton).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(backgroundColorButton).addGap(11, 11, 11)
						.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		previewPanel.setBorder(BorderFactory.createTitledBorder("Preview"));
		previewPanel.setLayout(new java.awt.GridBagLayout());

		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setOpaque(true);
		Style.applyStyle(jLabel1, newDeck.getStyle());
		jLabel1.setText(newDeck.getName());

		previewPanel.add(jLabel1, new java.awt.GridBagConstraints());

		deckBinderSettingsPanel.setBorder(BorderFactory.createTitledBorder("Deck Settings"));

		nameLabel.setText("Name:");

		jTextField1.setText(newDeck.getName());
		jTextField1.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				updatePreview();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updatePreview();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updatePreview();
			}

			private void updatePreview() {
				System.out.println("HI");
				jLabel1.setText(jTextField1.getText());
			}
		});

		if (newDeck.getStyle().isBold()) {
			boldToggle.setSelected(true);
		}
		boldToggle.setFont(UIManager.getFont("Button.font").deriveFont(Font.BOLD));
		boldToggle.setText("B");
		boldToggle.addActionListener(fontEffectActionListener);

		if (newDeck.getStyle().isItalic()) {
			italicToggle.setSelected(true);
		}
		italicToggle.setFont(UIManager.getFont("Button.font").deriveFont(Font.PLAIN).deriveFont(Font.ITALIC));
		italicToggle.setText("I");
		italicToggle.addActionListener(fontEffectActionListener);

		if (newDeck.getStyle().isUnderline()) {
			underlineToggle.setSelected(true);
		}
		underlineToggle.setText("U");
		Font font = UIManager.getFont("Button.font").deriveFont(Font.PLAIN);
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		underlineToggle.setFont(new Font(attributes));
		underlineToggle.addActionListener(fontEffectActionListener);

		if (newDeck.getStyle().isStrikethrough()) {
			strikethroughToggle.setSelected(true);
		}
		strikethroughToggle.setText("abc");
		Font font1 = UIManager.getFont("Button.font").deriveFont(Font.PLAIN);
		Map attributes1 = font1.getAttributes();
		attributes1.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		strikethroughToggle.setFont(new Font(attributes1));
		strikethroughToggle.addActionListener(fontEffectActionListener);

		doneButton.setText("Done");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (validateDone()) {
					originalDeck.setName(jTextField1.getText());
					originalDeck.setStyle(newDeck.getStyle());
					originalDeck.setImportCode(newDeck.getImportCode());
					originalDeck.setNotes(newDeck.getNotes());
					Style.applyStyle(DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().getDBP().getComboBox(), newDeck.getStyle());
					DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().getDBP().getComboBox().getComponent(0)
							.setBackground(UIManager.getColor("ComboBox.background"));
					dispose();
				}
			}
		});

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dispose();
			}
		});

		GroupLayout deckBinderSettingsPanelLayout = new GroupLayout(deckBinderSettingsPanel);
		deckBinderSettingsPanel.setLayout(deckBinderSettingsPanelLayout);
		deckBinderSettingsPanelLayout.setHorizontalGroup(deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				deckBinderSettingsPanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								deckBinderSettingsPanelLayout
										.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(tabPane, GroupLayout.Alignment.LEADING)
										.addGroup(
												GroupLayout.Alignment.LEADING,
												deckBinderSettingsPanelLayout.createSequentialGroup().addComponent(nameLabel)
														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jTextField1))
										.addGroup(
												GroupLayout.Alignment.LEADING,
												deckBinderSettingsPanelLayout.createSequentialGroup().addComponent(boldToggle)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(italicToggle)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(underlineToggle)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(strikethroughToggle)
														.addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
		deckBinderSettingsPanelLayout.setVerticalGroup(deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				deckBinderSettingsPanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLabel)
										.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								deckBinderSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(boldToggle)
										.addComponent(italicToggle).addComponent(underlineToggle).addComponent(strikethroughToggle))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(tabPane).addContainerGap()));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 23, Short.MAX_VALUE));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE).addComponent(doneButton)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(cancelButton))
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(deckBinderSettingsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(
																layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																		.addComponent(previewPanel, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(appearancePanel, GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(
												GroupLayout.Alignment.TRAILING,
												layout.createSequentialGroup()
														.addComponent(appearancePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(previewPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addComponent(deckBinderSettingsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(jPanel3, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(
												GroupLayout.Alignment.TRAILING,
												layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(doneButton)
														.addComponent(cancelButton))).addContainerGap()));

		pack();
	}

	public boolean validateDone() {
		boolean b = false;
		if (1 > jTextField1.getText().length()) {
			JOptionPane.showMessageDialog(DeckEditDialog.this, "The name field is blank. Please enter a name.", "Try again",
					JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}
		DeckBinder db = DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder();
		if (null == db || (db.containsDeck(jTextField1.getText()) && !jTextField1.getText().equals(originalDeck.getName()))) {
			JOptionPane.showMessageDialog(this, "Sorry, \"" + jTextField1.getText() + "\" " + "already exists in this deck binder.\n"
					+ "Please enter a different name.", "Try again", JOptionPane.ERROR_MESSAGE);
			jTextField1.requestFocusInWindow();
			return b;
		}

		b = true;
		return b;
	}

	public void initTextArea(JTextArea textArea) {
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		Document doc = textArea.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});

		InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = textArea.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

		am.put("Undo", new AbstractAction() {

			private static final long serialVersionUID = 3219481349254783881L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canUndo()) {
						undoManager.undo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
		am.put("Redo", new AbstractAction() {

			private static final long serialVersionUID = -334636873359044400L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canRedo()) {
						undoManager.redo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
	}
}
