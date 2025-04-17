package princ.anemos.mixin;

import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static princ.anemos.Anemosystem.config;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Redirect(method = "computeFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"))
    private static float removeFogColor(LivingEntity livingEntity, float f) {
        if (config.fakeNightVision.enabled.get()) {
            return 0.0F;
        }
        return GameRenderer.getNightVisionScale(livingEntity, f);
    }
}
