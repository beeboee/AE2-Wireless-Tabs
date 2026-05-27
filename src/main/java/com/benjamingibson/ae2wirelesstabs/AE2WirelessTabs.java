package com.benjamingibson.ae2wirelesstabs;

import com.benjamingibson.ae2wirelesstabs.registry.ModComponents;
import com.benjamingibson.ae2wirelesstabs.registry.ModItems;
import com.benjamingibson.ae2wirelesstabs.registry.ModMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AE2WirelessTabs.MOD_ID)
public class AE2WirelessTabs {
    public static final String MOD_ID = "ae2wirelesstabs";

    public AE2WirelessTabs(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();
        ModItems.ITEMS.register(modBus);
        ModMenus.MENUS.register(modBus);
        ModComponents.COMPONENTS.register(modBus);
        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModItems::registerAe2Upgrades);
    }
}
