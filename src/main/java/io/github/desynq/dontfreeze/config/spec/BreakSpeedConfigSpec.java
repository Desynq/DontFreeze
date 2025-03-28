package io.github.desynq.dontfreeze.config.spec;

import io.github.desynq.dontfreeze.config.value.ModConfigValue;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

public class BreakSpeedConfigSpec {

    public final ModConfigValue<Boolean> snowTakesLongerToMine = ModConfigValue.create(
            "snow_takes_longer_to_mine", (builder, path) -> builder
                    .comment("Should snow layers take longer to break?")
                    .comment("Formula is miningSpeed / layers")
                    .define(path, true)
    );

    public final ModConfigValue<Double> inPowderSnowMiningModifier = ModConfigValue.create(
            "in_powder_snow_mining_modifier", (builder, path) -> builder
                    .comment("How much longer should mining while in powder snow take as a percentage?")
                    .defineInRange(path, 10.0, 0.0, Float.MAX_VALUE)
    );

    public final ModConfigValue<Double> snowyBlocksMiningModifier = ModConfigValue.create(
            "snowy_blocks_mining_modifier", (builder, path) -> builder
                    .comment("How much longer should mining snowy blocks take as a percentage?")
                    .defineInRange(path, 1.0, 0.0, Float.MAX_VALUE)
    );

    public BreakSpeedConfigSpec(@NotNull ForgeConfigSpec.Builder builder) {
        builder.comment("Block breaking speed related settings")
                .push("break_speed");

        snowTakesLongerToMine.build(builder);
        inPowderSnowMiningModifier.build(builder);
        snowyBlocksMiningModifier.build(builder);

        builder.pop();
    }
}
