package Telegram;

import Calc.CalculationType;
import Calc.StatCalculator;
import Excel.ExcelDataCall;
import Excel.ExcelDataInput;
import Json.JsonDataInput;
import Upload.*;
import Check.CheckCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class Mediator {
    private final Map<String, UserState> userStateMap;
    private final Map<String, Command> commandMap;
    final TelegramBot bot;
    private final ExcelDataInput excelDataInput;
    private final JsonDataInput jsonDataInput;
    private final Map<String, UserData> userDataMap;

    private Set<MessageListener> messageListeners = new HashSet<>();

    public Mediator(TelegramBot bot, ExcelDataInput excelDataInput, JsonDataInput jsonDataInput) {
        this.bot = bot;
        this.excelDataInput = excelDataInput;
        this.jsonDataInput = jsonDataInput;
        userStateMap = new HashMap<>();
        commandMap = new HashMap<>();
        userDataMap = new HashMap<>();
        initCommands();
    }

    private void initCommands() {
        commandMap.put("/check", new CheckCommand(this));
        commandMap.put("/upload", new UploadCommand(this, excelDataInput, jsonDataInput, new ExcelDataCall()));
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

    public void processIncomingMessage(Message message) {
        for (MessageListener listener : messageListeners) {
            listener.onMessage(message);
        }
    }

    public TelegramBot getBot() {
        return bot;
    }

    public void processUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String chatId = message.getChatId().toString();
            String text = message.getText();

            if (text.startsWith("/")) {
                Command command = commandMap.get(text);
                if (command != null) {
                    command.execute(message);
                }
            } else {
                UserState userState = userStateMap.get(chatId);
                if (userState != null) {
                    processInput(message, chatId, text, userState);
                } else {
                    SendMessage errorMessage = new SendMessage(chatId, "Пожалуйста, введите команду или загрузите файл.");
                    sendMessage(errorMessage);
                }
            }
        }
    }

    private void processInput(Message message, String chatId, String inputText, UserState userState) {
        if (inputText.startsWith("/")) {
            userDataMap.remove(chatId);
            userStateMap.remove(chatId);

            Command command = commandMap.get(inputText);
            if (command != null) {
                command.execute(message);
            }
            return;
        }

        double value;
        try {
            value = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            String errorText = "Неверный ввод. Введите номер.";
            sendMessage(new SendMessage(chatId, errorText));
            return;
        }

        UserData userData;

        if (!userDataMap.containsKey(chatId)) {
            userData = new UserData(value, 0, 0, 0);
            userDataMap.put(chatId, userData);
        } else {
            userData = userDataMap.get(chatId);
        }

        switch (userState) {
            case WAITING_FOR_TRUE_POSITIVE:
                userData.setTruePositive(value);
                userStateMap.put(chatId, UserState.WAITING_FOR_FALSE_POSITIVE);
                sendMessage(new SendMessage(chatId, userData.getTableAsString()));
                sendMessage(new SendMessage(chatId, "Введите количество ложно положительных результатов (FP):"));
                break;
            case WAITING_FOR_FALSE_POSITIVE:
                userData.setFalsePositive(value);
                userStateMap.put(chatId, UserState.WAITING_FOR_FALSE_NEGATIVE);
                sendMessage(new SendMessage(chatId, userData.getTableAsString()));
                sendMessage(new SendMessage(chatId, "Введите количество ложно отрицательных результатов (FN):"));
                break;
            case WAITING_FOR_FALSE_NEGATIVE:
                userData.setFalseNegative(value);
                userStateMap.put(chatId, UserState.WAITING_FOR_TRUE_NEGATIVE);
                sendMessage(new SendMessage(chatId, userData.getTableAsString()));
                sendMessage(new SendMessage(chatId, "Введите количество истинно отрицательных результатов (TN):"));
                break;
            case WAITING_FOR_TRUE_NEGATIVE:
                userData.setTrueNegative(value);
                sendMessage(new SendMessage(chatId, userData.getTableAsString()));
                userStateMap.put(chatId, UserState.WAITING_FOR_METRIC_INPUT);
                sendMessage(new SendMessage(chatId, generateMetricsMessage(chatId)));
                break;
            case WAITING_FOR_METRIC_INPUT:
                String[] metricIds = inputText.split("\\s+");
                StringBuilder resultBuilder = new StringBuilder();

                double TP = userData.getTruePositive();
                double FP = userData.getFalsePositive();
                double FN = userData.getFalseNegative();
                double TN = userData.getTrueNegative();
                double p = TP + FN;
                double n = FP + TN;

                StatCalculator statCalculator = new StatCalculator(TP, FP, FN, TN, p, n);

                for (String metricId : metricIds) {
                    if (metricId.equals("24")) {
                        resultBuilder.append(statCalculator.calculateAllStats()).append("\n");
                        break;
                    } else {
                        resultBuilder.append(statCalculator.calculateStats(metricId)).append("\n");
                    }
                }

                sendMessage(new SendMessage(chatId, resultBuilder.toString()));
                userStateMap.put(chatId, UserState.WAITING_FOR_INDEX);
                userDataMap.remove(chatId);
                break;
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setUserState(String chatId, UserState state) {
        userStateMap.put(chatId, state);
    }

    public String generateMetricsMessage(String chatId) {
        StringBuilder messageBuilder = new StringBuilder("Указываем какие расчеты нам нужны (можно через пробел):\n");
        for (CalculationType type : CalculationType.values()) {
            messageBuilder.append(type.getId()).append(" - ").append(type.getName()).append("\n");
        }
        messageBuilder.append("24 - Вывести все\n");
        return messageBuilder.toString();
    }

    public void showMenu(String chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("/check");
        row.add("/upload");
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);

        SendMessage sendMessage = new SendMessage(chatId, "Выберите команду:");
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        sendMessage(sendMessage);
    }
}
