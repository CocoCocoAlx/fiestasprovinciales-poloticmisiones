package com.polotic.FiestasProvinciales.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorreoServicio {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Async
    public void enviarCorreoSimple(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage correo = new SimpleMailMessage();

        correo.setFrom(remitente);
        correo.setTo(destinatario);
        correo.setSubject(asunto);
        correo.setText(cuerpo);

        mailSender.send(correo);
    }

    @Async
    public void enviarCorreoConAdjunto(String destinatario, String asunto, String cuerpo, String[] adjuntos)
    {
        MimeMessage correo = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(correo, true);
            
            mimeMessageHelper.setFrom(remitente);
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject(asunto);
            mimeMessageHelper.setText(cuerpo);

            for (String a: adjuntos)
            {
                File f = ResourceUtils.getFile(a);
                mimeMessageHelper.addAttachment(f.getName(), f);
            }

            mailSender.send(correo);
        } catch (MessagingException err) {
            err.printStackTrace();
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }

        }

        public void enviarMailHtml(String destinatario, String asunto, String plantilla,
        Map<String, Object> valores) throws MessagingException {

        MimeMessage correo = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(correo, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(valores);
        helper.setFrom(remitente);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        String html = springTemplateEngine.process(plantilla, context);
        helper.setText(html, true);
        mailSender.send(correo);
    }

}
