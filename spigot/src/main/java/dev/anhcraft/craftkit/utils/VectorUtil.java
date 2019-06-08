package dev.anhcraft.craftkit.utils;

import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility methods for working with {@link Vector}.
 */
public class VectorUtil {
    /**
     * Converts the given {@link Vector} to {@link String}.
     * @param vector the vector
     * @return the vector as a string
     */
    public static String toString(@Nullable Vector vector) {
        return vector == null ? "null" : (vector.getX() + ":" +
                vector.getY() + ":" + vector.getZ());
    }

    /**
     * Converts the given string to the vector which it represents.
     * @param str a vector string
     * @return the vector
     */
    public static Vector fromString(@Nullable String str) {
        if(str == null || str.equalsIgnoreCase("null")) return new Vector();
        String[] str2vec = str.split(":");
        return new Vector(Double.parseDouble(str2vec[0]),
                Double.parseDouble(str2vec[1]),Double.parseDouble(str2vec[2]));
    }

    /**
     * Rotates the given vector around the axis X.
     * @param vector the vector
     * @param angle angle from the x axis
     * @return rotated vector
     */
    public static Vector rotateAroundAxisX(@NotNull Vector vector, double angle) {
        Condition.argNotNull("vector", vector);
        var rad = Math.toRadians(angle);
        var sin = Math.sin(rad);
        var cos = Math.cos(rad);
        return new Vector(
                vector.getX(),
                vector.getY() * cos - vector.getZ() * sin,
                vector.getY() * sin + vector.getZ() * cos
        );
    }
    /**
     * Rotates the given vector around the axis Y.
     * @param vector the vector
     * @param angle angle from the y axis
     * @return rotated vector
     */
    public static Vector rotateAroundAxisY(@NotNull Vector vector, double angle) {
        Condition.argNotNull("vector", vector);
        var rad = Math.toRadians(angle);
        var sin = Math.sin(rad);
        var cos = Math.cos(rad);
        return new Vector(
                vector.getX() * cos + vector.getZ() * sin,
                vector.getY(),
                -vector.getX() * sin + vector.getZ() * cos
        );
    }
    /**
     * Rotates the given vector around the axis Z.
     * @param vector the vector
     * @param angle angle from the z axis
     * @return rotated vector
     */
    public static Vector rotateAroundAxisZ(@NotNull Vector vector, double angle) {
        Condition.argNotNull("vector", vector);
        var rad = Math.toRadians(angle);
        var sin = Math.sin(rad);
        var cos = Math.cos(rad);
        return new Vector(
                vector.getX() * cos - vector.getY() * sin,
                vector.getX() * sin + vector.getY() * cos,
                vector.getZ()
        );
    }

    /**
     * Gets the pitch based on the given vector.
     * @param vector the vector
     * @return pitch
     */
    public static double getPitch(@NotNull Vector vector){
        Condition.argNotNull("vector", vector);
        vector = vector.clone().normalize();
        return Math.asin(-vector.getY()); // https://stackoverflow.com/a/33790309
    }

    /**
     * Get the yaw based on the given vector.
     * @param vector the vector
     * @return yaw
     */
    public static double getYaw(@NotNull Vector vector){
        Condition.argNotNull("vector", vector);
        vector = vector.clone().normalize();
        return Math.atan2(vector.getX(), vector.getZ()); // https://stackoverflow.com/a/33790309
    }

    /**
     * Get the direction by the given yaw and the given pitch.
     * @param yaw the yaw (in degrees)
     * @param pitch the pitch (in degrees)
     * @return the vector represents that direction
     */
    public static Vector getDirection(float yaw, float pitch){
        var yawRad = Math.toRadians(yaw);
        var pitchRad = Math.toRadians(pitch);
        var x = Math.cos(yawRad) * Math.sin(pitchRad);
        var y = Math.sin(yawRad) * Math.sin(pitchRad);
        var z = Math.cos(pitchRad);
        return new Vector(x, y, z);
    }

    /**
     * Rotates the given vector by two dimensions.
     * @param vector the vector
     * @param yaw the yaw (in degrees)
     * @param pitch the pitch (in degrees)
     * @return rotated vector
     */
    public static Vector rotateVector(@NotNull Vector vector, float yaw, float pitch){
        Condition.argNotNull("vector", vector);
        return vector.clone().multiply(getDirection(yaw, pitch));
    }
}
