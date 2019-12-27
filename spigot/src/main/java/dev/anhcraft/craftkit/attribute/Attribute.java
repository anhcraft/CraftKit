package dev.anhcraft.craftkit.attribute;

import dev.anhcraft.jvmkit.utils.Condition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Available attributes.
 */
public enum Attribute {
    GENERIC_MAX_HEALTH(20.0, 0.0, 1024.0, "generic.maxHealth"),
    GENERIC_FOLLOW_RANGE(32.0, 0.0, 2048.0, "generic.followRange"),
    GENERIC_KNOCKBACK_RESISTANCE(0.0, 0.0, 1.0, "generic.knockbackResistance"),
    GENERIC_MOVEMENT_SPEED(0.7, 0.0, 1024.0, "generic.movementSpeed"),
    GENERIC_ATTACK_DAMAGE(2.0, 0.0, 2048.0, "generic.attackDamage"),
    GENERIC_ARMOR(0.0, 0.0, 30.0, "generic.armor"),
    GENERIC_ARMOR_TOUGHNESS(0.0, 0.0, 20.0, "generic.armorToughness"),
    GENERIC_ATTACK_SPEED(4.0, 0.0, 1024.0, "generic.attackSpeed"),
    GENERIC_LUCK(0.0, -1024.0, 1024.0, "generic.luck"),
    HORSE_JUMP_STRENGTH(0.7, 0.0, 2.0, "generic.jumpStrength"),
    ZOMBIE_SPAWN_REINFORCEMENTS(0.0, 0.0, 1.0, "generic.spawnReinforcements"),
    PARROTS_FLYING_SPEED(0.4, 0.0, 1024.0, "generic.flyingSpeed");

    private double min;
    private double max;
    private double base;
    private String id;

    Attribute(double base, double min, double max, String id){
        this.min = min;
        this.max = max;
        this.base = base;
        this.id = id;
    }

    /**
     * Returns the minimum value
     * @return minimum value
     */
    public double getMinValue(){
        return min;
    }

    /**
     * Returns the maximum value.
     * @return maximum value
     */
    public double getMaxValue(){
        return max;
    }

    /**
     * Returns the base value.
     * @return base value
     */
    public double getBaseValue(){
        return base;
    }

    /**
     * Returns the id of this attribute.
     * @return attribute's id
     */
    @NotNull
    public String getId(){
        return this.id;
    }

    /**
     * Gets an attribute by its id.
     * @param id attribute's id
     * @return attribute
     */
    @Nullable
    public static Attribute getById(@NotNull String id){
        Condition.argNotNull("id", id);
        for(Attribute attr : values()){
            if(id.equals(attr.getId())) return attr;
        }
        return null;
    }
}
