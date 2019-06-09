package dev.anhcraft.craftkit.utils;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_common.internal.CBPlayerService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.kits.entity.FakeOperator;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.craftkit.internal.listeners.PlayerListener;
import dev.anhcraft.craftkit.lang.enumeration.HandSlot;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility methods which are related to {@link Player}.
 */
public class PlayerUtil {
    private static final CBPlayerService SERVICE = CBProvider.getService(CBPlayerService.class).orElseThrow();

    /**
     * Gets the current ping of given player.
     * @param player the player
     * @return ping
     */
    public static int getPing(@NotNull Player player){
        Condition.argNotNull("player", player);
        return SERVICE.getPing(player);
    }

    /**
     * Gets the offline id of given player.
     * @param player the player
     * @return the offline id.
     */
    public static UUID getOfflineId(@NotNull String player){
        Condition.argNotNull("player", player);
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + player).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gets the profile of given player.
     * @param player player
     * @return game profile
     */
    public static GameProfile getProfile(@NotNull Player player){
        Condition.argNotNull("player", player);
        return SERVICE.getProfile(player);
    }

    /**
     * Overrides the profile of given player.
     * @param player player
     * @param profile new profile
     */
    public static void setProfile(@NotNull Player player, @NotNull GameProfile profile){
        Condition.argNotNull("player", player);
        Condition.argNotNull("profile", profile);
        SERVICE.setProfile(player, profile);
    }

    /**
     * Gets the skin of given player.
     * @param player player
     * @return the skin
     */
    @Nullable
    public static Skin getSkin(@NotNull Player player){
        // we do not need the validator here since #getProfile() already does that
        var gp = getProfile(player);
        if(gp.getProperties().containsKey("textures")){
            var v = gp.getProperties().get("textures").iterator();
            if(v.hasNext()){
                var s = v.next();
                return new Skin(s.getValue(), s.getSignature());
            }
        }
        return null;
    }

    /**
     * Changes the skin of given player.
     * @param player the player
     * @param skin the new skin
     */
    public static void changeSkin(@NotNull Player player, @NotNull Skin skin){
        Condition.argNotNull("player", player);
        Condition.argNotNull("skin", skin);
        var d = Bukkit.getViewDistance();
        List<Player> players = player.getWorld().getNearbyEntities(player.getLocation(), d, d, d)
                .stream()
                .filter(entity -> entity instanceof Player && ((Player) entity).canSee(player))
                .map(entity -> (Player) entity)
                .collect(Collectors.toList());
        players.add(player);
        SERVICE.changeSkin(player, skin, players);
    }

    /**
     * Changes the skin of given player.
     * @param player the player
     * @param skin the new skin
     * @param viewers list of players who can see the new skin of that player (if that player is not included in this list, he will not able to view his new skin)
     */
    public static void changeSkin(@NotNull Player player, @NotNull Skin skin, @NotNull List<Player> viewers){
        Condition.argNotNull("player", player);
        Condition.argNotNull("skin", skin);
        Condition.argNotNull("viewers", viewers);
        SERVICE.changeSkin(player, skin, viewers);
    }

    /**
     * Freezes the given player.
     * @param player the player
     */
    public static void freeze(@NotNull Player player){
        Condition.argNotNull("player", player);
        PlayerListener.freezedPlayers.put(player.getUniqueId(), player.getLocation());
    }

    /**
     * Unfreezes the given player.
     * @param player the player
     */
    public static void unfreeze(@Nullable Player player){
        if(player != null) PlayerListener.freezedPlayers.remove(player.getUniqueId());
    }

    /**
     * Creates a fake operator from the given player.
     * @param player the player
     * @return the operator
     */
    public static FakeOperator fakeOperator(@NotNull Player player){
        Condition.argNotNull("player", player);
        return SERVICE.fakeOp(player);
    }

    /**
     * Sets the camera of given player with the camera of another entity.
     * @param entityId the id of the entity
     * @param player the player
     */
    public static void setCamera(@NotNull Player player, int entityId){
        Condition.argNotNull("player", player);
        SERVICE.setCamera(entityId, player);
    }

    /**
     * Resets the camera of the given player.
     * @param player the player
     */
    public static void resetCamera(@NotNull Player player){
        setCamera(player, player.getEntityId());
    }

    /**
     * Tells the client of given player to open the book GUI.
     * @param player the player who is holding a book
     * @param slot the hand slot which has the book
     */
    public static void openBookGUI(@NotNull Player player, @NotNull HandSlot slot){
        Condition.argNotNull("player", player);
        Condition.argNotNull("slot", slot);
        SERVICE.openBook(player, slot == HandSlot.MAINHAND ? 0 : 1);
    }

    /**
     * Fakes the experience bar of given player.
     * @param player the player
     * @param expBar the progress from reaching the next level (0-1)
     * @param level the current level
     * @param totalExp the total experience
     */
    public static void fakeExp(@NotNull Player player, float expBar, int level, int totalExp){
        Condition.argNotNull("player", player);
        SERVICE.fakeExp(expBar, level, totalExp, player);
    }
}
