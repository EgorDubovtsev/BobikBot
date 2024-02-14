package org.example.commands;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.checkerframework.checker.units.qual.C;
import org.example.cache.ChatIdCache;
import org.example.cache.UserInfoHolder;
import org.example.entity.Question;
import org.example.exceptions.CommandExecutionException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class PollActualQuestions implements Command{
    private final List<Question> questions;
    private final ChatIdCache cache;
    private final String NAME = "/actualQuestions";
    private final String DESCRIPTION = "Помочь бобику решить насущные вопросы.";
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
        String user = UserInfoHolder.getUsername().get();
        if (stageId > questions.size() -1) {
            return getFinishMessage(user);
        }
        Question question = questions.get(stageId);
        List<KeyboardRow> answerButtons = getRows(question.getAnswers());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup(answerButtons));
        sendMessage.setText(question.getQuestion());
        cache.addCommandToCache(user, this, stageId+ 1);
        return sendMessage;
    }

    private List<KeyboardRow> getRows(List<String> answers){
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        for (String answ : answers) {
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton(answ);
            keyboardRow.add(keyboardButton);
            keyboardRowList.add(keyboardRow);
        }

        return keyboardRowList;
    }

    private SendMessage getFinishMessage(String user){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Спасибо, все запомнил!");
        sendMessage.setReplyMarkup(null);
        cache.userGotAnswer(user);
        return sendMessage;
    }
}
