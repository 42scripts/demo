package com.example.demo.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class DemoTables {
    @Id
    @Column(name = "GRID_NAME", nullable = false)
    private String gridName;

    private Table<Integer, String, String> grid = HashBasedTable.create();

}
