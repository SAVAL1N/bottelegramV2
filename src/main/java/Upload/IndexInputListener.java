package Upload;

import Excel.ExcelData;
import Excel.ExcelDataCall;
import Telegram.Mediator;
import Telegram.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class IndexInputListener implements MessageListener {
    private final Mediator mediator;
    private final List<ExcelData> excelDataList;
    private final String chatId;
    private final ExcelDataCall excelDataCall;

    public IndexInputListener(Mediator mediator, List<ExcelData> excelDataList, String chatId, ExcelDataCall excelDataCall) {
        this.mediator = mediator;
        this.excelDataList = excelDataList;
        this.chatId = chatId;
        this.excelDataCall = excelDataCall;
    }

    @Override
    public void onMessage(Message message) {
        if (message.getChatId().toString().equals(chatId)) {
            String text = message.getText();
            if (text.matches("\\d+")) {
                try {
                    int index = Integer.parseInt(text);
                    System.out.println(index);

                    int[] results = excelDataCall.calculateAndPrintResults(excelDataList, index);

                    int TP = results[0];
                    int FP = results[1];
                    int FN = results[2];
                    int TN = results[3];
                    int p = results[4];
                    int n = results[5];
                    SendMessage messageTable = new SendMessage(chatId, ("------------------------------\n" +
                            "|\tTP\t=\t"+ TP + "\t|\t\tFP\t=\t" + FP + "\t\t\t|" +
                            "\n------------------------------\n" +
                            "|\tFN\t=\\t"+ FN + "\t|\tTN\t=\t" + TN + "|" +
                            "\n------------------------------\n"
                    ));
                    mediator.sendMessage(messageTable);

                    String metricsMessage = mediator.generateMetricsMessage(chatId);
                    SendMessage askFor = new SendMessage(chatId, metricsMessage);
                    mediator.sendMessage(askFor);

                    mediator.setUserState(chatId, UserState.WAITING_FOR_METRICS);

                    StatCalculator statCalculator = new StatCalculator(TP, FP, FN, TN, p, n);
                    UserInputListener userInputListener = new UserInputListener(mediator, statCalculator, chatId);
                    mediator.addMessageListener(userInputListener);

                } catch (NumberFormatException e) {
                    SendMessage errorMessage = new SendMessage(chatId, "Invalid index format. Please enter an integer.");
                    mediator.sendMessage(errorMessage);
                }
                mediator.removeMessageListener(this);
            } else {
                SendMessage errorMessage = new SendMessage(chatId, "Invalid input format. Please enter an integer.");
                mediator.sendMessage(errorMessage);
            }
        }
    }
}
