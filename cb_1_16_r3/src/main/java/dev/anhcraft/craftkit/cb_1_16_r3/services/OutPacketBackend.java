package dev.anhcraft.craftkit.cb_1_16_r3.services;

import dev.anhcraft.craftkit.cb_1_16_r3.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBOutPacketBackend;
import dev.anhcraft.craftkit.cb_common.internal.enums.PlayerInfoEnum;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;

import java.util.List;

public class OutPacketBackend extends CBModule implements CBOutPacketBackend {
    @Override
    public void playerInfo(List<Object> entityPlayers, PlayerInfoEnum e, List<Object> receivers) {
        sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.valueOf(e.toString()), castEntityPlayers(entityPlayers)), castEntityPlayers(receivers));
    }

    @Override
    public void namedEntitySpawn(Object entityHuman, List<Object> receivers) {
        sendPacket(new PacketPlayOutNamedEntitySpawn((EntityHuman) entityHuman), castEntityPlayers(receivers));
    }

    @Override
    public void entityDestroy(int[] ids, List<Object> receivers) {
        sendPacket(new PacketPlayOutEntityDestroy(ids), castEntityPlayers(receivers));
    }
}
