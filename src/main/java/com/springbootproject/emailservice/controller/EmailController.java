package com.springbootproject.emailservice.controller;

import com.springbootproject.emailservice.model.EmailDetails;
import com.springbootproject.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

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

    @PostMapping("send-html-email")
    public String sendHtmlEmail(@RequestBody EmailDetails details){
        Context context = new Context();
        context.setVariable("message", details.getMsgBody());
        return emailService.sendEmailWithHtmlTemplate(details.getRecipient(), details.getSubject(), "email-template", context);
    }
}
