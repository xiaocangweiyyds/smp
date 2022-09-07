package com.yr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String head;
    private String userName;
    private Integer sex;
    private Integer age;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

}
