package com.example.demo.repository;

import com.example.demo.model.DemoTables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<DemoTables, String> {

}
