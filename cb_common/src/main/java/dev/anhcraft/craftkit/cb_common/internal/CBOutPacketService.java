package dev.anhcraft.craftkit.cb_common.internal;

import dev.anhcraft.craftkit.cb_common.internal.enums.PlayerInfoEnum;
import dev.anhcraft.craftkit.cb_common.internal.annotation.IsNMS;

import java.util.List;

public interface CBOutPacketService extends CBService {
    void playerInfo(@IsNMS List<Object> entityPlayers, PlayerInfoEnum e, @IsNMS List<Object> receivers);
    void namedEntitySpawn(@IsNMS Object entityHuman, @IsNMS List<Object> receivers);
    void entityDestroy(int[] ids, @IsNMS List<Object> receivers);
}
