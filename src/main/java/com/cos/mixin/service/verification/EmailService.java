package com.cos.mixin.service.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cos.mixin.dto.verification.VerificationReqDto.MailReqDto;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendMail(MailReqDto mailReqDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailReqDto.getUserEmail());
        message.setSubject(mailReqDto.getTitle());
        message.setText(mailReqDto.getContent());
        mailSender.send(message);
        return "메일 전송 완료";
    }
}
