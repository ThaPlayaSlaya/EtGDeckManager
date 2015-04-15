package com.thaplayaslaya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Config {

	private static final String cfgFile = "cfg.json";
	private static Case briefcase;
	private File file;
	private String json;
	private Gson gson = DeckManager.gson;

	public Config() {
		file = new File(cfgFile);
		if (!file.isFile()) {
			System.out.println("no file");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			briefcase = new Case();
		} else {
			readCaseData();
		}
	}

	public Case getCase() {
		return briefcase;
	}

	private void readCaseData() {
		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			briefcase = gson.fromJson(br, Case.class);

			if (briefcase == null) {
				briefcase = new Case();
			}

			for (DeckBinder db : briefcase.getDeckBinders()) {
				db.setDBP();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile() {
		json = DeckManager.gson.toJson(briefcase);
		try {
			if (!file.isFile()) {
				file.createNewFile();
			}

			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
