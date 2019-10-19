package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.internal.services.ServiceProvider;
import dev.anhcraft.craftkit.cb_common.internal.services.CBServerService;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class contains extra methods which are related to the whole server.
 */
public class ServerUtil {
    private static final CBServerService SERVICE = ServiceProvider.getService(CBServerService.class).orElseThrow(UnsupportedOperationException::new);

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
     * Gets all entities over the server.
     * @param entityConsumer the consumer for getting entities
     */
    public static void getAllEntities(@NotNull Consumer<Entity> entityConsumer){
        Condition.argNotNull("entityConsumer", entityConsumer);
        for(World w : Bukkit.getWorlds())
            w.getEntities().forEach(entityConsumer);
    }

    /**
     * Gets all entities which are same species over the server.
     * @param entityClass the class represents a type of entity
     * @param <E> the entity type
     * @return list of entities
     */
    @NotNull
    public static <E extends Entity> List<E> getAllEntitiesByClass(@NotNull Class<E> entityClass){
        Condition.argNotNull("entityClass", entityClass);
        List<E> e = new ArrayList<>();
        for(World w : Bukkit.getWorlds()) e.addAll(w.getEntitiesByClass(entityClass));
        return e;
    }

    /**
     * Gets all entities which are same species over the server.
     * @param entityClass the class represents a type of entity
     * @param entityConsumer the consumer for getting entities
     * @param <E> the entity type
     */
    public static <E extends Entity> void getAllEntitiesByClass(@NotNull Class<E> entityClass, @NotNull Consumer<E> entityConsumer){
        Condition.argNotNull("entityClass", entityClass);
        Condition.argNotNull("entityConsumer", entityConsumer);
        for(World w : Bukkit.getWorlds()) w.getEntitiesByClass(entityClass).forEach(entityConsumer);
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
