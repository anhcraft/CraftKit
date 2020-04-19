package dev.anhcraft.craftkit.cb_common.internal.backend;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_common.internal.annotation.IsNMS;
import org.bukkit.World;

public interface CBEntityNPCBackend extends IBackend {
    @IsNMS Object create(GameProfile profile, World world);
}
