package Upload;

import Excel.ExcelData;
import Excel.ExcelDataCall;
import FileReader.FileReaderFactory;
import FileReader.ExcelFileReaderFactory;
import FileReader.JsonFileReaderFactory;
import Json.JsonDataInput;
import Excel.ExcelDataInput;
import Telegram.Command;
import Telegram.Mediator;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class UploadCommand implements Command {
    private final Mediator mediator;
    private final ExcelDataInput excelDataInput;
    private final JsonDataInput jsonDataInput;
    private List<ExcelData> dataList;
    private final ExcelDataCall excelDataCall;

    public UploadCommand(Mediator mediator, ExcelDataInput excelDataInput, JsonDataInput jsonDataInput, ExcelDataCall excelDataCall) {
        this.mediator = mediator;
        this.excelDataInput = excelDataInput;
        this.jsonDataInput = jsonDataInput;
        this.excelDataCall = excelDataCall;
    }

    @Override
    public void execute(Message message) {
        String chatId = message.getChatId().toString();
        if (message.hasDocument()) {
            Document document = message.getDocument();
            try {
                File file = mediator.getBot().execute(new GetFile(document.getFileId()));
                String filePath = "https://api.telegram.org/file/bot" + mediator.getBot().getBotToken() + "/" + file.getFilePath();
                URL url = new URL(filePath);
                InputStream inputStream = url.openStream();

                String fileName = document.getFileName();
                FileReaderFactory fileReaderFactory;

                if (fileName.endsWith(".xlsx")) {
                    fileReaderFactory = new ExcelFileReaderFactory(excelDataInput);
                } else if (fileName.endsWith(".json")) {
                    fileReaderFactory = new JsonFileReaderFactory(jsonDataInput);
                } else {
                    SendMessage sendMessage = new SendMessage(chatId, "Загрузите файл в формате Excel (.xlsx) или JSON (.json).");
                    mediator.sendMessage(sendMessage);
                    return;
                }

                dataList = fileReaderFactory.importData(inputStream);

                SendMessage sendMessage = new SendMessage(chatId, "Данные успешно загружены.");
                mediator.sendMessage(sendMessage);

                SendMessage askIndexMessage = new SendMessage(chatId, "Введите значение индекса:");
                mediator.sendMessage(askIndexMessage);

                IndexInputListener indexInputListener = new IndexInputListener(mediator, dataList, chatId, excelDataCall);
                mediator.addMessageListener(indexInputListener);

            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        } else {
            SendMessage sendMessage = new SendMessage(chatId, "Загрузите файл Excel или файл JSON.");
            mediator.sendMessage(sendMessage);
        }
    }
}
