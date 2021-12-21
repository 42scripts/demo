package com.example.demo.controller;

import com.example.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
@Validated
public class Controller {
    private final DemoService service;

    @PostMapping("/createGrid/{name}/{n}")
    @Operation(summary = "Создать таблицу", description = "Создает таблицу")
    public void createGrid(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable String name,
            @Parameter(description = "Размер таблицы") @Size(max = 10000) @PathVariable Integer n
){
        service.createGrid(name, n);
    }

    @PostMapping("/updateName/{newName}")
    @Operation(summary = "Переименовать таблицу", description = "Переименовывает таблицу")
    public void updateName(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @RequestBody String oldName,
            @Parameter(description = "Новое имя таблицы") @Size(max = 200) @PathVariable String newName){
        service.renameGrid(oldName, newName);
    }


    @DeleteMapping("/deleteGrid/{gridName}")
    @Operation(summary = "Удалить таблицу", description = "Удаляет таблицу")
    public void deleteGrid(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable String gridName) {
        service.deleteGrid(gridName);
    }

    @GetMapping("/getlistgrids")
    @Operation(summary = "Информация о таблицах", description = "Имена и n-значения")
    public List<String> getListGrids(){
        return service.getListGrids();
    }

    @GetMapping("/getinfo/{gridName}")
    @Operation(summary = "Информация о таблице", description = "Имя, n-значение, фактический размер")
    public String getInfo(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName
    ){
        return service.getInfo(gridName);
    }



    @PostMapping("/addcolumn/{gridName}/{colName}")
    @Operation(summary = "Добавить столбец", description = "Добавляет столбец к таблице")
    public void addColumn(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName,
            @Parameter(description = "Имя колонки") @Size(max = 200) @PathVariable String colName){
        service.addCol(gridName, colName);
    }

    @DeleteMapping("/deletecol/{gridName}/{colName}")
    @Operation(summary = "Удалить столбец", description = "Удаляет столбец таблицы")
    public void deleteColumn(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName,
            @Parameter(description = "Имя колонки") @Size(max = 200) @PathVariable String colName){
        service.deleteCol(gridName, colName);
    }

    @GetMapping("/columns/{gridName}")
    @Operation(summary = "Столбцы таблицы", description = "Получить имена столбцов таблицы")
    public Set<String> getColumns(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName){
        return service.getColumns(gridName);
    }

    @DeleteMapping("/deleterow/{gridName}/{rowName}")
    @Operation(summary = "Удалить строку", description = "Удаляет строку таблицы")
    public void deleteRow(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName,
            @Parameter(description = "Имя колонки") @Size(max = 200) @PathVariable String rowName){
        service.deleteRow(gridName, rowName);
    }

    @GetMapping("/rows/{gridName}")
    @Operation(summary = "Строки таблицы", description = "Получить данные о строках")
    public Set<String> getRows(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName){
        return service.getRows(gridName);
    }

    @PostMapping("/setvalue/{gridName}/{row}/{col}/{value}")
    @Operation(summary = "Установить значение ячейки", description = "Устанавливает значение")
    public void setValue(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName,
            @Parameter(description = "Номер строки") @PathVariable  Integer row,
            @Parameter(description = "Имя столбца") @Size(max = 200) @PathVariable  String col,
            @Parameter(description = "Значение") @PathVariable  String value){
        service.setValue(gridName, row, col, value);
    }

    @GetMapping("/perfix/{gridName}")
    @Operation(summary = "Префикс", description = "Префикс таблицы")
    public String calcPerfix(
            @Parameter(description = "Имя таблицы") @Size(max = 200) @PathVariable  String gridName){
        return service.getPrefix(gridName);
    }
}
