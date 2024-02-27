package oy.interact.tira.factories;

import oy.interact.tira.NotYetImplementedException;

import oy.interact.tira.model.Coder;
import oy.interact.tira.student.ArrayQueue;
import oy.interact.tira.util.QueueInterface;

public class QueueFactory {

	private QueueFactory() {
		// empty
	}

	public static QueueInterface<Integer> createIntegerQueue() {
		// return new LinkedListQueue<>();
		return new ArrayQueue<>();
	}

	public static QueueInterface<Integer> createIntegerQueue(int capacity) {
		if (capacity <= 0) {
			throw new NotYetImplementedException("Stack capacity must be non-null");
		}
		// return new LinkedListQueue<>(capacity);
		return new ArrayQueue<>(capacity);
	}

	public static QueueInterface<String> createStringQueue() {
		// return new LinkedListQueue<>();
		return new ArrayQueue<>();
	}

	public static QueueInterface<String> createStringQueue(int capacity) {
		if (capacity <= 0) {
			throw new NotYetImplementedException("Stack capacity must be non-null");
		}
		// return new LinkedListQueue<>(capacity);
		return new ArrayQueue<>();
	}

	public static QueueInterface<Coder> createCoderQueue() {
		// return new LinkedListQueue<>();
		return new ArrayQueue<>();
	}
}
