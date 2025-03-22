package io.github.desynq.dontfreeze.config;

import io.github.desynq.dontfreeze.DontFreeze;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

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

    public static final ModConfigValue<Double> inPowderSnowBreakSpeed = new ModConfigValue<>(BUILDER
            .comment("How much should mining speed decrease by (%) when in powder snow?")
            .defineInRange("in_powder_snow_break_speed", 0.99, 0.0, 1.0));

    public static final ForgeConfigSpec SPEC = BUILDER.build();


    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName &&
                ForgeRegistries.ITEMS.containsKey(ResourceLocation.tryParse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        ModConfigValue.CONFIG_VALUES.forEach(ModConfigValue::load);
    }
}
