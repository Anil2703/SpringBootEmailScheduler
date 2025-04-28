package com.rocky.scheduler;

import com.rocky.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ScheduledEmailTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledEmailTask.class);

    @Autowired
    private EmailSenderService emailSenderService;

    private final int EMAIL_FREQUENCY = 10000; //10 Seconds

    //@Scheduled(fixedRate = 3600000)
    @Scheduled(fixedRate = EMAIL_FREQUENCY)
    public void sendReminderEmail() {
        try {

            //emailSenderService.sendEmail(
            //"recipient.email@example.com",
            //"Friendly Reminder",
            //"Hey! This is your hourly reminder email."
            //);

            emailSenderService.sendEmailWithAttachment();
            log.info("Scheduled email sent successfully!");
        } catch (Exception e) {
            log.error("Error sending scheduled email: {}", e.getMessage(), e);
        }
    }
}