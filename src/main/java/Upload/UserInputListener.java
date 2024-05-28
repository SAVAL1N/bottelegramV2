package Upload;

import Calc.StatCalculator;
import Telegram.Mediator;
import Telegram.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UserInputListener implements MessageListener {
    private final Mediator mediator;
    private final StatCalculator statCalculator;
    private final String chatId;

    public UserInputListener(Mediator mediator, StatCalculator statCalculator, String chatId) {
        this.mediator = mediator;
        this.statCalculator = statCalculator;
        this.chatId = chatId;
    }

    @Override
    public void onMessage(Message message) {
        if (message.getChatId().toString().equals(chatId)) {
            String userInput = message.getText();
            String[] metricIds = userInput.split("\\s+");
            StringBuilder resultBuilder = new StringBuilder();

            for (String metricId : metricIds) {
                String result;
                if (metricId.equals("24")) {
                    result = statCalculator.calculateAllStats();
                    resultBuilder.append(result).append("");
                    break;
                } else {
                    result = statCalculator.calculateStats(metricId);
                    resultBuilder.append(result).append("\n");
                }
            }

            SendMessage sendMessage = new SendMessage(chatId, resultBuilder.toString());
            mediator.sendMessage(sendMessage);

            mediator.setUserState(chatId, UserState.WAITING_FOR_INDEX);

            mediator.removeMessageListener(this);
        }
    }
}
