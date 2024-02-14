package org.example.cache;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoHolder {
    @Getter
    private static ThreadLocal<String> username = new ThreadLocal<>();
    @Getter
    private static ThreadLocal<String> chatId = new ThreadLocal<>();
    @Getter
    private static ThreadLocal<String> message = new ThreadLocal<>();

    public static void setUsername(String login) {
        username.set(login);
    }
    public static void setMessage(String mss) {
        message.set(mss);
    }
    public static void setChatId(String cid) {
        chatId.set(cid);
    }
}
