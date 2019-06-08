package dev.anhcraft.craftkit.kits.entity;

import dev.anhcraft.craftkit.common.lang.annotation.NoAutomaticCleaner;
import dev.anhcraft.jvmkit.lang.annotation.Beta;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents {@code CustomEntity} implementation.
 */
@Beta
@NoAutomaticCleaner
public abstract class CustomEntity {
    private final Object VIEWER_MODIFY_LOCK = new Object(); // we must warrant the synchronization across three methods #addViewer, #removeViewer and #setViewers
    @NoAutomaticCleaner private List<Player> viewers = new ArrayList<>();
    private boolean isDead;
    int id = -1;
    Location location;

    protected abstract void addViewerCallback(List<Player> players);
    protected abstract void removeViewerCallback(List<Player> players);
    protected abstract void killCallback();

    /**
     * Checks if this entity was spawned.<br>
     * This entity may be determined as "spawned" even if it died after.
     * @return {@code true} if it was or {@code false} if not
     */
    public boolean isSpawned(){
        return id != -1 && location != null;
    }

    /**
     * Allows the given player to see this entity.
     * @param player the player
     */
    public void addViewer(@NotNull Player player){
        Condition.check(!isDead, "Oops... This entity died!");
        Condition.argNotNull("player", player);
        Condition.check(player.isOnline(), "Wait... the player is not online!!!");
        synchronized (VIEWER_MODIFY_LOCK) {
            viewers.add(player);
            addViewerCallback(Collections.singletonList(player));
        }
    }

    /**
     * Prevents the given player from seeing this entity.
     * @param player the player
     */
    public void removeViewer(@NotNull Player player){
        Condition.check(!isDead, "Oops... This entity died!");
        Condition.argNotNull("player", player);
        synchronized (VIEWER_MODIFY_LOCK) {
            viewers.remove(player);
            removeViewerCallback(Collections.singletonList(player));
        }
    }

    /**
     * Sets the list of viewers who can see this entity.
     * @param players list of players
     */
    public void setViewers(@NotNull List<Player> players){
        Condition.check(!isDead, "Oops... This entity died!");
        Condition.argNotNull("players", players);
        synchronized (VIEWER_MODIFY_LOCK) {
            List<Player> newPlayers = new ArrayList<>(players);
            List<Player> oldPlayers = new ArrayList<>(viewers);
            newPlayers.removeAll(viewers); // skip all current viewers
            addViewerCallback(newPlayers); // only add new viewers
            oldPlayers.removeAll(players); // skip all available viewers
            removeViewerCallback(oldPlayers); // only remove unavailable viewers
            viewers = new ArrayList<>(players); // we do not want to get changes from the given list
        }
    }

    /**
     * Returns the list of viewers.
     * @return viewers
     */
    public List<Player> getViewers(){
        return Collections.unmodifiableList(viewers);
    }

    /**
     * Checks if this entity died.
     * @return {@code true} if it died or {@code false} if it is still living.
     */
    public boolean isDead(){
        return isDead;
    }

    /**
     * Kills this entity.
     */
    public void kill(){
        if(isDead()) return;
        isDead = true;
        killCallback();
        viewers.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEntity that = (CustomEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}