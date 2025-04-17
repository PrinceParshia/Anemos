package princ.anemos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import princ.anemos.client.KeyMappingRegistry;

@Environment(EnvType.CLIENT)
public class Anemos implements ClientModInitializer {
	public static final String NAMESPACE = "anemos";
	public static final String NAME = "Anemos";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitializeClient() {
		KeyMappingRegistry.registerKeyMapping();
	}
}