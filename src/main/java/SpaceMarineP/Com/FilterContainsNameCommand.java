package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.model.SpaceMarine;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда {@code filter_contains_name} – выводит элементы коллекции, у которых поле {@link SpaceMarine#getName()}
 * содержит заданную подстроку (без учёта регистра).
 * <p>
 * Аргумент команды – искомая подстрока. Если подстрока не указана, выводится сообщение об ошибке.
 * Вывод осуществляется в порядке естественной сортировки элементов.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 */
public class FilterContainsNameCommand implements Command {
    private final CollectionManager manager;

    /**
     * Конструктор команды.
     *
     * @param manager менеджер коллекции, из которого будут извлекаться элементы
     */
    public FilterContainsNameCommand(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Возвращает имя команды – {@code "filter_contains_name"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "filter_contains_name";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "вывести элементы, значение поля name которых содержит заданную подстроку."
     */
    @Override
    public String description() {
        return "вывести элементы, значение поля name которых содержит заданную подстроку.";
    }

    /**
     * Выполняет фильтрацию элементов по подстроке в имени.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Проверяет наличие аргумента (подстроки). Если аргумент отсутствует, выводится сообщение об ошибке.</li>
     *     <li>Приводит подстроку к нижнему регистру для поиска без учёта регистра.</li>
     *     <li>Фильтрует коллекцию, оставляя только те элементы, имя которых (в нижнем регистре) содержит указанную подстроку.</li>
     *     <li>Выводит отфильтрованные элементы в отсортированном порядке (естественная сортировка).</li>
     * </ul>
     * </p>
     *
     * @param args массив аргументов команды; ожидается один элемент – подстрока для поиска
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите подстроку.");
            return;
        }
        String substring = args[0].toLowerCase();
        List<SpaceMarine> filtered = manager.sortedValues().stream()
                .filter(m -> m.getName().toLowerCase().contains(substring))
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            System.out.println("Элементы, содержащие '" + substring + "' в имени, не найдены.");
        } else {
            filtered.forEach(System.out::println);
        }
    }
}