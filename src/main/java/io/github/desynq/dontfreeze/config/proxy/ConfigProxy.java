package io.github.desynq.dontfreeze.config.proxy;

import io.github.desynq.dontfreeze.config.ConfigManager;

public final class ConfigProxy {

    public static float getFogRenderDistance() {
        return ConfigManager.getCommonConfig().fogRenderDistance.getValue().floatValue();
    }
}
