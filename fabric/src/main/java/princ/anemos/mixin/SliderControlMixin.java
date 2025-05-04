package princ.anemos.mixin;

import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static princ.anemos.AnemosConstants.*;
import static princ.anemos.util.UnitValueConverter.*;

@Pseudo
@Mixin(SliderControl.class)
public class SliderControlMixin {
    @Shadow
    @Final
    @Mutable
    private int min;

    @Shadow
    @Final
    @Mutable
    private int max;

    @Shadow
    @Final
    @Mutable
    private int interval;

    @Shadow
    @Final
    @Mutable
    private ControlValueFormatter mode;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Option<Integer> option, int min, int max, int interval, ControlValueFormatter mode, CallbackInfo info) {
        if (option.getName().getContents() instanceof TranslatableContents content && content.getKey().equals("options.gamma")) {
            this.min = (int) (toDoublePercent(configInternal.gamma.min));
            this.max = (int) (toDoublePercent(configInternal.gamma.max));
            this.interval = (int) (config.gamma.sliderInterval.get().doubleValue());
            this.mode = (v) -> {
                if (v == toPercent((int) configInternal.gamma.min)) {
                    return Component.translatable("options.gamma.min");
                } else if (v == config.gamma.defaultValue.get().intValue()) {
                    return Component.translatable("options.gamma.default");
                } else {
                    return v == toPercent((int) configInternal.gamma.max) ? Component.translatable("options.gamma.max") : Component.literal(v + "%");
                }
            };
        }
    }
}