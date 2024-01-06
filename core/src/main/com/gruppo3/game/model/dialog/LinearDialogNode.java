package com.gruppo3.game.model.dialog;

import java.util.ArrayList;
import java.util.List;

import com.gruppo3.game.util.Action;

public class LinearDialogNode implements DialogNode {
	
	private String text;
	private int id;
	private List<Integer> pointers = new ArrayList<Integer>();
	private Action action;
	
	public LinearDialogNode(String text, int id) {
		this.text = text;
		this.id = id;
		this.action = null;
	}

	public LinearDialogNode(String text, int id, Action action) {
		this.text = text;
		this.id = id;
		this.action = action;
	}

	public void setAction(Action action) {
		this.action = action;
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

	public void setText(String text) {
		this.text = text;
	}

	public Action getAction() {
		return this.action;
	}

}