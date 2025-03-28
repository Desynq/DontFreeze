package io.github.desynq.dontfreeze.event;

import io.github.desynq.dontfreeze.config.proxy.BreakSpeedConfigProxy;
import io.github.desynq.dontfreeze.registry.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

import static io.github.desynq.dontfreeze.config.proxy.BreakSpeedConfigProxy.snowSlowsMiningCumulatively;
import static io.github.desynq.dontfreeze.util.PlayerHelper.isHoldingShovel;
import static io.github.desynq.dontfreeze.util.PlayerHelper.isUsingCorrectTool;

public final class BlockBreakSpeedModifier {

    private final BlockState blockBeingMined;
    private final Player player;
    private float currentSpeed;

    public BlockBreakSpeedModifier(BlockState blockBeingMined, Player player, float currentSpeed) {
        this.blockBeingMined = blockBeingMined;
        this.player = player;
        this.currentSpeed = currentSpeed;

        makeSnowyBlocksTakeLongerToMine();
        makeCumulativeSnowLayersTakeLongerToMine();
        makePowderSnowMoreDifficultToGetOutOf();
        forceUsingCorrectToolForBlocks();
    }

    public float getModifiedSpeed() {
        return currentSpeed;
    }

    private void makeSnowyBlocksTakeLongerToMine() {
        if (blockBeingMined.is(ModTags.SNOWY)) {
            currentSpeed /= 1f + BreakSpeedConfigProxy.getSnowyBlocksModifier();
        }
    }


    private void makeCumulativeSnowLayersTakeLongerToMine() {

        if (snowSlowsMiningCumulatively() && blockBeingMined.is(Blocks.SNOW)) {
            final int layers = blockBeingMined.getValue(SnowLayerBlock.LAYERS);
            currentSpeed /= layers;
        }
    }


    private void makePowderSnowMoreDifficultToGetOutOf() {
        final boolean isMiningPowderSnow = blockBeingMined.is(Blocks.POWDER_SNOW);

        if (player.isInPowderSnow) {
            if (isMiningPowderSnow && !isHoldingShovel(player)) {
                currentSpeed = 0f;
            }
            else {
                currentSpeed /= 1f + BreakSpeedConfigProxy.getInPowderSnowModifier();
            }
        }
        else if (isMiningPowderSnow && isHoldingShovel(player)) {
            currentSpeed *= 2f;
        }
    }


    private void forceUsingCorrectToolForBlocks() {
        // e.g. diamond ore requires an iron pickaxe or greater or won't be breakable at all
        // logs and planks require an axe in order to break
        if (isUsingCorrectTool(player, blockBeingMined)) {
            return;
        }

        boolean blockIsMineableWithAPickaxe = blockBeingMined.is(BlockTags.MINEABLE_WITH_PICKAXE);
        boolean blockIsMineableWithAnAxe = blockBeingMined.is(BlockTags.MINEABLE_WITH_AXE);

        if (blockIsMineableWithAnAxe || blockIsMineableWithAPickaxe) {
            currentSpeed = 0f;
        }
    }
}
