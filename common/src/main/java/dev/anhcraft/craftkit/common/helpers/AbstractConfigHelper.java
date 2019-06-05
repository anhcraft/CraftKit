package dev.anhcraft.craftkit.common.helpers;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

import java.io.File;

/**
 * Represents {@code ConfigHelper} implementation.
 */
public abstract class AbstractConfigHelper {
    protected File file;

    /**
     * Constructs an instance of {@code AbstractConfigHelper}.
     * @param file a configuration file
     */
    public AbstractConfigHelper(@NotNull File file){
        this.file = file;
    }

    /**
     * Imports the configuration file into the given object.<br>
     * With each field, its {@code Label} indicates the paths of configuration entries. This method will try all until one of these paths is existed and can be imported successfully.
     * @param config configuration object
     */
    public abstract void loadTo(@NotNull Object config);

    /**
     * Exports the data of given object into the configuration file.<br>
     * With each field, this method only use the last path from its {@code Label}.
     * @param config configuration object
     */
    public abstract void saveFrom(@NotNull Object config);
}
