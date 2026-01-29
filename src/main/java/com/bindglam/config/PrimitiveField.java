package com.bindglam.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public class PrimitiveField<T> implements Field<T> {
    private final String path;
    private final T defaultValue;

    private T value;

    public PrimitiveField(String path, T defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void load(YamlConfiguration config) {
        value = (T) config.get(path);

        if(value == null) {
            value = defaultValue;

            config.set(path, defaultValue);
        }
    }

    @Override
    public @NotNull T value() {
        return value;
    }
}
