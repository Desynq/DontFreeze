package io.github.desynq.dontfreeze.event;

import io.github.desynq.dontfreeze.DontFreeze;
import io.github.desynq.dontfreeze.config.ModConfig;
import io.github.desynq.dontfreeze.util.PlayerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DontFreeze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModifyBreakSpeedEvent {

    @SubscribeEvent
    public static void modifyBreakSpeed(final PlayerEvent.BreakSpeed event) {
        new ModifyBreakSpeedEvent(event);
    }

    private final BlockState blockState;
    private final Player player;
    private float currentSpeed;

    private ModifyBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        blockState = event.getState();
        player = event.getEntity();
        currentSpeed = event.getOriginalSpeed();

        makeCumulativeSnowLayersTakeLongerToMine();
        makePowderSnowMoreDifficultToGetOutOf();
        event.setNewSpeed(currentSpeed);
    }

    private void makeCumulativeSnowLayersTakeLongerToMine() {
        if (blockState.is(Blocks.SNOW) && ModConfig.snowTakesLongerToMine.getValue()) {
            int layers = blockState.getValue(SnowLayerBlock.LAYERS);
            currentSpeed /= layers;
        }
    }

    // forces player to either use a shovel to mine powder snow when they're stuck in it
    // alternatively they can also simply get out of the powder snow or mine blocks adjacent to the powder snow
    // this effectively makes being fully in powder snow extremely dangerous if the player has no shovel
    private void makePowderSnowMoreDifficultToGetOutOf() {
        if (player.isInPowderSnow) {
            float modifier = ModConfig.inPowderSnowBreakSpeed.getValue().floatValue();
            currentSpeed *= 1f - modifier;
            if (blockState.is(Blocks.POWDER_SNOW) && !PlayerHelper.isHoldingShovel(player)) {
                currentSpeed = 0;
            }
        }
    }
}
