package dev.anhcraft.craftkit.callbacks.bungee;

import dev.anhcraft.craftkit.callbacks.Callback;

import java.util.List;

public interface PlayerListCallback extends Callback {
    void call(String server, List<String> players);
}
