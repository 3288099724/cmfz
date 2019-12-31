package com.bs.service;

import com.bs.entity.Chapter;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    Map<String, Object> queryAll(String al_id, int page, int rows);

    String add(Chapter chapter);

    void delete(Chapter chapter, HttpSession session);

    void update(Chapter chapter);  //服务于添加


    //--------------------------------
    List<Map<String, Object>> queryAllCha(String al_id);
}
