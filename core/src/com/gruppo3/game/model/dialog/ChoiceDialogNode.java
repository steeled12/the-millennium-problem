package com.gruppo3.game.model.dialog;

import java.util.ArrayList;
import java.util.List;

public class ChoiceDialogNode implements DialogNode {
	
	private String text;
	private int id;
	
	private List<Integer> pointers = new ArrayList<Integer>();
	private List<String> labels = new ArrayList<String>();
	
	public ChoiceDialogNode(String text, int id) {
		this.text = text;
		this.id = id;
	}
	
	public void addChoice(String text, int targetId) {
		pointers.add(targetId);
		labels.add(text);
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
	
	public List<String> getLabels() {
		return labels;
	}

}