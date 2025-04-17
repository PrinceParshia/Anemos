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

import static princ.anemos.Anemosystem.config;

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
    private void init(Option<Integer> option, int min, int max, int interval, ControlValueFormatter mode, CallbackInfo info) {
        if (option.getName().getContents() instanceof TranslatableContents content && content.getKey().equals("options.gamma")) {
            this.min = (int) (config.gamma.min.get().doubleValue());
            this.max = (int) (config.gamma.max.get().doubleValue());
            this.interval = (int) (config.gamma.sliderInterval.get().doubleValue());
            this.mode = (v) -> {
                if (v == config.gamma.min.get().intValue()) {
                    return Component.translatable("options.gamma.min");
                } else if (v == config.gamma.default_.get().intValue()) {
                    return Component.translatable("options.gamma.default");
                } else {
                    return v == config.gamma.max.get().intValue() ? Component.translatable("options.gamma.max") : Component.literal(v + "%");
                }
            };
        }
    }
}