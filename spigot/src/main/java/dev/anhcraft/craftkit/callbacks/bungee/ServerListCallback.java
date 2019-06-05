package dev.anhcraft.craftkit.callbacks.bungee;

import dev.anhcraft.craftkit.callbacks.Callback;

import java.util.List;

public interface ServerListCallback extends Callback {
    void call(List<String> servers);
}
