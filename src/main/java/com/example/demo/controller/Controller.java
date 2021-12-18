package com.example.demo.controller;

import com.example.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;


@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
@Validated
public class Controller {
    private final DemoService service;

    @GetMapping
    public void healthCheck(){
        this.createGrid("tst", 1);
    }

    @PostMapping("/createGrid")
    @Operation(summary = "Создать таблицу", description = "Создает таблицу")
    public void createGrid(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @RequestBody String name,
            @Parameter(description = "Размер таблицы") @Size(max = 10000) @RequestBody Integer n
){
        service.createGrid(name, n);
    }






    @PostMapping("/updateName")
    public void updateName(String name){}

    @DeleteMapping("/deleteGrid")
    public void deleteGrid(){}

/*    @GetMapping("/getlist")
    public List<String> getList(){}*/

    @GetMapping("/getinfo")
    public void getInfo(){    }

    @PostMapping("/addcolumn")
    public void addColumn(){}

    @DeleteMapping("/deletecol")
    public void deleteColumn(String name){}
/*
    @GetMapping("/getcolumns")
    public List<ColumnData> getColumnList(){}*/

    @PostMapping("/insertrow")
    public void insertRow(){}

    @DeleteMapping("/deleterow")
    public void deleteRow(Integer id){}

/*    @GetMapping("/listrowsmetadata")
    public List<String> getListRowsMetadata(){}*/

    @PostMapping("/setvalue")
    public void setValue(String value){}

/*    @GetMapping("/perfix")
    public String calcPerfix(Integer id){}*/
}
