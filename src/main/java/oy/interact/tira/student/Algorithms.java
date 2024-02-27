package oy.interact.tira.student;

import java.util.Comparator;

public class Algorithms {

   private Algorithms() {
   }

   ///////////////////////////////////////////
   // S W A P
   ///////////////////////////////////////////
   public static <T> void swap(T[] array, int first, int second) {
      final T temp = array[first];
      array[first] = array[second];
      array[second] = temp;
   }

   ///////////////////////////////////////////
   // I N S E R T I O N S O R T
   ///////////////////////////////////////////
   public static <T extends Comparable<T>> void insertionSort(T[] array) {
      insertionSort(array, 0, array.length);
   }

   public static <T extends Comparable<T>> void insertionSort(T[] array, int fromIndex, int toIndex) {
      for (int tarkasteltava_alkio = fromIndex + 1; tarkasteltava_alkio < toIndex; tarkasteltava_alkio++) {
         T alkio = array[tarkasteltava_alkio];
         int edellinen_alkio = tarkasteltava_alkio - 1;
         // Aloitetaan järjestäminen vasemmalle puolelle
         while (edellinen_alkio >= fromIndex && array[edellinen_alkio].compareTo(alkio) > 0) {
            swap(array, edellinen_alkio, edellinen_alkio + 1);
            edellinen_alkio--;
         }
      }
   }

   public static <T> void insertionSort(T[] array, Comparator<T> comparator) {
      insertionSort(array, 0, array.length, comparator);
   }

   public static <T> void insertionSort(T[] array, int fromIndex, int toIndex, Comparator<T> comparator) {
      for (int tarkasteltava_alkio = fromIndex; tarkasteltava_alkio < toIndex; tarkasteltava_alkio++) {
         T alkio = array[tarkasteltava_alkio];
         int edellinen_alkio = tarkasteltava_alkio - 1;
         while (edellinen_alkio >= 0 && alkio != null && comparator.compare(array[edellinen_alkio], alkio) > 0) {
            swap(array, edellinen_alkio, edellinen_alkio + 1);
            edellinen_alkio--;
         }
      }
   }

   ////////////////////////////////////////////
   // R E V E R S E
   ///////////////////////////////////////////
   public static <T> void reverse(T[] array) {
      reverse(array, 0, array.length);
   }

   public static <T> void reverse(T[] array, int fromIndex, int toIndex) {
      for (int tarkasteltava_alkio = fromIndex; tarkasteltava_alkio < toIndex / 2; tarkasteltava_alkio++) {
         swap(array, tarkasteltava_alkio, (toIndex - 1 - tarkasteltava_alkio));
      }
   }

   ///////////////////////////////////////////
   // B I N A R Y S E A R C H
   ///////////////////////////////////////////

   public static <T extends Comparable<T>> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex) {
      int alinPiste = fromIndex;
      int korkeinPiste = toIndex - 1;

      // Iteratiivinen

      while (alinPiste <= korkeinPiste) {
         int keskiPiste = alinPiste + (korkeinPiste - alinPiste) / 2;
         if (fromArray[keskiPiste].compareTo(aValue) == 0) {
            return keskiPiste;
         } else if (fromArray[keskiPiste].compareTo(aValue) < 0) {
            alinPiste = keskiPiste + 1;
         } else {
            korkeinPiste = keskiPiste - 1;
         }
      }

      // Rekurssiivinen
      /*
       * if (alinPiste <= korkeinPiste) {
       * int keskiPiste = alinPiste + (korkeinPiste - alinPiste) / 2;
       * if (fromArray[keskiPiste].compareTo(aValue) == 0) {
       * return keskiPiste;
       * } else if (fromArray[keskiPiste].compareTo(aValue) < 0) {
       * return binarySearch(aValue, fromArray, keskiPiste + 1, toIndex);
       * } else {
       * return binarySearch(aValue, fromArray, fromIndex, keskiPiste - 1);
       * }
       * }
       */

      return -1; // aValue eli haluttua arvoa ei löytynyt taulukosta.
   }

   public static <T> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex, Comparator<T> comparator) {
      int alinPiste = fromIndex;
      int korkeinPiste = toIndex - 1;
      // Iteratiivinen
      while (alinPiste <= korkeinPiste) {
         int keskiPiste = alinPiste + (korkeinPiste - alinPiste) / 2;
         int vertailija = comparator.compare(fromArray[keskiPiste], aValue);
         // Ehtolause
         if (vertailija == 0) {
            return keskiPiste;
            // Jos negatiivinen
         } else if (vertailija < 0) {
            alinPiste = keskiPiste + 1;
            // Jos muutoin positiivinen
         } else {
            korkeinPiste = keskiPiste - 1;
         }
      }

      return -1; // aValue:ta ei löytynyt taulukosta
   }

   ///////////////////////////////////////////
   // F A S T S O R T
   ///////////////////////////////////////////
   public static <E extends Comparable<E>> void fastSort(E[] array) {
      quickSort(array, 0, array.length - 1, Comparator.naturalOrder());
      // heapSort(array, Comparator.naturalOrder());
      // mergeSort(array, 0, array.length - 1, Comparator.naturalOrder());
   }

   public static <E> void fastSort(E[] array, Comparator<E> comparator) {
      quickSort(array, comparator);
      // heapSort(array, comparator);
      // mergeSort(array, comparator);
   }

   public static <E> void fastSort(E[] array, int fromIndex, int toIndex, Comparator<E> comparator) {

      quickSort(array, fromIndex, toIndex - 1, comparator);
      // heapSort(array, fromIndex, toIndex - 1, comparator);
      // mergeSort(array, fromIndex, toIndex - 1, comparator);
   }

   ///////////////////////////////////////////
   // Q U I C K S O R T
   ///////////////////////////////////////////

   private static <E> int partition(E[] array, int fromIndex, int toIndex, Comparator<E> comparator) {
      // Käytä viimeistä elementtiä pivotina
      E pivot = array[toIndex];
      int edellinen_alkio = fromIndex - 1;

      for (int alkio = fromIndex; alkio < toIndex; alkio++) {
         if (comparator.compare(array[alkio], pivot) <= 0) {
            edellinen_alkio++;

            // vaihdetaan paikkoja
            swap(array, alkio, edellinen_alkio);
         }
      }

      // Vaihdetaan pivot oikeaan paikkaan jälkeen viimeisen "pienemmän" alkion
      swap(array, edellinen_alkio + 1, toIndex);

      return edellinen_alkio + 1; // Palautetaan pivotin uusi indeksi
   }

   public static <E> void quickSort(E[] array, Comparator<E> comparator) {
      if (array == null) {
         throw new IllegalArgumentException("Array cannot be null");
      }

      quickSort(array, 0, array.length - 1, comparator);
   }

   public static <E> void quickSort(E[] array, int fromIndex, int toIndex, Comparator<E> comparator) {
      if (array == null) {
         throw new IllegalArgumentException("Array cannot be null");
      }

      if (fromIndex < toIndex) {
         int pi = partition(array, fromIndex, toIndex, comparator);

         quickSort(array, fromIndex, pi - 1, comparator);
         quickSort(array, pi + 1, toIndex, comparator);
      }
   }

   ///////////////////////////////////////////
   // H E A P S O R T ( MAX-HEAP )
   ///////////////////////////////////////////

   private static int parent(int currentNode) {
      return (int) Math.floor((currentNode - 1) / 2);
   }

   private static int leftChild(int currentNode) {
      return (2 * currentNode) + 1;
   }

   // private static int rightChild(int currentNode) {
   // return (2 * currentNode) + 2;
   // }

   private static <E> void siftDown(E[] array, int start, int end, Comparator<E> comparator) {
      int root = start;

      while (leftChild(root) <= end) {
         int child = leftChild(root);
         int swap = root;
         if (comparator.compare(array[swap], array[child]) < 0) {
            swap = child;
         }
         if (child + 1 <= end && comparator.compare(array[swap], array[child + 1]) < 0) {
            swap = child + 1;
         }
         if (swap == root) {
            return;
         } else {
            swap(array, root, swap);
            root = swap;
         }
      }
   }

   private static <E> void heapify(E[] array, int end, Comparator<E> comparator) {
      int start = parent(end);
      while (start >= 0) {
         siftDown(array, start, end, comparator);
         start--;
      }
   }

   public static <E> void heapSort(E[] array, Comparator<E> comparator) {
      heapSort(array, 0, array.length - 1, comparator);
   }

   public static <E> void heapSort(E[] array, int fromIndex, int toIndex, Comparator<E> comparator) {
      heapify(array, toIndex, comparator);

      while (toIndex > 0) {
         swap(array, toIndex, 0);
         toIndex--;
         siftDown(array, 0, toIndex, comparator);
      }
   }

   ///////////////////////////////////////////
   // M E R G E S O R T
   ///////////////////////////////////////////

   private static <E> void merge(E[] array, int fromIndex, int toIndex, int midIndex, Comparator<E> comparator) {
      // Määritetään apuarrayt vasemmalle ja oikealle puolelle.
      int leftSize = midIndex - fromIndex + 1;
      int rightSize = toIndex - midIndex;

      // Luo väliaikaiset taulukot.
      E[] leftArray = (E[]) new Object[leftSize];
      E[] rightArray = (E[]) new Object[rightSize];

      // Kopioi tiedot väliaikaisiin taulukoihin.
      for (int alkioVasen = 0; alkioVasen < leftSize; alkioVasen++) {
         leftArray[alkioVasen] = array[fromIndex + alkioVasen];
      }
      for (int alkioOikea = 0; alkioOikea < rightSize; alkioOikea++) {
         rightArray[alkioOikea] = array[midIndex + 1 + alkioOikea];
      }

   }

   public static <E> void mergeSort(E[] array, Comparator<E> comparator) {
      if (array == null) {
         throw new IllegalArgumentException("Array cannot be null");
      }
      mergeSort(array, 0, array.length - 1, comparator);
   }

   public static <E> void mergeSort(E[] array, int fromIndex, int toIndex, Comparator<E> comparator) {
      if (array == null) {
         throw new IllegalArgumentException("Array cannot be null");
      }

      if (fromIndex < toIndex) {
         // Otetaan keskipiste
         int keskipiste = fromIndex + (toIndex - fromIndex) / 2;

         // merge sorting
         mergeSort(array, fromIndex, keskipiste, comparator);
         mergeSort(array, keskipiste + 1, toIndex, comparator);

         merge(array, fromIndex, toIndex, keskipiste, comparator);
      }
   }

}
