package io.github.desynq.dontfreeze.event;

import io.github.desynq.dontfreeze.DontFreeze;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DontFreeze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class EventHandler {

    @SubscribeEvent
    public static void modifyBreakSpeed(final PlayerEvent.BreakSpeed event) {
        float modifiedBreakSpeed = new BlockBreakSpeedModifier(
                event.getState(),
                event.getEntity(),
                event.getOriginalSpeed()
        ).getModifiedSpeed();
        event.setNewSpeed(modifiedBreakSpeed);
    }
}
