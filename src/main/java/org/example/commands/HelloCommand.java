package org.example.commands;

import lombok.AllArgsConstructor;
import org.example.cache.UserInfoHolder;
import org.example.service.SendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@AllArgsConstructor
public class HelloCommand implements Command {
    private final static String COMMAND_NAME = "/hello";
    private final static String DESCRIPTION = "Получить приветствие";
    @Override
    public SendMessage execute() {
        System.out.println("Thread "+ Thread.currentThread().getId() + " " + UserInfoHolder.getUsername().get());
        return new SendMessage(UserInfoHolder.getChatId().get(), "Hello from Bob");
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
