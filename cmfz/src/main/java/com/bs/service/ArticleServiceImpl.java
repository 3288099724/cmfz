package com.bs.service;

import com.bs.dao.ArticleDAO;
import com.bs.dao.UserDAO;
import com.bs.entity.Article;
import com.bs.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds r = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDAO.selectByRowBounds(article, r);
        int i = articleDAO.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", articles);
        map.put("total", i % rows == 0 ? i / rows : i / rows + 1);
        map.put("records", i);
        return map;
    }

    @Override
    public void add(Article article) {
        article.setAr_id(UUID.randomUUID().toString());
        article.setAr_cdate(new Date());
        int i = articleDAO.insertSelective(article);
        System.out.println(i);
    }

    @Override
    public void update(Article article) {
        int i = articleDAO.updateByPrimaryKeySelective(article);
        System.out.println(i);
    }

    @Override
    public void delete(Article article) {
        int i = articleDAO.deleteByPrimaryKey(article);
        System.out.println(i);
    }

    //-------------------------------
    @Override
    public List<Map<String, Object>> queryAllArtTest(String uid) {
        List<Article> articles = new ArrayList<>();

        if (uid == null) {
            articles = articleDAO.selectAll();
        } else {
            User user = new User();
            user.setUser_id(uid);   //根据用户uid,---->师傅id--->其文章
            User one = userDAO.selectOne(user);
            //
            Example e = new Example(Article.class);
            e.createCriteria().andEqualTo("ar_guru", one.getUser_father());
            articles = articleDAO.selectByExample(e);
        }

        List<Map<String, Object>> artList = new ArrayList<>();
        for (Article article : articles) {
            Map<String, Object> map = new HashMap<>();
            map.put("author", article.getAr_guru());
            map.put("create_date", article.getAr_cdate());
            map.put("title", article.getAr_title());
            map.put("author", article.getAr_guru());
            map.put("type", 1);
            artList.add(map);
        }
        return artList;

    }
}
