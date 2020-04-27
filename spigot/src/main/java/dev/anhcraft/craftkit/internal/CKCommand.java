package dev.anhcraft.craftkit.internal;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.chat.Chat;
import dev.anhcraft.craftkit.common.internal.CKInfo;
import dev.anhcraft.craftkit.internal.tests.TestChain;
import dev.anhcraft.jvmkit.utils.JarUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@CommandAlias("ck|craftkit")
public class CKCommand extends BaseCommand {
    @HelpCommand
    public void help(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("info")
    @Description("Show plugin information")
    public void info(CommandSender sender) {
        Chat.noPrefix().message(sender, "&b&l=== CraftKit v" + CKInfo.getPluginVersion()+" ===")
                .message(sender, "&aDiscord: https://discord.gg/QSpc5xH")
                .message(sender, "&eContact me if any errors occurs!");
    }

    @Subcommand("dev test")
    @Description("Run in-game tests")
    @CommandPermission("ck.dev.test")
    public void devTest(Player player) {
        new TestChain(player, chain -> {
            if(chain.isSuccess() && chain.isFinished()) {
                CraftKit.INFO_CHAT.message(chain.getPlayer(), "All tests passed!");
            } else {
                CraftKit.WARN_CHAT.message(chain.getPlayer(), "Test failed: &f" + chain.getCurrentTest());
            }
        });
    }

    @Subcommand("dev load-jar")
    @Description("Load library jar")
    @CommandPermission("ck.dev.load-jar")
    public void devLoadJar(Player player, String file) {
        File f = new File(CraftKit.libDir, file + ".jar");
        if(!f.exists()) {
            f = new File(CraftKit.libDir, file);
            if(!f.exists()) {
                CraftKit.WARN_CHAT.message(player, "File not found");
                return;
            }
        }
        try {
            JarUtil.loadJar(f, ((CraftKit) CraftExtension.of(CraftKit.class).getPlugin()).getURLClassLoader());
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            CraftKit.WARN_CHAT.message(player, "Failed to load!");
            e.printStackTrace();
        }
    }
}
