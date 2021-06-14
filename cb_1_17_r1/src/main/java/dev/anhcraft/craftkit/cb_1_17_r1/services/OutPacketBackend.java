package dev.anhcraft.craftkit.cb_1_17_r1.services;

import dev.anhcraft.craftkit.cb_1_17_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBOutPacketBackend;
import dev.anhcraft.craftkit.cb_common.internal.enums.PlayerInfoEnum;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.world.entity.player.EntityHuman;

import java.util.List;

public class OutPacketBackend extends CBModule implements CBOutPacketBackend {
    @Override
    public void playerInfo(List<Object> entityPlayers, PlayerInfoEnum e, List<Object> receivers) {
        sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.values()[e.ordinal()], castEntityPlayers(entityPlayers)), castEntityPlayers(receivers));
    }

    @Override
    public void namedEntitySpawn(Object entityHuman, List<Object> receivers) {
        sendPacket(new PacketPlayOutNamedEntitySpawn((EntityHuman) entityHuman), castEntityPlayers(receivers));
    }

    @Override
    public void entityDestroy(int[] ids, List<Object> receivers) {
        for (int i : ids) {
            sendPacket(new PacketPlayOutEntityDestroy(i), castEntityPlayers(receivers));
        }
    }
}
