package oy.interact.tira.factories;

import oy.interact.tira.NotYetImplementedException;
import oy.interact.tira.student.StackImplementation;
import oy.interact.tira.util.StackInterface;

public class StackFactory {
	private StackFactory() {
		// Empty
	}

	public static StackInterface<Integer> createIntegerStack() {
		return new StackImplementation<>();
	}

	public static StackInterface<Integer> createIntegerStack(int capacity) {
		if (capacity <= 0) {
			throw new NotYetImplementedException("Stack capacity must be non-null");
		}
		return new StackImplementation<>(capacity);
	}

	public static StackInterface<Character> createCharacterStack() {
		return new StackImplementation<>();
	}

	public static StackInterface<Character> createCharacterStack(int capacity) {
		if (capacity <= 0) {
			throw new NotYetImplementedException("Stack capacity must be non-null");
		}
		return new StackImplementation<>(capacity);
	}

}
