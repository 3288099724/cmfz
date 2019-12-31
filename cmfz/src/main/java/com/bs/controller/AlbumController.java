package com.bs.controller;

import com.bs.entity.Album;
import com.bs.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;


    @RequestMapping("/queryAll")
    public Map<String, Object> queryAll(int page, int rows) {
        System.out.println("---queryAll---------");
        Map<String, Object> map = albumService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    public Map<String, Object> edit(String oper, Album album) {
        System.out.println("Album --edit-----oper:" + oper + "album:" + album);
        //album.setAlbum_id(id);
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = add(album);
        }
        if ("edit".equals(oper)) {
            map = update(album);
        }
        return map;
    }

    @RequestMapping("/upload")
    public void upload(String id, MultipartFile album_cover, HttpServletRequest request) {
        //1.进行文件上传
        String name = UUID.randomUUID().toString() + album_cover.getOriginalFilename();
        System.out.println("upload===name:===" + name);
        try {
            album_cover.transferTo(new File(request.getSession().getServletContext().getRealPath("face"), name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.修改对应库表中数据
        Album album = new Album();
        album.setAlbum_id(id);
        album.setAlbum_cover(name);
        System.out.println("===upload=================" + album);
        albumService.update(album);
    }

    public Map<String, Object> add(Album album) {
        System.out.println("C  add--------------" + album);
        Map<String, Object> map = new HashMap<>();
        try {
            String i = albumService.add(album);
            map.put("status", true);
            map.put("message", i);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> update(Album album) {
        System.out.println("update--------------------" + album);
        Map<String, Object> map = new HashMap<>();
        try {
            if (album.getAlbum_cover().equals("")) album.setAlbum_cover(null);
            albumService.update(album);
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

}
