package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.internal.CBServerService;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains extra methods which are related to the whole server.
 */
public class ServerUtil {
    private static final CBServerService SERVICE = CBProvider.getService(CBServerService.class).orElseThrow(UnsupportedOperationException::new);

    /**
     * Gets all entities over the server.
     * @return a list of entities
     */
    @NotNull
    public static List<Entity> getAllEntities(){
        List<Entity> e = new ArrayList<>();
        for(World w : Bukkit.getWorlds()) e.addAll(w.getEntities());
        return e;
    }

    /**
     * Gets all entities which are same species over the server.
     * @param entityClass the class represents a type of entity
     * @param <E> the entity type
     * @return list of entities
     */
    @NotNull
    public static <E extends Entity> List<Entity> getAllEntitiesByClass(@NotNull Class<E> entityClass){
        Condition.argNotNull("entityClass", entityClass);
        List<Entity> e = new ArrayList<>();
        for(World w : Bukkit.getWorlds()) e.addAll(w.getEntitiesByClass(entityClass));
        return e;
    }

    /**
     * Gets three recent TPS records (1 min, 10 min, 15 min ago).
     * @return an array that contains three recent TPS values
     */
    @NotNull
    public static double[] getTPS(){
        return SERVICE.getTPS();
    }
}
