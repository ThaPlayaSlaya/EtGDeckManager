package com.thaplayaslaya.datastructures;

import java.util.ArrayList;

import com.thaplayaslaya.DeckManager;
import com.thaplayaslaya.gui.CustomDialog;
import com.thaplayaslaya.gui.DeckBinderPanel;

public class DeckBinder {

	private String name;
	private transient DeckBinderPanel dBP;
	private ArrayList<Deck> decks = new ArrayList<Deck>();

	public DeckBinder() {

	}

	// If this constructor is called, it is because
	// the user made a new DeckBinder.
	public DeckBinder(String name) {
		this.name = name;
		DeckManager.getDeckManagerGUI().setCurrentlySelectedDeckBinder(name);
		DeckManager.getDeckManagerGUI().setCurrentlySelectedDeck(null);
		DeckManager.cfg.getCase().addDeckBinder(this);

		dBP = new DeckBinderPanel(this.name);
		finalizeDBSetup();
	}

	// called once Config has populated Case completely with JSON data.
	public void setDBP() {
		dBP = new DeckBinderPanel(this.name);

		for (Deck d : decks) {
			dBP.getComboBox().addItem(d);
		}

		finalizeDBSetup();
	}

	private void finalizeDBSetup() {
		dBP.getComboBox().addItem(Deck.DEFAULT);
		dBP.setListeners();
	}

	// System is adding a deck. (if user, see "addNewDeck()")
	public void addDeck(Deck deck) {
		decks.add(deck);
		dBP.getComboBox().insertItemAt(deck, dBP.getComboBox().getItemCount() - 1);
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

	public Deck getDeck(String name) {
		for (Deck d : this.decks) {
			if (d.getName().equals(name)) {
				return d;
			}
		}
		return null;
	}

	public DeckBinderPanel getDeckBinderPanel() {
		return dBP;
	}

	public ArrayList<Deck> getDecks() {
		return decks;
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
		new CustomDialog(DeckManager.getDeckManagerGUI(), OperationType.ADD_NEW_DECK, null);
	}

	public void delete(DeckBinder deckBinder) {
		DeckManager.getDeckManagerGUI().removeDeckBinderPanel(deckBinder.dBP);
		deckBinder = null;
	}
}