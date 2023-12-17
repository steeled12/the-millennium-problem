package com.gruppo3.game.model.dialog;

import java.util.ArrayList;
import java.util.List;

public class LinearDialogNode implements DialogNode {
	
	private String text;
	private int id;
	private List<Integer> pointers = new ArrayList<Integer>();
	
	public LinearDialogNode(String text, int id) {
		this.text = text;
		this.id = id;
	}
	
	public void setPointer(int id) {
		pointers.add(id);
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

}