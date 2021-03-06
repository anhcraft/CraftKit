package dev.anhcraft.craftkit.utils;

import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for working with {@link Location}.
 */
public class LocationUtil {
    /**
     * Converts the given {@link Location} to {@link String}.
     * @param loc the location
     * @return the location as a string
     */
    @NotNull
    public static String toString(@Nullable Location loc) {
        if(loc == null) return "null";
        World w = loc.getWorld();
        return (w == null ? "/null/" : w.getName()) + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getPitch() + ":" + loc.getYaw();
    }

    /**
     * Converts the given string to the location which it represents.
     * @param str a location string
     * @return the location
     */
    @NotNull
    public static Location fromString(@Nullable String str) {
        if(str == null || str.equalsIgnoreCase("null")) return Bukkit.getWorlds().get(0).getSpawnLocation();
        String[] str2loc = str.split(":");
        Location loc = new Location(
                Bukkit.getWorld(str2loc[0]),
                Double.parseDouble(str2loc[1]),
                Double.parseDouble(str2loc[2]),
                Double.parseDouble(str2loc[3]));
        if(str2loc.length >= 5) {
            loc.setPitch(Float.parseFloat(str2loc[4]));
        }
        if(str2loc.length >= 6) {
            loc.setYaw(Float.parseFloat(str2loc[5]));
        }
        return loc;
    }

    /**
     * Gets all locations of blocks in specific distance from given central location.
     * @param loc the center location
     * @param rx distance on the x axis
     * @param ry distance on the y axis
     * @param rz distance on the z axis
     * @return list of locations
     */
    @NotNull
    public static List<Location> getNearbyLocations(@NotNull Location loc, int rx, int ry, int rz){
        Condition.argNotNull("loc", loc);

        List<Location> locations = new ArrayList<>();
        World w = loc.getWorld();
        double cx = loc.getX();
        double cy = loc.getY();
        double cz = loc.getZ();

        for (int x = -rx; x <= rx; x++){
            for (int y = -ry; y <= ry; y++) {
                for (int z = -rz; z <= rz; z++) {
                    locations.add(new Location(w, cx + x, cy + y, cz + z));
                }
            }
        }
        return locations;
    }

    /**
     * Checks if the given location is under a block.
     * @param loc the location
     * @return {@code true} if it is or {@code false} if not
     */
    public static boolean isUnderBlock(@NotNull Location loc) {
        Condition.argNotNull("loc", loc);
        Condition.check(loc.getWorld() != null, "World must be present");
        int h = loc.getWorld().getMaxHeight();
        if(loc.getY() >= h) return false;
        loc = loc.clone();
        for(int i = loc.getBlockY(); i < h; i++){
            if(!loc.add(0, 1, 0).getBlock().getType().equals(Material.AIR)) return true;
        }
        return false;
    }
}
