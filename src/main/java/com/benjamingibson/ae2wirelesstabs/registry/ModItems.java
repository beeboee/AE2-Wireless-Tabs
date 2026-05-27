package com.benjamingibson.ae2wirelesstabs.registry;

import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import com.benjamingibson.ae2wirelesstabs.AE2WirelessTabs;
import com.benjamingibson.ae2wirelesstabs.item.DynamicViewCardItem;
import com.benjamingibson.ae2wirelesstabs.item.ViewTabsCardItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AE2WirelessTabs.MOD_ID);

    public static final DeferredHolder<Item, ViewTabsCardItem> VIEW_TABS_CARD = ITEMS.register("view_tabs_card",
            () -> new ViewTabsCardItem(new Item.Properties().stacksTo(1)));

    public static final DeferredHolder<Item, DynamicViewCardItem> DYNAMIC_VIEW_CARD = ITEMS.register("dynamic_view_card",
            () -> new DynamicViewCardItem(new Item.Properties().stacksTo(1)));

    private ModItems() {
    }

    public static void registerAe2Upgrades() {
        String wirelessTerminals = "gui.ae2.WirelessTerminals";
        Upgrades.add(VIEW_TABS_CARD.get(), AEItems.WIRELESS_TERMINAL, 1, wirelessTerminals);
        Upgrades.add(VIEW_TABS_CARD.get(), AEItems.WIRELESS_CRAFTING_TERMINAL, 1, wirelessTerminals);
    }
}
