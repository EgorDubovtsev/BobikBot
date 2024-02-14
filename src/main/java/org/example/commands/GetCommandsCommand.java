package org.example.commands;

import lombok.RequiredArgsConstructor;
import org.example.service.SendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCommandsCommand implements Command {
    private static final String NAME = "/help";
    private final static String DESCRIPTION = "Список комманд";

    private final List<Command> commandList;
    @Override
    public SendMessage execute() {
        StringBuilder sb = new StringBuilder();
        sb.append("Что умеет бобик:\n");
        for (Command command : commandList) {
            sb.append(command.getName())
                    .append(" ")
                    .append(command.getDescription())
                    .append("\n");
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(sb.toString());

        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup(getRows()));
        return sendMessage;
    }

    private List<KeyboardRow> getRows(){
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        for (Command command : commandList) {
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton(command.getName());
            keyboardRow.add(keyboardButton);
            keyboardRowList.add(keyboardRow);
        }

        return keyboardRowList;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
