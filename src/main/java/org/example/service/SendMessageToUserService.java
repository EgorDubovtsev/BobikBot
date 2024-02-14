package org.example.service;

import org.example.cache.ChatIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class SendMessageToUserService {
    private static final String splitter = "/";
    @Autowired
    private ChatIdCache cache;
    public SendMessage generateAdminMessageToUser(String text) {
        String[] splittedMessage = text.split(splitter);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(splittedMessage[1]);
        sendMessage.setChatId(cache.getChatId(splittedMessage[0].trim()));


        return sendMessage;
    }
}
