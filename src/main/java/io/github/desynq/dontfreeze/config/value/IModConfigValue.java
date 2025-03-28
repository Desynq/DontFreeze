package io.github.desynq.dontfreeze.config.value;

import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

public interface IModConfigValue<T> {

    default void commentate(ForgeConfigSpec.Builder builder, String @NotNull ... comments) {
        for (String comment : comments) {
            builder.comment(" " + comment);
        }
    }

    default String toPath(@NotNull String name) {
        String[] parts = name.split("\\.");
        return parts[parts.length - 1];
    }

    void load();
    void build(ForgeConfigSpec.Builder builder);
}
