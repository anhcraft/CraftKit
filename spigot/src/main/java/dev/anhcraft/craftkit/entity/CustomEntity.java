package dev.anhcraft.craftkit.entity;

import dev.anhcraft.jvmkit.lang.annotation.Beta;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents {@code CustomEntity} implementation.
 */
@Beta
public abstract class CustomEntity {
    List<Player> viewers = Collections.synchronizedList(new ArrayList<>());
    private boolean isDead;
    int id = -1;
    Location location;

    protected abstract void addViewerCallback(List<Player> players);
    protected abstract void removeViewerCallback(List<Player> players);
    protected abstract void killCallback();
    protected abstract void teleportCallback();

    /**
     * Returns the id of this entity.
     * @return entity's id
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if this entity was spawned.<br>
     * This entity may be determined as "spawned" even if it died.
     * @return {@code true} if it was or {@code false} if not
     */
    public boolean isSpawned(){
        return id != -1 && location != null;
    }

    /**
     * Gets the location of this entity with the provided {@link Location}.
     * @param location the provided {@link Location}
     * @return the given location
     */
    @NotNull
    public Location getLocation(Location location){
        location.setWorld(this.location.getWorld());
        location.setX(this.location.getX());
        location.setY(this.location.getY());
        location.setZ(this.location.getZ());
        return location;
    }

    /**
     * Gets the location of this entity.
     * @return a clone of current {@link Location}
     */
    @NotNull
    public Location getLocation(){
        return location.clone();
    }

    /**
     * Let the given player see this entity.
     * @param player the player
     */
    public void addViewer(@NotNull Player player){
        Condition.check(!isDead, "Oops... This entity died!");
        Condition.argNotNull("player", player);
        Condition.check(player.isOnline(), "Wait... the player is not online!!!");
        if(viewers.contains(player)) return;
        viewers.add(player);
        addViewerCallback(Collections.singletonList(player));
    }

    /**
     * Prevents the given player from seeing this entity.
     * @param player the player
     */
    public void removeViewer(@NotNull Player player){
        Condition.check(!isDead, "Oops... This entity died!");
        Condition.argNotNull("player", player);
        viewers.remove(player);
        removeViewerCallback(Collections.singletonList(player));
    }

    /**
     * Sets the list of viewers who can see this entity.
     * @param players list of players
     */
    public void setViewers(@NotNull List<Player> players){
        Condition.check(!isDead, "Oops... This entity died!");
        Condition.argNotNull("players", players);
        List<Player> newPlayers = new ArrayList<>(players);
        List<Player> oldPlayers = new ArrayList<>(viewers);
        newPlayers.removeAll(viewers); // skip all current viewers
        addViewerCallback(newPlayers); // only add new viewers
        oldPlayers.removeAll(players); // skip all available viewers
        removeViewerCallback(oldPlayers); // only remove unavailable viewers
        viewers = Collections.synchronizedList(new ArrayList<>(players)); // we do not want to get changes from the given list
    }

    /**
     * Returns the immutable list of viewers.
     * @return viewers
     */
    @NotNull
    public List<Player> getViewers(){
        return Collections.unmodifiableList(viewers);
    }

    /**
     * Checks if the given player is viewing this entity.
     * @param player viewers
     * @return {@code true} if he is or {@code false} if not
     */
    public boolean hasViewer(@NotNull Player player){
        Condition.argNotNull("player", player);
        return viewers.contains(player);
    }

    /**
     * Teleports this entity to the given location.
     * @param location the location
     */
    public void teleport(@NotNull Location location){
        Condition.check(!isDead(), "Oops... This entity died!");
        Condition.argNotNull("location", location);
        this.location = location;
        teleportCallback();
    }

    /**
     * Teleports this entity to another entity.
     * @param entity another entity
     */
    public void teleport(@NotNull Entity entity){
        Condition.check(!isDead(), "Oops... This entity died!");
        Condition.argNotNull("entity", entity);
        location = (location == null) ? entity.getLocation() : entity.getLocation(location);
        teleportCallback();
    }

    /**
     * Teleports this entity to the given block.
     * @param block the block
     */
    public void teleport(@NotNull Block block){
        Condition.check(!isDead(), "Oops... This entity died!");
        Condition.argNotNull("block", block);
        location = (location == null) ? block.getLocation() : block.getLocation(location);
        teleportCallback();
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
        killCallback();
        isDead = true;
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
