package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.internal.integrations.VaultIntegration;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;

import java.util.Optional;

/**
 * Extra methods for working with the Vault API.
 */
public class VaultApiUtil extends VaultIntegration {
    /**
     * Returns the {@link Economy} API.
     * @return the container of {@link Economy} instance (the instance may be null if the API is not ready yet)
     */
    public static Optional<Economy> getEconomyApi(){
        return Optional.ofNullable(eco);
    }

    /**
     * Returns the {@link Permission} API.
     * @return the container of {@link Permission} instance (the instance may be null if the API is not ready yet)
     */
    public static Optional<Permission> getPermissionApi(){
        return Optional.ofNullable(perm);
    }

    /**
     * Returns the {@link Chat} API.
     * @return the container of {@link Chat} instance (the instance may be null if the API is not ready yet)
     */
    public static Optional<Chat> getChatApi(){
        return Optional.ofNullable(chat);
    }

    /**
     * Checks if the given player has enough balance.
     * @param player the player
     * @param balance balance
     * @return {@code true} if he has or {@code false} if not
     */
    public static boolean hasEnoughBalance(@NotNull OfflinePlayer player, double balance){
        Condition.argNotNull("player", player);
        return eco.getBalance(player) >= balance;
    }

    /**
     * Overrides the current balance of given player with {@code newBalance}.<br>
     * {@code newBalance} can be negative but still not be recommended.
     * @param player the player
     * @param newBalance the new balance
     * @return {@code true} if the action was successful. Otherwise is {@code false}.
     */
    public static boolean setBalance(@NotNull OfflinePlayer player, double newBalance){
        Condition.argNotNull("player", player);
        if(eco == null) return false;

        var delta = eco.getBalance(player)-newBalance;
        var abs = Math.abs(delta);
        if(delta == 0) return true;
        else if(delta > 0) return eco.withdrawPlayer(player, abs).transactionSuccess();
        else return eco.depositPlayer(player, abs).transactionSuccess();
    }
}
