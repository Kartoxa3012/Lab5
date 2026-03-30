package SpaceMarineP.Com;

import java.util.Map;

/**
 * Команда {@code help} – выводит справку по всем доступным командам.
 * <p>
 * Для каждой зарегистрированной команды выводится её имя и описание.
 * Список команд формируется на основе карты, переданной в конструктор.
 * </p>
 *
 * @see Command
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    /**
     * Конструктор команды.
     *
     * @param commands карта, содержащая все зарегистрированные команды (ключ – имя команды,
     *                 значение – объект команды). Обычно передаётся из {@link SpaceMarineP.manager.Invoker#getCommands()}.
     */
    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием: "вывести справку по доступным командам"
     */
    @Override
    public String description() {
        return "вывести справку по доступным командам";
    }

    /**
     * Возвращает имя команды – {@code "help"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "help";
    }

    /**
     * Выполняет вывод справки.
     * <p>
     * Для каждой команды из переданной карты выводится строка формата:
     * {@code имя_команды - описание_команды}.
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        for (Command c : commands.values()) {
            System.out.println(c.getName() + " - " + c.description());
        }
    }
}