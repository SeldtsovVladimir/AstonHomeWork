package org.example.HomeWork1;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Реализация динамического списка, предназначенного для хранения и управления элементами
 * обобщенного типа {@code E}. Предоставляет методы для добавления, удаления, получения
 * и манипуляции элементами в структуре, похожей на список.
 *
 * Основные возможности:
 * - Динамическое увеличение размера массива при добавлении новых элементов.
 * - Операции добавления элементов по индексу, удаления, сортировки.
 * - Реализация сортировки методом "быстрой сортировки" с использованием {@link Comparator}.
 * - Утилитарные методы: проверка, отсортирован ли список, изменение размера списка с помощью {@code split}.
 *
 * @param <E> тип элементов в списке
 */
public class ArrayList_VladimirSeledtsov<E> implements IntensiveList<E> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 15;

    public ArrayList_VladimirSeledtsov() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    /**
     * Возвращает количество элементов в списке.
     *
     * @return размер списка
     */
    @Override
    public int size() {
        return size;
    }
    /**
     * Добавляет новый элемент в конец списка.
     *
     * @param element элемент, который нужно добавить
     */
    @Override
    public void add(E element) {
        ensureCapacity(); // Увеличиваем вместимость, если нужно
        elements[size++] = element;
    }

    /**
     * Вставляет элемент в заданный индекс, сдвигая последующие элементы вправо.
     *
     * @param index   индекс, куда нужно вставить элемент
     * @param element элемент для добавления
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    @Override
    public void add(int index, E element) {
        validateIndexForAdd(index); // Проверяем индекс
        ensureCapacity();
        // Сдвигаем элементы вправо для вставки
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }
    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента
     * @return элемент, расположенный по указанному индексу
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */

    @Override
    public E get(int index) {
        validateIndex(index);
        return elementAt(index);
    }
    /**
     * Заменяет элемент по указанному индексу на новый элемент.
     *
     * @param index   индекс заменяемого элемента
     * @param element новый элемент
     * @return старый элемент, который находился по указанному индексу
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */

    @Override
    public E set(int index, E element) {
        validateIndex(index);
        E oldElement = elementAt(index);
        elements[index] = element;
        return oldElement;
    }

    /**
     * Удаляет элемент по указанному индексу, сдвигая последующие элементы влево.
     *
     * @param index индекс удаляемого элемента
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    @Override
    public E remove(int index) {
        validateIndex(index);
        E removedElement = elementAt(index); // Сдвигаем элементы влево
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null; // Удаляем последний элемент
        return removedElement;
    }

    /**
     * Очищает список, удаляя все элементы.
     */
    @Override
    public void clear() {
        elements = new Object[DEFAULT_CAPACITY]; // Создаем новый массив
        size = 0;
    }
    /**
     * Сортирует список методом быстрой сортировки, используя заданный {@link Comparator}.
     *
     * @param comparator компаратор для сравнения элементов
     */

    @Override
    public void quickSort(Comparator<E> comparator) {
        if (size > 1) {
            quickSort(0, size - 1, comparator);
        }
    }
    /**
     * Проверяет, отсортирован ли список в порядке возрастания.
     *
     * @return {@code true}, если список отсортирован, иначе {@code false}
     */
    private void quickSort(int low, int high, Comparator<E> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quickSort(low, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<E> comparator) {
        E pivot = elementAt(high);
        int i = low;
        for (int j = low; j < high; j++) {
            if (comparator.compare(elementAt(j), pivot) < 0) {
                swap(i, j);
                i++;
            }
        }
        swap(i, high);
        return i;
    }

    private void swap(int i, int j) {
        E temp = elementAt(i);
        elements[i] = elements[j];
        elements[j] = temp;
    }
    /**
     * Проверяет, отсортирован ли список в порядке возрастания.
     *
     * @return {@code true}, если список отсортирован, иначе {@code false}
     */

    @Override
    public boolean isSorted() {
        if (size < 2) {
            return true;
        }
        for (int i = 1; i < size; i++) {
            Comparable<E> current = (Comparable<E>) elementAt(i - 1);
            if (current.compareTo(elementAt(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Урезает список до указанного размера, удаляя все элементы за его пределами.
     *
     * @param newSize новый размер списка
     * @throws IllegalArgumentException если новый размер недопустим
     */
    @Override
    public void split(int newSize) {
        if (newSize < 0 || newSize > size) {
            throw new IllegalArgumentException("Invalid size");
        }
        Arrays.fill(elements, newSize, size, null); // Удаляем лишние элементы
        size = newSize;
    }

    /**
     * Увеличивает вместимость внутреннего массива, если нужно.
     */
    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2); // Увеличиваем размер
        }
    }
    /**
     * Проверяет, что индекс находится в допустимом диапазоне для существующих элементов.
     *
     * @param index проверяемый индекс
     * @throws IndexOutOfBoundsException если индекс недопустим
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    /**
     * Проверяет, что индекс находится в допустимом диапазоне для добавления элементов.
     *
     * @param index проверяемый индекс
     * @throws IndexOutOfBoundsException если индекс недопустим
     */
    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента
     * @return элемент, расположенный по указанному индексу
     */
    @SuppressWarnings("unchecked")
    private E elementAt(int index) {
        return (E) elements[index];
    }

}
