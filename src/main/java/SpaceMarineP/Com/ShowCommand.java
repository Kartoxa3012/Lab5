package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.model.SpaceMarine;

import java.util.List;

/**
 * Команда {@code show} – выводит все элементы коллекции в строковом представлении.
 * <p>
 * Элементы выводятся в отсортированном порядке согласно естественной сортировке
 * {@link SpaceMarine#compareTo(SpaceMarine)} (по убыванию health, затем по имени, затем по id).
 * Если коллекция пуста, выводится соответствующее сообщение.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 */
public class ShowCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции, элементы которой будут выведены
     */
    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает имя команды – {@code "show"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "show";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "вывести все элементы коллекции в строковом представлении"
     */
    @Override
    public String description() {
        return "вывести все элементы коллекции в строковом представлении";
    }

    /**
     * Выполняет вывод элементов коллекции.
     * <p>
     * Если коллекция не пуста, получает отсортированный список через {@link CollectionManager#sortedValues()}
     * и выводит каждый элемент в консоль. В противном случае выводит сообщение о пустоте коллекции.
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        if (collectionManager.size() == 0) {
            System.out.println("Коллекция пуста.");
            return;
        }

        List<SpaceMarine> sorted = collectionManager.sortedValues();
        for (SpaceMarine marine : sorted) {
            System.out.println(marine);
        }
    }
}