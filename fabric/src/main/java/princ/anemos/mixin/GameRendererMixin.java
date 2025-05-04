package princ.anemos.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static princ.anemos.AnemosConstants.*;
import static princ.anemos.util.UnitValueConverter.*;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void adjustNightVisionScale(LivingEntity livingEntity, float f, CallbackInfoReturnable<Float> cir) {
        if (config.fakeNightVision.enabled.get()) {
            cir.setReturnValue(fromFloatPercent(fnvScale().get()));
        }
    }
}
