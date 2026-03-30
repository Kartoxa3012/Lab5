package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.utility.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Команда {@code save} – сохраняет текущее состояние коллекции в файл.
 * <p>
 * Путь к файлу задаётся в конструкторе команды и обычно извлекается из переменной окружения
 * {@code SPACE_MARINES_DATA} в точке запуска приложения. При выполнении коллекция сериализуется
 * в формат CSV с помощью {@link CsvWriter} и записывается в указанный файл.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 * @see CsvWriter
 */
public class SaveCommand implements Command {
    private final CollectionManager collectionManager;
    private final String filePath;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции, подлежащей сохранению
     * @param filePath путь к файлу, в который будет сохранена коллекция
     */
    public SaveCommand(CollectionManager collectionManager, String filePath) {
        this.collectionManager = collectionManager;
        this.filePath = filePath;
    }

    /**
     * Возвращает имя команды – {@code "save"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "save";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "сохранить коллекцию в файл"
     */
    @Override
    public String description() {
        return "сохранить коллекцию в файл";
    }

    /**
     * Выполняет сохранение коллекции.
     * <p>
     * Использует {@link CsvWriter#writeCsv(PrintWriter, CollectionManager)} для записи данных.
     * В случае ошибок ввода-вывода (например, отсутствие прав на запись) выводится сообщение об ошибке.
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            CsvWriter.writeCsv(writer, collectionManager);
            System.out.println("Сохранено в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }
}