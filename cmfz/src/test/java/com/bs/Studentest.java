package com.bs;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.bs.entity.Student;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

public class Studentest {


    @Test
    public void test() throws IOException {
        List<Student> list = new ArrayList<>();
        list.add(new Student("1", "小黑", 18, new Date()));
        list.add(new Student("2", "小花", 29, new Date()));
        list.add(new Student("3", "小熊", 88, new Date()));

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("182学生", "学生"),
                Student.class, list);
        workbook.write(new FileOutputStream("D:/a.xls"));
    }
}
