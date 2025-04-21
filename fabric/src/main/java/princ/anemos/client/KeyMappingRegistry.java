package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import static princ.anemos.client.Anemos.NAMESPACE;
import static princ.anemos.client.Anemosystem.*;

@Environment(EnvType.CLIENT)
public class KeyMappingRegistry {
    private static final String CATEGORY = "key.categories." + NAMESPACE;
    private static final String GENERIC_KEY_NAMESPACE = "key." + NAMESPACE;

    public static final KeyMapping gammaKey = new KeyMapping(GENERIC_KEY_NAMESPACE + ".gamma", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, CATEGORY);
    public static final KeyMapping fakeNightVisionKey = new KeyMapping(GENERIC_KEY_NAMESPACE + ".fnv", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, CATEGORY);
    public static final KeyMapping removeBlindnessKey = new KeyMapping(GENERIC_KEY_NAMESPACE + ".rmb", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, CATEGORY);

    public static void registerKeyMapping() {
        registerBrightnessAuraKey();
        registerFakeNightVisionKey();
        registerRemoveBlindnessKey();
    }

    private static void registerBrightnessAuraKey() {
        KeyBindingHelper.registerKeyBinding(gammaKey);
        ClientTickEvents.END_CLIENT_TICK.register(client -> execGammaKeyAction());
    }

    private static void registerFakeNightVisionKey() {
        KeyBindingHelper.registerKeyBinding(fakeNightVisionKey);
        ClientTickEvents.END_CLIENT_TICK.register(client -> execFnvKeyAction());
    }

    private static void registerRemoveBlindnessKey() {
        KeyBindingHelper.registerKeyBinding(removeBlindnessKey);
        ClientTickEvents.END_CLIENT_TICK.register(client -> execRmbKeyAction());
    }
}
