package com.bs;

import com.bs.dao.ChapterDAO;
import com.bs.entity.Chapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ChapterTest {
    @Autowired
    private ChapterDAO chapterDAO;

    @Test
    public void test() {
        Chapter chapter = new Chapter();
        chapter.setCh_id("a4bac16b-4b15-4ece-a328-0641631a7932");
        Chapter chapter1 = chapterDAO.selectOne(chapter);
        System.out.println(chapter1);

        //
        Chapter chapter2 = chapterDAO.selectByPrimaryKey(chapter.getCh_id());
        System.out.println(chapter2);
    }
}
