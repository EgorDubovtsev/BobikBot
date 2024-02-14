package org.example.service;

import org.example.aspect.annotation.LoggingUserMessage;
import org.example.cache.ChatIdCache;
import org.example.cache.UserInfoHolder;
import org.example.commands.Command;
import org.example.dto.Stage;
import org.example.exceptions.CommandExecutionException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.consts.Const.COMMAND_PREFIX;

@Service
public class StarterServiceImpl implements StarterService {
    private final Map<String, Command> comamds = new HashMap<>();
    private ChatIdCache cache;

    public StarterServiceImpl(List<Command> commandList, ChatIdCache cache) {
        for (Command command : commandList) {
            comamds.put(command.getName(), command);
        }
        this.cache = cache;
    }

    @Override
    @LoggingUserMessage
    public SendMessage startExecution(String message) {
        try {

            if (cache.isUserWaitAnswer(UserInfoHolder.getUsername().get())) {
                Stage stage = cache.getStage(UserInfoHolder.getUsername().get());
                return stage.getCommand().executeNextStage(stage.getStageId());

            }
        } catch (CommandExecutionException e) {
            System.out.println(e.getMessage());
            cache.userGotAnswer(UserInfoHolder.getUsername().get());
        }
        if (message.startsWith(COMMAND_PREFIX)) {
            return comamds.get(message).execute();
        }
        return comamds.get("/help").execute();
    }
}
