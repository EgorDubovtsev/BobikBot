package org.example.bot;

import lombok.RequiredArgsConstructor;
import org.example.aspect.annotation.LoggingUserMessage;
import org.example.cache.ChatIdCache;
import org.example.cache.UserInfoHolder;
import org.example.service.StarterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class BobitBot extends TelegramLongPollingBot {
    @Value("${bot.bobik.owner}")
    private String ownerLogin;

    @Value("${bot.bobik.username}")
    private String username;

    @Value("${bot.bobik.token}")
    private String token;

    private final static ExecutorService threadPoll = Executors.newFixedThreadPool(10);
    private final StarterService starterService;
    private final ChatIdCache cache;

    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        System.out.println(this);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Runnable thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getId());

            String username = update.getMessage().getFrom().getUserName();
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            cache.addChatToCache(username, chatId);
            UserInfoHolder.setUsername(username);
            UserInfoHolder.setChatId(String.valueOf(chatId));
            UserInfoHolder.setMessage(message);

            SendMessage response = starterService.startExecution(message);
            response.setChatId(chatId);
            try {
                sendMessageFromBot(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        threadPoll.execute(thread);


    }

    public void sendMessageFromBot(SendMessage sendMessage) {
        System.out.println("INSIDE " + UserInfoHolder.getUsername().get());
        if (sendMessage == null || sendMessage.getText() == null) {
            return;
        }
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
