package com.bs.controller;

import com.bs.entity.Admin;
import com.bs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")

    public @ResponseBody
    Map<String, Object> login(Admin admin, @RequestParam("enCode") String enCode, HttpSession session) {
        System.out.println("=========" + enCode);
        System.out.println(admin);
        Map<String, Object> map = new HashMap<>();
        try {
            adminService.login(admin, enCode, session);
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }


}
