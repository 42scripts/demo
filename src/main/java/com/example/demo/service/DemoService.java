package com.example.demo.service;

import com.example.demo.model.DemoTable;
import com.example.demo.repository.DemoRepository;
import com.google.common.base.Strings;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoRepository repository;

    public void createGrid(String name, Integer n) {
        DemoTable demoTables = new DemoTable();
        demoTables.setGridName(name);
        demoTables.setGridSize(n);
        HashBasedTable<Integer, String, String> testTable
                = HashBasedTable.create();

        demoTables.setGrid(testTable);
        repository.save(demoTables);
    }

    public void renameGrid(String oldName, String newName) {
        repository.updateGridName(newName, oldName);
    }

    public void deleteGrid(String gridName) {
        repository.clearTable(gridName);
    }

    public List<String> getListGrids() {
        return repository.findAll().stream()
                .map(x -> x.getGridName() + ", " + x.getGridSize())
                .collect(Collectors.toList());
    }

    public String getInfo(String gridName) {
        DemoTable demoTable = repository.findById(gridName).get();
        if (demoTable == null) {
            return "";
        }
        int size = 0;
        if (demoTable.getGrid() != null) {
            size = demoTable.getGrid().size();
        }
        return demoTable.getGridName() + ", " + demoTable.getGridSize() + ", " + size;
    }

    @Transactional
    public void addCol(String gridName, String colName) {
        Optional<DemoTable> demoTable = repository.findById(gridName);
        if (demoTable.isPresent()) {
            Table grid = demoTable.get().getGrid();
            if (grid != null) {
                grid.put(1, colName, "0");
                repository.save(demoTable.get());
            }
        }
    }

    @Transactional
    public void deleteCol(String gridName, String colName) {
        Optional<DemoTable> demoTable = repository.findById(gridName);
        if (demoTable.isPresent()) {
            Table grid = demoTable.get().getGrid();
            if (grid != null) {
                grid.columnKeySet().remove(colName);
                repository.save(demoTable.get());
            }
        }
    }

    public Set<String> getColumns(String gridName) {
        Set result = null;
        Optional<DemoTable> demoTable = repository.findById(gridName);
        if (demoTable.isPresent()) {
            Table grid = demoTable.get().getGrid();
            if (grid != null) {
                result = grid.columnKeySet();
            }
        }
        return result;
    }

    @Transactional
    public void deleteRow(String gridName, String rowName) {
        Optional<DemoTable> demoTable = repository.findById(gridName);
        if (demoTable.isPresent()) {
            Table grid = demoTable.get().getGrid();
            if (grid != null) {
                grid.rowKeySet().remove(rowName);
                repository.save(demoTable.get());
            }
        }
    }

    public Set<String> getRows(String gridName) {
        Set result = null;
        Optional<DemoTable> demoTable = repository.findById(gridName);
        if (demoTable.isPresent()) {
            Table grid = demoTable.get().getGrid();
            if (grid != null) {
                result = grid.rowKeySet();
            }
        }
        return result;
    }

    @Transactional
    public void setValue(String gridName, Integer row, String col, String value) {
        Optional<DemoTable> demoTable = repository.findById(gridName);
        if (demoTable.isPresent() && validateValue(demoTable.get().getGridSize(), value)) {
            Table grid = demoTable.get().getGrid();
            if (grid != null) {
                grid.put(row, col, value);
            } else {
                grid = HashBasedTable.create();
                grid.put(row, col, value);
            }
            repository.save(demoTable.get());
        }
    }

    private boolean validateValue(Integer n, String value) {
        if (n == null || value == null || value.length() > n) {
            return false;
        }
        return value.codePoints().filter(ch -> ch == '0' || ch == '1').count() == value.length();
    }

    public String getPrefix(String gridName) {
        Optional<DemoTable> demoTable = repository.findById(gridName);
        String prefix = null;
        if (demoTable.isPresent()) {
            Table<Integer, String, String> grid = demoTable.get().getGrid();
            if (grid != null) {
                for (Object s : grid.values()) {
                    if (prefix == null) {
                        prefix = String.valueOf(s);
                    }
                    prefix = Strings.commonPrefix(prefix, String.valueOf(s));
                }
            }
        }
        return prefix;
    }
}