package here.you.are;


public class Main {
    public static void main(String[] args)
    {
        FileParsersHandler fileParsersHandler;
        fileParsersHandler = new FileParsersHandler(); // Поток-менеджер обработчиков файлов
        fileParsersHandler.start();
        UI ui = new UI(fileParsersHandler); // Пользовательский интерфейс
        ui.startUI();
    }
}
