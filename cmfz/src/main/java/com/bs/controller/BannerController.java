package com.bs.controller;

import com.bs.entity.Banner;
import com.bs.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;


    @RequestMapping("/queryAll")
    public Map<String, Object> show(Integer page, Integer rows) {
        System.out.println("find");
        Map<String, Object> map = bannerService.queryAll(page, rows);
        return map;
    }


    @RequestMapping("/edit")
    public Map<String, Object> edit(String oper, Banner banner, String id) {
        System.out.println("oper:" + oper);
        System.out.println("id:" + id);

        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = add(banner);
        } else if ("del".equals(oper)) {
            banner.setBan_id(id);
            map = delete(banner);
        } else if ("edit".equals(oper)) {
            banner.setBan_id(id);
            map = update(banner);
        }
        return map;

    }

    @RequestMapping("/upload")
    public void upload(String id, MultipartFile ban_cover, HttpSession session) throws IOException {
        System.out.println("upload------------");
        System.out.println("id:" + id);
        System.out.println("ban_cover:" + ban_cover);
        //使用UUID+文件名，，防止上传的文件重名
        String name = UUID.randomUUID().toString() + ban_cover.getOriginalFilename();
        String realPath = session.getServletContext().getRealPath("banner-img");
        //把ban_cover这个文件以name为名字，放在realPath下
        ban_cover.transferTo(new File(realPath, name));

        //需要在submit之后进行一次图片路径的修改
        Banner banner = new Banner();
        banner.setBan_id(id);
        banner.setBan_cover(name);
        bannerService.update(banner);

    }


    public Map<String, Object> add(Banner banner) {
        Map<String, Object> map = new HashMap<>();
        try {
            String id = bannerService.add(banner);
            map.put("status", true);
            map.put("message", id);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
        }
        return map;
    }

    public Map<String, Object> delete(Banner banner) {
        System.out.println("delete---" + banner);

        Map<String, Object> map = new HashMap<>();
        try {
            bannerService.delete(banner);
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
        }
        return map;
    }

    public Map<String, Object> update(Banner banner) {
        System.out.println("update---" + banner);

        Map<String, Object> map = new HashMap<>();
        try {
            if (banner.getBan_cover().equals("")) {
                //修改的时候不能修改轮播图的图片，所以文件为空，需要做判断
                banner.setBan_cover(null);
            }
            bannerService.update(banner);
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
        }
        return map;
    }

}
