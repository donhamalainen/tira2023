package oy.interact.tira.util;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.function.Predicate;

import oy.interact.tira.NotYetImplementedException;
import oy.interact.tira.student.Algorithms;

public class SimpleContainer<E extends Comparable<E>> implements TIRAContainer<E> {

	private static final int DEFAULT_ARRAY_SIZE = 20;

	private E[] array = null;
	private Class<E> clazz;

	private int count = 0;
	private int reallocationCount = 0;

	private boolean sorted = false;

	public SimpleContainer(Class<E> clazz) {
		this(clazz, DEFAULT_ARRAY_SIZE);
	}

	@SuppressWarnings("unchecked")
	public SimpleContainer(Class<E> clazz, int capacity) {
		this.clazz = clazz;
		array = (E[]) Array.newInstance(clazz, capacity);
		count = 0;
		reallocationCount = 0;
	}

	@Override
	public void add(E element) throws OutOfMemoryError, IllegalArgumentException {
		if (null == element)
			throw new IllegalArgumentException("Key cannot be null");
		// Must not have duplicate items, so check if key is already in the array.
		for (int index = 0; index < array.length; index++) {
			if (array[index] == null) {
				break;
			}
			if (array[index].equals(element)) {
				array[index] = element;
				return;
			}
		}
		if (count >= array.length) {
			reallocate(array.length * 2);
		}
		array[count] = element;
		count++;
		sorted = false;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		return array[index];
	}

	@Override
	public E get(E element) throws IllegalArgumentException {
		// TODO: Student: finish this as part of task 02.
		for (int silmukka = 0; silmukka < count; silmukka++) {
			if (array[silmukka].equals(element)) {
				return array[silmukka];
			}
		}
		throw new NotYetImplementedException("Task 02-TASK on linear search not yet implemented");

	}

	@Override
	public int indexOf(E element, Comparator<E> usingComparator) {
		// TODO: Student: finish this as part of task 02.
		if (isSorted()) {
			return Algorithms.binarySearch(element, array, 0, count, usingComparator);
		} else {
			for (int silmukka = 0; silmukka < count; silmukka++) {
				if (array[silmukka].compareTo(element) == 0) {
					return silmukka;
				}
			}
		}

		throw new NotYetImplementedException("Task 02-TASK on linear search not yet implemented");
	}

	// Note: This method is NOT USED by tests and TIRA Coders GUI.
	@Override
	public E remove(E element) throws IllegalArgumentException {
		if (null == element)
			throw new IllegalArgumentException("Key to remove cannot be null");
		E found = null;
		int foundIndex = 0;

		if (sorted) {
			// Does not work until binary search has been implemented.
			foundIndex = Algorithms.binarySearch(element, (E[]) array, 0, count);
			if (foundIndex >= 0) {
				found = (E) array[foundIndex];
				array[foundIndex] = null;
				count--;
			}
		} else {
			for (; foundIndex < count; foundIndex++) {
				if (array[foundIndex] == null) {
					break;
				}
				if (element.equals(array[foundIndex])) {
					found = (E) array[foundIndex];
					array[foundIndex] = null;
					count--;
					break;
				}
			}
		}
		// Move all the rest down one step to fill the null in array.
		if (null != found) {
			while (foundIndex < array.length - 1) {
				if (array[foundIndex + 1] == null) {
					break;
				}
				array[foundIndex] = array[foundIndex + 1];
			}
		}
		return found;
	}

	@Override
	public int findIndex(Predicate<E> searcher) {
		// TODO: Student: finish this as part of task 02.
		for (int silmukka = 0; silmukka < count; silmukka++) {
			if (searcher.test(array[silmukka])) {
				return silmukka;
			}
		}
		return -1;
	}

	@Override
	public E find(Predicate<E> searcher) {
		// TODO: Student: finish this as part of task 02.
		for (int silmukka = 0; silmukka <= count - 1; silmukka++) {
			if (searcher.test(array[silmukka])) {
				return array[silmukka];
			}
		}
		return null;
	}

	@Override
	public int size() {
		return count;
	}

	@Override
	public int capacity() {
		return array.length;
	}

	@Override
	@java.lang.SuppressWarnings("unchecked")
	public void ensureCapacity(int capacity) throws OutOfMemoryError, IllegalArgumentException {
		if (capacity <= count) {
			throw new IllegalArgumentException("New capacity cannot be smaller than count of elements");
		}
		if (count == 0) {
			array = (E[]) Array.newInstance(clazz, capacity);
			count = 0;
			reallocationCount = 0;
		} else {
			reallocate(capacity);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear() {
		array = (E[]) Array.newInstance(clazz, DEFAULT_ARRAY_SIZE);
		count = 0;
		reallocationCount = 0;
	}

	@java.lang.SuppressWarnings("unchecked")
	private void reallocate(int newSize) throws OutOfMemoryError {
		E[] newArray = (E[]) Array.newInstance(clazz, newSize);
		for (int index = 0; index < count; index++) {
			newArray[index] = array[index];
		}
		array = newArray;
		reallocationCount++;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray() {
		E[] toReturn = (E[]) Array.newInstance(clazz, count);
		for (int index = 0; index < count; index++) {
			toReturn[index] = array[index];
		}
		return toReturn;
	}

	@Override
	public boolean isSorted() {
		return sorted;
	}

	@Override
	public void reverse() {
		// TODO: Student: finish this as part of task 02.
		Algorithms.reverse(this.array, 0, count);
		this.sorted = true;
	}

	// TEACHERS: TODO: Remove the call to Algorithms sort method.
	@Override
	public void sort() {
		// TODO: Student: finish this as part of task 02.
		// Algorithms.insertionSort(this.array, 0, count);
		Algorithms.fastSort(this.array);
		this.sorted = true;
	}

	// TEACHERS: TODO: Remove the call to Algorithms sort method.
	@Override
	public void sort(Comparator<E> usingComparator) {
		// TODO: Student: finish this as part of task 02.
		// Algorithms.insertionSort(this.array, 0, count, usingComparator);
		Algorithms.fastSort(this.array, 0, count, usingComparator);
		this.sorted = true;
	}
}
