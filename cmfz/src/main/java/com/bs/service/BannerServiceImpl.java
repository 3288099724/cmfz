package com.bs.service;

import com.bs.dao.BannerDAO;
import com.bs.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDAO bannerDAO;


    @Override
    public String add(Banner banner) {
        String id = UUID.randomUUID().toString();
        banner.setBan_id(id);
        banner.setBan_cdate(new Date());
        int i = bannerDAO.insertSelective(banner);
        if (i == 0) throw new RuntimeException("添加失败");
        return id;
    }

    @Override
    public void delete(Banner banner) {
        int i = bannerDAO.delete(banner);
        if (i == 0) throw new RuntimeException("删除失败");
    }

    @Override
    public void update(Banner banner) {

        int i = bannerDAO.updateByPrimaryKeySelective(banner);
        if (i == 0) throw new RuntimeException("修改失败");
    }

    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Banner banner = new Banner();
        RowBounds r = new RowBounds((page - 1) * rows, rows);
        List<Banner> banners = bannerDAO.selectByRowBounds(banner, r);

        int i = bannerDAO.selectCount(banner);

        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", banners);
        map.put("total", i % rows == 0 ? i / rows : i / rows + 1);
        map.put("records", i);
        return map;
    }


    //-------------------------------------------------
/*    @Override
    public List<Ban> queryAllBan() {
        List<Banner> banners = bannerDAO.selectAll();
        List<Ban> banList = new ArrayList<>();
        for (Banner banner : banners) {
            Ban ban = new Ban();
            ban.setId(banner.getBan_id());
            ban.setDesc(banner.getBan_description());
            ban.setThumbnail("http://http://localhost:8989/cmfz/banner-img/"+banner.getBan_cover());
            banList.add(ban);
        }
        return banList;
    }*/

    @Override
    public List<Map<String, Object>> queryAllTest() {
        List<Map<String, Object>> list = new ArrayList<>();

        List<Banner> banners = bannerDAO.selectAll();
        for (Banner banner : banners) {
            Map<String, Object> ban = new HashMap<>();
            ban.put("id", banner.getBan_id());
            ban.put("desc", banner.getBan_description());
            ban.put("thumbnail", "http://http://localhost:8989/cmfz/banner-img/" + banner.getBan_cover());

            list.add(ban);
        }
        return list;
    }
}
