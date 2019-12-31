package com.bs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "banner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    private String ban_id;
    private String ban_name;
    private String ban_cover;
    private String ban_description;
    private String ban_status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ban_cdate;

}
