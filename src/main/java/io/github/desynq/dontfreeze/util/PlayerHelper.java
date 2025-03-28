package io.github.desynq.dontfreeze.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class PlayerHelper {

    public static boolean isHoldingShovel(@NotNull Player player) {
        return player.getMainHandItem().getItem() instanceof ShovelItem;
    }

    public static boolean isHoldingAxe(@NotNull Player player) {
        return player.getMainHandItem().getItem() instanceof AxeItem;
    }

    public static boolean isHoldingPickaxe(@NotNull Player player) {
        return player.getMainHandItem().getItem() instanceof PickaxeItem;
    }

    public static boolean isHoldingSword(@NotNull Player player) {
        return player.getMainHandItem().getItem() instanceof SwordItem;
    }

    public static boolean isUsingCorrectTool(@NotNull Player player, BlockState blockState) {
        return player.getMainHandItem().getItem().isCorrectToolForDrops(blockState);
    }
}
