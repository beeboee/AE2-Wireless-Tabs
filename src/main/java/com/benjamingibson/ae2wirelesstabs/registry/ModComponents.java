package com.benjamingibson.ae2wirelesstabs.registry;

import com.benjamingibson.ae2wirelesstabs.AE2WirelessTabs;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AE2WirelessTabs.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DYNAMIC_SEARCH =
            COMPONENTS.register("dynamic_search", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DYNAMIC_TAB_NAME =
            COMPONENTS.register("dynamic_tab_name", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> DYNAMIC_ICON =
            COMPONENTS.register("dynamic_icon", () -> DataComponentType.<ItemContainerContents>builder()
                    .persistent(ItemContainerContents.CODEC)
                    .build());

    private ModComponents() {
    }
}
