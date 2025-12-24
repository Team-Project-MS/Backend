package com.evonX.demo.service;

import com.evonX.demo.entity.Event;
import com.evonX.demo.entity.User;
import com.evonX.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public void sendNewEventNotification(Event event) {
        List<User> subscribers = userRepository.findBySubscribedTrue();
        for (User user : subscribers) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("New Event: " + event.getTitle());
            message.setText(getEventDetails(event));
            mailSender.send(message);
        }
    }

    public void sendUpdateEventNotification(Event event) {
        List<User> subscribers = userRepository.findBySubscribedTrue();
        for (User user : subscribers) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Updated Event: " + event.getTitle());
            message.setText(getEventDetails(event) + "\n\nThis event has been updated.");
            mailSender.send(message);
        }
    }

    private String getEventDetails(Event event) {
        return "Title: " + event.getTitle() + "\n" +
                "Description: " + event.getDescription() + "\n" +
                "Category: " + event.getCategory() + "\n" +
                "University: " + event.getUniversity() + "\n" +
                "Type: " + event.getEventType() + "\n" +
                "Date: " + event.getEventDate() + "\n" +
                "Time: " + event.getEventTime() + "\n" +
                (event.getMeetingLink() != null ? "Meeting Link: " + event.getMeetingLink() + "\n" : "");
    }
}
