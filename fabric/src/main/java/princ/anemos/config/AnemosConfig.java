package princ.anemos.config;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import net.minecraft.resources.ResourceLocation;

import static princ.anemos.client.Anemos.NAMESPACE;

public class AnemosConfig extends Config {
    private static final String GENERIC_TRANSLATION_PREFIX = "config." + NAMESPACE;

    public AnemosConfig() {
        super(ResourceLocation.fromNamespaceAndPath(NAMESPACE, "toml"), "", "", NAMESPACE);
    }

    public GammaConfig gamma = new GammaConfig();
    public FakeNightVivsionConfig fakeNightVision = new FakeNightVivsionConfig();
    public RemoveBlindnessConfig removeBlindness = new RemoveBlindnessConfig();

    @Translation( prefix = GENERIC_TRANSLATION_PREFIX + ".gamma" )
    public static class GammaConfig extends ConfigSection {
        public ValidatedDouble min = new ValidatedDouble(0.0, 100.0, 0.0);
        public ValidatedDouble default_ = new ValidatedDouble(100.0, 100.0, 0.0);
        public ValidatedDouble max = new ValidatedDouble(1500.0, 1500.0, 1000.0);
        public ValidatedDouble sliderInterval = new ValidatedDouble(1, 100, 1);
        public boolean transition = false;
        public int transitionTime = 30;
        public double prev = default_.get();
    }

    @Translation( prefix = GENERIC_TRANSLATION_PREFIX + ".fnv" )
    public static class FakeNightVivsionConfig extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public boolean transition = false;
        public int transitionTime = 20;
        public boolean fog;
    }

    @Translation( prefix = GENERIC_TRANSLATION_PREFIX + ".rmb" )
    public static class RemoveBlindnessConfig extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public boolean transition = false;
        public int transitionTime = 20;
    }
}
