package SpaceMarineP.utility;

import SpaceMarineP.manager.CollectionManager;
import SpaceMarineP.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Утилитарный класс для чтения коллекции из CSV-файла.
 * <p>
 * Формат файла: строки с разделителем {@code ;} (точка с запятой).
 * Каждая строка должна содержать ровно 13 полей в следующем порядке:
 * <ol start="0">
 *     <li>ключ (String) – идентификатор элемента в коллекции</li>
 *     <li>id (int) – уникальный идентификатор</li>
 *     <li>имя (String) – имя десантника</li>
 *     <li>координата x (float)</li>
 *     <li>координата y (float)</li>
 *     <li>дата создания (LocalDateTime) в формате ISO_LOCAL_DATE_TIME</li>
 *     <li>здоровье (float)</li>
 *     <li>категория (AstartesCategory) – может быть пустым</li>
 *     <li>тип оружия (Weapon) – не может быть пустым</li>
 *     <li>оружие ближнего боя (MeleeWeapon) – может быть пустым</li>
 *     <li>название главы (String) – может быть пустым</li>
 *     <li>количество десантников в главе (int) – может быть пустым, если глава отсутствует</li>
 *     <li>мир главы (String) – может быть пустым</li>
 * </ol>
 * Пустые поля обозначаются отсутствием значения между разделителями (например, {@code ;;}).
 * При чтении некорректные строки пропускаются с выводом сообщения в {@code System.err}.
 * </p>
 *
 * @see CollectionManager
 * @see SpaceMarine
 */
public class CsvReader {

    /**
     * Загружает коллекцию из CSV-файла по указанному пути.
     * <p>
     * Метод открывает файл, построчно разбирает его содержимое, создаёт объекты
     * {@link SpaceMarine} и добавляет их в коллекцию через {@link CollectionManager#put(String, SpaceMarine)}.
     * При возникновении ошибок (недостаток полей, неверный формат данных и т.п.) строка пропускается,
     * а сообщение об ошибке выводится в стандартный поток ошибок.
     * </p>
     * <p>
     * <strong>Важно:</strong> метод содержит отладочный вывод в консоль, который выводит каждую строку
     * и количество полей. Для реального использования этот вывод может быть удалён или закомментирован.
     * </p>
     *
     * @param filePath           путь к CSV-файлу
     * @param collectionManager  менеджер коллекции, в который будут добавлены элементы
     * @throws RuntimeException если файл не найден – метод завершает программу с кодом 1
     */
    public static void ReadCsv(String filePath, CollectionManager collectionManager) {
        System.out.println("Чтение файла: " + filePath);

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int rowCount = 0;

            // Читаем данные
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(";", -1);

                System.out.print("Строка " + (++rowCount) + ": ");
                for (String field : fields) {
                    System.out.print(field.trim() + " | ");
                }
                System.out.println();
                if (fields.length < 13) {
                    System.err.println("Строка " + rowCount + ": недостаточно полей, пропущена.");
                    System.err.println("Ожидалось 13 полей, получено " + fields.length);
                    continue;
                }
                try {
                    String key = fields[0];
                    if (key.isEmpty()) {
                        System.err.println("Строка " + rowCount + ": пустой ключ, пропущена.");
                        continue;
                    }
                    int id = Integer.parseInt(fields[1]);
                    String name = fields[2];
                    float x = Float.parseFloat(fields[3]);
                    Float y = fields[4].isEmpty() ? null : Float.parseFloat(fields[4]);
                    if (y == null) throw new IllegalArgumentException("y не может быть null");

                    LocalDateTime creationDate = LocalDateTime.parse(fields[5]);
                    float health = Float.parseFloat(fields[6]);

                    AstartesCategory category = fields[7].isEmpty() ? null : AstartesCategory.valueOf(fields[7]);
                    Weapon weapon = Weapon.valueOf(fields[8]); // не null
                    MeleeWeapon meleeWeapon = fields[9].isEmpty() ? null : MeleeWeapon.valueOf(fields[9]);

                    Chapter chapter = null;
                    if (!fields[10].isEmpty()) {
                        String chName = fields[10];
                        int chCount = Integer.parseInt(fields[11]);
                        String chWorld = fields[12].isEmpty() ? null : fields[12];
                        chapter = new Chapter(chName, chCount, chWorld);
                    }

                    Coordinates coords = new Coordinates(x, y);
                    SpaceMarine marine = new SpaceMarine(id, name, coords, creationDate, health, category, weapon, meleeWeapon, chapter);
                    collectionManager.put(key, marine);

                } catch (Exception e) {
                    System.err.println("Строка " + rowCount + ": ошибка парсинга – " + e.getMessage());
                }
            }

            System.out.println("Всего прочитано строк: " + rowCount);

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
            System.err.println("Проверьте путь: " + filePath);
            System.exit(1);
        }
    }
}