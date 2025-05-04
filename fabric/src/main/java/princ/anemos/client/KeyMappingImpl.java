package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

import static princ.anemos.AnemosConstants.*;

@Environment(EnvType.CLIENT)
public class KeyMappingImpl {
    public static final KeyMapping gammaKey = new KeyMapping(GENERIC_KEY_NAMESPACE + ".gamma", InputConstants.Type.KEYSYM, InputConstants.KEY_G, KEY_CATEGORY);
    public static final KeyMapping fakeNightVisionKey = new KeyMapping(GENERIC_KEY_NAMESPACE + ".fnv", InputConstants.Type.KEYSYM, InputConstants.KEY_N, KEY_CATEGORY);
    public static final KeyMapping removeBlindnessKey = new KeyMapping(GENERIC_KEY_NAMESPACE + ".rmb", InputConstants.Type.KEYSYM, InputConstants.KEY_B, KEY_CATEGORY);
    public static final KeyMapping removeDarknessKey = new KeyMapping(GENERIC_KEY_NAMESPACE + ".rmd", InputConstants.Type.KEYSYM, InputConstants.KEY_M, KEY_CATEGORY);

    public static void registerMappings() {
        KeyBindingHelper.registerKeyBinding(gammaKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (gammaKey.consumeClick()) adjustGamma(config.gamma.transition);
            handleGammaTransition();
        });

        KeyBindingHelper.registerKeyBinding(fakeNightVisionKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            adjustFakeNightVision();
            if (fakeNightVisionKey.consumeClick()) adjustFakeNightVisionScale(config.fakeNightVision.transition);
            handleFakeNightVisionScaleTransition();
        });

        KeyBindingHelper.registerKeyBinding(removeBlindnessKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (removeBlindnessKey.consumeClick()) adjustRemoveBlindness(config.removeBlindness.transition);
            handleRemoveBlindnessTransition();
        });

        KeyBindingHelper.registerKeyBinding(removeDarknessKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (removeDarknessKey.consumeClick()) adjustRemoveDarkness(config.removeDarkness.transition);
            handleRemoveDarknessTransition();
        });
    }
}
