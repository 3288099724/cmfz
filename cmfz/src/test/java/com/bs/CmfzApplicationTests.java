package com.bs;

import com.bs.dao.AdminDAO;
import com.bs.entity.Admin;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {


    @Autowired
    private AdminDAO adminDAO;

    @Test
    public void contextLoads() {
        List<Admin> adminList = adminDAO.selectAll();
        adminList.forEach(admin -> System.out.println(admin));
    }

    @Test
    public void test2() {
        Admin admin = new Admin();
        admin.setAd_name("admin");
        List<Admin> adminList = adminDAO.select(admin);
        adminList.forEach(admin1 -> System.out.println(admin1));
    }

    @Test
    public void test3() {
        Example e = new Example(Admin.class);
        e.createCriteria().andEqualTo("ad_name", "admin").andEqualTo("ad_password", "admin");
        List<Admin> adminList = adminDAO.selectByExample(e);
        adminList.forEach(admin1 -> System.out.println(admin1));
    }

    @Test
    public void test4() {
        int page = 2;
        int rows = 2;
        Admin a = new Admin();
        RowBounds r = new RowBounds((page - 1) * rows, rows);
        List<Admin> adminList = adminDAO.selectByRowBounds(a, r);
        adminList.forEach(admin1 -> System.out.println(admin1));
    }

    @Test
    public void test5() {
        Admin a = new Admin();
        a.setAd_id("10");
        a.setAd_name("aaa");
        a.setAd_password("bbb");
        a.setAd_nickname("heihei");
        int i = adminDAO.insert(a);
        System.out.println(i);
    }

    @Test
    public void test6() {
        Admin a = new Admin();
        a.setAd_id("10");
        a.setAd_name("aabb");
        a.setAd_password("bbcc");
        a.setAd_nickname("heihei");
        int i = adminDAO.updateByPrimaryKey(a);
        System.out.println(i);
    }

    @Test
    public void test7() {
        Admin a = new Admin();
        a.setAd_id("10");
        a.setAd_name("aa");
        a.setAd_password("bb");
        int i = adminDAO.updateByPrimaryKeySelective(a);
        System.out.println(i);
    }

    @Test
    public void test8() {
        Admin a = new Admin();
        a.setAd_id("10");
        a.setAd_name("aa");
        a.setAd_password("bb");
        int i = adminDAO.delete(a);
        System.out.println(i);
    }
}
