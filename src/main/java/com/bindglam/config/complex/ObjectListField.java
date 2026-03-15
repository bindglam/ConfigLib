package com.bindglam.config.complex;

import com.bindglam.config.ComplexField;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ObjectListField<T> extends ComplexField<ConfigurationSection, List<T>> {
    public ObjectListField(String path, List<T> defaultValue, Function<ConfigurationSection, T> loadFunction, BiConsumer<ConfigurationSection, T> saveFunction) {
        super(path, defaultValue, config -> {
            List<T> list = new ArrayList<>();
            for(int i = 0; i < Integer.MAX_VALUE; i++) {
                ConfigurationSection objConfig = config.getConfigurationSection(Integer.toString(i));
                if(objConfig == null) break;

                list.add(loadFunction.apply(objConfig));
            }
            return list;
        }, (config, list) -> {
            ConfigurationSection listConfig = config.createSection(path);

            for(int i = 0; i < list.size(); i++) {
                T obj = list.get(i);
                ConfigurationSection objConfig = listConfig.createSection(Integer.toString(i));

                saveFunction.accept(objConfig, obj);
            }

            return listConfig;
        });
    }
}
