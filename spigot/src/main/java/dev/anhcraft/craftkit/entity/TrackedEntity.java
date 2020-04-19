package dev.anhcraft.craftkit.entity;

import dev.anhcraft.craftkit.common.internal.Supervisor;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A wrapper for {@link CustomEntity}.
 *
 * @param <T> The entity type
 */
public class TrackedEntity<T extends CustomEntity> extends CustomEntity {
    private final T entity;
    private double viewDistance = Bukkit.getViewDistance();

    /**
     * @deprecated INTERNAL USES ONLY!
     */
    @Deprecated
    public TrackedEntity(@NotNull T entity){
        Supervisor.checkAccess();
        Condition.argNotNull("entity", entity);
        Condition.check(!entity.isDead(), "Oops... This entity died!");
        this.entity = entity;
        location = entity.location;
        id = entity.id;
        setViewers(entity.viewers);
    }

    @Override
    protected void addViewerCallback(List<Player> players) {
    }

    @Override
    protected void removeViewerCallback(List<Player> players) {
        entity.removeViewerCallback(players);
    }

    @Override
    protected void killCallback() {
        entity.killCallback();
    }

    @Override
    protected void teleportCallback() {
        entity.location = location;
        entity.teleportCallback();
    }

    /**
     * Gets the view distance.
     * @return the maximum allowed distance for player to see
     */
    public double getViewDistance() {
        return viewDistance;
    }

    /**
     * Sets the view distance.
     * @param viewDistance view distance
     */
    public void setViewDistance(double viewDistance) {
        Condition.check(viewDistance >= 1, "View distance must be higher than 0");
        this.viewDistance = viewDistance;
    }

    /**
     * @deprecated INTERNAL USES ONLY!
     */
    @Deprecated
    public void updateView(){
        Location loc = location.clone();
        double x = viewDistance * viewDistance;
        viewers.removeIf(p -> {
            if(p.isOnline()){
                if(!p.getWorld().equals(location.getWorld()) || p.getLocation(loc).distanceSquared(location) >= x){
                    entity.removeViewer(p);
                } else {
                    entity.addViewer(p);
                }
                return false;
            }
            return true;
        });
    }

    @NotNull
    public T getEntity() {
        return entity;
    }
}
