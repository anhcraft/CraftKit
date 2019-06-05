package dev.anhcraft.craftkit.cb_common.internal;

import dev.anhcraft.craftkit.cb_common.internal.annotation.IsNMS;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;

public interface CBEntityService extends CBService {
    Object toNms(Entity entity);
    int getId(@IsNMS Object entity);
    void rotate(@IsNMS Object entity, float yaw, float pitch, @IsNMS List<Object> viewers);
    void teleport(@IsNMS Object entity, Location location, @IsNMS List<Object> viewers);
}
