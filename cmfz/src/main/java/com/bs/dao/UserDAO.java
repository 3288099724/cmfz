package com.bs.dao;

import com.bs.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserDAO extends Mapper<User> {
    //void selectAll(User user, RowBounds r);

    Integer queryNum(@Param("pre") String pre, @Param("sex") String sex);
}
