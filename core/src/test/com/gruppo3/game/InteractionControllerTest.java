package com.gruppo3.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.InteractionController;
import com.gruppo3.game.model.interactables.Item;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TestScreen;
import com.badlogic.gdx.Input.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InteractionControllerTest {

    private InteractionController interactionController;

    @Mock
    private List<NPC> npcList;

    @Mock
    private List<Item> itemList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new MyGame(), config);
        Gdx.gl = mock(GL20.class);
    }

    @Test
    void testKeyDownWithItemList() {
        Item item = mock(Item.class);
        when(item.getBox()).thenReturn(mock(Rectangle.class));
        when(item.getBox().overlaps(Player.getPlayer().getPlayerBox())).thenReturn(true);

        when(itemList.iterator()).thenReturn(Arrays.asList(item).iterator());
        npcList = null;
        interactionController = new InteractionController(npcList, itemList);
        assertTrue(interactionController.keyDown(Keys.X));

        verify(item).action(TestScreen.dialogController);
    }

    @Test
    void testKeyDownWithNPCList() {
        NPC npc = mock(NPC.class);
        when(npc.getNpcBox()).thenReturn(mock(Rectangle.class));
        when(npc.getNpcBox().overlaps(Player.getPlayer().getPlayerBox())).thenReturn(true);

        when(npcList.iterator()).thenReturn(Arrays.asList(npc).iterator());
        itemList = null;
        interactionController = new InteractionController(npcList, itemList);

        assertTrue(interactionController.keyDown(Keys.X));

        verify(npc).action(TestScreen.dialogController);
        verify(npc).setNPCDirection(any(NPC.Direction.class));
    }

    @Test
    void testKeyDownWithNoItemListAndNoNPCList() {
        interactionController = new InteractionController(null, null);
        assertFalse(interactionController.keyDown(Keys.X));
    }
}
