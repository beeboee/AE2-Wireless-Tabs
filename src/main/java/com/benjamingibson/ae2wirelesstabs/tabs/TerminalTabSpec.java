package com.benjamingibson.ae2wirelesstabs.tabs;

import net.minecraft.world.item.ItemStack;

public record TerminalTabSpec(
        String name,
        ItemStack icon,
        ItemStack backingStack,
        String search,
        boolean viewCell
) {
    public boolean isDynamicSearch() {
        return !viewCell && search != null && !search.isBlank();
    }
}
