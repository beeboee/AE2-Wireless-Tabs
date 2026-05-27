package com.benjamingibson.ae2wirelesstabs.menu;

import com.benjamingibson.ae2wirelesstabs.item.ViewTabsCardItem;
import com.benjamingibson.ae2wirelesstabs.registry.ModMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ViewTabsCardMenu extends AbstractContainerMenu {
    private final InteractionHand hand;
    private final ItemStackHandler tabs;

    public ViewTabsCardMenu(int id, Inventory playerInventory, InteractionHand hand) {
        super(ModMenus.VIEW_TABS_CARD_MENU.get(), id);
        this.hand = hand;
        this.tabs = ViewTabsCardItem.getTabInventory(playerInventory.player.getItemInHand(hand));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slot = col + row * 9;
                addSlot(new SlotItemHandler(tabs, slot, 8 + col * 18, 18 + row * 18));
            }
        }

        addPlayerInventory(playerInventory, 8, 84);
    }

    public static ViewTabsCardMenu fromNetwork(int id, Inventory inv, RegistryFriendlyByteBuf buf) {
        return new ViewTabsCardMenu(id, inv, buf.readEnum(InteractionHand.class));
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand).getItem() instanceof ViewTabsCardItem;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack original = slot.getItem().copy();
        ItemStack stack = slot.getItem();
        if (index < ViewTabsCardItem.TAB_SLOTS) {
            if (!moveItemStackTo(stack, ViewTabsCardItem.TAB_SLOTS, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(stack, 0, ViewTabsCardItem.TAB_SLOTS, false)) {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return original;
    }

    private void addPlayerInventory(Inventory inv, int left, int top) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, left + col * 18, top + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(inv, col, left + col * 18, top + 58));
        }
    }
}
