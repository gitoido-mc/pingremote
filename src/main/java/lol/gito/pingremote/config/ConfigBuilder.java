package lol.gito.pingremote.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class ConfigBuilder<T> {
    private final Class<T> clazz;
    private final String path;

    private ConfigBuilder(Class<T> clazz, String path) {
        this.clazz = clazz;
        this.path = path;
    }

    public static <T> T load(Class<T> clazz, String path) {
        return new ConfigBuilder<>(clazz, path)._load();
    }

    public T _load() {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();

        T config = gson.fromJson("{}", clazz);
        File configFile = new File("config/" + path + ".json");
        configFile.getParentFile().mkdirs();

        if (configFile.exists()) {
            try (FileReader fileReader = new FileReader(configFile)) {
                config = gson.fromJson(fileReader, clazz);
            } catch (Exception e) {
                System.out.println("Error reading config file");
            }
        }

        try (PrintWriter pw = new PrintWriter(configFile)) {
            gson.toJson(config, pw);
        } catch (Exception e) {
            System.out.println("Error writing config file");
        }

        return config;
    }
}

