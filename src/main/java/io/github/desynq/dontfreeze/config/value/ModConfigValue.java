package io.github.desynq.dontfreeze.config.value;

import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ModConfigValue<T> implements IModConfigValue<T> {
    public static final List<ModConfigValue<?>> CONFIG_VALUES = new ArrayList<>();

    protected ForgeConfigSpec.ConfigValue<T> configValue;
    private final String path;
    private final BiFunction<ForgeConfigSpec.Builder, String, ForgeConfigSpec.ConfigValue<T>> defineFunction;
    private T value;

    private ModConfigValue(
            String path,
            BiFunction<ForgeConfigSpec.Builder, String, ForgeConfigSpec.ConfigValue<T>> defineFunction) {
        this.path = path;
        this.defineFunction = defineFunction;
        CONFIG_VALUES.add(this);
    }

    @Override
    public void load() {
        value = configValue.get();
    }

    public T getValue() {
        return value;
    }

    @Override
    public void build(ForgeConfigSpec.Builder builder) {
        configValue = defineFunction.apply(builder, toPath(path));
    }


    @Contract("_, _ -> new")
    public static <T> @NotNull ModConfigValue<T> create(
            String path,
            BiFunction<ForgeConfigSpec.Builder, String, ForgeConfigSpec.ConfigValue<T>> defineFunction) {
        return new ModConfigValue<>(path, defineFunction);
    }
}
