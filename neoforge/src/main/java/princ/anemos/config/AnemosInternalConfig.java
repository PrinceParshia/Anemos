package princ.anemos.config;

import me.fzzyhmstrs.fzzy_config.api.SaveType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.resources.ResourceLocation;

import static princ.anemos.AnemosConstants.*;

public class AnemosInternalConfig extends Config {
    public AnemosInternalConfig() {
        super(ResourceLocation.fromNamespaceAndPath(NAMESPACE, "internal"));
    }

    public InternalGammaConfig gamma = new InternalGammaConfig();
    public InternalFakeNightVisionConfig fakeNightVision = new InternalFakeNightVisionConfig();

    public static class InternalGammaConfig extends ConfigSection {
        public double min = 0.0, max = 15.0;
        public ValidatedDouble prev = new ValidatedDouble(config.gamma.defaultValue.get(), config.gamma.toggleValue.get(), 0.0);
    }

    public static class InternalFakeNightVisionConfig extends ConfigSection {
        public float minScale = 0.0F, maxScale = 1.0F;
        public ValidatedFloat prev = new ValidatedFloat(0.0F, 100.0F, 0.0F);
    }

    @Override
    public SaveType saveType() {
        return SaveType.SEPARATE;
    }
}
