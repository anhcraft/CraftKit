package dev.anhcraft.craftkit.callbacks.bungee;

import dev.anhcraft.craftkit.callbacks.Callback;

public interface PlayerCountCallback extends Callback {
    void call(String server, int count);
}
