package SpaceMarineP.Com;

import SpaceMarineP.manager.Invoker;

/**
 * Команда {@code exit} – завершает выполнение программы.
 * <p>
 * При вызове останавливает цикл обработки команд, выводя сообщение о завершении.
 * Сохранение коллекции в файл при этом <b>не производится</b>.
 * </p>
 *
 * @see Command
 * @see Invoker
 */
public class ExitCommand implements Command {
    private final Invoker invoker;

    /**
     * Конструктор команды.
     *
     * @param invoker объект, управляющий циклом выполнения команд; его метод {@link Invoker#stop()}
     *                будет вызван для завершения программы
     */
    public ExitCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Возвращает имя команды – {@code "exit"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "exit";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "завершить программу (без сохранения в файл)"
     */
    @Override
    public String description() {
        return "завершить программу (без сохранения в файл)";
    }

    /**
     * Выполняет завершение программы.
     * <p>
     * Выводит сообщение в консоль и вызывает {@link Invoker#stop()}, что приводит к остановке
     * основного цикла ввода команд.
     * </p>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Завершение программы.");
        invoker.stop();
    }
}