package com.signore.hater.Service;

public interface MailSender {
    void send(String emailTo, String subject, String message);
}
