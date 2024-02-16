package org.example;

import org.example.bot.BobitBot;
import org.example.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;
@EnableAspectJAutoProxy
@SpringBootApplication
public class Main   {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}