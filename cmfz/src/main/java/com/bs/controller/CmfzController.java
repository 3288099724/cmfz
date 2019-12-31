package com.bs.controller;

import com.bs.service.AlbumService;
import com.bs.service.ArticleService;
import com.bs.service.BannerService;
import com.bs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CmfzController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    //http://192.168.0.3:8080/cmfz/first_page
    @RequestMapping("first_page")
    public Map<String, Object> first_page(String uid, String type, String sub_type) {
        Map<String, Object> map = new HashMap<>();
        if (uid == null || type == null) {
            map.put("error", "错误");
        } else {
            if (type.equals("all")) {

                map.put("header", bannerService.queryAllTest());
            }
            if (type.equals("wen")) {
                map.put("body", albumService.queryAllTest());
            }
            if (type.equals("si")) {
                if (sub_type.equals("ssyj")) {  //仅显示当前用户的老师文章
                    map.put("body", articleService.queryAllArtTest(uid));
                }
                if (sub_type.equals("xmfy")) {
                    map.put("body", articleService.queryAllArtTest(null));
                }
            }
        }

        return map;
    }

    //http://api.chimingfazhou.cn/detail/wen   专辑详情
    @RequestMapping("/detail/wen")
    public Map<String, Object> wen(String uid, String id) {
        Map<String, Object> map = albumService.queryDetail(id);
        return map;
    }

    //http://api.chimingfazhou.cn/account/login
    @RequestMapping("/account/login")
    public Map<String, Object> login(String phone, String password, String code) {
        Map<String, Object> map = userService.login(phone, password, code);
        return map;
    }

    //http://api.chimingfazhou.cn/account/regist
    @RequestMapping("/account/regist")
    public Map<String, Object> regist(String phone, String password) {
        Map<String, Object> map = userService.regist(phone, password);
        return map;
    }

    // http://api.chimingfazhou.cn/account/modify
    @RequestMapping("/account/modify")
    public Map<String, Object> modify(String uid, String gender, String photo, String location, String description, String nickname, String province, String city, String password) {
        Map<String, Object> map = userService.modify(uid, gender, photo, location, description, nickname, province, city, password);
        return map;

    }

    //http://api.chimingfazhou.cn/identify/obtain
    @RequestMapping("/identify/obtain")
    public String obtain(String phone) {
        return "code";
    }

    //http://api.chimingfazhou.cn/identify/check
    @RequestMapping("/identify/check")
    public Map<String, Object> check(String phone, String code) {
        Map<String, Object> map = new HashMap<>();
        //校验代码
        map.put("result", "success");
        //或者
        map.put("result", "fail");
        return map;
    }

    // http://api.chimingfazhou.cn/member
    @RequestMapping("/member")
    public List<Map<String, Object>> member(String uid) {
        List<Map<String, Object>> list = userService.member(uid);
        return list;
    }

}
