package com.example.demo.repository;

import com.example.demo.model.DemoTable;
import com.google.common.collect.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DemoRepository extends JpaRepository<DemoTable, String> {

    @Modifying
    @Transactional
    @Query(value = "update demo_tables dt set grid_name = :newName where dt.grid_name = :oldName", nativeQuery = true)
    void updateGridName(String newName, String oldName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM demo_tables dt WHERE dt.grid_name = :gridName", nativeQuery = true)
    void clearTable(String gridName);

}
