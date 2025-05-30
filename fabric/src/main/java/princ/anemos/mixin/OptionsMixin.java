package princ.anemos.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import princ.anemos.client.OptionInstanceImpl.UnitDouble;

import static net.minecraft.client.Options.genericValueLabel;
import static princ.anemos.AnemosConstants.*;
import static princ.anemos.util.UnitValueConverter.*;

@Mixin(Options.class)
public class OptionsMixin {
    @Shadow
    @Final
    @Mutable
    private OptionInstance<Double> gamma;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;gamma:Lnet/minecraft/client/OptionInstance;"))
    public void init(Options options, OptionInstance<?> instance) {
        this.gamma = new OptionInstance<>("options.gamma", OptionInstance.noTooltip(), (component, double_) -> {
            int i = (int) (double_ * (double) 100.0F);
            if (i == toPercent((int) configInternal.gamma.min)) {
                return genericValueLabel(component, Component.translatable("options.gamma.min"));
            } else if (i == config.gamma.defaultValue.get().intValue()) {
                return genericValueLabel(component, Component.translatable("options.gamma.default"));
            } else {
                return i == toPercent((int) configInternal.gamma.max) ? genericValueLabel(component, Component.translatable("options.gamma.max")) : genericValueLabel(component, i);
            }
        }, UnitDouble.INSTANCE, fromDoublePercent(config.gamma.defaultValue.get()), (double_) -> {
        });
    }
}