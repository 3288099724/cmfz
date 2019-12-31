package com.bs.service;

import com.bs.dao.AdminDAO;
import com.bs.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public void login(Admin admin, String enCode, HttpSession session) {
        System.out.println("encode:" + enCode);
        System.out.println("admin:" + admin);
        String realCode = (String) session.getAttribute("code");
        System.out.println("realCode:" + realCode);
        if (!realCode.equals(enCode.trim())) throw new RuntimeException("验证码错误");
        Admin a = new Admin();
        a.setAd_name(admin.getAd_name());
        Admin one = adminDAO.selectOne(a);
        if (one == null) throw new RuntimeException("用户名不存在");
        if (!one.getAd_password().equals(admin.getAd_password())) throw new RuntimeException("密码错误");
        //
        session.setAttribute("loginAdmin", admin);
        /*if(realCode.equals(enCode)){
            Example e = new Example(Admin.class);
            e.createCriteria().andEqualTo("ad_name","admin").andEqualTo("ad_password","admin");
            List<Admin> admins = adminDAO.selectByExample(e);
            System.out.println("+++++++"+admins.size());
            if(admins.size()!=0){
               return true;
           }
        }
        return false;*/
    }

}
