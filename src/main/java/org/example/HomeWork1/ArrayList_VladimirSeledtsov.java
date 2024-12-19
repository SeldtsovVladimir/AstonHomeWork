package org.example.HomeWork1;

import java.util.Arrays;
import java.util.Comparator;


public class ArrayList_VladimirSeledtsov<E> implements IntensiveList<E> {
    private Object[] elements; // Массив для хранения элементов
    private int size; // Текущий размер списка
    private static final int DEFAULT_CAPACITY = 15; // Начальная вместимость


    public ArrayList_VladimirSeledtsov() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public void add(E element) {
        ensureCapacity(); // Увеличиваем вместимость, если нужно
        elements[size++] = element;
    }


    @Override
    public void add(int index, E element) {
        validateIndexForAdd(index); // Проверяем индекс
        ensureCapacity();
        // Сдвигаем элементы вправо для вставки
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }


    @Override
    public E get(int index) {
        validateIndex(index);
        return elementAt(index);
    }


    @Override
    public E set(int index, E element) {
        validateIndex(index);
        E oldElement = elementAt(index);
        elements[index] = element;
        return oldElement;
    }


    @Override
    public E remove(int index) {
        validateIndex(index);
        E removedElement = elementAt(index); // Сдвигаем элементы влево
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null; // Удаляем последний элемент
        return removedElement;
    }


    @Override
    public void clear() {
        elements = new Object[DEFAULT_CAPACITY]; // Создаем новый массив
        size = 0;
    }


    @Override
    public void quickSort(Comparator<E> comparator) {
        if (size > 1) {
            quickSort(0, size - 1, comparator);
        }
    }

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


    @Override
    public void split(int newSize) {
        if (newSize < 0 || newSize > size) {
            throw new IllegalArgumentException("Invalid size");
        }
        Arrays.fill(elements, newSize, size, null); // Удаляем лишние элементы
        size = newSize;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2); // Увеличиваем размер
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @SuppressWarnings("unchecked")
    private E elementAt(int index) {
        return (E) elements[index];
    }

}
