package com.bindglam.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public interface Field<T> {
    void load(YamlConfiguration config);

    @NotNull T value();
}
