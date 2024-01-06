package com.gruppo3.game.model.dialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gruppo3.game.util.Action;

public class ChoiceDialogNode implements DialogNode {
	
	private String text;
	private int id;
	
	private List<Integer> pointers = new ArrayList<Integer>();
	private Map<String, Action> options = new LinkedHashMap<String, Action>();
	
	public ChoiceDialogNode(String text, int id) {
		this.text = text;
		this.id = id;
	}

	//nessun nodo dopo scelta
	public void addChoice(String text) {
		pointers.add(-1);
		options.put(text, null);
	}
	
	//nodo senza azione dopo scelta
	public void addChoice(String text, int targetId) {
		pointers.add(targetId);
		options.put(text, null);
	}

	//nodo con azione dopo scelta
	public void addChoice(String text, int targetId, Action action) {
		pointers.add(targetId);
		options.put(text, action);
	}
	
	public String getText() {
		return text;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public List<Integer> getPointers() {
		return pointers;
	}
	
	public Map<String, Action> getOptions() {
		return options;
	}

}