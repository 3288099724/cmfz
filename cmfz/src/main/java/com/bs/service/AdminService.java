package com.bs.service;

import com.bs.entity.Admin;

import javax.servlet.http.HttpSession;

public interface AdminService {
    public void login(Admin admin, String enCode, HttpSession session);

}
