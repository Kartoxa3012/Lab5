package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;

import java.time.format.DateTimeFormatter;

/**
 * Команда {@code info} – выводит информацию о коллекции.
 * <p>
 * Выводит в стандартный поток вывода:
 * <ul>
 *     <li>тип коллекции (полное имя класса)</li>
 *     <li>дату инициализации коллекции (в формате ISO_LOCAL_DATE_TIME)</li>
 *     <li>количество элементов в коллекции</li>
 * </ul>
 * </p>
 *
 * @see Command
 * @see CollectionManager
 */
public class InfoCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции, информацию о котором нужно вывести
     */
    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает имя команды – {@code "info"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "info";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "вывести информацию о коллекции (тип, дата инициализации, количество элементов)"
     */
    @Override
    public String description() {
        return "вывести информацию о коллекции (тип, дата инициализации, количество элементов)";
    }

    /**
     * Выполняет вывод информации о коллекции.
     * <p>
     * Использует {@link CollectionManager#getCollection()} для получения типа коллекции,
     * {@link CollectionManager#getInitDate()} для даты инициализации и
     * {@link CollectionManager#size()} для количества элементов.
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Тип коллекции: " + collectionManager.getCollection().getClass().getName());
        System.out.println("Дата инициализации: " + collectionManager.getInitDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("Количество элементов: " + collectionManager.size());
    }
}