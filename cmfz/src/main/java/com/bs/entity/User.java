package com.bs.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user0")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @ExcelIgnore
    private String user_id;
    @Excel(name = "姓名")
    private String user_name;
    @ExcelIgnore
    private String user_password;
    @ExcelIgnore
    private String user_salt;
    @Excel(name = "上师")
    private String user_dharma;
    @Excel(name = "省份")
    private String user_province;
    @Excel(name = "城市")
    private String user_city;
    @Excel(name = "签名")
    private String user_sign;
    @Excel(name = "性别")
    private String user_sex;
    @Excel(name = "头像")
    private String user_photo;
    @Excel(name = "状态")
    private String user_status;
    @Excel(name = "电话")
    private String user_phone;
    @DateTimeFormat
    @JsonFormat
    @Excel(name = "注册日期")
    private Date user_cdate;
    private String user_father;
}
