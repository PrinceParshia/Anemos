package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;

import static princ.anemos.AnemosConstants.*;

@EventBusSubscriber(modid = NAMESPACE, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyMappingImpl {
    public static final Lazy<KeyMapping> gammaKey = Lazy.of(() -> new KeyMapping(GENERIC_KEY_NAMESPACE + ".gamma", InputConstants.Type.KEYSYM, InputConstants.KEY_G, KEY_CATEGORY));
    public static final Lazy<KeyMapping> fakeNightVisionKey = Lazy.of(() -> new KeyMapping(GENERIC_KEY_NAMESPACE + ".fnv", InputConstants.Type.KEYSYM, InputConstants.KEY_N, KEY_CATEGORY));
    public static final Lazy<KeyMapping> removeBlindnessKey = Lazy.of(() -> new KeyMapping(GENERIC_KEY_NAMESPACE + ".rmb", InputConstants.Type.KEYSYM, InputConstants.KEY_B, KEY_CATEGORY));
    public static final Lazy<KeyMapping> removeDarknessKey = Lazy.of(() -> new KeyMapping(GENERIC_KEY_NAMESPACE + ".rmd", InputConstants.Type.KEYSYM, InputConstants.KEY_M, KEY_CATEGORY));

    @SubscribeEvent
    private static void registerMappings(RegisterKeyMappingsEvent event) {
        event.register(gammaKey.get());
        event.register(fakeNightVisionKey.get());
        event.register(removeBlindnessKey.get());
        event.register(removeDarknessKey.get());
    }
}
