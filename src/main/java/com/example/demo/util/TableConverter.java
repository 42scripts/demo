package com.example.demo.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.lang.reflect.Type;

@Converter
@AllArgsConstructor
public class TableConverter implements
        AttributeConverter<HashBasedTable, String> {
    private final TableTypeHierarchyAdapter tableTypeHierarchyAdapter;


    @Override
    public String convertToDatabaseColumn(HashBasedTable hashBasedTable) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeHierarchyAdapter(Table.class, new TableTypeHierarchyAdapter())
                .create();

        return gson.toJson(hashBasedTable);
    }

    @Override
    public HashBasedTable convertToEntityAttribute(String s) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeHierarchyAdapter(Table.class, new TableTypeHierarchyAdapter())
                .create();
        Type typeOfTable = new TypeToken<Table<String, String, Integer>>() {}.getType();
        return gson.fromJson(s, typeOfTable);
    }
}
