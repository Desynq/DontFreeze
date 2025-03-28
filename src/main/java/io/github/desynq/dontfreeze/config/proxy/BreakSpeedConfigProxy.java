package io.github.desynq.dontfreeze.config.proxy;

import io.github.desynq.dontfreeze.config.ConfigManager;

public final class BreakSpeedConfigProxy {

    public static boolean snowSlowsMiningCumulatively() {
        return ConfigManager.getCommonConfig().breakSpeedConfigSpec.snowTakesLongerToMine.getValue();
    }

    public static float getInPowderSnowModifier() {
        return ConfigManager.getCommonConfig().breakSpeedConfigSpec.inPowderSnowMiningModifier.getValue().floatValue();
    }

    public static float getSnowyBlocksModifier() {
        return ConfigManager.getCommonConfig().breakSpeedConfigSpec.snowyBlocksMiningModifier.getValue().floatValue();
    }
}
