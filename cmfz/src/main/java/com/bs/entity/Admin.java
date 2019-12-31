package com.bs.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "admin5")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    private String ad_id;
    private String ad_name;
    private String ad_password;
    private String ad_nickname;
}
