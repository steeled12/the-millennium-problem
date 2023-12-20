package com.gruppo3.game;

import com.badlogic.gdx.Input;
import com.gruppo3.game.controller.InteractionController;
import com.gruppo3.game.model.interactables.Item;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TestScreen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.math.Rectangle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;

import static org.mockito.Mockito.*;


public class InteractionControllerTest {

    private InteractionController interactionController;

    @Mock
    private List<NPC> npcList;

    @Mock
    private List<Item> itemList;

    @Mock
    private Item item;

    @Mock
    private NPC npc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        interactionController = new InteractionController(npcList, itemList);
    }

    @Test
    public void testKeyDownWithItemListAndItemOverlap() {
        when(itemList.size()).thenReturn(1);
        when(itemList.get(0)).thenReturn(item);
        when(item.getBox()).thenReturn(new Rectangle(0, 0, 10, 10));
        when(Player.getPlayer().getPlayerBox()).thenReturn(new Rectangle(5, 5, 10, 10));

        Assertions.assertTrue(interactionController.keyDown(Input.Keys.X));

        verify(item).action(TestScreen.dialogController);
    }

    @Test
    public void testKeyDownWithItemListAndNoItemOverlap() {
        when(itemList.size()).thenReturn(1);
        when(itemList.get(0)).thenReturn(item);
        when(item.getBox()).thenReturn(new Rectangle(20, 20, 10, 10));
        when(Player.getPlayer().getPlayerBox()).thenReturn(new Rectangle(5, 5, 10, 10));

        Assertions.assertFalse(interactionController.keyDown(Input.Keys.X));

        verify(item, never()).action(TestScreen.dialogController);
    }

    @Test
    public void testKeyDownWithNpcListAndNpcOverlap() {
        when(npcList.size()).thenReturn(1);
        when(npcList.get(0)).thenReturn(npc);
        when(npc.getNpcBox()).thenReturn(new Rectangle(0, 0, 10, 10));
        when(Player.getPlayer().getPlayerBox()).thenReturn(new Rectangle(5, 5, 10, 10));

        Assertions.assertTrue(interactionController.keyDown(Input.Keys.X));

        verify(npc).action(TestScreen.dialogController);
    }

    @Test
    public void testKeyDownWithNpcListAndNoNpcOverlap() {
        when(npcList.size()).thenReturn(1);
        when(npcList.get(0)).thenReturn(npc);
        when(npc.getNpcBox()).thenReturn(new Rectangle(20, 20, 10, 10));
        when(Player.getPlayer().getPlayerBox()).thenReturn(new Rectangle(5, 5, 10, 10));

        Assertions.assertFalse(interactionController.keyDown(Input.Keys.X));

        verify(npc, never()).action(TestScreen.dialogController);
    }

    @Test
    public void testKeyDownWithEmptyItemListAndNpcList() {
        when(itemList.size()).thenReturn(0);
        when(npcList.size()).thenReturn(0);

        Assertions.assertFalse(interactionController.keyDown(Input.Keys.X));

        verify(item, never()).action(TestScreen.dialogController);
        verify(npc, never()).action(TestScreen.dialogController);
    }
}