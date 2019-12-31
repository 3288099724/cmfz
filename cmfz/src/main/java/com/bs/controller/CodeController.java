package com.bs.controller;

import com.bs.util.SecurityCode;
import com.bs.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class CodeController {

    @RequestMapping("/getCode")
    public void getCode(HttpSession session, HttpServletResponse response) {
        String code = SecurityCode.getSecurityCode();

        session.setAttribute("code", code);
        //根据验证码生成图片
        BufferedImage img = SecurityImage.createImage(code);

        OutputStream oStream = null;
        try {
            oStream = response.getOutputStream();
            ImageIO.write(img, "png", oStream);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
