package dev.anhcraft.craftkit.cb_common.internal.services;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_common.internal.annotation.IsNMS;
import org.bukkit.World;

public interface CBEntityNPCService extends CBService {
    @IsNMS Object create(GameProfile profile, World world);
}
