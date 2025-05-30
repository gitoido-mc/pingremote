package lol.gito.pingremote;

import lol.gito.pingremote.config.ConfigBuilder;
import lol.gito.pingremote.config.PingRemoteConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingRemote implements ModInitializer {
	public static final String MOD_ID = "pingremote";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static PingRemoteConfig config;
	public static int counter;

	@Override
	public void onInitialize() {
		LOGGER.info("Ping Remote initialized.");
		config = ConfigBuilder.load(PingRemoteConfig.class, MOD_ID);

		ServerTickEvents.START_SERVER_TICK.register((serverWorld) -> {
			PingRemote.counter++;
		});

		ServerTickEvents.END_WORLD_TICK.register((serverWorld) -> {
			if (PingRemote.counter == PingRemote.config.getTickAmount()) {

			}
		});
	}
}