package dev.anhcraft.craftkit.cb_1_15_r1.services;

import dev.anhcraft.craftkit.cb_1_15_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.services.CBOutPacketService;
import dev.anhcraft.craftkit.cb_common.internal.enums.PlayerInfoEnum;
import net.minecraft.server.v1_15_R1.EntityHuman;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;

import java.util.List;

public class OutPacketService extends CBModule implements CBOutPacketService {
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