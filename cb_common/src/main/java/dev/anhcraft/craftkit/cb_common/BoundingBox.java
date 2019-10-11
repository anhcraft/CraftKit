package dev.anhcraft.craftkit.cb_common;

import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BoundingBox {
    @NotNull
    public static BoundingBox of(@NotNull Vector v){
        return of(v, v);
    }

    @NotNull
    public static BoundingBox of(@NotNull Vector min, @NotNull Vector max){
        Condition.argNotNull("min", min);
        Condition.argNotNull("max", max);
        return new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    @NotNull
    public static BoundingBox of(@NotNull Vector v, double offsetX, double offsetY, double offsetZ){
        Condition.argNotNull("v", v);
        Condition.check(offsetX >= 0, "Offset X must not be negative");
        Condition.check(offsetY >= 0, "Offset Y must not be negative");
        Condition.check(offsetZ >= 0, "Offset Z must not be negative");
        return new BoundingBox(v.getX() - offsetX, v.getY() - offsetY, v.getZ() - offsetZ, v.getX() + offsetX, v.getY() + offsetY, v.getZ() + offsetZ);
    }

    @NotNull
    public static BoundingBox of(@NotNull Location loc){
        return of(loc, loc);
    }

    @NotNull
    public static BoundingBox of(@NotNull Location min, @NotNull Location max){
        Condition.argNotNull("min", min);
        Condition.argNotNull("max", max);
        return new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    @NotNull
    public static BoundingBox of(@NotNull Location loc, double offsetX, double offsetY, double offsetZ){
        Condition.argNotNull("loc", loc);
        Condition.check(offsetX >= 0, "Offset X must not be negative");
        Condition.check(offsetY >= 0, "Offset Y must not be negative");
        Condition.check(offsetZ >= 0, "Offset Z must not be negative");
        return new BoundingBox(loc.getX() - offsetX, loc.getY() - offsetY, loc.getZ() - offsetZ, loc.getX() + offsetX, loc.getY() + offsetY, loc.getZ() + offsetZ);
    }

    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;

    private void setPoints(double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(maxX, minX);
        this.maxY = Math.max(maxY, minY);
        this.maxZ = Math.max(maxZ, minZ);
    }
    
    public BoundingBox() {
    }

    public BoundingBox(double x, double y, double z) {
        this.minX = x;
        this.minY = y;
        this.minZ = z;
        this.maxX = x;
        this.maxY = y;
        this.maxZ = z;
    }

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        setPoints(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public BoundingBox(@NotNull BoundingBox box) {
        Condition.argNotNull("box", box);
        this.minX = box.minX;
        this.minY = box.minY;
        this.minZ = box.minZ;
        this.maxX = box.maxX;
        this.maxY = box.maxY;
        this.maxZ = box.maxZ;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    @NotNull
    public Vector getMin(){
        return new Vector(minX, minY, minZ);
    }

    @NotNull
    public Vector getMax(){
        return new Vector(maxX, maxY, maxZ);
    }

    public double getLength(){
        return maxX - minX;
    }

    public double getHeight(){
        return maxY - minY;
    }

    public double getWidth(){
        return maxZ - minZ;
    }

    public double getVolume(){
        return getLength() * getHeight() * getWidth();
    }

    public double getAreaXY(){
        return getLength() * getHeight();
    }

    public double getAreaYZ(){
        return getHeight() * getWidth();
    }

    public double getAreaXZ(){
        return getLength() * getWidth();
    }

    public double getCenterX(){
        return (maxX - minX) * 0.5;
    }

    public double getCenterY(){
        return (maxY - minY) * 0.5;
    }

    public double getCenterZ(){
        return (maxZ - minZ) * 0.5;
    }

    @NotNull
    public Vector getCenter(){
        return new Vector(getCenterX(), getCenterY(), getCenterZ());
    }

    @Contract("_, _, _ -> this")
    public BoundingBox expand(double offsetX, double offsetY, double offsetZ){
        Condition.check(offsetX >= 0, "Offset X must not be negative");
        Condition.check(offsetY >= 0, "Offset Y must not be negative");
        Condition.check(offsetZ >= 0, "Offset Z must not be negative");
        minX -= offsetX;
        minY -= offsetY;
        minZ -= offsetZ;
        maxX += offsetX;
        maxY += offsetY;
        maxZ += offsetZ;
        return this;
    }

    @Contract("_, _, _, _, _, _ -> this")
    public BoundingBox expand(double offsetMinX, double offsetMinY, double offsetMinZ, double offsetMaxX, double offsetMaxY, double offsetMaxZ){
        Condition.check(offsetMinX >= 0, "Offset min X must not be negative");
        Condition.check(offsetMinY >= 0, "Offset min Y must not be negative");
        Condition.check(offsetMinZ >= 0, "Offset min Z must not be negative");
        Condition.check(offsetMaxX >= 0, "Offset max X must not be negative");
        Condition.check(offsetMaxY >= 0, "Offset max Y must not be negative");
        Condition.check(offsetMaxZ >= 0, "Offset max Z must not be negative");
        minX -= offsetMinX;
        minY -= offsetMinY;
        minZ -= offsetMinZ;
        maxX += offsetMaxX;
        maxY += offsetMaxY;
        maxZ += offsetMaxZ;
        return this;
    }

    @Contract("_ -> this")
    public BoundingBox expand(@NotNull Vector offset){
        Condition.argNotNull("offset", offset);
        return expand(offset.getX(), offset.getY(), offset.getZ());
    }

    @Contract("_, _ -> this")
    public BoundingBox expand(@NotNull Vector offsetMin, @NotNull Vector offsetMax){
        Condition.argNotNull("offsetMin", offsetMin);
        Condition.argNotNull("offsetMax", offsetMax);
        return expand(offsetMin.getX(), offsetMin.getY(), offsetMin.getZ(), offsetMax.getX(), offsetMax.getY(), offsetMax.getZ());
    }

    @Contract("_, _, _ -> this")
    public BoundingBox shrink(double offsetX, double offsetY, double offsetZ){
        Condition.check(offsetX >= 0, "Offset X must not be negative");
        Condition.check(offsetY >= 0, "Offset Y must not be negative");
        Condition.check(offsetZ >= 0, "Offset Z must not be negative");
        if(offsetX > getCenterX()) offsetX = getCenterX();
        if(offsetY > getCenterY()) offsetY = getCenterY();
        if(offsetZ > getCenterZ()) offsetZ = getCenterZ();
        minX += offsetX;
        minY += offsetY;
        minZ += offsetZ;
        maxX -= offsetX;
        maxY -= offsetY;
        maxZ -= offsetZ;
        return this;
    }

    @Contract("_, _, _, _, _, _ -> this")
    public BoundingBox shrink(double offsetMinX, double offsetMinY, double offsetMinZ, double offsetMaxX, double offsetMaxY, double offsetMaxZ){
        Condition.check(offsetMinX >= 0, "Offset min X must not be negative");
        Condition.check(offsetMinY >= 0, "Offset min Y must not be negative");
        Condition.check(offsetMinZ >= 0, "Offset min Z must not be negative");
        Condition.check(offsetMaxX >= 0, "Offset max X must not be negative");
        Condition.check(offsetMaxY >= 0, "Offset max Y must not be negative");
        Condition.check(offsetMaxZ >= 0, "Offset max Z must not be negative");
        double centerX = getCenterX();
        double centerY = getCenterY();
        double centerZ = getCenterZ();
        if(offsetMinX > centerX) offsetMinX = centerX;
        if(offsetMinY > centerY) offsetMinY = centerY;
        if(offsetMinZ > centerZ) offsetMinZ = centerZ;
        if(offsetMaxX > centerX) offsetMaxX = centerX;
        if(offsetMaxY > centerY) offsetMaxY = centerY;
        if(offsetMaxZ > centerZ) offsetMaxZ = centerZ;
        minX += offsetMinX;
        minY += offsetMinY;
        minZ += offsetMinZ;
        maxX -= offsetMaxX;
        maxY -= offsetMaxY;
        maxZ -= offsetMaxZ;
        return this;
    }

    @Contract("_ -> this")
    public BoundingBox shrink(@NotNull Vector offset){
        Condition.argNotNull("offset", offset);
        return shrink(offset.getX(), offset.getY(), offset.getZ());
    }

    @Contract("_, _ -> this")
    public BoundingBox shrink(@NotNull Vector offsetMin, @NotNull Vector offsetMax){
        Condition.argNotNull("offsetMin", offsetMin);
        Condition.argNotNull("offsetMax", offsetMax);
        return shrink(offsetMin.getX(), offsetMin.getY(), offsetMin.getZ(), offsetMax.getX(), offsetMax.getY(), offsetMax.getZ());
    }

    @Contract("_ -> this")
    public BoundingBox union(@NotNull BoundingBox box){
        Condition.argNotNull("box", box);
        this.minX = Math.min(this.minX, box.minX);
        this.minY = Math.min(this.minY, box.minY);
        this.minZ = Math.min(this.minZ, box.minZ);
        this.maxX = Math.max(this.maxX, box.maxX);
        this.maxY = Math.max(this.maxY, box.maxY);
        this.maxZ = Math.max(this.maxZ, box.maxZ);
        return this;
    }

    public boolean contains(double x, double y, double z) {
        return (x >= this.minX && x <= this.maxX) &&
                (y >= this.minY && y <= this.maxY) &&
                (z >= this.minZ && z <= this.maxZ);
    }

    public boolean contains(@NotNull Vector v) {
        Condition.argNotNull("v", v);
        return contains(v.getX(), v.getY(), v.getZ());
    }

    public boolean contains(@NotNull Location loc) {
        Condition.argNotNull("loc", loc);
        return contains(loc.getX(), loc.getY(), loc.getZ());
    }

    public boolean contains(@NotNull BoundingBox box) {
        Condition.argNotNull("box", box);
        return (this.minX <= box.minX) && (this.maxX >= box.maxX) &&
                (this.minY <= box.minY) && (this.maxY >= box.maxY) &&
                (this.minZ <= box.minZ) && (this.maxZ >= box.maxZ);
    }

    public boolean intersect(@NotNull BoundingBox box) {
        Condition.argNotNull("box", box);
        return ((minX <= box.maxX) && (maxX >= box.minX)) ||
                ((minY <= box.maxY) && (maxY >= box.minY)) ||
                ((minZ <= box.maxZ) && (maxZ >= box.minZ));
    }

    @NotNull
    public BoundingBox duplicate(){
        return new BoundingBox(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundingBox that = (BoundingBox) o;
        return Double.compare(that.minX, minX) == 0 &&
                Double.compare(that.minY, minY) == 0 &&
                Double.compare(that.minZ, minZ) == 0 &&
                Double.compare(that.maxX, maxX) == 0 &&
                Double.compare(that.maxY, maxY) == 0 &&
                Double.compare(that.maxZ, maxZ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
