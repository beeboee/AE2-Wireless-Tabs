package com.benjamingibson.ae2wirelesstabs.item;

import appeng.items.materials.UpgradeCardItem;
import com.benjamingibson.ae2wirelesstabs.menu.ViewTabsCardMenu;
import com.benjamingibson.ae2wirelesstabs.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class ViewTabsCardItem extends UpgradeCardItem {
    public static final int TAB_SLOTS = 27;

    public ViewTabsCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new ViewTabsCardMenu(id, inv, hand),
                    Component.translatable("menu.ae2wirelesstabs.view_tabs_card")
            ), buf -> buf.writeEnum(hand));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> lines, TooltipFlag flag) {
        super.appendHoverText(stack, context, lines, flag);
        int count = 0;
        var inv = getTabInventory(stack);
        for (int i = 0; i < inv.getSlots(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                count++;
            }
        }
        lines.add(Component.translatable("tooltip.ae2wirelesstabs.view_tabs_card"));
        lines.add(Component.literal(count + " configured tabs"));
    }

    public static ItemStackHandler getTabInventory(ItemStack stack) {
        var handler = new ItemStackHandler(TAB_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                stack.set(DataComponents.CONTAINER, toContainerContents(this));
            }

            @Override
            public boolean isItemValid(int slot, ItemStack candidate) {
                return candidate.is(ModItems.DYNAMIC_VIEW_CARD.get()) || candidate.getItem() instanceof appeng.items.storage.ViewCellItem;
            }
        };
        var contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(handler.getStacks());
        return handler;
    }

    private static ItemContainerContents toContainerContents(ItemStackHandler handler) {
        var list = new java.util.ArrayList<ItemStack>(handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            list.add(handler.getStackInSlot(i));
        }
        return ItemContainerContents.fromItems(list);
    }
}
