package com.springbootproject.emailservice.service;

import com.springbootproject.emailservice.model.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}") private String sender;
    public String sendSimpleMail(EmailDetails details){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully";
        }catch(Exception e){
            return "Error While Sending Mail";
        }
    }

    public String sendMailWithAttachment(EmailDetails details){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            FileSystemResource file = new FileSystemResource(details.getAttachment());
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            javaMailSender.send(mimeMessage);
            return "Mail with Attachment sent succesfully";
        }catch(Exception e){
            return "Error While Sending Mail!!";
        }
    }

    public String sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try{
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            String htmlContent = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
            return "Mail Sent successfully with html page";
        }catch(MessagingException e){
            return "Error While Sending Mail!!";
        }
    }
}
