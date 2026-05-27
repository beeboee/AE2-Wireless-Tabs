package com.benjamingibson.ae2wirelesstabs.item;

import com.benjamingibson.ae2wirelesstabs.menu.DynamicViewCardMenu;
import com.benjamingibson.ae2wirelesstabs.registry.ModComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class DynamicViewCardItem extends Item {
    public DynamicViewCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new DynamicViewCardMenu(id, inv, hand),
                    Component.translatable("menu.ae2wirelesstabs.dynamic_view_card")
            ), buf -> buf.writeEnum(hand));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> lines, TooltipFlag flag) {
        super.appendHoverText(stack, context, lines, flag);
        String search = getSearch(stack);
        if (search.isBlank()) {
            lines.add(Component.translatable("tooltip.ae2wirelesstabs.empty_search"));
        } else {
            lines.add(Component.translatable("tooltip.ae2wirelesstabs.search", search));
        }
    }

    public static String getSearch(ItemStack stack) {
        String explicit = stack.getOrDefault(ModComponents.DYNAMIC_SEARCH.get(), "");
        if (!explicit.isBlank()) {
            return explicit;
        }
        if (stack.has(DataComponents.CUSTOM_NAME)) {
            return stack.getHoverName().getString();
        }
        return "";
    }

    public static String getTabName(ItemStack stack) {
        String explicit = stack.getOrDefault(ModComponents.DYNAMIC_TAB_NAME.get(), "");
        if (!explicit.isBlank()) {
            return explicit;
        }
        String search = getSearch(stack);
        return search.isBlank() ? "Dynamic" : search;
    }

    public static ItemStack getIcon(ItemStack stack) {
        ItemContainerContents contents = stack.getOrDefault(ModComponents.DYNAMIC_ICON.get(), ItemContainerContents.EMPTY);
        var list = contents.stream().toList();
        if (!list.isEmpty() && !list.getFirst().isEmpty()) {
            return list.getFirst().copyWithCount(1);
        }
        return stack.copyWithCount(1);
    }
}
