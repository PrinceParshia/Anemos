package princ.anemos.config;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.resources.ResourceLocation;

import static princ.anemos.AnemosConstants.*;

@Translation( prefix = GENERIC_CONFIG_TRANSLATION_PREFIX )
public class AnemosConfig extends Config {
    public AnemosConfig() {
        super(ResourceLocation.fromNamespaceAndPath(NAMESPACE, "general"));
    }

    public GammaConfig gamma = new GammaConfig();
    public FakeNightVisionConfig fakeNightVision = new FakeNightVisionConfig();
    public RemoveBlindnessConfig removeBlindness = new RemoveBlindnessConfig();
    public RemoveDarknessConfig removeDarkness = new RemoveDarknessConfig();
    public FogConfig fog = new FogConfig();

    @Translation( prefix = GENERIC_CONFIG_TRANSLATION_PREFIX + ".gamma" )
    public static class GammaConfig extends ConfigSection {
        public ValidatedDouble toggleValue = new ValidatedDouble(1500.0, 1500.0, 500.0);
        public ValidatedDouble defaultValue = new ValidatedDouble(100.0, 100.0, 50.0);
        public ValidatedDouble sliderInterval = new ValidatedDouble(1.0, 100.0, 1.0);
        public boolean transition = false;
        public int transitionTime = 30;
    }

    @Translation( prefix = GENERIC_CONFIG_TRANSLATION_PREFIX + ".fnv" )
    public static class FakeNightVisionConfig extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public ValidatedFloat scale = new ValidatedFloat(100.0F, 100.0F, 0.0F);
        public boolean transition = false;
        public int transitionTime = 20;
        public boolean fog = false;
    }

    @Translation( prefix = GENERIC_CONFIG_TRANSLATION_PREFIX + ".rmb" )
    public static class RemoveBlindnessConfig extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public boolean transition = false;
        public int transitionTime = 20;
    }

    @Translation( prefix = GENERIC_CONFIG_TRANSLATION_PREFIX + ".rmd" )
    public static class RemoveDarknessConfig extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public boolean transition = false;
        public int transitionTime = 20;
    }

    @Translation( prefix = GENERIC_CONFIG_TRANSLATION_PREFIX + ".fog" )
    public static class FogConfig extends ConfigSection {
        public boolean enabled = true;
        public boolean lava = true;
        public boolean powderSnow = true;
        public boolean water = true;
        public boolean sky = true;
        public boolean terrain = true;
    }
}