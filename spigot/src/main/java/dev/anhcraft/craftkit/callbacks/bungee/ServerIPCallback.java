package dev.anhcraft.craftkit.callbacks.bungee;

import dev.anhcraft.craftkit.common.callbacks.Callback;

public interface ServerIPCallback extends Callback {
    void call(String server, String host, int port);
}
