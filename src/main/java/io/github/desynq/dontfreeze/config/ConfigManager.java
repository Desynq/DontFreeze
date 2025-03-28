package io.github.desynq.dontfreeze.config;

import io.github.desynq.dontfreeze.DontFreeze;
import io.github.desynq.dontfreeze.config.value.ModConfigValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class ConfigManager {

    public static final String COMMON_CONFIG_PATH = DontFreeze.MOD_ID + File.separator + "common.toml";

    private static Pair<CommonConfig, ForgeConfigSpec> commonConfigValues;

    public static void initialize() {
        commonConfigValues = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
    }

    public static void register(@NotNull FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, commonConfigValues.getRight(), COMMON_CONFIG_PATH);
    }

    public static void loadConfigValues() {
        ModConfigValue.CONFIG_VALUES.forEach(ModConfigValue::load);
    }

    public static CommonConfig getCommonConfig() {
        return commonConfigValues.getLeft();
    }


    @SubscribeEvent
    public static void onConfigLoad(final ModConfigEvent.Loading event) {
        loadConfigValues();
    }

    @SubscribeEvent
    public static synchronized void onConfigReload(final ModConfigEvent.Reloading event) {
        loadConfigValues();
    }
}
