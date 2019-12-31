package com.bs.service;


import com.bs.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map<String, Object> queryAll(Integer page, Integer rows);

    void add(Article article);

    void update(Article article);

    void delete(Article article);

    //------------------
    List<Map<String, Object>> queryAllArtTest(String uid);
}
