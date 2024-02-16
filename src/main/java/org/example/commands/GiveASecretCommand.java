package org.example.commands;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.example.cache.ChatIdCache;
import org.example.cache.UserInfoHolder;
import org.example.dto.Stage;
import org.example.exceptions.CommandExecutionException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
@Component
@RequiredArgsConstructor
public class GiveASecretCommand implements Command{
    private static final String NAME ="/secret";
    private static final String DESCRIPTION ="Хотите в чем то признаться? Покаяться перед бобиком? Боб всегда выслушает...";
    private final ChatIdCache cache;

//    @SneakyThrows
    @Override
    public SendMessage execute() {
        cache.addCommandToCache(UserInfoHolder.getUsername().get(), this, 1);

        try {
            return executeNextStage(1);
        } catch (CommandExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage executeNextStage(Integer stageId) throws CommandExecutionException {
        SendMessage sendMessage = null;
        switch (stageId) {
            case 1: sendMessage = tellMeSecret();
            break;
            case 2: sendMessage = secretSaved();
            break;
            default: throw new CommandExecutionException("Шаг не обнаружен");
        }
        Stage stage = cache.getStage(UserInfoHolder.getUsername().get());
        cache.addCommandToCache(UserInfoHolder.getUsername().get(), this, stage.getStageId() + 1);
        return sendMessage;
    }

    private SendMessage tellMeSecret(){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Я вас слушаю... Рассказывай...");
        return sendMessage;
    }

    private SendMessage secretSaved(){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("О боже... Я никому не расскажу... Я даже не думал...");
        return sendMessage;
    }
}
