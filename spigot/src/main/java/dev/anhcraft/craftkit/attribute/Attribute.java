package dev.anhcraft.craftkit.attribute;

import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.jvmkit.utils.Condition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Available attributes.
 */
public enum Attribute {
    GENERIC_MAX_HEALTH(20.0, 0.0, 1024.0, "generic.max_health"),
    GENERIC_FOLLOW_RANGE(32.0, 0.0, 2048.0, "generic.follow_range"),
    GENERIC_KNOCKBACK_RESISTANCE(0.0, 0.0, 1.0, "generic.knockback_resistance"),
    GENERIC_MOVEMENT_SPEED(0.7, 0.0, 1024.0, "generic.movement_speed"),
    GENERIC_ATTACK_DAMAGE(2.0, 0.0, 2048.0, "generic.attack_damage"),
    GENERIC_ATTACK_KNOCKBACK(0.0, 0.0, 8.0, "generic.attack_knockback"),
    GENERIC_ARMOR(0.0, 0.0, 30.0, "generic.armor"),
    GENERIC_ARMOR_TOUGHNESS(0.0, 0.0, 20.0, "generic.armor_toughness"),
    GENERIC_ATTACK_SPEED(4.0, 0.0, 1024.0, "generic.attack_speed"),
    GENERIC_LUCK(0.0, -1024.0, 1024.0, "generic.luck"),
    HORSE_JUMP_STRENGTH(0.7, 0.0, 2.0, "generic.jump_strength"),
    ZOMBIE_SPAWN_REINFORCEMENTS(0.0, 0.0, 1.0, "generic.spawn_reinforcements"),
    PARROTS_FLYING_SPEED(0.4, 0.0, 1024.0, "generic.flying_speed");

    private final double min;
    private final double max;
    private final double base;
    private final String id;
    private final String legacyId;

    Attribute(double base, double min, double max, String id){
        this.min = min;
        this.max = max;
        this.base = base;
        this.id = id;
        if(NMSVersion.current().compare(NMSVersion.v1_16_R1) < 0) {
            StringBuilder legacyId = new StringBuilder();
            boolean up = false;
            for (char c : id.toCharArray()) {
                if (c == '_') {
                    up = true;
                    continue;
                } else if (up) {
                    legacyId.append(Character.toUpperCase(c));
                    up = false;
                    continue;
                }
                legacyId.append(c);
            }
            this.legacyId = legacyId.toString();
        } else {
            this.legacyId = id;
        }
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
     * Returns the id.
     * @return attribute's id
     */
    @NotNull
    public String getId(){
        return this.id;
    }

    /**
     * Returns the legacy id.
     * @return attribute's legacy id
     */
    @NotNull
    @Deprecated
    public String getLegacyId(){
        return this.legacyId;
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
            if(id.equals(attr.getId()) || id.equals(attr.getLegacyId())) {
                return attr;
            }
        }
        return null;
    }
}
