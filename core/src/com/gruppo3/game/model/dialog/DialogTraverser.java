package com.gruppo3.game.model.dialog;

public class DialogTraverser {
	
	private Dialog dialog;
	private DialogNode currentNode;
	
	public DialogTraverser(Dialog dialog) {
		this.dialog = dialog;
		currentNode = dialog.getNode(dialog.getStart());
	}
	
	public DialogNode getNextNode(int pointerIndex) {
		if (currentNode.getPointers().isEmpty()) {
			return null;
		}
		DialogNode nextNode = dialog.getNode(currentNode.getPointers().get(pointerIndex));
		currentNode = nextNode;
		return nextNode;
	}
	
	public DialogNode getNode() {
		return currentNode;
	}
}