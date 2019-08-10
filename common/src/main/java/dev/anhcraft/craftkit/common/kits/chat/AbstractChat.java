package dev.anhcraft.craftkit.common.kits.chat;

import dev.anhcraft.craftkit.common.utils.ChatUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents {@code Chat} implementation.
 */
public abstract class AbstractChat {
    protected BaseComponent prefixComponent = new TextComponent();
    protected String prefix = "";

    /**
     * Constructs an instance of {@code AbstractChat} with the given chat prefix.
     * @param prefix the chat prefix (formatting codes that begun with ampersands ({@code &}) are supported)
     */
    public AbstractChat(@Nullable String prefix){
        if(prefix == null) return;
        prefixComponent = new TextComponent(TextComponent.fromLegacyText(ChatUtil.formatColorCodes(prefix)));
        this.prefix = ChatUtil.formatColorCodes(prefix);
    }

    /**
     * Constructs an instance of {@code AbstractChat} with the given prefix component.
     * @param prefixComponent prefix component
     */
    public AbstractChat(@Nullable BaseComponent prefixComponent){
        if(prefixComponent == null) return;
        this.prefixComponent = prefixComponent;
        prefix = prefixComponent.toLegacyText();
    }

    /**
     * Broadcasts the given message.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param msg a message
     * @return this object
     */
    public abstract AbstractChat broadcast(@NotNull String msg);

    /**
     * Broadcasts given chat components.<br>
     * These components will be connected into a single message.
     * @param components components
     * @return this object
     */
    public abstract AbstractChat broadcast(@NotNull BaseComponent... components);

    /**
     * Sends the given message to the console.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param msg a message
     * @return this object
     */
    public abstract AbstractChat messageConsole(@NotNull String msg);

    /**
     * Sends given chat components to the console.<br>
     * These components will be connected into a single message.
     * @param components components
     * @return this object
     */
    public abstract AbstractChat messageConsole(@NotNull BaseComponent... components);
}
