// Package
package oy.interact.tira.student;

// Imports
import oy.interact.tira.util.StackInterface;

// Class
public class StackImplementation<E> implements StackInterface<E> {

    // Variables
    private static final int DEFAULT_STACK_SIZE = 10;
    private int capacity = DEFAULT_STACK_SIZE;
    // Taulukko, jossa elementit ovat.
    private Object[] itemArray = new Object[capacity];
    /*
     * Kokonaislukumuuttuja currentIndex pitää kirjaa viimeisimmästä indexi kohdasta
     * johon on lisätty tietoa.
     */
    private int currentIndex = -1;

    // Constructor
    public StackImplementation() {
        this(DEFAULT_STACK_SIZE);
    }

    public StackImplementation(int parameterCapacity) {
        capacity = parameterCapacity;
        itemArray = new Object[parameterCapacity];
    }

    // Methods

    // OMA: Reallokointi metodi.
    private void reallocate() {
        /*
         * Tuplataan ensin capacity, jonka jälkeen luodaan newArray joka saa taulukon
         * alustuksessa pituudeksi newCapacity. Tämä on tuplat edellisestä.
         */
        int newCapacity = capacity * 2;
        Object[] newArray = new Object[newCapacity];

        // Siirretään/Kopioidaan vanhasta taulukosta alkiot uuteen taulukkoon.
        for (int alkio = 0; alkio <= currentIndex; alkio++) {
            newArray[alkio] = itemArray[alkio];
        }
        // Lopuksi asetetaan uusi taulukko ja capacity
        itemArray = newArray;
        capacity = newCapacity;
    }

    @Override
    public int capacity() {
        // Palauttaa capacity:n arvon joka on listan tila-arvo
        return this.capacity;
    }

    @Override
    public void push(E element) throws OutOfMemoryError, NullPointerException {
        /*
         * Tarkistetaan onko elementti NULL, jos on niin palautetaan virhe
         * NullPointerExpection
         */
        if (element == null) {
            throw new NullPointerException("ERROR: Element is null");
        }
        // Tarkistetaan onko taulukko täynnä, jos on niin reallokoidaan taulukko
        if (size() == capacity) {
            reallocate();
        }

        // Lisätään elementti taulukkoon kohtaan currentIndex.
        itemArray[++currentIndex] = element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E pop() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        // Otetaan viimeisen alkion elementti ja vapautetaan viite
        E removedElement = (E) itemArray[currentIndex];
        itemArray[currentIndex] = null;
        // Kerrotaan currentIndexille että yksi alkio on poistunut
        currentIndex--;

        return removedElement;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        // Otetaan viimeisen alkion elementti ja palautetaan sen arvo
        E peekElement = (E) itemArray[currentIndex];
        return peekElement;
    }

    @Override
    public int size() {
        /*
         * Palauttaa taulukon koon jos taulukko ei ole tyhjä, jo niin nii palautetaan
         * arvo 0
         */
        return currentIndex == -1 ? 0 : currentIndex + 1;
    }

    // !KESKEN
    @Override
    public boolean isEmpty() {
        // Palauttaa boolean arvon
        return size() == 0;
    }

    @Override
    public void clear() {
        // Asetetaan capacity default arvo takaisin DEFAULT_STACK_SIZE arvoksi
        capacity = DEFAULT_STACK_SIZE;
        // Sanotaan että itemArray on yhtäsuuri kuin uusi object taulukko, mikä on
        // capacity:n kokoinen
        itemArray = new Object[capacity];
        // Asetetaan currentIndex = -1, joka kertoo että taulukko on tyhjä
        currentIndex = -1;
    }

    // toString
    @Override
    public String toString() {
        // palautettu merkkijono sisältää pinon sisällön alkaen "pohjalta", täsmälleen
        // seuraavassa formaatissa, pilkut ja välilyönnit mukaanlukien: [110, 119, 121]

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        // For silmukka
        for (int alkio = 0; alkio <= currentIndex; alkio++) {
            // Lisätään alkion sisältö taulukkoon
            stringBuilder.append(itemArray[alkio]);
            // Jos alkio on pienempää kuin viimeinen eli currentIndex, niin lyödään pilkku
            if (alkio < currentIndex) {
                stringBuilder.append(", ");
            }
        }
        // Lisätään lopuksi ]
        stringBuilder.append("]");
        // Palautetaan merkkijono
        return stringBuilder.toString();
    }
}
