package org.example.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface StarterService {
    SendMessage startExecution(String message);
}
