package io.github.desynq.dontfreeze.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ModConfigValue<T> {
    public static final List<ModConfigValue<?>> CONFIG_VALUES = new ArrayList<>();

    private final ForgeConfigSpec.ConfigValue<T> configValue;
    private T value;

    public ModConfigValue(ForgeConfigSpec.ConfigValue<T> configValue) {
        this.configValue = configValue;
        CONFIG_VALUES.add(this);
    }

    public void load() {
        value = configValue.get();
    }

    public T getValue() {
        return value;
    }
}
