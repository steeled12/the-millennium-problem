package com.gruppo3.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gruppo3.game.controller.SettingController;

public class OptionBox extends Table {

	private int selectorIndex = 0;

	private List<Image> arrows = new ArrayList<Image>();
	private List<Label> options = new ArrayList<Label>();

	private Table uiContainer;

	private Sound sound;

	public OptionBox(Skin skin) {
		super(skin);
		this.setBackground("optionbox");
		uiContainer = new Table();
		this.add(uiContainer).pad(5f);
		this.sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-blipfemale.wav"));
	}

	public void addOption(String option) {
		Label optionLabel = new Label(option, this.getSkin());
		options.add(optionLabel);
		Image selectorLabel = new Image(this.getSkin(), "arrow");
		selectorLabel.setScaling(Scaling.none);
		arrows.add(selectorLabel);
		selectorLabel.setVisible(false);

		if (selectorLabel == arrows.get(selectorIndex)) {
			selectorLabel.setVisible(true);
		}

		uiContainer.add(selectorLabel).expand().align(Align.left).space(5f);
		uiContainer.add(optionLabel)
				.expand()
				.align(Align.left)
				.space(8f);
		uiContainer.row();
	}

	public void moveUp() {
		selectorIndex--;
		if (selectorIndex < 0) {
			selectorIndex = 0;
		}
		for (int i = 0; i < arrows.size(); i++) {
			if (i == selectorIndex) {
				arrows.get(i).setVisible(true);
			} else {
				arrows.get(i).setVisible(false);
			}
		}
		sound.play();
	}

	public void moveDown() {
		selectorIndex++;
		if (selectorIndex >= arrows.size()) {
			selectorIndex = arrows.size() - 1;
		}
		for (int i = 0; i < arrows.size(); i++) {
			if (i == selectorIndex) {
				arrows.get(i).setVisible(true);
			} else {
				arrows.get(i).setVisible(false);
			}
		}
		sound.play(SettingController.gameVolume);
	}

	public void clearChoices() {
		uiContainer.clearChildren();
		options.clear();
		arrows.clear();
		selectorIndex = 0;
	}

	public int getIndex() {
		return selectorIndex;
	}

	public int getAmount() {
		return options.size();
	}
}