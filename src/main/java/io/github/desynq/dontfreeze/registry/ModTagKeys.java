package io.github.desynq.dontfreeze.registry;

import io.github.desynq.dontfreeze.DontFreeze;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTagKeys {

    public static final TagKey<Item> CAN_WALK_ON_POWDER_SNOW = TagKey.create(
            ForgeRegistries.ITEMS.getRegistryKey(),
            DontFreeze.id("can_walk_on_powder_snow")
    );
    public static final TagKey<Block> SNOWY = TagKey.create(
            ForgeRegistries.BLOCKS.getRegistryKey(),
            DontFreeze.id("snowy")
    );

    public static void register() {}
}
