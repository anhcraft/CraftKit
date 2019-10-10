package dev.anhcraft.craftkit.entity;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_common.internal.*;
import dev.anhcraft.craftkit.cb_common.internal.enums.PlayerInfoEnum;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a fake entity player (NPC).
 */
public class NPC extends CustomEntity {
    private static final CBOutPacketService SERVICE_1 = CBProvider.getService(CBOutPacketService.class).orElseThrow(UnsupportedOperationException::new);
    private static final CBEntityNPCService SERVICE_2 = CBProvider.getService(CBEntityNPCService.class).orElseThrow(UnsupportedOperationException::new);
    private static final CBEntityService SERVICE_3 = CBProvider.getService(CBEntityService.class).orElseThrow(UnsupportedOperationException::new);
    private static final CBPlayerService SERVICE_4 = CBProvider.getService(CBPlayerService.class).orElseThrow(UnsupportedOperationException::new);

    /**
     * Spawns a new NPC with the given information.
     * @param profile the profile of the NPC
     * @param location the spawning location
     * @return the NPC which has just spawned
     */
    @NotNull
    public static NPC spawn(@NotNull GameProfile profile, @NotNull Location location){
        Condition.argNotNull("profile", profile);
        Condition.argNotNull("location", location);
        return new NPC(SERVICE_2.create(profile, location.getWorld()), location);
    }

    private Object npc;
    private boolean showOnTablist;

    private NPC(Object npc, Location location){
        this.npc = npc;
        this.location = location;
        this.id = SERVICE_3.getId(npc);
        SERVICE_3.teleport(npc, location, new ArrayList<>());
    }

    @Override
    protected void addViewerCallback(List<Player> players) {
        List<Object> viewers = SERVICE_4.toNmsEntityPlayers(players);
        SERVICE_1.playerInfo(Collections.singletonList(npc), PlayerInfoEnum.ADD_PLAYER, viewers);
        SERVICE_1.namedEntitySpawn(npc, viewers);
        if(!isShowOnTablist()) CKProvider.TASK_HELPER.newDelayedAsyncTask(() -> SERVICE_1.playerInfo(Collections.singletonList(npc), PlayerInfoEnum.REMOVE_PLAYER, viewers), 40);
    }

    @Override
    protected void removeViewerCallback(List<Player> players) {
        List<Object> viewers = SERVICE_4.toNmsEntityPlayers(players);
        SERVICE_1.playerInfo(Collections.singletonList(npc), PlayerInfoEnum.REMOVE_PLAYER, viewers);
        SERVICE_1.entityDestroy(new int[]{id}, viewers);
    }

    @Override
    protected void killCallback() {
        removeViewerCallback(getViewers());
    }

    /**
     * Checks is the NPC is currently shown on viewers tablist.
     * @return {@code true} if it is or {@code false} if not
     */
    public boolean isShowOnTablist() {
        return showOnTablist;
    }

    /**
     * Returns the current location of this NPC.
     * @return NPC's location
     */
    @NotNull
    public Location getLocation() {
        return location;
    }

    /**
     * Shows or hides this NPC on viewers tablist.
     * @param showOnTablist {@code true} to show or {@code false} to hide
     */
    public synchronized void setShowOnTablist(boolean showOnTablist) {
        if(showOnTablist == this.showOnTablist) return;
        Condition.check(!isDead(), "Oops... This entity died!");
        this.showOnTablist = showOnTablist;
        List<Object> viewers = SERVICE_4.toNmsEntityPlayers(getViewers());
        PlayerInfoEnum en = showOnTablist ? PlayerInfoEnum.ADD_PLAYER : PlayerInfoEnum.REMOVE_PLAYER;
        SERVICE_1.playerInfo(Collections.singletonList(npc), en, viewers);
    }

    /**
     * Teleports this NPC to the given location
     * @param location new NPC location
     */
    public synchronized void teleport(@Nullable Location location){
        if(location == null || location.equals(this.location)) return;
        Condition.check(!isDead(), "Oops... This entity died!");
        this.location = location;
        List<Object> viewers = SERVICE_4.toNmsEntityPlayers(getViewers());
        SERVICE_3.teleport(npc, location, viewers);
    }

    /**
     * Rotates this NPC.
     * @param yaw the new yaw (in degrees)
     * @param pitch the new pitch (in degrees)
     */
    public synchronized void rotate(float yaw, float pitch){
        Condition.check(!isDead(), "Oops... This entity died!");
        location.setYaw(yaw);
        location.setPitch(pitch);
        List<Object> viewers = SERVICE_4.toNmsEntityPlayers(getViewers());
        SERVICE_3.rotate(npc, yaw, pitch, viewers);
    }
}
