package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.manager.InputManager;
import SpaceMarineP.model.SpaceMarine;
import SpaceMarineP.utility.IdGenerator;

/**
 * Команда {@code insert} – добавляет новый элемент в коллекцию с заданным ключом.
 * <p>
 * Команда ожидает один аргумент – ключ, под которым элемент будет храниться в коллекции.
 * Далее последовательно запрашиваются все поля элемента (кроме {@code id} и {@code creationDate},
 * которые генерируются автоматически) с помощью {@link InputManager}. После успешного ввода
 * элемент добавляется в коллекцию, а ему присваивается уникальный идентификатор, сгенерированный
 * {@link IdGenerator}.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 * @see InputManager
 * @see IdGenerator
 */
public class InsertCommand implements Command {
    private final CollectionManager manager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param manager     менеджер коллекции, в которую будет добавлен элемент
     * @param inputManager менеджер ввода для последовательного запроса полей
     */
    public InsertCommand(CollectionManager manager, InputManager inputManager) {
        this.manager = manager;
        this.inputManager = inputManager;
    }

    /**
     * Возвращает имя команды – {@code "insert"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "insert";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "Добавить новый элемент с заданным ключом"
     */
    @Override
    public String description() {
        return "Добавить новый элемент с заданным ключом";
    }

    /**
     * Выполняет добавление элемента в коллекцию.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Проверяет наличие ключа в аргументах.</li>
     *     <li>Проверяет, что ключ не пуст и ещё не используется в коллекции.</li>
     *     <li>Выводит приглашение и через {@link InputManager#readNewMarine()} запрашивает все поля.</li>
     *     <li>Генерирует новый уникальный идентификатор через {@link IdGenerator#generateId(CollectionManager)}.</li>
     *     <li>Сохраняет элемент в коллекции и выводит сообщение об успехе.</li>
     * </ul>
     * </p>
     *
     * @param args массив аргументов команды; ожидается один элемент – ключ
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: не указан ключ. Использование: insert <ключ>");
            return;
        }
        String key = args[0];
        if (key.trim().isEmpty()) {
            System.out.println("Ошибка: ключ не может быть пустым.");
            return;
        }
        if (manager.containsKey(key)) {
            System.out.println("Элемент с ключом '" + key + "' уже существует.");
            return;
        }

        System.out.println("Введите данные для нового элемента (ключ: " + key + "):");
        SpaceMarine marine = inputManager.readNewMarine();
        marine.setId(IdGenerator.generateId(manager));
        manager.put(key, marine);
        System.out.println("Элемент добавлен. ID = " + marine.getId());
    }
}