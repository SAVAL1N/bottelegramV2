package Check;

import Telegram.Command;
import Telegram.Mediator;
import Telegram.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CheckCommand implements Command {
    private final Mediator mediator;

    public CheckCommand(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void execute(Message message) {
        String chatId = message.getChatId().toString();

        SendMessage MessageTable = new SendMessage(chatId, ("Enter the data in the following format\n" + "------------------------------\n" +
                "|\t\t\tTP\t=\t?"+ "\t\t|\t\t\tFP\t=\t?" +"\t\t\t|" +
                "\n------------------------------\n" +
                "|\t\t\tFN\t=\t?"+ "\t|\t\t\tTN\t=\t?" + "\t\t\t|" +
                "\n------------------------------\n"
        ));

        mediator.sendMessage(MessageTable);

        mediator.setUserState(chatId, UserState.WAITING_FOR_TRUE_POSITIVE);
        mediator.sendMessage(new SendMessage(chatId, "Enter True positive (TP):"));

    }
}
