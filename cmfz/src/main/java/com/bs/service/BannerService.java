package com.bs.service;

import com.bs.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    String add(Banner banner);

    void delete(Banner banner);

    void update(Banner banner);

    Map<String, Object> queryAll(Integer page, Integer rows);

    //------------

    List<Map<String, Object>> queryAllTest();
}
