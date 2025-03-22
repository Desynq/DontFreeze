package io.github.desynq.dontfreeze.event;

import io.github.desynq.dontfreeze.DontFreeze;
import io.github.desynq.dontfreeze.config.ModConfig;
import io.github.desynq.dontfreeze.registry.ModTagKeys;
import io.github.desynq.dontfreeze.util.PlayerHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = DontFreeze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModifyBreakSpeedEvent {

    @SubscribeEvent
    public static void modifyBreakSpeed(final PlayerEvent.BreakSpeed event) {
        new ModifyBreakSpeedEvent(event);
    }

    private final BlockState blockState;
    private final Player player;
    private float currentSpeed;

    private ModifyBreakSpeedEvent(@NotNull final PlayerEvent.BreakSpeed event) {
        blockState = event.getState();
        player = event.getEntity();
        currentSpeed = event.getOriginalSpeed();

        makeSnowyBlocksTakeLongerToMine();
        makeCumulativeSnowLayersTakeLongerToMine();
        makePowderSnowMoreDifficultToGetOutOf();
        makeLogsUnbreakableWithoutAxe();
        event.setNewSpeed(currentSpeed);
    }



    private void makeSnowyBlocksTakeLongerToMine() {
        if (blockState.is(ModTagKeys.SNOWY)) {
            currentSpeed /= 1f + ModConfig.snowyBlocksMiningModifier.getValue().floatValue();
        }
    }



    private void makeCumulativeSnowLayersTakeLongerToMine() {
        if (blockState.is(Blocks.SNOW) && ModConfig.snowTakesLongerToMine.getValue()) {
            int layers = blockState.getValue(SnowLayerBlock.LAYERS);
            currentSpeed /= layers;
        }
    }



    private void makePowderSnowMoreDifficultToGetOutOf() {
        if (player.isInPowderSnow) {
            if (isMiningPowderSnowWithoutAShovel()) {
                currentSpeed = 0f;
            }
            else {
                currentSpeed /= 1f + ModConfig.inPowderSnowMiningModifier.getValue().floatValue();
            }
        }
        else if (isMiningPowderSnowWithAShovel()) {
            currentSpeed *= 2f;
        }
    }

    private boolean isMiningPowderSnowWithoutAShovel() {
        return blockState.is(Blocks.POWDER_SNOW) && !PlayerHelper.isHoldingShovel(player);
    }

    private boolean isMiningPowderSnowWithAShovel() {
        return blockState.is(Blocks.POWDER_SNOW) && PlayerHelper.isHoldingShovel(player);
    }



    private void makeLogsUnbreakableWithoutAxe() {
        if (blockState.is(BlockTags.LOGS) && !PlayerHelper.isHoldingAxe(player)) {
            currentSpeed = 0;
        }
    }
}
