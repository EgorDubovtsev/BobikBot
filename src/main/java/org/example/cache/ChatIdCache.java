package org.example.cache;

import org.example.commands.Command;
import org.example.commands.HelloCommand;
import org.example.dto.Stage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ChatIdCache {
    private final HashMap<String, Long> chatIdsByUsername = new HashMap<>();
    private final ConcurrentMap<String, Stage> userAndLastCommand = new ConcurrentHashMap<>();

    public void addChatToCache(String username, Long chatId) {
        chatIdsByUsername.put(username, chatId);
    }

    public Long getChatId(String username) {
        return chatIdsByUsername.get(username);
    }

    public void addCommandToCache(String username, Command currentCommand, Integer stage) {
        userAndLastCommand.put(username, new Stage(currentCommand, stage));
    }

    public void userGotAnswer(String login){
        userAndLastCommand.remove(login);
    }

    public Stage getStage(String username) {
        return  userAndLastCommand.get(username);
    }

    public synchronized boolean isUserWaitAnswer(String login) {
        return userAndLastCommand.get(login) != null;
    }



}
