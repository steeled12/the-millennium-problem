package com.gruppo3.game.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.DialogNode;
import com.gruppo3.game.model.dialog.DialogTraverser;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.ui.DialogBox;
import com.gruppo3.game.ui.OptionBox;


public class DialogController extends InputAdapter {
	
	private DialogTraverser traverser;
	private DialogBox dialogBox;
	private OptionBox optionBox;
	
	public DialogController(DialogBox box, OptionBox optionBox) {
		this.dialogBox = box;
		this.optionBox = optionBox;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (dialogBox.isVisible()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (optionBox.isVisible()) {
			if (keycode == Keys.UP) {
				optionBox.moveUp();
				return true;
			} else if (keycode == Keys.DOWN) {
				optionBox.moveDown();
				return true;
			}
		}
		if (dialogBox.isVisible() && !dialogBox.isFinished()) {
			return false;
		}
		if (traverser != null && keycode == Keys.X) { // continue through tree
			DialogNode thisNode = traverser.getNode();
			
			if (thisNode instanceof LinearDialogNode)  {
				LinearDialogNode node = (LinearDialogNode)thisNode;
				if (node.getPointers().isEmpty()) { // dead end, since no pointers
					traverser = null;				// end dialog
					dialogBox.setVisible(false);
				} else {
					progress(0); // progress through first pointer
				}
			}
			if (thisNode instanceof ChoiceDialogNode)  {
				ChoiceDialogNode node = (ChoiceDialogNode)thisNode;
				progress(optionBox.getIndex());
			}
			
			return true;
		}
		if (dialogBox.isVisible()) {
			return true;
		}
		return false;
	}
	
	public void update(float delta) {
		if (dialogBox.isFinished() && traverser != null) {
			DialogNode nextNode = traverser.getNode();
			if (nextNode instanceof ChoiceDialogNode) {
				optionBox.setVisible(true);
			}
		}
	}
	
	public void startDialog(Dialog dialog) {
		traverser = new DialogTraverser(dialog);
		dialogBox.setVisible(true);
		
		DialogNode nextNode = traverser.getNode();
		if (nextNode instanceof LinearDialogNode) {
			LinearDialogNode node = (LinearDialogNode)nextNode;
			dialogBox.animateText(node.getText());
		}
		if (nextNode instanceof ChoiceDialogNode) {
			ChoiceDialogNode node = (ChoiceDialogNode)nextNode;
			dialogBox.animateText(node.getText());
			optionBox.clear();
			for (String s : node.getLabels()) {
				optionBox.addOption(s);
			}
		}
	}
	
	private void progress(int index) {
		optionBox.setVisible(false);
		DialogNode nextNode = traverser.getNextNode(index);
		
		if (nextNode instanceof LinearDialogNode) {
			LinearDialogNode node = (LinearDialogNode)nextNode;
			dialogBox.animateText(node.getText());
		}
		if (nextNode instanceof ChoiceDialogNode) {
			ChoiceDialogNode node = (ChoiceDialogNode)nextNode;
			dialogBox.animateText(node.getText());
			optionBox.clearChoices();
			for (String s : node.getLabels()) {
				optionBox.addOption(s);
			}
		}
	}
	
	public boolean isDialogShowing() {
		return dialogBox.isVisible();
	}
}