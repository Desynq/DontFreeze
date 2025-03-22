package io.github.desynq.dontfreeze.config;

import io.github.desynq.dontfreeze.DontFreeze;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DontFreeze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ModConfigValue<Integer> fogRenderDistance = new ModConfigValue<>(BUILDER
            .comment("What render distance setting Primal Winter's fog should use")
            .defineInRange("fog_render_distance", 16, 2, 64));

    public static final ModConfigValue<Boolean> snowTakesLongerToMine = new ModConfigValue<>(BUILDER
            .comment("Should snow layers take longer to break?")
            .comment("Formula is miningSpeed / layers")
            .define("snow_takes_longer_to_mine", true));

    public static final ModConfigValue<Double> inPowderSnowMiningModifier = new ModConfigValue<>(BUILDER
            .comment("How much longer should mining while in powder snow take as a percentage?")
            .defineInRange("in_powder_snow_mining_modifier", 10.0, 0.0, Float.MAX_VALUE));

    public static final ModConfigValue<Double> snowyBlocksMiningModifier = new ModConfigValue<>(BUILDER
            .comment("How much longer should mining snowy blocks take as a percentage?")
            .defineInRange("snowy_blocks_mining_modifier", 1.0, 0.0, Float.MAX_VALUE));

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        ModConfigValue.CONFIG_VALUES.forEach(ModConfigValue::load);
    }
}
