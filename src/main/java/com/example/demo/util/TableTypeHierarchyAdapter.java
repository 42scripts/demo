package com.example.demo.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class TableTypeHierarchyAdapter<R, C, V> implements JsonSerializer<Table<R, C, V>>, JsonDeserializer<Table<R, C, V>> {
    @Override
    public JsonElement serialize(Table<R, C, V> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray rowKeysJsonArray = new JsonArray();
        Map<R, Integer> rowKeyToIndex = new HashMap<>();
        for (R rowKey : src.rowKeySet()) {
            rowKeyToIndex.put(rowKey, rowKeyToIndex.size());
            rowKeysJsonArray.add(context.serialize(rowKey));
        }
        JsonArray columnKeysJsonArray = new JsonArray();
        Map<C, Integer> columnKeyToIndex = new HashMap<>();
        for (C columnKey : src.columnKeySet()) {
            columnKeyToIndex.put(columnKey, columnKeyToIndex.size());
            columnKeysJsonArray.add(context.serialize(columnKey));
        }
        JsonArray cellsJsonArray = new JsonArray();
        for (Table.Cell<R, C, V> cell : src.cellSet()) {
            JsonObject cellJsonObject = new JsonObject();
            int rowIndex = rowKeyToIndex.get(cell.getRowKey());
            int columnIndex = columnKeyToIndex.get(cell.getColumnKey());
            cellJsonObject.addProperty("rowIndex", rowIndex);
            cellJsonObject.addProperty("columnIndex", columnIndex);
            cellJsonObject.add("value", context.serialize(cell.getValue()));
            cellsJsonArray.add(cellJsonObject);
        }
        JsonObject tableJsonObject = new JsonObject();
        tableJsonObject.add("rowKeys", rowKeysJsonArray);
        tableJsonObject.add("columnKeys", columnKeysJsonArray);
        tableJsonObject.add("cells", cellsJsonArray);
        return tableJsonObject;
    }

    @Override
    public Table<R, C, V> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Type typeOfR;
        Type typeOfC;
        Type typeOfV;
        {
            ParameterizedType parameterizedType = (ParameterizedType) typeOfT;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            typeOfR = actualTypeArguments[0];
            typeOfC = actualTypeArguments[1];
            typeOfV = actualTypeArguments[2];
        }
        JsonObject tableJsonObject = json.getAsJsonObject();
        JsonArray rowKeysJsonArray = tableJsonObject.getAsJsonArray("rowKeys");
        Map<Integer, R> rowIndexToKey = new HashMap<>();
        for (JsonElement jsonElement : rowKeysJsonArray) {
            R rowKey = context.deserialize(jsonElement, typeOfR);
            rowIndexToKey.put(rowIndexToKey.size(), rowKey);
        }
        JsonArray columnKeysJsonArray = tableJsonObject.getAsJsonArray("columnKeys");
        Map<Integer, C> columnIndexToKey = new HashMap<>();
        for (JsonElement jsonElement : columnKeysJsonArray) {
            C columnKey = context.deserialize(jsonElement, typeOfC);
            columnIndexToKey.put(columnIndexToKey.size(), columnKey);
        }
        JsonArray cellsJsonArray = tableJsonObject.getAsJsonArray("cells");
        Table table = HashBasedTable.create();
        for (JsonElement jsonElement : cellsJsonArray) {
            JsonObject cellJsonObject = jsonElement.getAsJsonObject();
            int rowIndex = cellJsonObject.get("rowIndex").getAsInt();
            int columnIndex = cellJsonObject.get("columnIndex").getAsInt();
            R rowKey = rowIndexToKey.get(rowIndex);
            C columnKey = columnIndexToKey.get(columnIndex);
            V value = context.deserialize(cellJsonObject.get("value"), typeOfV);
            table.put(rowKey, columnKey, value);
        }
        return table;
    }
}
