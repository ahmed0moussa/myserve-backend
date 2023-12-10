package com.myserv.api.rh.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.yml")
@Setter
public class EmailService {



    @Autowired
    private JavaMailSender javaMailSender;


    public String sendMail( String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("ahmed.mousa.20151@gmail.com");
            mimeMessageHelper.setTo(to);

            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body,true);



            javaMailSender.send(mimeMessage);

            return "mail send";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
