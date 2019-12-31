package com.bs.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.bs.entity.User;
import com.bs.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/queryAll")

    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = userService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    public void update(String oper, User user) {
        System.out.println("update----C---------" + user);
        try {
            if ("edit".equals(oper)) {
                userService.update(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("userOut")
    public void queryAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = userService.queryAll();
        String realPath = request.getSession().getServletContext().getRealPath("/user-img/");
        for (User u : users) {
            u.setUser_photo(realPath + u.getUser_photo());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("user", "222"),
                User.class, users);

        //设置响应头
        String encode = URLEncoder.encode("user.xls", "utf-8");
        response.setHeader("content-disposition", "attachment;fileName=" + encode);
        workbook.write(response.getOutputStream());
    }

    @RequestMapping("queryNum")

    public Map<String, Object> queryAll() {
        List<Integer> man = userService.queryNum("男");
        List<Integer> woman = userService.queryNum("女");
        Map<String, Object> map = new HashMap<>();
        map.put("man", man);
        map.put("woman", woman);
        return map;
    }

    @RequestMapping("login")
    public Map<String, Object> login(User user, String enCode, HttpSession session) {
        System.out.println("=========" + enCode);
        System.out.println(user);
        Map<String, Object> map = new HashMap<>();
        try {
            userService.login(user, enCode, session);
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

}
