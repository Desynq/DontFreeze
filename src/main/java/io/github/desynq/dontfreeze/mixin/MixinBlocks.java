package io.github.desynq.dontfreeze.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public final class MixinBlocks {

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=glowstone")),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V",
                    ordinal = 0
            )
    )
    private static BlockBehaviour.Properties modifyGlowstone(BlockBehaviour.Properties properties) {
        return properties
                .requiresCorrectToolForDrops()
                .strength(1f);
    }

    @Redirect(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=powder_snow")),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;strength(F)Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;",
                    ordinal = 0
            )
    )
    private static BlockBehaviour.Properties increasePowderSnowStrength(BlockBehaviour.Properties properties, float hardness) {
        return properties.strength(1f);
    }

}
