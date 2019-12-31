package com.bs.controller;

import com.bs.entity.Chapter;
import com.bs.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/queryAll")
    public Map<String, Object> queryAll(String al_id, int page, int rows) {
        System.out.println("------chapter---queryAll-------al_id:" + al_id);
        Map<String, Object> map = chapterService.queryAll(al_id, page, rows);
        return map;
    }

    @RequestMapping("/edit")
    public Map<String, Object> edit(String oper, Chapter chapter, String al_id, String id, HttpSession session) {
        System.out.println("chapter------edit-----oper:" + oper);
        System.out.println("chapter of album _id:" + al_id);
        System.out.println("will delete chapter_id:" + id);
        chapter.setAl_id(al_id);
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = add(chapter);
        }
        if ("del".equals(oper)) {
            chapter.setCh_id(id);
            map = delete(chapter, session);
        }
        return map;
    }

    @RequestMapping("/upload")
    public void upload(String id, MultipartFile ch_cover, HttpServletRequest request) {
        //1.进行文件上传
        String name = UUID.randomUUID().toString() + ch_cover.getOriginalFilename();

        File file = new File(request.getSession().getServletContext().getRealPath("mp3"), name); //--------
        try {
            ch_cover.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.修改对应库表中数据
        Chapter chapter = new Chapter();
        chapter.setCh_id(id);
        chapter.setCh_cover(name);
        //保存文件的大小
        BigDecimal size = new BigDecimal(ch_cover.getSize());
        BigDecimal dom = new BigDecimal(1024);
        BigDecimal bigDecimal = size.divide(dom).divide(dom).setScale(2, BigDecimal.ROUND_HALF_UP);
        chapter.setCh_size(bigDecimal + "MB");
        //获取文件的时长
        Encoder encoder = new Encoder();
        try {
            long duration = encoder.getInfo(file).getDuration();
            chapter.setCh_duration(duration / 1000 / 60 + ":" + duration / 1000 % 60);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        System.out.println("upload------chapter------" + chapter);
        chapterService.update(chapter);
    }

    public Map<String, Object> add(Chapter chapter) {
        System.out.println("add --------Chapter----" + chapter);
        Map<String, Object> map = new HashMap<>();
        try {
            String i = chapterService.add(chapter);  //返回值是ch_id
            map.put("status", true);
            map.put("message", i);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> update(Chapter chapter) {

        Map<String, Object> map = new HashMap<>();
        try {
            if (chapter.getCh_cover().equals("")) chapter.setCh_cover(null);
            chapterService.update(chapter);
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> delete(Chapter chapter, HttpSession session) {
        System.out.println("delete--------Chapter-------" + chapter);
        Map<String, Object> map = new HashMap<>();
        try {
            chapterService.delete(chapter, session);
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }
}
