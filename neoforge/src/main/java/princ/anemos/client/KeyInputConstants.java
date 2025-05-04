package princ.anemos.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static princ.anemos.AnemosConstants.*;
import static princ.anemos.client.KeyMappingImpl.*;

@EventBusSubscriber(modid = NAMESPACE, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class KeyInputConstants {
    @SubscribeEvent
    private static void registerGammaKeyAction(ClientTickEvent.Post event) {
        if (gammaKey.get().consumeClick()) adjustGamma(config.gamma.transition);
        handleGammaTransition();
    }

    @SubscribeEvent
    private static void registerFakeNightVisionKeyAction(ClientTickEvent.Post event) {
        adjustFakeNightVision();
        if (fakeNightVisionKey.get().consumeClick()) adjustFakeNightVisionScale(config.fakeNightVision.transition);
        handleFakeNightVisionScaleTransition();
    }

    @SubscribeEvent
    private static void registerRemoveBlindnessKeyAction(ClientTickEvent.Post event) {
        if (removeBlindnessKey.get().consumeClick()) adjustRemoveBlindness(config.removeBlindness.transition);
        handleRemoveBlindnessTransition();
    }

    @SubscribeEvent
    private static void registerRemoveDarknessKeyAction(ClientTickEvent.Post event) {
        if (removeDarknessKey.get().consumeClick()) adjustRemoveDarkness(config.removeDarkness.transition);
        handleRemoveDarknessTransition();
    }
}
