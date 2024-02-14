package org.example.threads;

import lombok.AllArgsConstructor;
import org.example.cache.UserInfoHolder;
import org.example.service.StarterService;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class ExecutionThread {
    private Update update;
    private StarterService starterService;


    public void run() {


    }
}
