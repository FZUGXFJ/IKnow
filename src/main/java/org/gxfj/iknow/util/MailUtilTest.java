package org.gxfj.iknow.util;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MailUtilTest {

    @Autowired
    MailUtil mailUtil;

    @org.junit.Test
    public void sendMail() throws MessagingException {

        mailUtil.sendMail("2367463184@qq.com","Test","tom你要过生日了 祝你生日快乐 万寿无疆");
    }
}