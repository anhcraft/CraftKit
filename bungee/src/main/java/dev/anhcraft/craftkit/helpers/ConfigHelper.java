package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.craftkit.common.helpers.AbstractConfigHelper;
import dev.anhcraft.jvmkit.lang.annotation.Label;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.ArrayUtil;
import dev.anhcraft.jvmkit.utils.Condition;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * A class which helps you to transfer data between a configuration {@link Object} and a configuration {@link File}.
 */
public class ConfigHelper extends AbstractConfigHelper {
    private static final YamlConfiguration PROVIDER = (YamlConfiguration) ConfigurationProvider.getProvider(YamlConfiguration.class);

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
            try {
                var conf = PROVIDER.load(file);
                var fields = config.getClass().getDeclaredFields();
                for (var f : fields) {
                    if (f.isAnnotationPresent(Label.class)) {
                        f.setAccessible(true);
                        var paths = f.getDeclaredAnnotation(Label.class).value();
                        for (var p : paths) {
                            if (conf.contains(p)) {
                                f.set(config, conf.get(p));
                                break;
                            }
                        }
                    }
                }
            } catch (IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveFrom(@NotNull Object config) {
        Condition.argNotNull("config", config);

        try {
            var conf = PROVIDER.load(file);
            var fields = config.getClass().getDeclaredFields();
            for (var f : fields) {
                if (f.isAnnotationPresent(Label.class)) {
                    f.setAccessible(true);
                    var path = ArrayUtil.last(f.getDeclaredAnnotation(Label.class).value());
                    conf.set(path, f.get(config));
                }
            }
            PROVIDER.save(conf, file);
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }
}
