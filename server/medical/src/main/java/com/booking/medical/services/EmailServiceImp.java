package com.booking.medical.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void SendVerifyCode(String ToEmail, String UserName, String VerifyCode) throws Exception {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                messageHelper.setTo(ToEmail);
                messageHelper.setSubject("MedBooking - Mã xác minh");
                //
                Context context = new Context();
                context.setVariable("UserName", UserName);
                context.setVariable("ToEmail", ToEmail);
                context.setVariable("VerifyCode", VerifyCode);

                String content = templateEngine.process("VerifyTemplate", context);
                //
                messageHelper.setText(content, true);
            }
        };
        javaMailSender.send(preparator);
    }

}
