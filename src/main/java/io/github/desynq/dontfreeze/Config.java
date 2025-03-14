package io.github.desynq.dontfreeze;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DontFreeze.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue FOG_MAX_RENDER_DISTANCE = BUILDER
            .comment("What the maximum render distance should be for primal winter's fog effect")
            .defineInRange("fog_max_render_distance", 16, 2, 255);

    protected static final ForgeConfigSpec SPEC = BUILDER.build();



    public static int fogMaxRenderDistance;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName &&
                ForgeRegistries.ITEMS.containsKey(ResourceLocation.tryParse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        fogMaxRenderDistance = FOG_MAX_RENDER_DISTANCE.get();
    }
}
