package com.bs.dao;

import com.bs.entity.Admin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminDAO extends Mapper<Admin> {
}
