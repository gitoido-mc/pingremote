package lol.gito.pingremote.config;

import java.net.URL;

public class PingRemoteConfig {
    private URL remoteHost;
    private int tickAmount = 6000;

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
}
