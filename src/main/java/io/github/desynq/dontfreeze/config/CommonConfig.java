package io.github.desynq.dontfreeze.config;

import io.github.desynq.dontfreeze.DontFreeze;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DontFreeze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final DFConfigValue<Integer> fogRenderDistance = new DFConfigValue<>(BUILDER
            .comment("What render distance setting Primal Winter's fog should use")
            .defineInRange("fog_render_distance", 16, 2, 64));

    public static final ForgeConfigSpec SPEC = BUILDER.build();


    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName &&
                ForgeRegistries.ITEMS.containsKey(ResourceLocation.tryParse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        DFConfigValue.CONFIG_VALUES.forEach(DFConfigValue::load);
    }
}
