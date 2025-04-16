package princ.anemos.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import net.minecraft.resources.ResourceLocation;

import static princ.anemos.Anemos.MOD_ID;

public class AnemosConfig extends Config {
    public AnemosConfig() {
        super(ResourceLocation.fromNamespaceAndPath(MOD_ID, "general"), "", "", MOD_ID);
    }

    public GammaConfig gamma = new GammaConfig();
    public FakeNightVivsionConfig fakeNightVision = new FakeNightVivsionConfig();
    public RemoveBlindnessConfig removeBlindness = new RemoveBlindnessConfig();

    public static class GammaConfig extends ConfigSection {
        public double min = 0.0, default_ = 1.0, max = 15.0;
        public double sliderInterval = 0.01;
        public boolean transition = false;
        public int transitionTime = 30;
        public double prev;
    }

    public static class FakeNightVivsionConfig extends ConfigSection {
        public boolean enabled;
        public boolean transition = false;
        public int transitionTime = 20;
    }

    public static class RemoveBlindnessConfig extends ConfigSection {
        public boolean enabled;
        public boolean transition = false;
        public int transitionTime = 20;
    }
}
