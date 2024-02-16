package org.example.commands;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.cache.ChatIdCache;
import org.example.cache.UserInfoHolder;
import org.example.exceptions.CommandExecutionException;
import org.example.util.CommandsUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AMENA
 */
@Component
@RequiredArgsConstructor
public class ClubCommand implements Command {
    private static final String NAME = "/club";
    private static final String DESCRIPTION = "Личный вопрос от Бобика..";
    private static final String YES_ANSWER = "Да, я! :)";
    private static final String NO_ANSWER = "Нет..";
    private static final List<String> ANSWERS = Arrays.asList(YES_ANSWER, NO_ANSWER);
    private final ChatIdCache cache;

    @SneakyThrows
    @Override
    public SendMessage execute() {
        return executeNextStage(0);
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
        cache.addCommandToCache(UserInfoHolder.getUsername().get(), this, stageId + 1);
        switch (stageId) {
            case 0:
                return getFirstMessage();
            case 1:
                return getAnswer();
            default:
                throw new CommandExecutionException("Шаг не найден");
        }
    }

    private SendMessage getAnswer() {
        String message = UserInfoHolder.getMessage().get();
        SendMessage sendMessage = new SendMessage();
        if (message.equals(YES_ANSWER)) {
            sendMessage.setText("ПОПАЛСЯ!!! Ты нарушил первое правило клуба!");
        } else {
            sendMessage.setText("Молодец, мой друг! Ты прошел проверку. Всегда помни правило Бобиковского клуба!");
        }
        sendMessage.setReplyMarkup(CommandsUtil.getDefaultRows());
        return sendMessage;
    }

    private SendMessage getFirstMessage() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ты участник Бобиковского клуба?");
        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup(getRows(ANSWERS)));
        return sendMessage;
    }

    private List<KeyboardRow> getRows(List<String> answers) {
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        for (String answ : answers) {
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton(answ);
            keyboardRow.add(keyboardButton);
            keyboardRowList.add(keyboardRow);
        }

        return keyboardRowList;
    }


}
