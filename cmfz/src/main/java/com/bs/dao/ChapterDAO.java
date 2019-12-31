package com.bs.dao;

import com.bs.entity.Chapter;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChapterDAO extends Mapper<Chapter> {
}
