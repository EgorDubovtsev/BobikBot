package org.example.service;

import lombok.AllArgsConstructor;
import org.example.bot.BobitBot;
import org.example.cache.UserInfoHolder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {


    @Override
    public void sendMessageFromBot(SendMessage sendMessage) {

    }
}
