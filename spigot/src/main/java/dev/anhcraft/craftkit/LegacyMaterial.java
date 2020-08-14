package dev.anhcraft.craftkit;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class LegacyMaterial {
    private final Material material;
    private final int variant;

    public LegacyMaterial(@NotNull Material material, int variant) {
        this.material = material;
        this.variant = variant;
    }

    @NotNull
    public Material getMaterial() {
        return material;
    }

    public int getVariant() {
        return variant;
    }
}
