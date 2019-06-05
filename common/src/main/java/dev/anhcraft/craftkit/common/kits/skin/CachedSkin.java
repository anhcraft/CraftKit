package dev.anhcraft.craftkit.common.kits.skin;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

/**
 * A cached version of {@code Skin} which can be reused in safer way.
 */
public class CachedSkin extends Skin {
    /**
     * Constructs an instance of {@code CachedSkin} with the given information.
     * @param value the value of the skin
     * @param signature the signature of the skin
     */
    public CachedSkin(@NotNull String value, @NotNull String signature) {
        super(value, signature);
    }

    // TODO ADD CACHED SKIN
}
