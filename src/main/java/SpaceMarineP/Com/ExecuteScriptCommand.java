package SpaceMarineP.Com;

import SpaceMarineP.manager.InputManager;
import SpaceMarineP.manager.Invoker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Команда {@code execute_script} – выполняет скрипт из указанного файла.
 * <p>
 * При выполнении временно подменяет источник ввода {@link InputManager} на файловый,
 * что позволяет командам, требующим ввода данных (например, {@code insert}, {@code update}),
 * читать параметры непосредственно из скрипта. После завершения скрипта ввод восстанавливается
 * на стандартный поток (консоль). Предотвращает рекурсивный вызов одного и того же скрипта.
 * </p>
 *
 * @see Command
 * @see Invoker
 * @see InputManager
 */
public class ExecuteScriptCommand implements Command {
    private final Invoker invoker;
    private final InputManager inputManager;

    /**
     * Множество абсолютных путей уже выполняемых скриптов.
     * Используется для защиты от бесконечной рекурсии.
     */
    private static final Set<String> activeScripts = new HashSet<>();

    /**
     * Конструктор команды.
     *
     * @param invoker      объект, управляющий выполнением команд
     * @param inputManager менеджер ввода, который будет временно переключён на файл
     */
    public ExecuteScriptCommand(Invoker invoker, InputManager inputManager) {
        this.invoker = invoker;
        this.inputManager = inputManager;
    }

    /**
     * Возвращает имя команды – {@code "execute_script"}.
     *
     * @return строковое имя команды
     */
    @Override
    public String getName() {
        return "execute_script";
    }

    /**
     * Возвращает краткое описание команды.
     *
     * @return описание: "считать и исполнить скрипт из указанного файла."
     */
    @Override
    public String description() {
        return "считать и исполнить скрипт из указанного файла.";
    }

    /**
     * Выполняет скрипт из файла, имя которого передано первым аргументом.
     * <p>
     * Последовательность действий:
     * <ul>
     *     <li>Проверяет наличие имени файла в аргументах.</li>
     *     <li>Проверяет существование и доступность файла для чтения.</li>
     *     <li>Проверяет, не выполняется ли уже этот скрипт (защита от рекурсии).</li>
     *     <li>Сохраняет текущий {@link Scanner} {@link InputManager} и заменяет его на файловый.</li>
     *     <li>Построчно читает файл, игнорируя пустые строки и комментарии (начинающиеся с '#'), и передаёт каждую команду в {@link Invoker#processCommand(String)}.</li>
     *     <li>Восстанавливает исходный {@link Scanner} после обработки скрипта.</li>
     * </ul>
     * </p>
     *
     * @param args массив аргументов команды; ожидается, что первый элемент — имя файла
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите имя файла.");
            return;
        }
        String fileName = args[0];
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл не найден: " + fileName);
            return;
        }
        if (!file.canRead()) {
            System.out.println("Нет прав на чтение файла: " + fileName);
            return;
        }

        // Защита от рекурсии
        String absolutePath;
        try {
            absolutePath = file.getCanonicalPath();
        } catch (Exception e) {
            absolutePath = file.getAbsolutePath();
        }
        if (activeScripts.contains(absolutePath)) {
            System.out.println("Обнаружена рекурсия! Скрипт " + fileName + " уже выполняется.");
            return;
        }

        activeScripts.add(absolutePath);
        Scanner oldScanner = inputManager.getScanner(); // предполагается наличие геттера
        try (Scanner fileScanner = new Scanner(file)) {
            inputManager.setScanner(fileScanner);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                System.out.println("[скрипт] " + line);
                invoker.processCommand(line);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            // Восстанавливаем исходный Scanner (консоль)
            inputManager.setScanner(oldScanner);
            activeScripts.remove(absolutePath);
        }
    }
}