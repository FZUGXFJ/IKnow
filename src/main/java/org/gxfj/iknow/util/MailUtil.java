package org.gxfj.iknow.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Component
public class MailUtil {

    @Value("${mail.userName}")
    private String userName;
    @Value("${mail.password}")
    private String password;


    public void sendMail(String email, String subject, String content)
            throws AddressException, MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session

        Properties props = new Properties();
        //设置发送的协议
        //普通SMTP
        props.setProperty("mail.transport.protocol", "SMTP");
        //设置发送邮件的服务器
//        props.setProperty("mail.host", "localhost");//本地
//        props.setProperty("mail.host", "smtp.qq.com"); //// 设置QQ邮件服务器
        //163邮箱
        props.setProperty("mail.host", "smtp.163.com");
        // 指定验证为true
        props.setProperty("mail.smtp.auth", "true");

        // 创建验证器
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);

        //TODO: debug
        System.out.println(userName + "test");
        //设置发送者
        message.setFrom(new InternetAddress(userName));

        //设置发送方式与接收者
        message.setRecipient(RecipientType.TO, new InternetAddress(email));
        message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(userName));

        //设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }

}
