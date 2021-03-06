package com.thaplayaslaya.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.datastructures.Deck;
import com.thaplayaslaya.datastructures.DeckBinder;
import com.thaplayaslaya.datastructures.Style;
import com.thaplayaslaya.gui.dialogs.DeckBinderEditDialog;

public class DeckBinderPanel extends JPanel implements ActionListener {

	public static final int MAX_HORIZONTAL = 200;
	public static final int MIN_HORIZONTAL = 60;
	private static final long serialVersionUID = -1215607079828446786L;
	private String name = "[Default Name]", upArrow = "UpArrow", downArrow = "DownArrow";
	private JLabel dBName = new JLabel(this.name, JLabel.LEFT);
	private JComboBox<Deck> comboBox = new DeckBinderComboBox<Deck>();
	private JButton renameButton = new JButton("E"), deleteButton = new JButton("D");
	private ItemChangeListener itemChangeListener = new ItemChangeListener();
	private cBFocusListener focusListener = new cBFocusListener();
	private boolean hasListenersEnabled = false;

	public DeckBinderPanel() {
		init(true);
	}

	// Copy Constructor + non-Functioning
	protected DeckBinderPanel(DeckBinderPanel dbp) {
		init(false);
		setName(dbp.getName());
		DeckBinder db = DeckManager.getCase().getDeckBinder(dbp.getName());
		for (Deck d : db.getDecks()) {
			getComboBox().addItem(d);
		}
		if (null != db.getStyle()) {
			Style.applyStyle(dBName, db.getStyle());
		}
		((DeckBinderComboBox<Deck>) comboBox).disableNoteWindows();
	}

	public DeckBinderPanel(String name, Style style) {
		init(true);
		if (null != style) {
			Style.applyStyle(dBName, style);
		}
		setName(name);
	}

	private void init(Boolean isFunctional) {
		renameButton.setFont(new Font("Dialog", 0, 10)); // NOI18N
		renameButton.setMargin(new java.awt.Insets(0, 2, 0, 2));

		deleteButton.setFont(new Font("Dialog", 0, 10)); // NOI18N
		deleteButton.setMargin(new java.awt.Insets(0, 2, 0, 2));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(dBName, 50, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGap(20, 20, 20).addComponent(renameButton)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(deleteButton))
										.addComponent(comboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(dBName)
										.addComponent(renameButton).addComponent(deleteButton))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		// Need this regardless of isFunctional
		// Primarily for preview panel of DBEditDialog
		// to update properly.

		comboBox.addItemListener(new StyleUpdateListener());
		comboBox.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				comboBox.transferFocusUpCycle();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

		});

		if (isFunctional) {
			comboBox.setToolTipText("Move (Shft+UP/DOWN)");

			renameButton.setToolTipText("Edit Deck Binder");
			renameButton.addActionListener(this);

			deleteButton.setToolTipText("Delete Deck Binder");
			deleteButton.addActionListener(this);

			dBName.setToolTipText("Move (Ctrl+UP/DOWN)");

			DeckManager.getDeckManagerGUI().getDeckBinderPanels().add(this);
		}
	}

	public void setListeners() {
		enableListeners();
		// Add keybinds to manipulate item order inside the comboBox.
		comboBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK), upArrow);
		comboBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK), downArrow);

		comboBox.getActionMap().put(upArrow, new VertArrowAction(upArrow));
		comboBox.getActionMap().put(downArrow, new VertArrowAction(downArrow));
	}

	public void disableListeners() {
		if (hasListenersEnabled) {
			comboBox.removeItemListener(itemChangeListener);
			comboBox.removeFocusListener(focusListener);
			this.hasListenersEnabled = false;
		}
	}

	public void enableListeners() {
		if (!hasListenersEnabled) {
			comboBox.addItemListener(itemChangeListener);
			comboBox.addFocusListener(focusListener);
			this.hasListenersEnabled = true;
		}
	}

	@Override
	public void setName(String name) {
		this.name = name;
		dBName.setName(name);
		dBName.setText(name);
		comboBox.setName(name);
	}

	@Override
	public String getName() {
		return this.name;
	}

	public JLabel getdBName() {
		return dBName;
	}

	public JComboBox<Deck> getComboBox() {
		return comboBox;
	}

	private class VertArrowAction extends AbstractAction {

		private static final long serialVersionUID = 2644070230078784280L;

		public VertArrowAction(String text) {
			super(text);
			putValue(ACTION_COMMAND_KEY, text);
		}

		// Move Items up/down within the JComboBox
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>) e.getSource();
			DeckBinder db = DeckManager.getCase().getDeckBinder(name);
			Deck selectedDeck = (Deck) comboBox.getSelectedItem();
			int selectedIndex = comboBox.getSelectedIndex();
			ItemListener il = null;
			loop: for (ItemListener l : comboBox.getItemListeners()) {
				if (l instanceof StyleUpdateListener) {
					il = l;
					comboBox.removeItemListener(l);
					break loop;
				}
			}
			if (actionCommand.equals(upArrow) && selectedIndex > 0) {
				db.removeDeck(selectedDeck);
				db.insertDeckAt(selectedDeck, selectedIndex - 1);
				comboBox.setSelectedIndex(selectedIndex - 1);
			} else if (actionCommand.equals(downArrow) && selectedIndex < comboBox.getItemCount() - 2) {
				db.removeDeck(selectedDeck);
				db.insertDeckAt(selectedDeck, selectedIndex + 1);
				comboBox.setSelectedIndex(selectedIndex + 1);
			}
			if (null != il) {
				comboBox.addItemListener(il);
			}
		}
	}

	private class ItemChangeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Deck deck = ((Deck) e.getItem());
				// this might be an unnecessary check.
				if ((deck.getName() != null) && (deck.getName().length() > 0)) {
					if (!deck.equals(Deck.DEFAULT)) {
						DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(deck);
					} else {
						DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().addNewDeck();
					}
				}
			}
		}
	}

	private class StyleUpdateListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			if (e.getStateChange() == ItemEvent.SELECTED) {
				Deck deck = ((Deck) e.getItem());
				if (deck != Deck.DEFAULT) {
					System.out.println("Applying style to " + deck.getName() + "[DeckBinderPanel:250]");
					Style.applyStyle(comboBox, deck.getStyle());
					comboBox.transferFocusUpCycle();
				}
			}

		}

	}

	private class cBFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<Deck> comboBox = (JComboBox<Deck>) e.getComponent();
			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(comboBox.getName());

			if (comboBox.getSelectedItem().equals(Deck.DEFAULT)) {
				DeckManager.getDeckManagerGUI().getCurrentlySelectedDeckBinder().addNewDeck();
			}

			DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck((Deck) comboBox.getSelectedItem());
		}

		@Override
		public void focusLost(FocusEvent e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (hasListenersEnabled) {
			if (e.getActionCommand().equals("E")) {
				new DeckBinderEditDialog(DeckManager.getCase().getDeckBinder(dBName.getText()));
			} else if (e.getActionCommand().equals("D")) {
				if (JOptionPane.showConfirmDialog(DeckManager.getDeckManagerGUI(), "Are you sure you want to delete this deck binder?") == JOptionPane.YES_OPTION) {
					DeckManager.getCase().removeDeckBinder(DeckManager.getCase().getDeckBinder(this.name));
				}
			}

		}

	}

	// Returns non-functioning copy of DBP
	public DeckBinderPanel copy() {
		return new DeckBinderPanel(this);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(30 + dBName.getSize().width + renameButton.getSize().width * 2, 6 + renameButton.getSize().height
				+ comboBox.getSize().height);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(MAX_HORIZONTAL, Short.MAX_VALUE);
	}
}
