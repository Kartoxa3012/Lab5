package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;

/**
 * Команда {@code remove_key} – удаляет элемент из коллекции по его ключу.
 * <p>
 * Команда ожидает один аргумент – ключ элемента, который следует удалить.
 * Если ключ не указан или элемент с таким ключом отсутствует, выводится соответствующее сообщение.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 */
public class RemoveCommand implements Command {
    private final CollectionManager manager;

    /**
     * Конструктор команды.
     *
     * @param manager менеджер коллекции, из которого будет удалён элемент
     */
    public RemoveCommand(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Возвращает имя команды – {@code "remove_key"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "remove_key";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "удалить элемент из коллекции по его ключу."
     */
    @Override
    public String description() {
        return "удалить элемент из коллекции по его ключу.";
    }

    /**
     * Выполняет удаление элемента из коллекции.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Проверяет наличие ключа в аргументах.</li>
     *     <li>Проверяет, существует ли элемент с указанным ключом.</li>
     *     <li>Если элемент найден, удаляет его и выводит подтверждение; иначе выводит сообщение об отсутствии.</li>
     * </ul>
     * </p>
     *
     * @param args массив аргументов команды; ожидается один элемент – ключ
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: не указан ключ. Использование: remove_key <ключ>");
            return;
        }
        String key = args[0];
        if (manager.containsKey(key)) {
            manager.remove(key);
            System.out.println("Элемент с ключом '" + key + "' удалён.");
        } else {
            System.out.println("Элемент с ключом '" + key + "' не найден.");
        }
    }
}