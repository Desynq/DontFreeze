package io.github.desynq.dontfreeze.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

// Don't Freeze Config Value
public class DFConfigValue<T> {
    public static final List<DFConfigValue<?>> CONFIG_VALUES = new ArrayList<>();

    private final ForgeConfigSpec.ConfigValue<T> configValue;
    private T value;

    public DFConfigValue(ForgeConfigSpec.ConfigValue<T> configValue) {
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
