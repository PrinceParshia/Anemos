package princ.anemos.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static princ.anemos.AnemosConstants.*;
import static princ.anemos.util.MobEffectUtil.*;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(method = "computeFogColor", at = @At("HEAD"), cancellable = true)
    private static void computeFogColor(Camera camera, float f, ClientLevel clientLevel, int i, float g, CallbackInfoReturnable<Vector4f> cir) {
        if ((hasEffect(MobEffects.BLINDNESS) && config.removeBlindness.enabled.get()) || (hasEffect(MobEffects.DARKNESS) && config.removeDarkness.enabled.get())) {
            cir.setReturnValue(new Vector4f(0.0F, 0.0F, 0.0F, 1.0F));
        }
    }

    @Redirect(method = "computeFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"))
    private static float redirectNightVisionFogColor(LivingEntity livingEntity, float f) {
        if (config.fakeNightVision.enabled.get() && !config.fakeNightVision.fog) {
            return 0.0F;
        }
        return GameRenderer.getNightVisionScale(livingEntity, f);
    }

    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    private static void setupFog(Camera camera, FogMode fogMode, Vector4f vector4f, float f, boolean bl, float g, CallbackInfoReturnable<FogParameters> cir) {
        if (!config.fog.enabled) {
            cir.setReturnValue(FogParameters.NO_FOG);
        } else {
            FogType fogType = camera.getFluidInCamera();
            if (fogType == FogType.LAVA && !config.fog.lava) {
                cir.setReturnValue(FogParameters.NO_FOG);
            } else if (fogType == FogType.POWDER_SNOW && !config.fog.powderSnow) {
                cir.setReturnValue(FogParameters.NO_FOG);
            } else if (fogType == FogType.WATER && !config.fog.water) {
                cir.setReturnValue(FogParameters.NO_FOG);
            } else if (fogMode == FogMode.FOG_SKY && !config.fog.sky) {
                cir.setReturnValue(FogParameters.NO_FOG);
            } else if (fogMode == FogMode.FOG_TERRAIN && !config.fog.terrain) {
                cir.setReturnValue(FogParameters.NO_FOG);
            }
        }
    }
}
