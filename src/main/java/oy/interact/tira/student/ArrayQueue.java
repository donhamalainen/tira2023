package oy.interact.tira.student;

import oy.interact.tira.util.QueueInterface;

public class ArrayQueue<E> implements QueueInterface<E> {

    /*
     * capacity(): O(1). (DONE)
     * 
     * enqueue(): O(1) paitsi kun/jos joudutaan reallokoimaan taulukkoratkaisussa:
     * O(n).
     * 
     * dequeue(): O(1).
     * 
     * element(): O(1).
     * 
     * size(): O(1).
     * 
     * isEmpty(): O(1).
     * 
     * clear(): O(1).
     * 
     * toString(): O(n).
     */

    // ******************
    // Attributes
    // ******************

    private static final int DEFAULT_STACK_SIZE = 10;

    // Tärkeimmät atribuutit
    private int head = 0, tail = 0, count = 0;
    private int capacity = DEFAULT_STACK_SIZE;
    private Object[] itemArray = new Object[capacity];

    // ******************
    // Contructors
    // ******************
    public ArrayQueue() {
        this(DEFAULT_STACK_SIZE);
    }

    public ArrayQueue(int parameterCapacity) {
        capacity = parameterCapacity;
        itemArray = new Object[parameterCapacity];
    }

    // ******************
    // Methods
    // ******************

    // OMA: Reallokointi metodi.
    private void reallocate() {
        /*
         * Tuplataan ensin capacity, jonka jälkeen luodaan newArray joka saa taulukon
         * alustuksessa pituudeksi newCapacity. Tämä on tuplat edellisestä.
         */
        int newCapacity = capacity * 2;
        Object[] newArray = new Object[newCapacity];

        // Siirretään vanhan taulukon elementit uuteen
        for (int alkio = 0; alkio < capacity; alkio++) {
            newArray[alkio] = itemArray[(head + alkio) % capacity];
        }
        head = 0;
        tail = capacity;
        itemArray = newArray;
        capacity = newCapacity;
    }

    @Override
    public int capacity() {
        // Palauttaa capacity:n
        return this.capacity;
    }

    // TAIL
    @Override
    public void enqueue(E element) throws OutOfMemoryError, NullPointerException {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }

        // Tarkistetaan onko taulukko jo täynnä
        // size() palauttaa count arvon joka on listan täytettyjen alkioiden lukumäärä
        if (size() == this.capacity) {
            reallocate();
        }
        // Lisätään taulukkoon elementti, ja +1 count ja otetaan huomioon tail arvot ja
        // pyörivän taulukon logiikka.
        itemArray[tail] = element;
        tail = (tail + 1) % capacity;
        count++;
    }

    // HEAD
    @Override
    @SuppressWarnings("unchecked")
    public E dequeue() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Empty queue");
        }
        E item = (E) itemArray[head];
        itemArray[head] = null;
        head = (head + 1) % capacity;
        count--;
        return item;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E element() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Element cannot be null");
        }

        // Palautetaan
        return (E) itemArray[head];
    }

    @Override
    public int size() {
        // Palauttaa alkioiden lukumäärän
        return this.count;
    }

    @Override
    public boolean isEmpty() {
        // Palauttaa totuusarvon
        return size() == 0;
    }

    @Override
    public void clear() {
        // Asetetaan capacity default arvo takaisin DEFAULT_STACK_SIZE arvoksi
        capacity = DEFAULT_STACK_SIZE;
        // Sanotaan että itemArray on yhtäsuuri kuin uusi object taulukko, mikä on
        // capacity:n kokoinen
        itemArray = new Object[capacity];
        // Asetetaan head, tail ja count alkuarvoihin
        tail = 0;
        head = 0;
        count = 0;
    }

    // toString
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        // Aloitetaan for silmukka head:stä ja päätetään se tail indexi arvoon.
        for (int alkio = 0; alkio < count; alkio++) {
            int index = (head + alkio) % capacity;
            // Lisätään alkion sisältö taulukkoon
            stringBuilder.append(itemArray[index]);

            // Jos kyseessä ei ole viimeinen elementti, lisätään pilkku ja välilyönti
            if (alkio < count - 1) {
                stringBuilder.append(", ");
            }
        }
        // Lisätään lopuksi ]
        stringBuilder.append("]");
        // Palautetaan merkkijono
        return stringBuilder.toString();
    }
}
