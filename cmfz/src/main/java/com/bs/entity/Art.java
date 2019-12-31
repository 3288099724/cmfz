package com.bs.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Art {

    //private String thumbnail;
    private String title;  //文章标题
    private String author;   //上师
    private Integer type;    //类型（0：闻，| 1：思--文章）
    //private Integer set_count;    //集数（只有闻的数据才有）
    private Date create_date;    //创建时间

}
