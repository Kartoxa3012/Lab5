package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.model.AstartesCategory;
import SpaceMarineP.model.SpaceMarine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда {@code filter_by_category} – выводит элементы коллекции, у которых значение поля
 * {@link SpaceMarine#getCategory()} равно заданной категории.
 * <p>
 * Аргумент команды (название категории) должен быть одной из констант перечисления
 * {@link AstartesCategory} (регистр не важен). Если категория не указана или указана неверно,
 * выводится сообщение об ошибке и список допустимых значений.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 * @see AstartesCategory
 */
public class FilterByCategoryCommand implements Command {
    private final CollectionManager manager;

    /**
     * Конструктор команды.
     *
     * @param manager менеджер коллекции, из которого будут извлекаться элементы
     */
    public FilterByCategoryCommand(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Возвращает имя команды – {@code "filter_by_category"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "filter_by_category";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "вывести элементы, значение поля category которых равно заданному."
     */
    @Override
    public String description() {
        return "вывести элементы, значение поля category которых равно заданному.";
    }

    /**
     * Выполняет фильтрацию элементов по категории.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Проверяет наличие аргумента (категории).</li>
     *     <li>Преобразует введённую строку в константу {@link AstartesCategory} (без учёта регистра).</li>
     *     <li>В случае неверного ввода выводит сообщение об ошибке и допустимые значения.</li>
     *     <li>Фильтрует коллекцию, оставляя только элементы с указанной категорией.</li>
     *     <li>Выводит отфильтрованные элементы в отсортированном порядке (естественная сортировка).</li>
     * </ul>
     * </p>
     *
     * @param args массив аргументов команды; ожидается один элемент – название категории
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите категорию. Допустимые значения: " +
                    Arrays.toString(AstartesCategory.values()));
            return;
        }
        AstartesCategory targetCategory;
        try {
            targetCategory = AstartesCategory.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Недопустимая категория. Допустимые значения: " +
                    Arrays.toString(AstartesCategory.values()));
            return;
        }
        List<SpaceMarine> filtered = manager.sortedValues().stream()
                .filter(m -> targetCategory.equals(m.getCategory()))
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            System.out.println("Элементы с категорией " + targetCategory + " не найдены.");
        } else {
            filtered.forEach(System.out::println);
        }
    }
}