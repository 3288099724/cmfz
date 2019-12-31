package com.bs.service;

import com.bs.dao.AlbumDAO;
import com.bs.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDAO albumDAO;
    @Autowired
    private ChapterService chapterService;


    @Override
    public Map<String, Object> queryAll(int page, int rows) {
        Album album = new Album();
        RowBounds r = new RowBounds((page - 1) * rows, rows);
        List<Album> albums = albumDAO.selectByRowBounds(album, r);
        int i = albumDAO.selectCount(album);
        //
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", albums);
        map.put("total", i % rows == 0 ? i / rows : i / rows + 1);
        map.put("records", i);
        return map;
    }

    @Override
    public String add(Album album) {
        String id = UUID.randomUUID().toString();
        album.setAlbum_id(id);
        album.setAlbum_cdate(new Date());
        album.setAlbum_score(10);
        album.setAlbum_count(0);
        System.out.println("service  add------------- " + album);
        int i = albumDAO.insertSelective(album);
        if (i == 0) {
            throw new RuntimeException("添加失败");
        }
        return id;
    }

    @Override
    public void update(Album album) {
        int i = albumDAO.updateByPrimaryKeySelective(album);
        if (i == 0) {
            throw new RuntimeException("修改失败");
        }
    }

    //---------------------------------------------------------------------
    @Override
    public List<Map<String, Object>> queryAllTest() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Album> albums = albumDAO.selectAll();


        for (Album album : albums) {

            Map<String, Object> i = new HashMap<>();
            i.put("thumbnail", "http://http://localhost:8989/cmfz/face/" + album.getAlbum_cover());
            i.put("title", album.getAlbum_title());
            i.put("set_count", album.getAlbum_count());
            i.put("create_date", album.getAlbum_cdate());
            i.put("type", 0);
            list.add(i);
        }
        return list;
    }

    //详细专辑信息he 其章节信息
    @Override
    public Map<String, Object> queryDetail(String id) {
        Map<String, Object> map = new HashMap<>();   //最外面的map

        Map<String, Object> i = new HashMap<>();   //封装专辑信息的map
        Album album = albumDAO.selectByPrimaryKey(id);
        i.put("thumbnail", "http://http://localhost:8989/cmfz/face/" + album.getAlbum_cover());
        i.put("title", album.getAlbum_title());
        i.put("score", album.getAlbum_score());
        i.put("author", album.getAlbum_author());
        i.put("broadcast", album.getAlbum_beam());
        i.put("set_count", album.getAlbum_count());
        i.put("brief", album.getAlbum_intro());
        i.put("create_date", album.getAlbum_cdate());

        map.put("introduction", i);

        List<Map<String, Object>> list = chapterService.queryAllCha(album.getAlbum_id());
        map.put("list", list);

        return map;

    }


}
