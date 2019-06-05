package dev.anhcraft.craftkit.common.internal;

import dev.anhcraft.jvmkit.lang.annotation.Label;

public class CKConfig {
    @Label("allow_check_update")
    public static boolean ALLOW_CHECK_UPDATE;

    @Label("allow_notify_update")
    public static boolean ALLOW_NOTIFY_UPDATE;

    @Label("beta_features.packet_handle_enabled")
    public static boolean PACKET_HANDLE_ENABLED;

    @Label("config_version")
    public static int CONFIG_VERSION;
}
