package com.bs.service;

import com.bs.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {

    Map<String, Object> queryAll(int page, int rows);

    String add(Album album);

    void update(Album album);

    //----------------------------------

    List<Map<String, Object>> queryAllTest();

    Map<String, Object> queryDetail(String id);
}