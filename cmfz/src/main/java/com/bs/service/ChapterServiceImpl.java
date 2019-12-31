package com.bs.service;

import com.bs.dao.AlbumDAO;
import com.bs.dao.ChapterDAO;
import com.bs.entity.Album;
import com.bs.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private AlbumDAO albumDAO;


    @Override
    public Map<String, Object> queryAll(String al_id, int page, int rows) {
        RowBounds r = new RowBounds((page - 1) * rows, rows);
        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("al_id", al_id);
        List<Chapter> chapters = chapterDAO.selectByExampleAndRowBounds(e, r);

        //int i = chapterDAO.selectCount(new Chapter());
        int i = chapters.size();
        //
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", chapters);
        map.put("total", i % rows == 0 ? i / rows : i / rows + 1);
        map.put("records", i);
        return map;
    }

    @Override
    public String add(Chapter chapter) {
        String id = UUID.randomUUID().toString();
        chapter.setCh_id(id);
        chapter.setCh_cdate(new Date());
        int i = chapterDAO.insertSelective(chapter);

        //没添加一个章节，专辑的集数+1
        Album album = albumDAO.selectByPrimaryKey(chapter.getAl_id());
        album.setAlbum_count(album.getAlbum_count() + 1);
        int i1 = albumDAO.updateByPrimaryKeySelective(album);

        if (i == 0 || i1 == 0) throw new RuntimeException("添加失败");
        return id;
    }

    @Override
    public void delete(Chapter chapter, HttpSession session) {
        System.out.println(chapter);
        int i = chapterDAO.deleteByPrimaryKey(chapter.getCh_id());
        System.out.println(i);
        if (i == 0) throw new RuntimeException("删除失败");


        //获取要删除章节的音频名cover,因为前台获取不到，只好从数据库查询
        Chapter one = chapterDAO.selectByPrimaryKey(chapter.getCh_id());
        System.out.println(one);
        //包括MP3目录下文件的删除
        //File f = new File(session.getServletContext().getRealPath("mp3")+"//"+chapter.getCh_cover());
        /*File f = new File(session.getServletContext().getRealPath("mp3")+"//"+one.getCh_cover());
        System.out.println("will delete file："+f);
        if(f!=null)  f.delete();*/

    }


    @Override
    public void update(Chapter chapter) {
        chapterDAO.updateByPrimaryKeySelective(chapter);

    }

    //----------------------------------------------------

    @Override
    public List<Map<String, Object>> queryAllCha(String al_id) {
        List<Map<String, Object>> list = new ArrayList<>(); //最终的

        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("al_id", al_id);
        List<Chapter> chapters = chapterDAO.selectByExample(e);

        for (Chapter chapter : chapters) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", chapter.getCh_title());
            //下载地址
            map.put("download_url", "http://http://localhost:8989/cmfz/mp3/" + chapter.getCh_cover());
            map.put("size", chapter.getCh_size());   //音频大小（字节数）
            map.put("duration", chapter.getCh_duration());   //音频时长（毫秒数）

            list.add(map);
        }
        return list;

    }
}
