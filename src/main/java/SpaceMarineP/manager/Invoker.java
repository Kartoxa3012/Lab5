package SpaceMarineP.manager;

import SpaceMarineP.Com.Command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * Управляет регистрацией и выполнением команд в интерактивном режиме.
 * <p>
 * Считывает строки из стандартного ввода, разбирает их на имя команды и аргументы,
 * находит зарегистрированную команду и вызывает её метод {@link Command#execute(String[])}.
 * Также предоставляет метод {@link #processCommand(String)} для выполнения команд из скриптов.
 * </p>
 * <p>
 * Поддерживает очередь команд: можно добавлять команды в очередь и выполнять их последовательно.
 * </p>
 *
 * @see Command
 */
public class Invoker {
    private Map<String, Command> commands;
    public Scanner scanner;
    private boolean running;
    private Queue<String> commandQueue;      // очередь отложенных команд
    private boolean queueMode;              // если true, команды не выполняются, а добавляются в очередь

    /**
     * Создаёт новый Invoker, инициализируя хранилище команд, сканнер для чтения ввода,
     * очередь команд и выключая режим очереди по умолчанию.
     */
    public Invoker() {
        this.commands = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.commandQueue = new LinkedList<>();
        this.queueMode = false;
    }

    /**
     * Регистрирует команду в карте команд.
     *
     * @param command команда, которую необходимо зарегистрировать
     */
    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    /**
     * Возвращает карту всех зарегистрированных команд.
     *
     * @return карта, где ключ – имя команды, значение – объект команды
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Добавляет строку команды в очередь.
     *
     * @param commandLine строка команды (как если бы её ввёл пользователь)
     */
    public void addToQueue(String commandLine) {
        commandQueue.add(commandLine);
    }

    /**
     * Очищает очередь команд.
     */
    public void clearQueue() {
        commandQueue.clear();
    }

    /**
     * Возвращает текущее количество команд в очереди.
     *
     * @return размер очереди
     */
    public int getQueueSize() {
        return commandQueue.size();
    }

    /**
     * Возвращает содержимое очереди (не изменяя её).
     *
     * @return копия очереди в виде массива строк
     */
    public String[] getQueueContents() {
        return commandQueue.toArray(new String[0]);
    }

    /**
     * Выполняет все команды из очереди последовательно, после чего очищает очередь.
     * Если в процессе выполнения какой-то команды возникнет ошибка, выполнение останавливается.
     */
    public void executeQueue() {
        while (!commandQueue.isEmpty()) {
            String cmd = commandQueue.poll();
            System.out.println("[выполнение очереди] " + cmd);
            processCommand(cmd);
        }
    }

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true, если очередь пуста
     */
    public boolean isQueueEmpty() {
        return commandQueue.isEmpty();
    }

    /**
     * Включает или выключает режим очереди.
     * В режиме очереди команды не выполняются сразу, а добавляются в очередь.
     *
     * @param mode true – включить режим очереди, false – выключить
     */
    public void setQueueMode(boolean mode) {
        this.queueMode = mode;
    }

    /**
     * Возвращает состояние режима очереди.
     *
     * @return true, если режим очереди включён
     */
    public boolean isQueueMode() {
        return queueMode;
    }

    /**
     * Запускает основной цикл обработки команд из консоли.
     * Цикл продолжается, пока поле {@code running} равно {@code true}.
     * При получении строки:
     * <ul>
     *   <li>если включён режим очереди – добавляет команду в очередь;</li>
     *   <li>иначе – сразу выполняет через {@link #processCommand(String)}.</li>
     * </ul>
     */
    public void run() {
        while (running) {
            System.out.print(queueMode ? "queue> " : "> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            if (queueMode) {
                commandQueue.add(line);
                System.out.println("Команда добавлена в очередь. Всего в очереди: " + commandQueue.size());
            } else {
                processCommand(line);
            }
        }
        scanner.close();
    }

    /**
     * Обрабатывает строку команды: разбивает на имя и аргументы,
     * находит соответствующую команду и выполняет её.
     * <p>
     * Этот метод используется как для прямого ввода из консоли, так и для выполнения скриптов
     * (например, из {@link SpaceMarineP.Com.ExecuteScriptCommand}) и для выполнения очереди.
     * </p>
     *
     * @param line строка команды, введённая пользователем или прочитанная из скрипта
     */
    public void processCommand(String line) {
        String[] tokens = line.split("\\s+");
        String commandName = tokens[0];
        String[] args = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, args, 0, args.length);

        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Неизвестная команда. Введите help.");
            return;
        }
        try {
            command.execute(args);
        } catch (Exception e) {
            System.out.println("Ошибка выполнения команды: " + e.getMessage());
            // e.printStackTrace();
        }
    }

    /**
     * Останавливает основной цикл обработки команд.
     * Вызывается, например, командой {@code exit}.
     */
    public void stop() {
        running = false;
    }
}
