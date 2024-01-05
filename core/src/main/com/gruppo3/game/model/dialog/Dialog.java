package com.gruppo3.game.model.dialog;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;


public class Dialog {
	
	private Map<Integer, DialogNode> nodes = new HashMap<Integer, DialogNode>();
	
	public DialogNode getNode(int id) {
		return nodes.get(id);
	}
	
	public void addNode(DialogNode node) {
		this.nodes.put(node.getID(), node);
	}
	
	public int getStart() {
		return 0;
	}
	
	public int size() {
		return nodes.size();
	}

	public void removeNode(int id) {
		DialogNode previousNode = nodes.get(id-1);
		previousNode.getPointers().remove((Integer) id);
		nodes.remove(id);
		
	}
}