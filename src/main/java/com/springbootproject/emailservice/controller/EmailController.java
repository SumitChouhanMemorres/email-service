package com.springbootproject.emailservice.controller;

import com.springbootproject.emailservice.model.EmailDetails;
import com.springbootproject.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("sendMail")
    public String sendMail(@RequestBody EmailDetails details){
        return emailService.sendSimpleMail(details);
    }

    public String sendMailWithAttachment(@RequestBody EmailDetails details){
        return emailService.sendMailWithAttachment(details);
    }
}
