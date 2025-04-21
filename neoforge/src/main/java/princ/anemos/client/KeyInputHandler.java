package princ.anemos.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static princ.anemos.Anemos.NAMESPACE;
import static princ.anemos.client.Anemosystem.*;

@EventBusSubscriber(modid = NAMESPACE, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    private static void registerGammaKeyAction(ClientTickEvent.Post event) {
        execGammaKeyAction();
    }

    @SubscribeEvent
    private static void registerFnvKeyAction(ClientTickEvent.Post event) {
        execFnvKeyAction();
    }

    @SubscribeEvent
    private static void registerRmbKeyAction(ClientTickEvent.Post event) {
        execRmbKeyAction();
    }
}
