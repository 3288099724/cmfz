package com.bs.service;

import com.bs.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> queryAll(Integer page, Integer rows);

    void update(User user);

    List<User> queryAll();

    void login(User user, String enCode, HttpSession session);

    //
    List<Integer> queryNum(String sex);

    //-------------------------------

    Map<String, Object> login(String phone, String password, String code);

    Map<String, Object> regist(String phone, String password);

    Map<String, Object> modify(String uid, String gender, String photo,
                               String location, String description, String nickname, String province, String city, String password);

    List<Map<String, Object>> member(String uid);

}
