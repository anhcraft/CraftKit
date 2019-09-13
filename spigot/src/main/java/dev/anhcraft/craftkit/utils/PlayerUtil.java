package dev.anhcraft.craftkit.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.anhcraft.craftkit.cb_common.internal.CBPlayerService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.craftkit.internal.listeners.PlayerListener;
import dev.anhcraft.craftkit.lang.enumeration.HandSlot;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility methods which are related to Player.
 */
public class PlayerUtil {
    private static final CBPlayerService SERVICE = CBProvider.getService(CBPlayerService.class).orElseThrow(UnsupportedOperationException::new);
    private static final Object FREEZE_LOCK = new Object();

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
    @NotNull
    public static UUID getOfflineId(@NotNull String player){
        Condition.argNotNull("player", player);
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + player).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gets the profile of given player.
     * @param player player
     * @return game profile
     */
    @NotNull
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
        GameProfile gp = getProfile(player);
        Collection<Property> t = gp.getProperties().get("textures");
        if(t != null){
            Iterator<Property> v = t.iterator();
            if(v.hasNext()){
                Property s = v.next();
                return new Skin(s.getValue(), s.getSignature());
            }
        }
        return null;
    }

    /**
     * Changes the skin of given player.<br>
     * If the server is running under proxy, please use {@link BungeeUtil#changeSkin(String, Skin)} instead.
     * @param player the player
     * @param skin the new skin
     */
    public static void changeSkin(@NotNull Player player, @NotNull Skin skin){
        Condition.argNotNull("player", player);
        Condition.argNotNull("skin", skin);
        List<Player> players = player.getWorld().getPlayers()
                .stream()
                .filter(entity -> entity.canSee(player))
                .collect(Collectors.toList());
        players.add(player);
        SERVICE.changeSkin(player, skin, players);
    }

    /**
     * Changes the skin of given player.<br>
     * If the server is running under proxy, please use {@link BungeeUtil#changeSkin(String, Skin)} instead.
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
        synchronized (FREEZE_LOCK) {
            PlayerListener.freezedPlayers.put(player.getUniqueId(), player.getLocation());
        }
    }

    /**
     * Unfreezes the given player.
     * @param player the player
     */
    public static void unfreeze(@Nullable Player player){
        if(player != null) {
            synchronized (FREEZE_LOCK) {
                PlayerListener.freezedPlayers.remove(player.getUniqueId());
            }
        }
    }

    /**
     * Executes the command as an operator.
     * @param player the player
     * @param cmds commands to be executed
     */
    public static void execCmdAsOp(@NotNull Player player, @NotNull String... cmds){
        Condition.argNotNull("player", player);
        Condition.argNotNull("cmds", cmds);
        for(String s : cmds) Bukkit.dispatchCommand(new FakeOperator(player), s);
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

    // A fake operator (tks OpticFusion)
    static class FakeOperator implements CommandSender {
        private Player player;

        FakeOperator(Player player) {
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public boolean isPermissionSet(@NotNull String name) {
            return true;
        }

        public boolean isPermissionSet(@NotNull Permission perm) {
            return true;
        }

        @Override
        public boolean isOp() {
            return true;
        }

        @Override
        public void setOp(boolean value) {
        }

        @Override
        public boolean hasPermission(@NotNull String name) {
            return true;
        }

        @Override
        public boolean hasPermission(@NotNull Permission perm) {
            return true;
        }

        @Override
        public void sendMessage(String string) {
            player.sendMessage(string);
        }

        @Override
        public void sendMessage(String[] strings) {
            player.sendMessage(strings);
        }

        @Override
        public Server getServer() {
            return player.getServer();
        }

        @Override
        public String getName() {
            return player.getName();
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln) {
            return player.addAttachment(plugin, string, bln);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin) {
            return player.addAttachment(plugin);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String string, boolean bln, int i) {
            return player.addAttachment(plugin, string, bln, i);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, int i) {
            return player.addAttachment(plugin, i);
        }

        @Override
        public void removeAttachment(PermissionAttachment pa) {
            player.removeAttachment(pa);
        }

        @Override
        public void recalculatePermissions() {
            player.recalculatePermissions();
        }

        @Override
        public Set<PermissionAttachmentInfo> getEffectivePermissions() {
            return player.getEffectivePermissions();
        }
    }
}
