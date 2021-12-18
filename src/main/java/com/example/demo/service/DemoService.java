package com.example.demo.service;

import com.example.demo.model.DemoTables;
import com.example.demo.repository.DemoRepository;
import com.google.common.base.Strings;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoRepository repository;

    public void createGrid(String name, Integer n) {
        DemoTables demoTables = new DemoTables();
        demoTables.setGridName(name);
        Table<Integer, String, String> testTable
                = HashBasedTable.create();
        testTable.put(1, "Mumbai", "11001");
        testTable.put(2, "Mumbai", "11101");
        testTable.put(1, "Harvard", "11002");
        testTable.put(3, "Harvard", "010101");
        demoTables.setGrid(testTable);
        repository.save(demoTables);
    }

    private static void test(){
        Table<Integer, String, String> testTable
                = HashBasedTable.create();
        testTable.put(1, "Mumbai", "11001");
        testTable.put(2, "Mumbai", "11101");
        testTable.put(1, "Harvard", "11002");
        testTable.put(3, "Harvard", "010101");


        //все значения таблицы
        System.out.println(testTable.cellSet());
        //имена колонок
        System.out.println(testTable.columnKeySet());
        //id строк
        System.out.println(testTable.rowKeySet());
        //значения n-ой строки
        System.out.println(testTable.row(1));
        //Значения n-ого столбца
        System.out.println(testTable.column("Mumbai"));
        //префикс n-й строки
        System.out.println(testTable.row(1));

        System.out.println("---------------");
        System.out.println(getPrefix(testTable.row(1).values()));
    }

    private static String getPrefix(Collection<String> values) {
        String prefix = null;
        for (String s : values) {
            if (prefix == null) {
                prefix = s;
            }
            prefix = Strings.commonPrefix(prefix, s);
        }
        return prefix;
    }
}
