package dev.anhcraft.craftkit.common.builders;

import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.jvmkit.builders.Builder;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A builder which helps you to make chat components easier and faster.
 */
public class ChatComponentBuilder implements Builder<BaseComponent> {
    private BaseComponent component;
    private ClickEvent clickEvent;
    private HoverEvent hoverEvent;
    private String insertion;
    private LinkedHashMap<Object, Object[]> extra = new LinkedHashMap<>();
    private ChatColor color = ChatColor.WHITE;
    private boolean bold = false;
    private boolean obfuscate = false;
    private boolean underline = false;
    private boolean italic = false;
    private boolean strikethrough = false;

    /**
     * Constructs an instance of {@code ChatComponentBuilder} with the given text to create a new component.
     * @param text the text (formatting codes which are begun with ampersands ({@code &}) are supported)
     */
    public ChatComponentBuilder(String text){
        this.component = new TextComponent(TextComponent.fromLegacyText(text == null ? "" :
                ChatUtil.formatColorCodes(text)));
    }

    /**
     * Constructs an instance of {@code ChatComponentBuilder} with the given text to create a new component.
     * @param text the text (formatting codes which are begun with ampersands ({@code &}) are supported)
     * @param clazz the class type of the component
     */
    public ChatComponentBuilder(String text, @NotNull Class<? extends BaseComponent> clazz){
        Condition.argNotNull("clazz", clazz);

        text = text == null ? "" : ChatUtil.formatColorCodes(text);
        if(clazz.equals(TextComponent.class)){
            component = new TextComponent(TextComponent.fromLegacyText(text));
        } else if(clazz.equals(TranslatableComponent.class)){
            component = new TranslatableComponent(text);
        }
    }

    /**
     * Constructs an instance of {@code ChatComponentBuilder} to create an empty component.
     * @param clazz the class type of the component
     */
    public ChatComponentBuilder(@NotNull Class<? extends BaseComponent> clazz){
        Condition.argNotNull("clazz", clazz);

        if(clazz.equals(TextComponent.class)){
            component = new TextComponent();
        } else if(clazz.equals(TranslatableComponent.class)){
            component = new TranslatableComponent();
        }
    }

    /**
     * Constructs an instance of {@code ChatComponentBuilder} by cloning the given component.
     * @param component an existing component
     */
    public ChatComponentBuilder(@NotNull BaseComponent component){
        this.component = component.duplicate();
    }

    /**
     * Add an extra {@link TextComponent} as well as its events.
     * @param text the text (formatting codes which are begun with ampersands ({@code &}) are supported)
     * @param events events to trigger when interact with the extra component
     * @return this object
     */
    public ChatComponentBuilder text(@NotNull String text, @NotNull Object... events){
        Condition.argNotNull("text", text);
        Condition.argNotNull("events", events);
        extra.put(ChatUtil.formatColorCodes(text), events);
        return this;
    }

    /**
     * Add an extra component as well as its events.
     * @param component an extra component
     * @param events events to trigger when interact with the extra component
     * @return this object
     */
    public ChatComponentBuilder component(@NotNull BaseComponent component, @NotNull Object... events){
        Condition.argNotNull("component", component);
        Condition.argNotNull("events", events);
        extra.put(component, events);
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setColor(ChatColor)}.
     * @param color the chat color
     * @return this object
     */
    public ChatComponentBuilder color(ChatColor color){
        this.color = color;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setClickEvent(ClickEvent)}.
     * @param clickEvent the click event
     * @return this object
     */
    public ChatComponentBuilder event(ClickEvent clickEvent){
        this.clickEvent = clickEvent;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setHoverEvent(HoverEvent)}.
     * @param hoverEvent the hover event
     * @return this object
     */
    public ChatComponentBuilder event(HoverEvent hoverEvent){
        this.hoverEvent = hoverEvent;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setBold(Boolean)}.
     * @param b {@code true} or {@code false}
     * @return this object
     */
    public ChatComponentBuilder bold(boolean b){
        this.bold = b;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setItalic(Boolean)}.
     * @param b {@code true} or {@code false}
     * @return this object
     */
    public ChatComponentBuilder italic(boolean b){
        this.italic = b;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setUnderlined(Boolean)}.
     * @param b {@code true} or {@code false}
     * @return this object
     */
    public ChatComponentBuilder underline(boolean b){
        this.underline = b;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setStrikethrough(Boolean)}.
     * @param b {@code true} or {@code false}
     * @return this object
     */
    public ChatComponentBuilder strikethrough(boolean b){
        this.strikethrough = b;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setObfuscated(Boolean)}.
     * @param b {@code true} or {@code false}
     * @return this object
     */
    public ChatComponentBuilder obfuscate(boolean b){
        this.obfuscate = b;
        return this;
    }

    /**
     * This method is similar to {@link BaseComponent#setInsertion(String)}.
     * @param text the text (formatting codes which are begun with ampersands ({@code &}) are supported)
     * @return this object
     */
    public ChatComponentBuilder insertion(String text){
        this.insertion = text;
        return this;
    }

    /**
     * Builds this component and returns it.
     * @return the chat component
     */
    @Override
    public BaseComponent build(){
        component.setBold(bold);
        component.setObfuscated(obfuscate);
        component.setItalic(italic);
        component.setStrikethrough(strikethrough);
        component.setUnderlined(underline);
        if(color != null) component.setColor(color);
        if(clickEvent != null) component.setClickEvent(clickEvent);
        if(hoverEvent != null) component.setHoverEvent(hoverEvent);
        if(insertion != null) component.setInsertion(ChatUtil.formatColorCodes(insertion));
        for(Map.Entry<Object, Object[]> entry : extra.entrySet()){
            ChatComponentBuilder cb;
            if(entry.getKey() instanceof String) cb = new ChatComponentBuilder((String) entry.getKey(), TextComponent.class);
            else if(entry.getKey() instanceof BaseComponent) cb = new ChatComponentBuilder((BaseComponent) entry.getKey());
            else continue;
            for(Object obj : entry.getValue()){
                if(obj instanceof ClickEvent) cb.event((ClickEvent) obj);
                else if(obj instanceof HoverEvent) cb.event((HoverEvent) obj);
            }
            component.addExtra(cb.build());
        }
        return component;
    }
}
