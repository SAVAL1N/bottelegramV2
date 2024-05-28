package Telegram;

import Excel.ExcelDataInput;
import Json.JsonDataInput;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    private final Mediator mediator;
    private final String botToken;
    private final String botUsername;

    public TelegramBot(String botToken, String botUsername, ExcelDataInput excelDataInput, JsonDataInput jsonDataInput) {
        super(botToken); // Используем новый конструктор
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.mediator = new Mediator(this, excelDataInput, jsonDataInput);
        setBotCommands();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String messageText = message.getText();
                long chatId = message.getChatId();
                if (messageText.equals("/start")) {
                    SendMessage welcomeMessage = new SendMessage();
                    welcomeMessage.setChatId(chatId);
                    welcomeMessage.setText("Hello! I am a bot for calculating sensitivity and specificity. Please select one of the commands on the panel\n" +
                            "/check - calculator based on your values \n" +
                            "/upload - upload an Excel or JSON file with data");

                    mediator.showMenu(String.valueOf(chatId));

                    try {
                        execute(welcomeMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (messageText.equals("/check")) {
                    Command command = mediator.getCommandMap().get("/check");
                    if (command != null) {
                        command.execute(message);
                    }
                } else {
                    mediator.processUpdate(update);
                    mediator.processIncomingMessage(message);
                }
            } else if (message.hasDocument()) {
                Command command = mediator.getCommandMap().get("/upload");
                if (command != null) {
                    command.execute(message);
                }
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    private void setBotCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "Start bot"));
        commands.add(new BotCommand("/check", "Sensitivity and Specificity Calculator"));
        commands.add(new BotCommand("/upload", "Upload Excel or JSON file with data"));
        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commands);

        try {
            execute(setMyCommands);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String botToken = "";
        String botUsername = "SensiAndSpeBot_bot";

        ExcelDataInput excelDataInput = new ExcelDataInput();
        JsonDataInput jsonDataInput = new JsonDataInput();

        TelegramBot bot = new TelegramBot(botToken, botUsername, excelDataInput, jsonDataInput);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
