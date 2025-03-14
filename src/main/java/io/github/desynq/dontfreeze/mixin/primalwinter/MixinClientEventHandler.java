package io.github.desynq.dontfreeze.mixin.primalwinter;

import com.alcatrazescapee.primalwinter.client.ClientEventHandler;
import com.alcatrazescapee.primalwinter.platform.client.FogDensityCallback;
import com.alcatrazescapee.primalwinter.util.Config;
import io.github.desynq.dontfreeze.config.CommonConfig;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientEventHandler.class)
public class MixinClientEventHandler {

    @Shadow(remap = false)
    private static float prevFogDensity;

    @Shadow(remap = false)
    private static long prevFogTick;

    /**
     * @author Desynq
     * @reason Backporting 1.21 version of the method to 1.20.1 with some additions
     */
    @Overwrite(remap = false)
    public static void renderFogDensity(Camera camera, FogDensityCallback callback) {
        if (!(camera.getEntity() instanceof Player player)) {
            return;
        }

        final long thisTick = Util.getMillis();
        final boolean firstTick = prevFogTick == -1;
        final float deltaTick = firstTick ? 1e10f : (thisTick - prevFogTick) * 0.00005f;

        prevFogTick = thisTick;

        float expectedFogDensity = 0f;

        final Level level = player.level();
        if (Config.INSTANCE.isWinterDimension(level.dimension())) {
            final int light = level.getBrightness(LightLayer.SKY, BlockPos.containing(player.getEyePosition()));
            expectedFogDensity = Mth.clampedMap(light, 0f, 15f, 0f, 1f);
        }

        // Scale the output by the render distance, so changes to the render distance don't
        // visually affect the fog depth
        final float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
        final float renderDistanceAdjustment = (CommonConfig.fogRenderDistance.getValue() * 16f) / renderDistance;

        // Smoothly interpolate fog towards the expected value - increasing faster than it decreases
        if (expectedFogDensity > prevFogDensity) {
            prevFogDensity = Math.min(prevFogDensity + 4f * deltaTick, expectedFogDensity);
        } else if (expectedFogDensity < prevFogDensity) {
            prevFogDensity = Math.max(prevFogDensity - deltaTick, expectedFogDensity);
        }

        if (camera.getFluidInCamera() != FogType.NONE) {
            prevFogDensity = -1; // Immediately cancel fog if there's another fog effect going on
            prevFogTick = -1;
        }

        if (prevFogDensity > 0) {
            final float scaledDelta = 1 - (1 - prevFogDensity) * (1 - prevFogDensity);
            final float fogDensity = Config.INSTANCE.fogDensity.getAsFloat();
            final float farPlaneScale = Mth.lerp(scaledDelta, 1f, fogDensity) * renderDistanceAdjustment;
            final float nearPlaneScale = Mth.lerp(scaledDelta, 1f, 0.3f * fogDensity) * renderDistanceAdjustment;
            callback.accept(nearPlaneScale, farPlaneScale);
        }
    }
}
