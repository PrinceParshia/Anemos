package princ.anemos.mixin;

import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static princ.anemos.AnemosConstants.*;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin {
    @Inject(method = "renderSkyDisc", at = @At("HEAD"), cancellable = true)
    public void redirectRenderSkyDisc(float f, float g, float h, CallbackInfo ci) {
        if (!config.fog.enabled || !config.fog.sky) {
            ci.cancel();
        }
    }
}
