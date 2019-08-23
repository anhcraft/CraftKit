package dev.anhcraft.craftkit.common.utils;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A utility class for manipulating with chat messages.
 */
public class ChatUtil {
    /**
     * Formats color codes in the given string.<br>
     * Ampersands ({@code &}) will be replaced by section signs ({@code §})
     * @param str a string
     * @return formatted string
     */
    @Contract("null -> null")
    public static String formatColorCodes(@Nullable String str){
        if(str == null) return null;
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    /**
     * Formats color codes in all strings of the given list.<br>
     * Ampersands ({@code &}) will be replaced by section signs ({@code §})
     * @param listStr a list of strings
     * @return the list whose strings were formatted
     */
    @Contract("null -> null")
    public static List<String> formatColorCodes(@Nullable List<String> listStr){
        if(listStr == null) return null;
        return listStr.stream().filter(Objects::nonNull)
                .map(ChatUtil::formatColorCodes).collect(Collectors.toList());
    }
}
