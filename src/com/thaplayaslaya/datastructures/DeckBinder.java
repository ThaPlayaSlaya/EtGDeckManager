package com.thaplayaslaya.datastructures;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.gui.DeckBinderPanel;
import com.thaplayaslaya.gui.dialogs.DeckAddDialog;

public class DeckBinder implements IStylish {

	private String name;
	private Style style = new Style(UIManager.getColor("Label.foreground"), UIManager.getColor("Label.background"));
	private transient DeckBinderPanel dBP;
	private ArrayList<Deck> decks = new ArrayList<Deck>();

	// If this constructor is called, it is because
	// the user made a new DeckBinder.
	public DeckBinder(String name) {
		this.name = name;
		DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(name);
		DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(null);
		DeckManager.cfg.getCase().addDeckBinder(this);

		dBP = new DeckBinderPanel(this.name, this.style);
		finalizeDBSetup();
	}

	// Copy Constructor
	protected DeckBinder(DeckBinder db) {
		this.name = db.getName();
		this.style = db.getStyle().copy();
		for (Deck d : db.getDecks()) {
			this.decks.add(d.clone());
		}
		dBP = db.getDBP().copy();
	}

	// called once Config has populated Case completely with JSON data.
	public void setDBP() {
		dBP = new DeckBinderPanel(this.name, this.style);

		for (Deck d : decks) {
			dBP.getComboBox().addItem(d);
		}

		finalizeDBSetup();
	}

	private void finalizeDBSetup() {
		dBP.getComboBox().addItem(Deck.DEFAULT);
		dBP.setListeners();
		if (decks.isEmpty()) {
			Style.applyStyle(dBP.getComboBox(), Deck.DEFAULT.getStyle());
		} else {
			Style.applyStyle(dBP.getComboBox(), decks.get(0).getStyle());
		}
	}

	// System is adding a deck. (if user, see "addNewDeck()")
	// adds deck to end of DBP, but above "add new deck"
	public void addDeck(Deck deck) {
		decks.add(deck);
		dBP.getComboBox().insertItemAt(deck, dBP.getComboBox().getItemCount() - 1);
	}

	public void insertDeckAt(Deck deck, int index) {
		decks.add(index, deck);
		dBP.getComboBox().insertItemAt(deck, index);
	}

	public void removeDeck(Deck deck) {
		decks.remove(deck);
		dBP.getComboBox().removeItem(deck);
	}

	public void setName(String name) {
		this.name = name;
		dBP.setName(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
		Style.applyStyle(dBP.getdBName(), this.style);
	}

	public Deck getDeck(String name) {
		for (Deck d : this.decks) {
			if (d.getName().equals(name)) {
				return d;
			}
		}
		return null;
	}

	public ArrayList<Deck> getDecks() {
		return decks;
	}

	public void setDecks(DefaultComboBoxModel<Deck> model) {
		this.decks = new ArrayList<Deck>();
		for (int i = 0; i < model.getSize(); i++) {
			this.decks.add(model.getElementAt(i));
		}
		dBP.disableListeners();
		this.dBP.getComboBox().setModel(model);
		dBP.getComboBox().addItem(Deck.DEFAULT);
		dBP.enableListeners();
	}

	public boolean containsDeck(String name) {
		for (Deck d : this.getDecks()) {
			if (d.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// User is adding a new deck.
	public void addNewDeck() {
		new DeckAddDialog();
	}

	public void delete(DeckBinder deckBinder) {
		DeckManager.getDeckManagerGUI().removeDeckBinderPanel(deckBinder.dBP);
		deckBinder = null;
	}

	public DeckBinder copy() {
		return new DeckBinder(this);
	}

	@Override
	public String toString() {
		return name;
	}

	// Functions do the same thing
	// TODO: Consolidate later.
	public DeckBinderPanel getDeckBinderPanel() {
		return dBP;
	}

	public DeckBinderPanel getDBP() {
		return dBP;
	}
}
