package oy.interact.tira.task_08_hashtable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Testisovellus jossa voit toteuttaa omia hajautusfunktioita UUID
 * -merkkijonojen
 * tiivisteiden laskemiseen ja algoritmeja törmäysten käsittelyyn.
 * 
 * Toteuta metodi hashFrom jossa lasket tiivisteen UUID-merkkijonolle.
 * Toteuta myös metodi indexFrom jossa lasketaan tiivisteestä ja
 * törmäyslaskurista
 * indeksi annetun kokoiseen taulukkoon.
 * 
 * Testisovellus tulostaa (tietyillä toteutuksilla):
 * Testing hashing of UUIDs
 * Hashing 50000 UUIDs for array size 80000
 * Hashtable size: 80000, UUID count: 50000, initial collisions: 15542, all
 * collisions: 63342
 * 
 * Jossa
 * - initial collisions tarkoittaa montako kertaa olisi tapahtunut törmäys heti
 * ensimmäiseen laskettun indeksiin elementtiä laitettaessa.
 * - all collisions tarkoittaa, montako kertaa yhteensä törmäys tapahtui,
 * mukaanlukien
 * luodatessa taulukosta uutta indeksiä 1. törmäyksen jälkeen.
 * 
 * Initial collisions kertoo kuinka hyvä hajautusfunktio on. Mitä vähemmän näitä
 * törmäyksiä,
 * sitä paremmin hajautusfunktio hajauttaa arvoja.
 * 
 * All collisions kertoo, kuinka hyvin törmäyksiä hoidetaan eli kuinka paljon
 * (tai vähän)
 * pitää laskea uutta indeksiä 1. törmäyksen jälkeen.
 * 
 * Huomaa että myös taulukon täyttöaste vaikuttaa asiaan. Kyse on kolmen tekijän
 * kokonaisuudesta:
 * - hyvä/huono hajautusfunktio
 * - tehokas/tehoton törmäysten hallinta
 * - taulukon täyttöaste, paljonko se tuhlaa/säästää muistia.
 * 
 * Esimerkkinä muuten täsmälleen sama täyttöaste ja hajautusfunktio, mutta
 * törmäysten hallinta
 * on tehty toisin kuin yllä olevassa tulostuksessa:
 * 
 * Testing hashing of UUIDs
 * Hashing 50000 UUIDs for array size 80000
 * Hashtable size: 80000, UUID count: 50000, initial collisions: 15561, all
 * collisions: 82822
 *
 * Kun vertaat tuota ensimmäiseen tulostukeen, initial collisions on lähes sama,
 * mutta all
 * collisions on reilusti isompi -- törmäysten hallinnassa tapahtui paljon
 * enemmän törmäyksiä
 * törmäysten käsittelyssä, kuin toisessa toteutuksessa. Tässä tämä huonompi
 * toteutus käyttää
 * lineaarista luotaamista, kun taas tehokkaampi törmäysten käsittely käyttää
 * quadratic probing -algoritmia tietyillä vakioilla c1 ja c2 (katso
 * luentokalvot).
 */
public class UUIDHashTestApp {

    // Kuinka monta UUID:tä testissä luodaan.
    private static final int TEST_UUID_COUNT = 50000;
    // Mikä on hajautustaulun load factor, eli paljonko siellä
    // on vapaata suhteessa aineiston kokoon. Tässä taulukko täytettäisiin n.
    // 62%:sti
    // ennen reallokointia. 50 000 elementille tehtäisiin taulukko jonka koko on 80
    // 000.
    private static final double LOAD_FACTOR = 1.60;

    /*
     * Tässä toteutuksessa ei ole varsinaista taulukkoa johon elementtejä
     * laitettaisiin.
     * Sen sijaan tässä käytetään Javan tietojoukko -luokkaa (Set) tarkistamaan onko
     * laskettu indeksi jo käytössä, eli tuliko törmäys (collision). Jos indeksi on
     * jo
     * käytössä (se löytyy Set:stä), jatketaan uuden indeksin laskemista kyseiselle
     * UUID:lle.
     */

    public static void main(String[] args) throws Exception {
        System.out.println("Testing hashing of UUIDs");
        String[] uuids = generateUUIDs(TEST_UUID_COUNT);
        int arraySize = (int) (TEST_UUID_COUNT * LOAD_FACTOR);
        // TÄTÄ EI SAA KÄYTTÄÄ OMASSA TOTEUTUKSESSA niinkuin ei mitään muutakaan Javan
        // säiliöluokkaa!!
        Set<Integer> indices = new HashSet<>();
        System.out.format("Hashing %d UUIDs for array size %d%n", TEST_UUID_COUNT, arraySize);
        int addCollisionCount = 0;
        int totalCollisionCount = 0;
        for (String uuid : uuids) {
            int hash = hashFrom(uuid); // Coder.hashCode
            boolean added = false;
            int loopCount = 0;
            int index;
            do {
                index = indexFrom(hash, loopCount, arraySize);
                added = indices.add(index);
                if (!added) {
                    if (loopCount == 0) {
                        addCollisionCount++;
                        totalCollisionCount++;
                    } else {
                        totalCollisionCount++;
                    }
                }
                loopCount++;
            } while (!added);
            // Voit ottaa alla olevan rivin pois kommenteista jos testaat pienellä taulukon
            // koolla. Näin näet jokaiselle UUID:lle sen tiivisteen ja sen avulla lasketun
            // indeksin.
            System.out.format("%s >> %16d idx: %5d%n", uuid, hash, index);
        }
        final String formatStr = "Hashtable size: %d, UUID count: %d, initial collisions: %5d, all collisions: %5d%n";
        System.out.format(formatStr, arraySize, uuids.length, addCollisionCount, totalCollisionCount);
    }

    // Kokeile toteuttaa erilaisia hajautusfunktiota uuid:n luonteen tuntien.
    // Varmista että törmäyksiä tulisi mahdollisimman vähän.
    private static int hashFrom(final String uuid) {
        // TOSI huono, esim 100 000 koodarin aineistolla:
        // STATS: n: 100000, addCollided 99678, Total collision count: 14637312, max
        // probing steps: 563
        // * + index (hash << 2) xor
        int hash = 31;
        int length = uuid.length();
        for (int index = 0; index < length; index++) {
            int current = uuid.charAt(index); // random
            hash += current;
        }
        return hash;
    }

    // Kokeile erilaisia tapoja huolehtia että törmäyksiä tulisi
    // mahdollisimman vähän. Voit kokeilla lineaarista luotaamista,
    // quadratic probing, tai jotain muuta. Tämä vaikuttaa lähinnä
    // siihen montako kertaa 1. törmäyksen jälkeen tulee lisää törmäyksiä.
    private static int indexFrom(int hash, int collisions, int arraySize) {
        return ((hash + collisions) & 0x7FFFFFFF) % arraySize;
    }

    private static String[] generateUUIDs(int count) {
        String[] uuids = new String[count];
        while (count > 0) {
            uuids[count - 1] = UUID.randomUUID().toString();
            count--;
        }
        return uuids;
    }
}
