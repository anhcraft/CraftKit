package dev.anhcraft.craftkit.cb_1_16_r1.services;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.anhcraft.craftkit.cb_1_16_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBPlayerBackend;
import dev.anhcraft.craftkit.common.Skin;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerBackend extends CBModule implements CBPlayerBackend {
    @Override
    public Object toNmsEntityPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    @Override
    public List<Object> toNmsEntityPlayers(List<Player> players) {
        return players.stream().map(player -> ((CraftPlayer) player).getHandle()).collect(Collectors.toList());
    }

    @Override
    public int getPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }

    @Override
    public GameProfile getProfile(Player player) {
        return ((CraftPlayer) player).getHandle().getProfile();
    }

    @Override
    public void setProfile(Player player, GameProfile profile) {
        ReflectionUtil.setDeclaredField(EntityHuman.class, ((CraftPlayer) player).getHandle(), "bQ", profile);
    }

    @Override
    public void changeSkin(Player player, Skin skin, List<Player> viewers) {
        CraftPlayer cp = (CraftPlayer) player;
        EntityPlayer ent = cp.getHandle();
        List<EntityPlayer> vws = toEntityPlayers(viewers);
        sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ent), vws);
        sendPacket(new PacketPlayOutEntityDestroy(ent.getId()), vws);
        ent.getProfile().getProperties().removeAll("textures");
        ent.getProfile().getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
        sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ent), vws);
        sendPacket(new PacketPlayOutNamedEntitySpawn(ent), vws);
        craftServer.getHandle().moveToWorld(ent, ((CraftWorld) player.getWorld()).getHandle(), true, player.getLocation(), true);
    }

    @Override
    public void setCamera(int entityId, Player viewer) {
        PacketPlayOutCamera packet = new PacketPlayOutCamera();
        packet.a = entityId;
        sendPacket(packet, toEntityPlayer(viewer));
    }

    @Override
    public void openBook(Player player, int slot) {
        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, slot);
        buf.writerIndex(1);
        sendPacket(new PacketPlayOutCustomPayload(new MinecraftKey("minecraft:book_open"), new PacketDataSerializer(buf)), toEntityPlayer(player));
    }

    @Override
    public void fakeExp(float expBar, int level, int totalExp, Player player) {
        expBar = Math.min(1, Math.max(0, level));
        sendPacket(new PacketPlayOutExperience(expBar, level, totalExp), toEntityPlayer(player));
    }
}
