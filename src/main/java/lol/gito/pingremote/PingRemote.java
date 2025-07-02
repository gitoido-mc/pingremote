package lol.gito.pingremote;

import lol.gito.pingremote.config.ConfigBuilder;
import lol.gito.pingremote.config.PingRemoteConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PingRemote implements ModInitializer {
    public static final String MOD_ID = "pingremote";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static PingRemoteConfig config;
    public static int counter;

    @Override
    public void onInitialize() {
        LOGGER.info("Ping Remote initialized.");
        config = ConfigBuilder.load(PingRemoteConfig.class, MOD_ID);

        if (config.getRemoteHost() != null) {
            ServerTickEvents.START_SERVER_TICK.register((serverWorld) -> {
                PingRemote.counter++;
            });

            ServerTickEvents.END_WORLD_TICK.register((serverWorld) -> {
                if (counter == config.getTickAmount()) {
                    counter = 0;

                    try (HttpClient client = HttpClient.newBuilder()
                            .version(HttpClient.Version.HTTP_2)
                            .connectTimeout(Duration.ofSeconds(10))
                            .build()) {

                        try {
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(config.getRemoteHost().toURI())
                                    .timeout(Duration.ofMinutes(1))
                                    .build();

                            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                    .thenApply(HttpResponse::body)
                                    .thenAccept((response) -> {
                                        if (config.isDebug()) {
                                            LOGGER.info("Response body from remote host");
                                            LOGGER.info(response);
                                        }
                                    });
                        } catch (URISyntaxException e) {
                            LOGGER.warn("Cannot ping remote host, invalid uri");
                        }
                    }
                }
            });
        } else {
            LOGGER.warn("Remote host is not provided");
            LOGGER.warn("Skipping mod event registration");
        }
    }
}