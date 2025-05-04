package princ.anemos.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static princ.anemos.AnemosConstants.*;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "hasEffect", at = @At("HEAD"), cancellable = true)
    public void fakeHasEffect(Holder<MobEffect> holder, CallbackInfoReturnable<Boolean> cir) {
        if (holder == MobEffects.NIGHT_VISION && config.fakeNightVision.enabled.get()) {
            cir.setReturnValue(true);
        }

        if (holder == MobEffects.BLINDNESS && config.removeBlindness.enabled.get()) {
            cir.setReturnValue(false);
        }

        if (holder == MobEffects.DARKNESS && config.removeDarkness.enabled.get()) {
            cir.setReturnValue(false);
        }
    }
}