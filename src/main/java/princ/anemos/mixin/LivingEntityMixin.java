package princ.anemos.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static princ.anemos.Anemosystem.config;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "hasEffect", at = @At("HEAD"), cancellable = true)
    private void fakeHasEffect(Holder<MobEffect> holder, CallbackInfoReturnable<Boolean> cir) {
        if (holder == MobEffects.NIGHT_VISION && config.fakeNightVision.enabled.get()) {
            cir.setReturnValue(true);
        }

        if (holder == MobEffects.BLINDNESS && config.removeBlindness.enabled.get()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getEffect", at = @At("HEAD"), cancellable = true)
    private void fakeGetEffect(Holder<MobEffect> holder, CallbackInfoReturnable<MobEffectInstance> cir) {
        if (holder == MobEffects.NIGHT_VISION && config.fakeNightVision.enabled.get()) {
            cir.setReturnValue(new MobEffectInstance(MobEffects.NIGHT_VISION, MobEffectInstance.INFINITE_DURATION));
        }

        if (holder == MobEffects.BLINDNESS && config.removeBlindness.enabled.get()) {
            cir.setReturnValue(null);
        }
    }
}