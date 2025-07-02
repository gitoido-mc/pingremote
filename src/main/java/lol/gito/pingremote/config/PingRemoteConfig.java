package lol.gito.pingremote.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class PingRemoteConfig {
    private URL remoteHost = URI.create("https://example.com").toURL();
    private int tickAmount = 6000;
    private boolean debug = false;

    public PingRemoteConfig() throws MalformedURLException {
    }

    public URL getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(URL remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getTickAmount() {
        return tickAmount;
    }

    public void setTickAmount(int tickAmount) {
        this.tickAmount = tickAmount;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
