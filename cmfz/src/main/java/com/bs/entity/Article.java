package com.bs.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    private String ar_id;
    private String ar_title;
    private String ar_content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date ar_cdate;
    private String ar_guru;
}
