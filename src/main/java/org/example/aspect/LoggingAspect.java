package org.example.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.bot.BobitBot;
import org.example.cache.ChatIdCache;
import org.example.cache.UserInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final BobitBot bobitBot;
    private final ChatIdCache cache;
    @Value("${bot.logging.toAdmin}")
    private Boolean loggingEnabled;

    @Value("${bot.bobik.owner}")
    private String ownerLogin;

    @Around("@annotation(org.example.aspect.annotation.LoggingUserMessage)")
    private Object logMessageToAdmin(ProceedingJoinPoint call) throws Throwable {
        if (loggingEnabled) {
            Long chatId = cache.getChatId(ownerLogin);
            if (chatId == null) {
                return null;
            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Пользователь " + UserInfoHolder.getUsername().get() + " написал сообщение: " + UserInfoHolder.getMessage().get());
            sendMessage.setChatId(chatId);

            System.out.println(Arrays.toString(call.getArgs()));
            bobitBot.sendMessageFromBot(sendMessage);
        }
        return call.proceed();
    }
}
