package SpaceMarineP;

import SpaceMarineP.Com.*;
import SpaceMarineP.manager.InputManager;
import SpaceMarineP.manager.Invoker;
import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.utility.CsvReader;

import static SpaceMarineP.utility.CsvReader.ReadCsv;

/**
 * Главный класс приложения.
 * <p>
 * При запуске выполняет следующие действия:
 * <ul>
 *     <li>Считывает переменную окружения {@code SPACE_MARINES_DATA}, содержащую путь к CSV-файлу с данными.</li>
 *     <li>Если переменная не установлена или пуста, выводит сообщение об ошибке и завершает работу.</li>
 *     <li>Загружает коллекцию из указанного CSV-файла с помощью {@link CsvReader}.</li>
 *     <li>Создаёт и регистрирует все доступные команды в {@link Invoker}.</li>
 *     <li>Запускает интерактивный режим обработки команд.</li>
 * </ul>
 * </p>
 *
 * @author Kovalenko Vladislav 504673
 */
public class Main {
    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        CollectionManager manager = new CollectionManager();
        InputManager inputManager = new InputManager(invoker.scanner);
        // Получаем путь к файлу из переменной окружения
        String csvFile = System.getenv("SPACE_MARINES_DATA");

        // Проверяем, задана ли переменная окружения
        if (csvFile == null || csvFile.trim().isEmpty()) {
            System.err.println("Ошибка: переменная окружения не установлена");
            System.exit(1);
        }

        // Вызываем функцию ReadCsv
        ReadCsv(csvFile, manager);

        // Регистрация команд
        invoker.register(new ExitCommand(invoker));
        invoker.register(new InfoCommand(manager));
        invoker.register(new ShowCommand(manager));
        invoker.register(new ClearCommand(manager));
        invoker.register(new SaveCommand(manager, csvFile));
        invoker.register(new HelpCommand(invoker.getCommands()));
        invoker.register(new InsertCommand(manager, inputManager));
        invoker.register(new RemoveCommand(manager));
        invoker.register(new UpdateCommand(manager, inputManager));
        invoker.register(new RemoveGreaterCommand(manager, inputManager));
        invoker.register(new ReplaceIfGreaterCommand(manager, inputManager));
        invoker.register(new ReplaceIfLowerCommand(manager, inputManager));
        invoker.register(new FilterByCategoryCommand(manager));
        invoker.register(new FilterContainsNameCommand(manager));
        invoker.register(new PrintFieldDescendingHealthCommand(manager));
        invoker.register(new ExecuteScriptCommand(invoker, inputManager));

        invoker.run(); // запуск интерактивного режима
    }
}