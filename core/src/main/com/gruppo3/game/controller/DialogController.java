package com.gruppo3.game.controller;

import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.DialogNode;
import com.gruppo3.game.model.dialog.DialogTraverser;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.ui.DialogBox;
import com.gruppo3.game.ui.OptionBox;
import com.gruppo3.game.util.Action;
import com.badlogic.gdx.Gdx;

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

			if (thisNode instanceof LinearDialogNode) {
				LinearDialogNode node = (LinearDialogNode) thisNode;
				if (node.getAction() != null) {
					node.getAction().action();
				}
				if (node.getPointers().isEmpty()) { // dead end, since no pointers
					traverser = null; // end dialog
					dialogBox.setVisible(false);
				} else {
					progress(0); // progress through first pointer
				}
			}
			if (thisNode instanceof ChoiceDialogNode) {
				ChoiceDialogNode node = (ChoiceDialogNode) thisNode;
				optionBox.callAction();
				Gdx.app.log("DialogController", "Called action");
				int index = optionBox.getIndex();
				if(node.getPointers().get(index) == -1) {
					traverser = null;
					dialogBox.setVisible(false);
					optionBox.setVisible(false);
				} else {
					progress(index);
				}
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
			LinearDialogNode node = (LinearDialogNode) nextNode;
			dialogBox.animateText(node.getText());
		}
		if (nextNode instanceof ChoiceDialogNode) {
			ChoiceDialogNode node = (ChoiceDialogNode) nextNode;
			dialogBox.animateText(node.getText());
			optionBox.clearChoices();
			for (Map.Entry<String, Action> entry : node.getOptions().entrySet()) {
				String option = entry.getKey();
				Action action = entry.getValue();
				if (action != null) {
					optionBox.addOption(option, action);
				} else {
					optionBox.addOption(option);
				}
			}
		}
	}

	private void progress(int index) {
		optionBox.setVisible(false);
		DialogNode nextNode = traverser.getNextNode(index);

		if (nextNode instanceof LinearDialogNode) {
			LinearDialogNode node = (LinearDialogNode) nextNode;
			dialogBox.animateText(node.getText());
		}
		if (nextNode instanceof ChoiceDialogNode) {
			ChoiceDialogNode node = (ChoiceDialogNode) nextNode;
			dialogBox.animateText(node.getText());
			optionBox.clearChoices();
			for (Map.Entry<String, Action> entry : node.getOptions().entrySet()) {
				String option = entry.getKey();
				Action action = entry.getValue();
				if (action != null) {
					optionBox.addOption(option, action);
					Gdx.app.log("DialogController", "Added option with action");
				} else {
					optionBox.addOption(option);
				}
			}
		}
	}

	public boolean isDialogShowing() {
		return dialogBox.isVisible();
	}

	public DialogTraverser getTraverser() {
		return traverser;
	}
}