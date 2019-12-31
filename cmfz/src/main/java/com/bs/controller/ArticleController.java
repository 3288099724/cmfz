package com.bs.controller;

import com.bs.entity.Article;
import com.bs.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = articleService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(Article article, String oper, String id) {
        //System.out.println("id："+id);
        Map<String, Object> map = new HashMap<>();
        System.out.println("进行的操作：" + oper);
        if (oper.equals("add")) {
            map = add(article);
        }
        if (oper.equals("edit")) {
            map = edit(article);
        }
        if (oper.equals("del")) {
            article.setAr_id(id);
            map = del(article);
        }
        return map;
    }

    public Map<String, Object> add(Article article) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println("添加的文章：" + article);
            articleService.add(article);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> edit(Article article) {
        System.out.println("edit:" + article);
        Map<String, Object> map = new HashMap<>();
        try {
            articleService.update(article);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> del(Article article) {
        System.out.println("del:" + article);
        Map<String, Object> map = new HashMap<>();
        try {
            articleService.delete(article);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile imgFile, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        String name = UUID.randomUUID().toString() + imgFile.getOriginalFilename();
        String path = session.getServletContext().getRealPath("/article-face");
        try {
            imgFile.transferTo(new File(path, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        Map<String, Object> map = new HashMap<>();
        map.put("error", 0);
        String scheme = request.getScheme();  //协议 http
        InetAddress address = InetAddress.getLocalHost(); //ip  PC-201910311730/192.168.9.20
        String s = address.toString();
        String ip = s.split("/")[1];   //   192.168.9.20
        int port = request.getServerPort(); //  8989
        String contextPath = request.getContextPath();  //     /cmfz  带斜线

        String url = scheme + "://" + ip + ":" + port + contextPath + "/article-face/" + name;
        map.put("url", url);
        return map;
    }


    @RequestMapping("getAllImg")
    public Map<String, Object> getAll(HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        //获得目录
        String realPath = request.getSession().getServletContext().getRealPath("article-face");
        File files = new File(realPath);
        //获得所有图片名
        String[] names = files.list();
        //list集合的每一个元素是map,  每一个map存放一张图片的信息，
        List<Map<String, Object>> list = new ArrayList<>();
        for (String name : names) {
            Map<String, Object> file = new HashMap<>();
            file.put("is_dir", false);
            file.put("has_file", false);

            File file1 = new File(realPath, name);
            file.put("filesize", file1.length());
            file.put("dir_path", "");
            file.put("is_photo", true);
            file.put("filetype", FilenameUtils.getExtension(name));  //扩展名
            file.put("filename", name);
            file.put("datetime", "2018-06-06 00:36:39");
            list.add(file);
        }


        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");

        String scheme = request.getScheme();  //协议 http
        InetAddress address = InetAddress.getLocalHost(); //ip  PC-201910311730/192.168.9.20
        String s = address.toString();
        String ip = s.split("/")[1];   //   192.168.9.20
        int port = request.getServerPort(); //  8989
        String contextPath = request.getContextPath();  //     /cmfz  带斜线
        String url = scheme + "://" + ip + ":" + port + contextPath + "/article-face/";    //目录

        map.put("current_url", url);
        map.put("total_count", names.length);    //共有多少张图片
        map.put("file_list", list);
        return map;

    }
}
