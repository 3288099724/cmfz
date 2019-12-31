package com.bs.service;

import com.bs.dao.UserDAO;
import com.bs.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;


    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        User user = new User();
        RowBounds r = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDAO.selectByRowBounds(user, r);
        int i = userDAO.selectCount(user);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", users);
        map.put("total", i % rows == 0 ? i / rows : i / rows + 1);
        map.put("records", i);
        return map;
    }

    @Override
    public void update(User user) {
        System.out.println("update ---------S--" + user);
        int i = userDAO.updateByPrimaryKeySelective(user);
        if (i == 0) {
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public List<User> queryAll() {
        List<User> users = userDAO.selectAll();
        return users;
    }


    @Override
    public List<Integer> queryNum(String sex) {
        int t1 = userDAO.queryNum("1", sex).intValue();
        Integer i1 = t1;
        System.out.println(i1);
        int t2 = userDAO.queryNum("2", sex).intValue();
        Integer i2 = t2 - t1;
        System.out.println(i2);
        int t3 = userDAO.queryNum("3", sex).intValue();
        Integer i3 = t3 - t2;
        System.out.println(i3);
        int t4 = userDAO.queryNum("4", sex).intValue();
        Integer i4 = t4 - t3;
        System.out.println(i4);
        int t5 = userDAO.queryNum("5", sex).intValue();
        Integer i5 = t5 - t4;
        System.out.println(i5);
        int t6 = userDAO.queryNum("6", sex).intValue();
        Integer i6 = t6 - t5;
        System.out.println(i6);
        //
        List<Integer> list = new ArrayList<>();
        list.add(i6);
        list.add(i5);
        list.add(i4);
        list.add(i3);
        list.add(i2);
        list.add(i1);
        System.out.println("------------------");
        return list;
    }

    @Override
    public void login(User user, String enCode, HttpSession session) {
        System.out.println("encode:" + enCode);
        System.out.println("user:" + user);
        String realCode = (String) session.getAttribute("code");
        System.out.println("realCode:" + realCode);
        if (!realCode.equals(enCode.trim())) throw new RuntimeException("验证码错误");
        User a = new User();
        a.setUser_name(user.getUser_name());
        User one = userDAO.selectOne(a);

        if (one == null) throw new RuntimeException("用户名不存在");
        if (!one.getUser_password().equals(user.getUser_password())) throw new RuntimeException("密码错误");
        //
        session.setAttribute("loginUser", one);
        one.setUser_cdate(new Date());
        userDAO.updateByPrimaryKeySelective(one);
    }

    //-------------------------------------------------

    @Override
    public Map<String, Object> login(String phone, String password, String code) {
        Map<String, Object> map = new HashMap<>();

        if (phone == null) {
            map.put("error", "-200");
            map.put("errmsg", "手机号为空");
            return map;
        }
        if (password == null && code == null) {
            map.put("error", "-200");
            map.put("errmsg", "手机号和验证码都为空");
            return map;
        }
        User user = new User();

        if (code == null) { //说明用手机号，密码登录
            user.setUser_phone(phone);
            User one = userDAO.selectOne(user);
            if (phone.equals(one.getUser_phone())) {
                map.put("password", "after MD5：" + one.getUser_password());
                map.put("farmington", one.getUser_dharma());
                map.put("uid", one.getUser_id());
                map.put("nickname", one.getUser_name());
                map.put("gender", one.getUser_sex());
                map.put("photo", "http://http://localhost:8989/cmfz/user-img/" + one.getUser_photo());
                map.put("location", one.getUser_province() + " " + one.getUser_city());
                map.put("province", one.getUser_province());
                map.put("city", one.getUser_city());
                map.put("description", one.getUser_sign());
                map.put("phone", one.getUser_phone());
                return map;
            } else {
                map.put("error", "-200");
                map.put("errmsg", "密码错误");
                return map;
            }

        } else {   //说明用手机号，验证码登录

        }
        return map;
    }

    @Override
    public Map<String, Object> regist(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        User u = new User();
        u.setUser_password(password);
        u.setUser_phone(phone);
        User one = userDAO.selectOne(u);
        if (one != null) {
            map.put("error", "-200");
            map.put("error_msg", "该手机号已经存在");
            return map;
        } else {
            map.put("password", "after MD5：" + one.getUser_password());
            map.put("uid", one.getUser_id());
            map.put("phone", one.getUser_phone());
            return map;
        }

    }

    @Override
    public Map<String, Object> modify(String uid, String gender, String photo, String location, String description, String nickname, String province, String city, String password) {

        Map<String, Object> map = new HashMap<>();
        User u = new User();
        u.setUser_id(uid);
        u.setUser_sex(gender);
        u.setUser_photo(photo);
        u.setUser_province(province);
        u.setUser_city(city);
        u.setUser_sign(description);
        u.setUser_name(nickname);
        u.setUser_password(password);

        User one = userDAO.selectOne(u);   //查询用户全部信息
        try {
            userDAO.updateByPrimaryKeySelective(u);
            map.put("password", "after MD5：" + password);
            map.put("farmington", one.getUser_dharma());
            map.put("uid", uid);
            map.put("nickname", nickname);
            map.put("gender", gender);
            map.put("photo", "" + photo);
            map.put("location", province + " " + city);
            map.put("province", province);
            map.put("city", city);
            map.put("description", description);
            map.put("phone", one.getUser_phone());
            return map;
        } catch (Exception e) {
            map.put("error", "-200");
            map.put("error_msg", "修改失败");
            return map;
        }
    }

    @Override
    public List<Map<String, Object>> member(String uid) {
        List<Map<String, Object>> list = new ArrayList<>();

        User user = userDAO.selectByPrimaryKey(uid);
        Example e = new Example(User.class);
        e.createCriteria().andEqualTo("user_father", user.getUser_father());
        List<User> userList = userDAO.selectByExample(e);


        for (User one : userList) {
            Map<String, Object> map = new HashMap<>();
            map.put("farmington", one.getUser_dharma());
            map.put("nickname", one.getUser_name());
            map.put("gender", one.getUser_sex());
            map.put("photo", "http://http://localhost:8989/cmfz/user-img/" + one.getUser_photo());
            map.put("location", one.getUser_province() + " " + one.getUser_city());
            map.put("province", one.getUser_province());
            map.put("city", one.getUser_city());
            map.put("description", one.getUser_sign());
            map.put("phone", one.getUser_phone());
            list.add(map);
        }
        return list;
    }
}
