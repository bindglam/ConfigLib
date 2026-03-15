package com.bindglam.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ComplexField<P, C> implements Field<C> {
    private final String path;
    private final C defaultValue;
    private final Function<P, C> loadFunction;
    private final BiFunction<FileConfiguration, C, P> saveFunction;

    private C value;

    public ComplexField(String path, C defaultValue, Function<P, C> loadFunction, BiFunction<FileConfiguration, C, P> saveFunction) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.loadFunction = loadFunction;
        this.saveFunction = saveFunction;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load(YamlConfiguration config) {
        P rawValue = (P) config.get(path);

        if(rawValue == null) {
            value = defaultValue;

            config.set(path, saveFunction.apply(config, defaultValue));
        } else {
            value = loadFunction.apply(rawValue);
        }
    }

    @Override
    public @NotNull C value() {
        return value;
    }
}
