package com.bs.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController

@RequestMapping("echarts")
public class EchartsController {


    @RequestMapping("getAll")
    public List<Integer> getAll() {
        List<Integer> list = new ArrayList<>();

        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        list.add(new Random().nextInt(100));
        return list;
    }

}
