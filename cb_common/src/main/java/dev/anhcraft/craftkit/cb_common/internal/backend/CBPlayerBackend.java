package dev.anhcraft.craftkit.cb_common.internal.backend;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.common.Skin;
import org.bukkit.entity.Player;

import java.util.List;

public interface CBPlayerBackend extends IBackend {
    Object toNmsEntityPlayer(Player player);
    List<Object> toNmsEntityPlayers(List<Player> players);
    int getPing(Player player);
    GameProfile getProfile(Player player);
    void setProfile(Player player, GameProfile profile);
    void changeSkin(Player player, Skin skin, List<Player> viewers);
    void setCamera(int entityId, Player viewer);
    void openBook(Player player, int slot);
    void fakeExp(float expBar, int level, int totalExp, Player player);
}
