package io.github.desynq.dontfreeze.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;

public class PlayerHelper {

    public static boolean isHoldingShovel(Player player) {
        return player.getMainHandItem().getItem() instanceof ShovelItem;
    }
}
