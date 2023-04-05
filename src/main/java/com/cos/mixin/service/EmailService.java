package com.cos.mixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cos.mixin.dto.mail.MailDto;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(MailDto mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getAddress());
        message.setSubject(mail.getTitle());
        message.setText(mail.getContent());
        mailSender.send(message);
    }
}
