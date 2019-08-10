package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.craftkit.common.helpers.AbstractConfigHelper;
import dev.anhcraft.jvmkit.lang.annotation.Label;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.ArrayUtil;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * A class which helps you to interact with configuration.
 */
public class ConfigHelper extends AbstractConfigHelper {
    /**
     * Constructs an instance of {@code ConfigHelper}.
     * @param file a configuration file
     */
    public ConfigHelper(@NotNull File file) {
        super(file);
    }

    /**
     * Constructs an instance of {@code ConfigHelper}.
     * @param plugin a plugin which has your wanted configuration file
     */
    public ConfigHelper(@NotNull Plugin plugin) {
        super(new File(plugin.getDataFolder(), "config.yml"));
    }

    @Override
    public void loadTo(@NotNull Object config) {
        Condition.argNotNull("config", config);

        if(file.exists()) {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
            Field[] fields = config.getClass().getDeclaredFields();
            try {
                for (Field f : fields) {
                    if (f.isAnnotationPresent(Label.class)) {
                        f.setAccessible(true);
                        String[] paths = f.getDeclaredAnnotation(Label.class).value();
                        for (String p : paths) {
                            if (conf.isSet(p)) {
                                f.set(config, conf.get(p));
                                break;
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveFrom(@NotNull Object config) {
        Condition.argNotNull("config", config);

        YamlConfiguration conf = new YamlConfiguration();
        Field[] fields = config.getClass().getDeclaredFields();
        try {
            for (Field f : fields) {
                if (f.isAnnotationPresent(Label.class)) {
                    f.setAccessible(true);
                    String path = ArrayUtil.last(f.getDeclaredAnnotation(Label.class).value());
                    conf.set(path, f.get(config));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            conf.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
