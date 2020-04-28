package org.gxfj.iknow.action;

import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class QuestionDAOTest {
    @Autowired
    CommentAction commentAction;
    @Autowired
    CommentService commentService;

    @Test
    public void add() {
    }

    @Test
    public void get() {

    }

    @Test
    public void testGet() {
    }

    @Test
    public void update() {
    }

    @Test
    public void postComment(){

    }
}
