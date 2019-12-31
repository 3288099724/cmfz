package com.bs.dao;

import com.bs.entity.Article;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ArticleDAO extends Mapper<Article> {
}
