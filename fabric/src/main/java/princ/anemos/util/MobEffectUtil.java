package princ.anemos.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectUtil {
    public static boolean hasEffect(Holder<MobEffect> holder) {
        LivingEntity entity = Minecraft.getInstance().player;
        if (entity != null) {
            for (MobEffectInstance mobEffectInstance : entity.getActiveEffects()) {
                if (mobEffectInstance.getEffect() == holder) {
                    return true;
                }
            }
        }
        return false;
    }
}
