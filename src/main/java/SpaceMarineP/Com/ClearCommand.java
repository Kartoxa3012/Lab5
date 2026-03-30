package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;

/**
 * Команда {@code clear} – удаляет все элементы из коллекции.
 * <p>
 * При вызове очищает хранилище {@link CollectionManager},
 * после чего выводит сообщение об успешном выполнении.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 */
public class ClearCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции, который будет очищен
     */
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает имя команды – {@code "clear"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "clear";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "очистить коллекцию"
     */
    @Override
    public String description() {
        return "очистить коллекцию";
    }

    /**
     * Выполняет очистку коллекции.
     * <p>
     * Вызывает {@link CollectionManager#clear()}, затем выводит подтверждение в консоль.
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        collectionManager.clear();
        System.out.println("Коллекция очищена.");
    }
}