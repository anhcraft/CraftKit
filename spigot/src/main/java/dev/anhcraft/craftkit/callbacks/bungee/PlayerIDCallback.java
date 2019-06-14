package dev.anhcraft.craftkit.callbacks.bungee;

import dev.anhcraft.craftkit.common.callbacks.Callback;

import java.util.UUID;

public interface PlayerIDCallback extends Callback {
    void call(UUID id);
}
