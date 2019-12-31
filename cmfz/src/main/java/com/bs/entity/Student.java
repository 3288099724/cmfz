package com.bs.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @ExcelIgnore
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    private int age;
    @Excel(name = "生日", format = "yyyy年MM月dd日")
    private Date birthday;
}
