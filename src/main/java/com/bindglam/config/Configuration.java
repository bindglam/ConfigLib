package com.bindglam.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Configuration {
    private final List<Field<?>> fields = new ArrayList<>();

    private final File file;

    public Configuration(File file) {
        this.file = file;
    }

    public void load() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        fields.forEach((field) -> field.load(config));

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    protected <T> Field<T> createPrimitiveField(String path, T defaultValue) {
        PrimitiveField<T> field = new PrimitiveField<>(path, defaultValue);
        fields.add(field);
        return field;
    }

    protected <P, C> Field<C> createComplexField(String path, C defaultValue, Function<P, C> loadFunction, Function<C, P> saveFunction) {
        ComplexField<P, C> field = new ComplexField<>(path, defaultValue, loadFunction, saveFunction);
        fields.add(field);
        return field;
    }

    protected <P, C> Field<C> createExtendedComplexField(Supplier<? extends ComplexField<P, C>> supplier) {
        ComplexField<P, C> field = supplier.get();
        fields.add(field);
        return field;
    }
}
