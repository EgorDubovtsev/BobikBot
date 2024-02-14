package org.example.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendMessageService {
    void sendMessageFromBot(SendMessage sendMessage);
}
