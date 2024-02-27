package oy.interact.tira.student;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
// Imports
import java.util.function.Predicate;
import oy.interact.tira.util.Pair;
import oy.interact.tira.util.StackInterface;
import oy.interact.tira.util.TIRAKeyedOrderedContainer;
import oy.interact.tira.util.Visitor;

/* NOTES:
- Key: "String";
- Value: "Coder";

Taulukkopohjaisessa toteutuksessa kun lajittelujärjestys muuttui, käytettiin lajittelualgoritmia (quicksort, heapsort,...) taulukon järjestyksen muuttamiseksi. BST:n tapauksessa luodaan uusi puu eri Comparator -toteutuksella, johon koodarit vanhasta puusta siirretään.
*/
// MAIN
public class BinarySearchTreeContainer<K extends Comparable<K>, V> implements TIRAKeyedOrderedContainer<K, V> {
    // ATTRIBUTES
    TreeNode<K, V> root; // Root node of the tree, your private little helper class.
    int size = 0; // Number of elements currently in the tree.
    int maxDepth = 0; // Number of nodes in the tree
    private Comparator<K> comparator; // The comparator used to determine if new node will go to left or right
                                      // subtree.

    // CONSTRUCTORS
    public BinarySearchTreeContainer(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    // METODS
    @Override
    public void add(K key, V value) throws OutOfMemoryError, IllegalArgumentException {
        // Jos puussa ei ole vielä solmua, niin tehdään sellainen.
        if (root == null) {
            root = new TreeNode<K, V>(key, value);
            this.size++;
            this.maxDepth = 1;
        } else {
            // Alustetaan TreeNode<K, V> size = 1.
            TreeNode.addDepth = 1;
            if (root.insert(key, value, comparator)) {
                // Tallennetaan suurin syvyys vertailussa.
                this.maxDepth = Math.max(this.maxDepth, TreeNode.addDepth);
                this.size++;
            }
        }
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        // Jos key on null.
        if (key == null) {
            throw new IllegalArgumentException("Error: key cannot be null");
        }
        // Jos Root on null.
        if (root == null) {
            return null;
        }
        // Lähdetään etsimään.
        return root.find(key, comparator);
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public V find(Predicate<V> searcher) {
        if (root == null) {
            return null;
        }

        TreeNode<K, V> currentNode = root;
        TreeNode<K, V> parent = null;
        StackInterface<TreeNode<K, V>> nodeStack = new StackImplementation<>();

        while (!nodeStack.isEmpty() || currentNode != null) {
            if (currentNode != null) {
                nodeStack.push(currentNode);
                parent = currentNode;
                currentNode = currentNode.getLeftChild();
            } else {
                parent = nodeStack.pop();
                if (searcher.test(parent.getValue())) {
                    // Kun löydetään haluttu arvo, niin palautetaan se heti
                    return parent.getValue();
                }
                currentNode = parent.getRightChild();
            }
        }
        // Ei löytynyt
        return null;
    }

    @Override
    public int size() {
        // Palauttaa size attribuutin
        return this.size;
    }

    @Override
    public int capacity() {
        // Palauttaa puun maximaalisen kapasiteetin
        return Integer.MAX_VALUE;
    }

    @Override
    public void ensureCapacity(int capacity) throws OutOfMemoryError, IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'ensureCapacity'");
    }

    @Override
    public void clear() {
        // Puhdistetaan kaikki
        root = null;
        size = 0;
        maxDepth = 0;
    }

    @Override
    @SuppressWarnings("checked")
    public Pair<K, V>[] toArray() throws Exception {
        System.out.println("Puun syvyys: " + maxDepth);
        Pair<K, V>[] array = (Pair<K, V>[]) new Pair[size];
        AtomicInteger arrayIndex = new AtomicInteger(0);
        if (root != null) {
            root.toArray(array, arrayIndex);
        }
        return array;
    }

    // ====================================================
    // VOIMME HYÖDYNTÄÄ LASTEN LUKUMÄÄRÄÄ
    // ====================================================
    @Override
    public int indexOf(K itemKey) {

        // Tarkistetaan että puu ei ole tyhjä
        if (root == null || itemKey == null) {
            return -1;
        }

        // Alustetaan
        TreeNode<K, V> currentNode = root;
        // currentIndex on leftchild.childcount + 1, tai jos left childia ei ole, nolla
        int currentIndex = (currentNode.getLeftChild() != null) ? currentNode.getLeftChild().childCount + 1 : 0;
        // Toistetaan (huom!: tämä on siis iteratiivinen algoritmi) kunnes current on
        // null (on menty pohjalle eikä löydetty indeksiä)
        while (currentNode != null) {
            // Vertailija
            int compare = comparator.compare(itemKey, currentNode.key);

            if (compare == 0) {
                // Avain löytyi
                return currentIndex;
            } else if (compare < 0) {
                // Siirrytään vasemmalle
                // vähennetään currentIndexistä 1 (vasemmalle mennessä indeksit pienenevät)
                currentIndex--;
                currentNode = currentNode.getLeftChild();
                // JOS nodella on oikeanpuoleinen solmu
                if (currentNode.getRightChild() != null) {
                    // vähennetään currentIndeksistä oikeanpuoleisen solmu lasten lukumäärä + 1
                    currentIndex -= currentNode.getRightChild().childCount + 1;
                }
            } else {
                // Siirrytään oikealle
                // lisätään currentIndexiin 1 (indeksit kasvavat oikealle mentäessä)
                currentIndex++;
                currentNode = currentNode.getRightChild();
                // JOS nodella on vasemmanpuoleinen solmu
                if (currentNode.getLeftChild() != null) {
                    // lisätään currentIndexiin vasemmanpuoleisen solmun lasten lukumäärä + 1
                    // (siellä on tämän haaran pienemmät indeksit)
                    currentIndex += currentNode.getLeftChild().childCount + 1;
                }
            }
        }
        return -1; // Avainta ei löytynyt
    }

    @Override
    public Pair<K, V> getIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Error: index out of bounds");
        }
        if (root == null) {
            return null;
        }
        // currentIndex on leftchild.childcount + 1, tai jos left childia ei ole, nolla
        TreeNode<K, V> currentNode = root;
        int currentIndex = (currentNode.getLeftChild() != null) ? currentNode.getLeftChild().childCount + 1 : 0;

        // Toistetaan (huom!: tämä on siis iteratiivinen algoritmi) kunnes current on
        // null (on menty pohjalle eikä löydetty indeksiä)
        while (currentNode != null) {
            // Jos currentIndex == haettava, palautetaan current:n K,V Pair -oliossa.
            if (currentIndex == index) {
                // Löydettiin oikea solmu ja palautetaan
                return new Pair<K, V>(currentNode.getKey(), currentNode.getValue());
                // Mennään vasemmalle
            }
            // Jos haettava < currentIndex, mennään vasemmalle
            if (index < currentIndex) {
                // vähennetään currentIndexistä 1 (vasemmalle mennessä indeksit pienenevät)
                currentIndex--;
                currentNode = currentNode.getLeftChild();
                // JOS nodella on oikeanpuoleinen solmu
                if (currentNode.getRightChild() != null) {
                    // vähennetään currentIndeksistä oikeanpuoleisen solmu lasten lukumäärä + 1
                    currentIndex -= currentNode.getRightChild().childCount + 1;
                }
                // Jos haettava > currentIndex, mennään oikealle
            } else if (index > currentIndex) {
                // lisätään currentIndexiin 1 (indeksit kasvavat oikealle mentäessä)
                currentIndex++;
                currentNode = currentNode.getRightChild();
                // JOS nodella on vasemmanpuoleinen solmu
                if (currentNode.getLeftChild() != null) {
                    // lisätään currentIndexiin vasemmanpuoleisen solmun lasten lukumäärä + 1
                    // (siellä on tämän haaran pienemmät indeksit)
                    currentIndex += currentNode.getLeftChild().childCount + 1;
                }
            }
        }
        return null;
    }

    // ====================================================
    // VOIMME HYÖDYNTÄÄ LASTEN LUKUMÄÄRRÄÄ (END)
    // ====================================================

    @Override
    public int findIndex(Predicate<V> searcher) {
        StackInterface<TreeNode<K, V>> stack = new StackImplementation<>();
        TreeNode<K, V> current = root;
        TreeNode<K, V> parent = null;
        int index = 0;

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                parent = current;
                current = current.getLeftChild();
            } else {
                parent = stack.pop();
                current = parent.getRightChild();
                if (searcher.test(parent.getValue())) {
                    return index;
                }
                index++;
            }
        }
        return index;
    }

    @Override
    public void accept(Visitor<K, V> visitor) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }
}
