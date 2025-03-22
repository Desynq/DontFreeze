package io.github.desynq.dontfreeze.mixin;

import io.github.desynq.dontfreeze.registry.ModTagKeys;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
@Implements(@Interface(iface = IForgeItem.class, prefix = "custom$"))
public class MixinItem {

    public boolean custom$canWalkOnPowderedSnow(@NotNull ItemStack stack, LivingEntity wearer)
    {
        return stack.is(ModTagKeys.CAN_WALK_ON_POWDER_SNOW);
    }
}
