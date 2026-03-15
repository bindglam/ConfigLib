package com.bindglam.config.complex;

import com.bindglam.config.ComplexField;

public class EnumField<T extends Enum<T>> extends ComplexField<String, T> {
    public EnumField(String path, T defaultValue, Class<T> enumClass) {
        super(path, defaultValue, (value) -> Enum.valueOf(enumClass, value), (config, value) -> value.name());
    }
}
