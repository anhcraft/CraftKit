package dev.anhcraft.craftkit.callbacks.bungee;

import dev.anhcraft.craftkit.callbacks.Callback;

public interface PlayerIPCallback extends Callback {
    void call(String host, int port);
}
