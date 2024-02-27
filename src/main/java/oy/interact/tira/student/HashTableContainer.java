package oy.interact.tira.student;

import java.util.function.Predicate;

import oy.interact.tira.util.Pair;
import oy.interact.tira.util.TIRAKeyedContainer;

public class HashTableContainer<K extends Comparable<K>, V> implements TIRAKeyedContainer<K, V> {

    /////////////////////////////////////////////
    // ATTRIBUTES
    /////////////////////////////////////////////
    private Pair<K, V>[] array;
    // Counting variables
    private int count = 0;
    private int collisionCount = 0;
    private int maxProbingCount = 0;
    private int pairUpdateCount = 0;
    private int reallocateCount = 0;
    // static variables
    private static final int DEFAULT_TABLE_SIZE = 10;
    private static final double LOAD_FACTOR = 0.60;

    /////////////////////////////////////////////
    // CONSTRUCTORS
    /////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    public HashTableContainer() {
        this.array = (Pair<K, V>[]) new Pair[DEFAULT_TABLE_SIZE];
    }

    /////////////////////////////////////////////
    // METHODS
    /////////////////////////////////////////////
    @Override
    public void add(K key, V value) throws OutOfMemoryError, IllegalArgumentException {
        // Null arvojen tarkistus
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key & Value cannot be null");
        }
        if (size() >= capacity() * LOAD_FACTOR) {
            reallocate((int) (capacity() * (LOAD_FACTOR + 1)));
        }
        // Alustus
        int hash = key.hashCode();
        int hashModifier = 0;
        int currentProbingCount = 0;
        boolean added = false;

        do {
            int index = indexFor(hash, hashModifier);
            if (array[index] == null) {
                array[index] = new Pair<K, V>(key, value);
                count++;
                added = true;
            } else if (array[index].getKey().equals(key)) {
                array[index] = new Pair<K, V>(key, value);
                pairUpdateCount++;
                added = true;
            } else {
                hashModifier++;
                collisionCount++;
                currentProbingCount++;
            }
        } while (!added);
        maxProbingCount = Math.max(maxProbingCount, currentProbingCount);
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int hash = key.hashCode();
        int hashModifier = 0;
        while (hashModifier < this.array.length) {
            int index = indexFor(hash, hashModifier);
            Pair<K, V> pair = array[index];
            // if(pair != null && !pair.isRemoved())
            if (pair != null) {
                if (key.equals(pair.getKey())) {
                    return pair.getValue(); // Avain löytyi
                }
            }
            hashModifier++;
        }
        return null; // Avainta ei löytynyt
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public V find(Predicate<V> searcher) {
        if (searcher == null) {
            throw new IllegalArgumentException("Search predicate cannot be null");
        }
        // Käydään läpi kaikki taulukon elementit
        for (Pair<K, V> pair : array) {
            // if(pair != null && !pair.isRemoved())
            if (pair != null) {
                V value = pair.getValue();
                if (searcher.test(value)) {
                    return value; // Palautetaan ensimmäinen löytynyt elementti
                }
            }
        }
        return null; // Ei löytynyt.
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public int capacity() {
        return this.array.length;
    }

    @Override
    public void ensureCapacity(int capacity) throws OutOfMemoryError, IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'ensureCapacity'");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        array = (Pair<K, V>[]) new Pair[DEFAULT_TABLE_SIZE];
        this.count = 0;
        this.collisionCount = 0;
        this.maxProbingCount = 0;
        this.pairUpdateCount = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pair<K, V>[] toArray() throws Exception {

        Pair<K, V>[] arrayTwo = (Pair<K, V>[]) new Pair[count];
        int tempIndex = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Count\t\t: " + size() + "\n");
        sb.append("Capacity\t: " + capacity() + "\n");
        sb.append("Collisions\t: " + collisionCount + "\n");
        sb.append("Filled to\t: " + String.format("%.2f", ((double) size() / capacity()) * 100) + "\n");
        sb.append("Pair Updates\t: " + pairUpdateCount + "\n");
        sb.append("Load Factory\t: " + LOAD_FACTOR + "\n");
        sb.append("Max probing\t: " + maxProbingCount + "\n");

        System.out.println(sb.toString());
        for (int index = 0; index < this.array.length; index++) {
            // if(array[index] != null && !array[index].isRemoved()) {
            if (array[index] != null) {
                arrayTwo[tempIndex++] = array[index];
            }
        }
        return arrayTwo;
    }

    ////////////////////////////////////////////////////////////////
    // Reallakointi & IndexFor
    ////////////////////////////////////////////////////////////////

    private int indexFor(int hash, int hashModifier) {
        final int c1 = 4;
        final int c2 = 17;
        // Quadradic PROBING
        return ((hash + c1 * hashModifier + c2 * (hashModifier * hashModifier)) &
                0x7FFFFFFF) % this.array.length;

    }

    @SuppressWarnings("unchecked")
    private void reallocate(int newCapacity) {
        Pair<K, V>[] oldArray = this.array;
        this.array = (Pair<K, V>[]) new Pair[newCapacity];

        // Nollataan laskurit
        this.count = 0;
        this.collisionCount = 0;
        this.maxProbingCount = 0;
        this.pairUpdateCount = 0;

        // Siirretään elementit uuteen taulukkoon
        for (int index = 0; index < oldArray.length; index++) {
            if (oldArray[index] != null) {
                add(oldArray[index].getKey(), oldArray[index].getValue());
            }
        }
        // Lisätään laskentaa
        this.reallocateCount++;
    }
}
