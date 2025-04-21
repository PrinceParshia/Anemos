package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import static princ.anemos.Anemos.NAMESPACE;

@EventBusSubscriber(modid = NAMESPACE, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyMappingRegistry {
    private static final String CATEGORY = "key.categories." + NAMESPACE;
    private static final String GENERIC_KEY_NAMESPACE = "key." + NAMESPACE;

    public static final Lazy<KeyMapping> gammaKey = Lazy.of(() -> new KeyMapping(GENERIC_KEY_NAMESPACE + ".gamma", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, CATEGORY));
    public static final Lazy<KeyMapping> fakeNightVisionKey = Lazy.of(() -> new KeyMapping(GENERIC_KEY_NAMESPACE + ".fnv", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, CATEGORY));
    public static final Lazy<KeyMapping> removeBlindnessKey = Lazy.of(() -> new KeyMapping(GENERIC_KEY_NAMESPACE + ".rmb", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, CATEGORY));

    @SubscribeEvent
    private static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(gammaKey.get());
        event.register(fakeNightVisionKey.get());
        event.register(removeBlindnessKey.get());
    }
}
