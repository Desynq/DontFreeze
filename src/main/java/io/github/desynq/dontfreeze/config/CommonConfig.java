package io.github.desynq.dontfreeze.config;

import io.github.desynq.dontfreeze.config.spec.BreakSpeedConfigSpec;
import io.github.desynq.dontfreeze.config.value.ModConfigValue;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

public final class CommonConfig {

    public final ModConfigValue<Integer> fogRenderDistance = ModConfigValue.create(
            "fog_render_distance", (builder, path) -> builder
                    .comment("What render distance setting Primal Winter's fog should use.")
                    .defineInRange(path, 16, 2, 64)
    );

    public final BreakSpeedConfigSpec breakSpeedConfigSpec;

    public CommonConfig(@NotNull ForgeConfigSpec.Builder builder) {
        builder.comment("Don't Freeze Config")
                .push("dont_freeze");

        builder.comment("Primal Winter mod settings.")
                .push("primal_winter");

        fogRenderDistance.build(builder);

        builder.pop();

        breakSpeedConfigSpec = new BreakSpeedConfigSpec(builder);
    }
}
