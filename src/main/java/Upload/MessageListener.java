package Upload;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageListener {
    void onMessage(Message message);
}

