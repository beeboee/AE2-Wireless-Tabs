package com.benjamingibson.ae2wirelesstabs.registry;

import com.benjamingibson.ae2wirelesstabs.AE2WirelessTabs;
import com.benjamingibson.ae2wirelesstabs.menu.DynamicViewCardMenu;
import com.benjamingibson.ae2wirelesstabs.menu.ViewTabsCardMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, AE2WirelessTabs.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<ViewTabsCardMenu>> VIEW_TABS_CARD_MENU =
            MENUS.register("view_tabs_card", () -> IMenuTypeExtension.create(ViewTabsCardMenu::fromNetwork));

    public static final DeferredHolder<MenuType<?>, MenuType<DynamicViewCardMenu>> DYNAMIC_VIEW_CARD_MENU =
            MENUS.register("dynamic_view_card", () -> IMenuTypeExtension.create(DynamicViewCardMenu::fromNetwork));

    private ModMenus() {
    }
}
