package org.example.commands;

import org.example.exceptions.CommandExecutionException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface Command {
     SendMessage execute();

     String getName();
     String getDescription();

    default SendMessage executeNextStage(Integer stageId) throws CommandExecutionException {
         return null;
    };
}
