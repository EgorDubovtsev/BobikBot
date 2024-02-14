package org.example.functions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SendMessageParam  implements LoggingFunctionParam {
    private SendMessage sendMessage;

    public SendMessageParam(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public SendMessageParam() {
    }

    @Override
    public Object getParam() {
        return sendMessage;
    }
}
