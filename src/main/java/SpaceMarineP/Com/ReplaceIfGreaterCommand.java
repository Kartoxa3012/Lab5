package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.manager.InputManager;
import SpaceMarineP.model.SpaceMarine;

/**
 * Команда {@code replace_if_greater} – заменяет значение элемента по ключу,
 * если новое значение больше старого согласно естественному порядку {@link SpaceMarine#compareTo(SpaceMarine)}.
 * <p>
 * Команда принимает один аргумент – ключ элемента. Затем запрашивает новый элемент (поля вводятся
 * построчно, кроме {@code id} и {@code creationDate}, которые копируются из старого элемента).
 * Если новое значение оказывается больше старого, элемент заменяется; иначе выводится сообщение
 * об отсутствии замены.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 * @see InputManager
 */
public class ReplaceIfGreaterCommand implements Command {
    private final CollectionManager manager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param manager     менеджер коллекции
     * @param inputManager менеджер ввода для чтения нового элемента
     */
    public ReplaceIfGreaterCommand(CollectionManager manager, InputManager inputManager) {
        this.manager = manager;
        this.inputManager = inputManager;
    }

    /**
     * Возвращает имя команды – {@code "replace_if_greater"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "replace_if_greater";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "заменить значение по ключу, если новое значение больше старого."
     */
    @Override
    public String description() {
        return "заменить значение по ключу, если новое значение больше старого.";
    }

    /**
     * Выполняет замену элемента при условии, что новое значение больше старого.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Проверяет наличие ключа в аргументах; если отсутствует – выводит ошибку.</li>
     *     <li>Проверяет существование элемента с указанным ключом; если нет – выводит сообщение.</li>
     *     <li>Сохраняет старый элемент.</li>
     *     <li>Запрашивает новый элемент через {@link InputManager#readMarineForUpdate()} (без {@code id} и даты).</li>
     *     <li>Создаёт новый объект {@link SpaceMarine}, сохраняя старые {@code id} и {@code creationDate}.</li>
     *     <li>Сравнивает новый элемент со старым через {@link SpaceMarine#compareTo(SpaceMarine)}.</li>
     *     <li>Если новое значение больше, заменяет элемент в коллекции и выводит подтверждение;
     *         иначе выводит сообщение, что замена не выполнена.</li>
     * </ul>
     * </p>
     *
     * @param args массив аргументов команды; ожидается один элемент – ключ
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите ключ.");
            return;
        }
        String key = args[0];
        if (!manager.containsKey(key)) {
            System.out.println("Элемент с ключом '" + key + "' не найден.");
            return;
        }
        SpaceMarine oldMarine = manager.getByKey(key);
        System.out.println("Введите новый элемент:");
        SpaceMarine newMarineData = inputManager.readMarineForUpdate();
        SpaceMarine newMarine = new SpaceMarine(
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
        if (newMarine.compareTo(oldMarine) > 0) {
            manager.put(key, newMarine);
            System.out.println("Значение заменено.");
        } else {
            System.out.println("Новое значение не больше старого, замена не выполнена.");
        }
    }
}