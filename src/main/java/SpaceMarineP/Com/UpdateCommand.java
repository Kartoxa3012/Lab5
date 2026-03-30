package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.manager.InputManager;
import SpaceMarineP.model.SpaceMarine;

/**
 * Команда {@code update} – обновляет элемент коллекции по его идентификатору.
 * <p>
 * Команда принимает один аргумент – целочисленный {@code id} элемента. Если элемент с таким
 * {@code id} существует, пользователю предлагается ввести новые значения для всех полей
 * (кроме {@code id} и даты создания, которые остаются неизменными). После ввода элемент
 * заменяется в коллекции, сохраняя исходные {@code id} и {@code creationDate}.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 * @see InputManager
 */
public class UpdateCommand implements Command {
    private final CollectionManager manager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param manager     менеджер коллекции, в которой будет обновлён элемент
     * @param inputManager менеджер ввода для чтения новых данных
     */
    public UpdateCommand(CollectionManager manager, InputManager inputManager) {
        this.manager = manager;
        this.inputManager = inputManager;
    }

    /**
     * Возвращает имя команды – {@code "update"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "update";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "обновить значение элемента коллекции, id которого равен заданному."
     */
    @Override
    public String description() {
        return "обновить значение элемента коллекции, id которого равен заданному.";
    }

    /**
     * Выполняет обновление элемента по идентификатору.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Проверяет наличие аргумента – {@code id}. Если аргумент отсутствует или не является целым числом,
     *         выводится сообщение об ошибке.</li>
     *     <li>Ищет ключ элемента с указанным {@code id} через {@link CollectionManager#findKeyById(Integer)}.
     *         Если элемент не найден, выводится сообщение.</li>
     *     <li>Сохраняет старый элемент.</li>
     *     <li>Запрашивает новые данные через {@link InputManager#readMarineForUpdate()} (без {@code id} и даты).</li>
     *     <li>Создаёт новый объект {@link SpaceMarine}, сохраняя старые {@code id} и {@code creationDate}.</li>
     *     <li>Заменяет элемент в коллекции по найденному ключу и выводит подтверждение.</li>
     * </ul>
     * </p>
     *
     * @param args массив аргументов команды; ожидается один элемент – целочисленный {@code id}
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: не указан id. Использование: update <id>");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: id должен быть целым числом.");
            return;
        }
        String key = manager.findKeyById(id);
        if (key == null) {
            System.out.println("Элемент с id=" + id + " не найден.");
            return;
        }
        SpaceMarine oldMarine = manager.getByKey(key);
        System.out.println("Введите новые данные для элемента (id и дата создания сохранятся):");
        // Ввод всех полей, кроме id и даты
        SpaceMarine newMarineData = inputManager.readMarineForUpdate();
        // Создаём обновлённый объект с сохранением старого id и даты
        SpaceMarine updatedMarine = new SpaceMarine(
                oldMarine.getId(),
                newMarineData.getName(),
                newMarineData.getCoordinates(),
                oldMarine.getCreationDate(),
                newMarineData.getHealth(),
                newMarineData.getCategory(),
                newMarineData.getWeaponType(),
                newMarineData.getMeleeWeapon(),
                newMarineData.getChapter()
        );
        manager.put(key, updatedMarine);
        System.out.println("Элемент с id=" + id + " успешно обновлён.");
    }
}