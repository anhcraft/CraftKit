package dev.anhcraft.craftkit.builders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.anhcraft.craftkit.common.Skin;
import dev.anhcraft.craftkit.utils.PlayerUtil;
import dev.anhcraft.jvmkit.builders.Builder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * A builder that helps you to build and work with {@link GameProfile}.
 */
public class GameProfileBuilder implements Builder<GameProfile> {
    private final GameProfile gp;

    /**
     * Constructs an instance of {@code GameProfileBuilder}.
     * @param name the profile name
     */
    public GameProfileBuilder(@NotNull String name){
        Condition.argNotNull("name", name);
        gp = new GameProfile(PlayerUtil.getOfflineId(name), name);
    }

    /**
     * Constructs an instance of {@code GameProfileBuilder}.
     * @param id the profile id
     * @param name the profile name
     */
    public GameProfileBuilder(@NotNull UUID id, @NotNull String name){
        Condition.argNotNull("id", id);
        Condition.argNotNull("name", name);
        gp = new GameProfile(id, name);
        setLegacy(!Bukkit.getOnlineMode());
    }

    /**
     * Constructs an instance of {@code GameProfileBuilder} with the given existing profile.
     * @param profile the profile
     */
    public GameProfileBuilder(@NotNull GameProfile profile){
        Condition.argNotNull("profile", profile);
        gp = profile;
    }

    /**
     * Sets this profile id.
     * @param id the id
     * @return this object
     */
    @Contract("_ -> this")
    public GameProfileBuilder setUniqueId(@NotNull UUID id) {
        Condition.argNotNull("id", id);
        ReflectionUtil.setDeclaredField(GameProfile.class, gp, "id", id);
        return this;
    }

    /**
     * Sets the properties map.
     * @param properties properties map
     * @return this object
     */
    @Contract("_ -> this")
    public GameProfileBuilder setProperties(@NotNull PropertyMap properties) {
        Condition.argNotNull("properties", properties);
        ReflectionUtil.setDeclaredField(GameProfile.class, gp, "properties", properties);
        return this;
    }

    /**
     * Sets the legacy status.
     * @param legacy the legacy status
     * @return this object
     */
    @Contract("_ -> this")
    public GameProfileBuilder setLegacy(boolean legacy) {
        ReflectionUtil.setDeclaredField(GameProfile.class, gp, "legacy", legacy);
        return this;
    }

    /**
     * Sets this profile name.
     * @param name the name
     * @return this object
     */
    @Contract("_ -> this")
    public GameProfileBuilder setName(@NotNull String name) {
        Condition.argNotNull("name", name);
        ReflectionUtil.setDeclaredField(GameProfile.class, gp, "name", name);
        return this;
    }

    /**
     * Sets this profile skin.
     * @param skin the skin
     * @return this object
     */
    @Contract("_ -> this")
    public GameProfileBuilder setSkin(@NotNull Skin skin){
        Condition.argNotNull("skin", skin);
        gp.getProperties().removeAll("textures");
        gp.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
        return this;
    }

    /**
     * Builds this profile and returns it.
     * @return the profile
     */
    @Override
    @NotNull
    public GameProfile build(){
        return gp;
    }
}
