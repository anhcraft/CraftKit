package dev.anhcraft.craftkit.callbacks.bungee;

import dev.anhcraft.craftkit.common.callbacks.Callback;

import java.util.UUID;

public interface ProxyPlayerIDCallback extends Callback {
    void call(String player, UUID id);
}
