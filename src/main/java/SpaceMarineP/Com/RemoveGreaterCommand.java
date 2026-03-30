package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.manager.InputManager;
import SpaceMarineP.model.SpaceMarine;
import SpaceMarineP.utility.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Команда {@code remove_greater} – удаляет из коллекции все элементы, превышающие заданный.
 * <p>
 * Пользователь вводит эталонный элемент (все поля, кроме {@code id}, который генерируется временно),
 * после чего из коллекции удаляются все элементы, которые в естественном порядке {@link SpaceMarine#compareTo(SpaceMarine)}
 * оказываются больше введённого. Удаление выполняется по ключам.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 * @see InputManager
 */
public class RemoveGreaterCommand implements Command {
    private final CollectionManager manager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param manager     менеджер коллекции, из которой будут удаляться элементы
     * @param inputManager менеджер ввода для чтения эталонного элемента
     */
    public RemoveGreaterCommand(CollectionManager manager, InputManager inputManager) {
        this.manager = manager;
        this.inputManager = inputManager;
    }

    /**
     * Возвращает имя команды – {@code "remove_greater"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "remove_greater";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "удалить из коллекции все элементы, превышающие заданный"
     */
    @Override
    public String description() {
        return "удалить из коллекции все элементы, превышающие заданный";
    }

    /**
     * Выполняет удаление элементов, превышающих эталонный.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Выводит приглашение и через {@link InputManager#readNewMarine()} запрашивает эталонный элемент.</li>
     *     <li>Генерирует для него временный уникальный идентификатор (чтобы корректно работало сравнение).</li>
     *     <li>Проходит по всем элементам коллекции и собирает ключи тех, чей {@code compareTo(reference) > 0}.</li>
     *     <li>Удаляет найденные элементы и выводит количество удалённых.</li>
     * </ul>
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Введите элемент для сравнения (все поля, кроме id):");
        // Вводим новый элемент (без id, он будет сгенерирован временный)
        SpaceMarine reference = inputManager.readNewMarine();
        // Присваиваем фиктивный id, чтобы можно было сравнивать (id не влияет, если поля равны)
        reference.setId(IdGenerator.generateId(manager)); // можно просто 0, но генератор даст уникальный
        List<String> keysToRemove = new ArrayList<>();
        for (Map.Entry<String, SpaceMarine> entry : manager.getCollection().entrySet()) {
            if (entry.getValue().compareTo(reference) > 0) {
                keysToRemove.add(entry.getKey());
            }
        }
        for (String key : keysToRemove) {
            manager.remove(key);
        }
        System.out.println("Удалено элементов: " + keysToRemove.size());
    }
}