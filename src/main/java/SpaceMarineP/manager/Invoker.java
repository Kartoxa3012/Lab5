package SpaceMarineP.manager;

import SpaceMarineP.Com.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Управляет регистрацией и выполнением команд в интерактивном режиме.
 * <p>
 * Считывает строки из стандартного ввода, разбирает их на имя команды и аргументы,
 * находит зарегистрированную команду и вызывает её метод {@link Command#execute(String[])}.
 * Также предоставляет метод {@link #processCommand(String)} для выполнения команд из скриптов.
 * </p>
 *
 * @see Command
 */
public class Invoker {
    private Map<String, Command> commands;
    public Scanner scanner;
    private boolean running;

    /**
     * Создаёт новый Invoker, инициализируя хранилище команд и сканнер для чтения ввода.
     */
    public Invoker() {
        this.commands = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.running = true;
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
     * Запускает основной цикл обработки команд из консоли.
     * Цикл продолжается, пока поле {@code running} равно {@code true}.
     * При получении строки вызывает {@link #processCommand(String)} для её выполнения.
     */
    public void run() {
        while (running) {
            System.out.print(">");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            processCommand(line);
        }
        scanner.close();
    }

    /**
     * Обрабатывает строку команды: разбивает на имя и аргументы,
     * находит соответствующую команду и выполняет её.
     * <p>
     * Этот метод используется как для прямого ввода из консоли, так и для выполнения скриптов
     * (например, из {@link SpaceMarineP.Com.ExecuteScriptCommand}).
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