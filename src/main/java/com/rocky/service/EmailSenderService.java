package com.rocky.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${toEmailAddress}")
    private String toEmailAddress;

    @Value("${subject}")
    private String subject;

    @Value("${emailText}")
    private String emailText;

    @Value("${attachmentName}")
    private String attachmentName;

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 5000)
    )
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your.email@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }


    @Retryable(
            value = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public void sendEmailWithAttachment() throws MessagingException {

        MimeMessage msg = mailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(toEmailAddress);

        helper.setSubject(subject);

        // true = text/html
        helper.setText(emailText, true);

        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

        //Resource resource = new ClassPathResource("android.png");
        //InputStream input = resource.getInputStream();

        //ResourceUtils.getFile("classpath:android.png");

        helper.addAttachment(attachmentName, new ClassPathResource("android.png"));

        mailSender.send(msg);

    }
}