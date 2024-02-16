package org.example.util;

import org.example.commands.Command;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class CommandsUtil {
    public static ReplyKeyboardMarkup getDefaultRows() {
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton("/help");
        keyboardRow.add(keyboardButton);
        keyboardRowList.add(keyboardRow);
        return new ReplyKeyboardMarkup(keyboardRowList);

    }
}
