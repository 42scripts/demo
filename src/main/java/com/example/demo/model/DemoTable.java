package com.example.demo.model;

import com.example.demo.util.TableConverter;
import com.google.common.collect.HashBasedTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor

public class DemoTable {
    @Id
    @Column(name = "GRID_NAME", nullable = false)
    private String gridName;

    private Integer gridSize;

    @Convert(converter = TableConverter.class)
    @Column(length = 10000)
    private HashBasedTable<Integer, String, String> grid;
}
