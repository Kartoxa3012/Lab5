package SpaceMarineP.Com;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.model.SpaceMarine;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда {@code print_field_descending_health} – выводит значения поля {@link SpaceMarine#getHealth()}
 * всех элементов коллекции в порядке убывания.
 * <p>
 * Команда не принимает аргументов. Если коллекция пуста, выводится соответствующее сообщение.
 * В противном случае значения здоровья выводятся каждое с новой строки, округлённые до двух знаков
 * после запятой.
 * </p>
 *
 * @see Command
 * @see CollectionManager
 */
public class PrintFieldDescendingHealthCommand implements Command {
    private final CollectionManager manager;

    /**
     * Конструктор команды.
     *
     * @param manager менеджер коллекции, из которого извлекаются значения здоровья
     */
    public PrintFieldDescendingHealthCommand(CollectionManager manager) {
        this.manager = manager;
    }

    /**
     * Возвращает имя команды – {@code "print_field_descending_health"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "print_field_descending_health";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "вывести значения поля health всех элементов в порядке убывания"
     */
    @Override
    public String description() {
        return "вывести значения поля health всех элементов в порядке убывания";
    }

    /**
     * Выполняет вывод значений здоровья в порядке убывания.
     * <p>
     * Получает отсортированные элементы коллекции через {@link CollectionManager#sortedValues()},
     * извлекает их здоровье, сортирует в обратном порядке и выводит каждое значение с двумя знаками
     * после запятой. Если коллекция пуста, выводится сообщение.
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        List<Float> healthValues = manager.sortedValues().stream()
                .map(SpaceMarine::getHealth)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        if (healthValues.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            healthValues.forEach(h -> System.out.printf("%.2f%n", h));
        }
    }
}