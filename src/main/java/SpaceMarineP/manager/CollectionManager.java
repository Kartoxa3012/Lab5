package SpaceMarineP.manager;

import SpaceMarineP.model.SpaceMarine;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс, управляющий коллекцией объектов SpaceMarine.
 * Хранит элементы в Hashtable<String, SpaceMarine>.
 * Предоставляет методы для доступа, поиска, генерации id.
 */
public class CollectionManager {
    /** Основное хранилище (ключ — строка, значение — объект). */
    private final Hashtable<String, SpaceMarine> collection = new Hashtable<>();
    /** Дата инициализации коллекции (создания менеджера). */
    private final LocalDateTime initDate = LocalDateTime.now();

    /**
     * Возвращает всю коллекцию.
     *
     * @return Hashtable, содержащая все элементы
     */
    public Hashtable<String, SpaceMarine> getCollection() {
        return collection;
    }

    /**
     * @return дата инициализации
     */
    public LocalDateTime getInitDate() {
        return initDate;
    }

    /**
     * @return количество элементов в коллекции
     */
    public int size() {
        return collection.size();
    }

    /**
     * Возвращает элемент по ключу.
     *
     * @param key ключ
     * @return элемент или null, если ключ отсутствует
     */
    public SpaceMarine getByKey(String key) {
        return collection.get(key);
    }

    /**
     * Проверяет наличие ключа.
     *
     * @param key ключ
     * @return true, если ключ существует
     */
    public boolean containsKey(String key) {
        return collection.containsKey(key);
    }

    /**
     * Добавляет или заменяет элемент по ключу.
     *
     * @param key    ключ
     * @param marine элемент
     * @return предыдущее значение или null
     */
    public SpaceMarine put(String key, SpaceMarine marine) {
        return collection.put(key, marine);
    }

    /**
     * Удаляет элемент по ключу.
     *
     * @param key ключ
     * @return удалённый элемент или null
     */
    public SpaceMarine remove(String key) {
        return collection.remove(key);
    }

    /**
     * Очищает коллекцию.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Ищет ключ, под которым хранится элемент с заданным id.
     *
     * @param id идентификатор элемента
     * @return ключ или null, если элемент не найден
     */
    public String findKeyById(Integer id) {
        for (Map.Entry<String, SpaceMarine> entry : collection.entrySet()) {
            if (entry.getValue().getId().equals(id)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Возвращает все элементы коллекции, отсортированные согласно естественному порядку (compareTo).
     *
     * @return список отсортированных элементов
     */
    public List<SpaceMarine> sortedValues() {
        List<SpaceMarine> list = new ArrayList<>(collection.values());
        Collections.sort(list);
        return list;
    }
}