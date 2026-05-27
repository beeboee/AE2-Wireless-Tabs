package com.benjamingibson.ae2wirelesstabs.tabs;

import appeng.api.implementations.menuobjects.ItemMenuHost;
import appeng.api.stacks.AEItemKey;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.items.storage.ViewCellItem;
import appeng.menu.me.common.MEStorageMenu;
import com.benjamingibson.ae2wirelesstabs.item.DynamicViewCardItem;
import com.benjamingibson.ae2wirelesstabs.item.ViewTabsCardItem;
import com.benjamingibson.ae2wirelesstabs.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class TerminalTabs {
    private TerminalTabs() {
    }

    public static List<TerminalTabSpec> collectTabs(MEStorageMenu menu) {
        var result = new ArrayList<TerminalTabSpec>();
        var card = findInstalledTabsCard(menu);
        if (card.isEmpty()) {
            return result;
        }

        var inv = ViewTabsCardItem.getTabInventory(card);
        for (int i = 0; i < inv.size(); i++) {
            var entry = inv.getStackInSlot(i);
            if (entry.isEmpty()) {
                continue;
            }
            if (entry.getItem() instanceof ViewCellItem viewCellItem) {
                result.add(fromViewCell(entry, viewCellItem));
            } else if (entry.is(ModItems.DYNAMIC_VIEW_CARD.get())) {
                result.add(fromDynamic(entry));
            }
        }
        return result;
    }

    public static ItemStack findInstalledTabsCard(MEStorageMenu menu) {
        if (!(menu.getHost() instanceof ItemMenuHost<?> itemHost)) {
            return ItemStack.EMPTY;
        }

        IUpgradeInventory upgrades = itemHost.getUpgrades();
        for (var stack : upgrades) {
            if (stack.is(ModItems.VIEW_TABS_CARD.get())) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static TerminalTabSpec fromDynamic(ItemStack stack) {
        String search = DynamicViewCardItem.getSearch(stack);
        String name = DynamicViewCardItem.getTabName(stack);
        ItemStack icon = DynamicViewCardItem.getIcon(stack);
        return new TerminalTabSpec(name, icon, stack.copy(), search, false);
    }

    private static TerminalTabSpec fromViewCell(ItemStack stack, ViewCellItem item) {
        ItemStack icon = ItemStack.EMPTY;
        var config = item.getConfigInventory(stack);
        for (int i = 0; i < config.size(); i++) {
            var key = config.getKey(i);
            if (key instanceof AEItemKey itemKey) {
                icon = itemKey.toStack();
                break;
            }
        }
        if (icon.isEmpty()) {
            icon = stack.copyWithCount(1);
        }

        String name;
        var customName = stack.getHoverName();
        if (customName != null && !customName.equals(Component.translatable(stack.getDescriptionId()))) {
            name = customName.getString();
        } else {
            name = icon.getHoverName().getString();
        }
        return new TerminalTabSpec(name, icon.copyWithCount(1), stack.copy(), "", true);
    }
}
