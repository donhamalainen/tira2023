# Raportit tehtävistä

Kirjaa tähän tiedostoon **jokaiseen** tehtävään liittyvät omat raporttisi ja analyysisi. Muista että raportti on myös kurssilla **arvosteltava tehtävä**.

Voit sisällyttää raporttiin tekstimuotoisia taulukoita (tasaukset välilyönnein):

```
n     Fill     Search   Total
500   7        700      707
1000  9        288      297
```

Ja näihin liittyviä kuvatiedostoja:

![Esimerkkikuva](report-sample-image.png)

Nämä näkyvät sitten VS Coden Preview -näkymässä (tai oman repositorysi webbisivulla) oikein muotoiltuna. Käytä tässä dokumentissa olevia muotoiluja esimerkkinä kun kirjoitat raporttiasi. 

Huomaa että jos laitat kuvatiedostot vaikka omaan alihakemistoonsa, Markdown -muotoilussa on oltava suhteellinen polku tiedostoon, esimerkiksi `images/report-sample-image.png`. **Älä** käytä absoluuttisia polkuja `C:\Users\tippaleipa\kurssit\TIRA\kuva.png`, koska nämä eivät tietenkään toimi opettajan koneella. Ei kannata laittaa linkkiä etärepoosikaan, vaan nimenomaan paikalliseen tiedostoon.

Voit myös sisällyttää *lyhyitä* koodinpätkiä vaikkapa Java -formaatilla:

```Java
	@Override
	public int hashCode() {
		// Oma nerokas hajautufunktioni!
	}
```
Tarvittaessa käytä myös paremmin muotoiltuja taulukoita:

| n	| Fill	| Search	| Total |
|-----|--------|--------|-------|
| 500	 | 7	| 700	| 707 |
| 1000 |	9	| 288	| 297 | 

Alaluvut jokaisen tehtävän raportille löydät alta.


## 01-TASK

Kävin ensimmäisessä tehtävässä läpi insertion sort algorytmiä. Algorytmin rakentaminen ei ollut omasta puolestani haastavaa eikä edellyttänyt paljoakaan aikaa sen kokonaisuuden ymmärtämiseen. Haastavin asia oli sisäistää tai ymmärtää koko projektin toiminnallisuus. 

- Insetion Sort algorytmi on yleensä O(n^2) eli neliöllinen (quadratic / kvadraattinen), mutta voi myös olla lineaarinen O(n) riippuen JSON tai datan esitysjärjestyksestä.
- Reverse - algorytmi eli tässä tapauksessa metodi joka kääntää taulukon ympäri on aikakompleksisuutena O(n) eli lineaarinen.

Raportin kysmyksessä oli "Jos taulukko on jo valmiiksi järjestyksessä nousevaan järjestykseen, ja se aiotaan lajitella laskevaan järjestykseen, kannattaako taulukko lajitella vai kääntää sen järjestys? Miksi, perustele?"

Tässä tapauksessa Reverse metodi on suositeltavampaa koska sen aikakompleksisuus on lineaari O(n) kun taas insertion sort on O(n) tai O(n^2) riippuen taulukon koosta.

## 02-TASK

### Graaffi analyysi
Tehtävässä 2, askeleessa 3 tehdyssä tehtävässä sain tarkasteltavaksi arvoja, jotka ovat riippuvaisia algorytmistä insertionSort ja myös oman koneeni suoritustehosta. X-akselilla on datan kasvu (n). Huomamme että "Fill" tekee jonkinlaisen pudotuksen alussa, mutta on muutoin lineaarinen kun "Search" on taas lähes eksonentiaalinen. Kuvaajassa nähdään "Fill" ja "Search" risteyskohta, sekä pieniä "droppeja" graafissa. Uskon että nämä "dropit" graaffissa johtuvat tietorakenteen järjestyksestä jolloin hakuun voi mennä vähemmän aikaa. Risteyskohdasta en ole täysin varma.

![Fill & Search Chart](/student_images/task2_step3_chartImage_DonH.png)

![Data](/student_images/task2_step3_dataImage_DonH.png)

### Tehtävä raportti
Lineaarista hakualgorytmiä kutsutaan lineaariseksi, koska se käy tietorakenteen (esim. taulukon tai listan) läpi elementti kerrallaan alusta loppuun asti tai kunnes etsittävä kohde on löydetty. Lineaarisen haun aikakompleksisuusluokka big-O -notaatiolla esiteltynä on O(n), missä n on tietorakenteen pituus (elementtien lukumäärä). Tämä tarkoittaa että algorytmin suoritusaika kasvaa suoraan verrannollisesti tietorakenteen pituuteen (n). Luomamme insertionSort algorytmi ei ole lineaarinen vaan kvadraattinen O(n^2) riippuen tietorakenteen järjestyksestä ja pituudesta. SimpleContaineriin tehdyt muutokset kuten E get() jossa on yksinkertainen for-silmukka on lineaarinen.


## 03-TASK

Tehtävän tekeminen tarjosi merkittävän mahdollisuuden oppia lisää binäärisestä hakualgorytmistä. Opin että binäärinenhaku on tehokas tapa löytää tietty elementti järjestetystä tietorakenteesta, kuten taulukosta tai listasta. Sen periaate perustuu jatkuvaan puolittuvaan hakuun, jossa keskipiste määritellään ja vertaillaan haettavaan arvoon eli avainarvoon.

* Mikä oli haasteellista?
	* Iteratiivisen binäärihaun toteutus oli omastamielestäni yksinkertainen, kiitos visuaalisen esimerkin. Kuitenkin vaikeutta tuotti
	rekurssiivinen haku tuotti erityisiä haasteita pino ylivuodon takia. 

Toteutettu hakualgorytmejä kutsutaan lineaariseksi, koska ne käyvät tietorakennetta läpi yksikerrallaan. Tämä tarkoittaa että sen aikakompleksisuus on neliöllinen O(n^2) ja suoritusaika on suoraan verrannollinen tietorakenteen kokoon. Toteuttamani binäärihakualgorytmi on aikakompleksisuudeltaan O(log n), mikä tarkoittaa että sen suoritusaika kasvaa hitaasti, kun tietorakenteen koko kasvaa. Tämä tekee siitä erityisen tehokkaan tietorakenteen. 

### Graaffi analyysi

Graaffissa olevat sininen (Series1 = Fill) ja oranssi (Series2 = Search) viiva kuvaavat kuinka binäärihakualgorytmi suoritutuu tehtävästä. Huomataan erityisen hyvin kuinka oranssi viiva tasaisena vaikka Fill kasvaa. Tästä voidaan tehdä johtopäätös kuinka tehokas binäärihakualgorytmi oikeasti on isommillakin tietorakenteilla. Se pystyy löytämään halutun elementin suuremmastakin tietorakenteesta

![Fill & Search Chart](/student_images/task3_chartImage_DonH.png)

## 04-TASK

Kirjaa raporttiisi RAPORTTI.markdown mitä opit tehtävän tekemisestä, mikä oli vaikeaa, mikä helppoa, jne.
Analysoi erityisesti sitä, onko toteutuksesi sellainen että se vastaa tehtävän alussa esiteltyjä aikakompleksisuusvaatimuksia. Perustele miksi.
Miten tarkistin toimii jos lainausmerkit tekstissä ovat väärin (niitä puuttuu tai on liikaa)? Analysoi algoritmin oikeellisuutta tässä tilanteessa.
Mikä tahansa metodi jossa on silmukka, ei voi olla O(1) -- onko sinulla silmukoita metodeissa joissa vaatimus oli O(1)? Tai kutsutko tällaisesta metodista jotain toista metodia jonka O on muuta kuin O(1)? Jos näin on, aikakompleksisuusvaatimus ei täyty.

### Tehtävä raportti

Tehtävässä toteutin StackImplementation tiedoston ja ParenthesisChecker tiedostossa olevan metodin checkParentheses(). 

* Haasteita:
	- Koin itse tehtävän toisen osan haasteelliseksi, koska en oikein ymmärtänyt tehtävänantoa kunnolla, mutta muutamien tuntien jälkeen idea alkoi selventyä. Muutoin tehtävä oli aika simppeli. 
	- Toinen haaste joka ilmeni StackImplementation oli aluksi se että mitä metodeilta halutaan. Kieltämättä myös omaa tyhmyyttäni että en osannut heti liittää näitä termistoja, mutta muutoin tehtävä oli helppo. En oikein kyllä tiedä miten näitä aikakompleksisuuksia testaisin, mutta uskon että olen ottanut ne huomioon parhaani mukaan.

Uskon onnistuneeni tehtävänannossa mainittujen aikakompleksisuuksien osalta, vaikkakin niiden tarkistusta en oikein osannut toteuttaa tässä tilanteessa. Otin aikakompleksisuuden huomioon esim siten että en hyödyntänyt capacity(), size() tai muissa "laskennallisissa" metodeissa for-silmukkaa tai muitakaan silmukallisia lauseita jotka pitkittäisivät aikakompleksisuutta huomattavasti. Nyt olen varma että aikakompleksisuus on yksi:yhteen. 

Toisen tehtävän tarkistus (")-merkeille aktivoi boolean omaavan muuttujan joka toimii "tarkistajana" ohjelmassa. Ohitan sen alla olevat koodit seuraavaan iteraatioon asti (continue;) komennon avulla. Kuitenkin, jos muuttuja saa arvoksi (")-merkin niin muuttaa se boolean arvon true arvoksi jolloin if()- ehtolause päästää lukijan continue; lauseeseen. 

En ole toteuttanut silmukallisia lauseita ohjelmassa, jossa aikakompleksisuus vaatimus on O(1). 

## 05-TASK

Kirjaa raporttiisi RAPORTTI.markdown mitä opit tehtävän tekemisestä, mikä oli vaikeaa, mikä helppoa, jne.
Jos toteutit tässä molemmat tietorakenteet, mainitse siitä raportissasi, jotta opettajat huomaavat testata ne ja antaa tehtävästä lisäpisteet!
Vaikket olisi toteuttanutkaan molempia vaihtoehtoisia toteutuksia jonosta (taulukko ja linkitetty lista), pohdi miten ne eroavat toisistaan:

Missä asioissa linkitetty lista on parempi kuin taulukkopohjainen toteutus, muistin käytön (muistikompleksisuus) ja aikatehokkuuden (aikakompleksisuus) suhteen?
Missä asioissa taulukkopohjainen toteutus päihittää linkitetyn listan, muistin käytön ja aikatehokkuuden suhteen?

Analysoi erityisesti sitä, onko toteutuksesi oikeasti sellainen että se vastaa tehtävän alussa esiteltyjä aikakompleksisuusvaatimuksia.

### Tehtävä raportti

Tehtävässä toteutin jono-tietorakenteen, jossa pääsin syventymään tarkemmin sen periaatteisiin. Opin "karusellimäisen" ajattelutavan tietorakenteen lukemisesta. Toteuttamani jono-tietorakenne hyödyntää (tail) ja (head) muuttujia jotka kuvaavat tietorakenteessa olevia kohtia. Tail muuttuja lisää enqueue() metodin avulla tietorakenteeseen alkion, ja vaihtaa paikkaa seuraavaan tietorakenteen kohtaan. Head taas poistaa dequeue() metodin avulla tietorakenteesta alkion. Nämä kaksi yhdessä toimivat karusellimäisesti eli jos tietorakenne ei ole täynnä niin tail siirtyy vasemmalta katsottuna seuraavaan tyhjään indexi kohtaan.

Metodit enqueue() ja dequeue() ovat big-O -notaatiolla O(1), koska ne eivät sisällä minkäänlaisia silmukoita. Ohjelma sisältää silmukan ainoastaan reallakointi funktiossa reallocate().

Ero linkitetyssä ja taulukkopojaisessa jono-tietorakenteessa on muistin käyttö. Linkitetyssä muistia ei varata enempää mitä on tarvetta varata, kun taas taulukkopohjaisessa annetaan, jonkinlainen aloitus muistiarvo kuten tässä DEFAULT_STACK_SIZE = 10. 
Reallakoidessa tämä tuplataan.

Olen yrittänyt toteuttaa tässä tehtävässä linkitetyn jono-tietorakenteen ja se löytyy nimellä LinkedListQueue.java /student/ kansiosta. Tämä rakennelma ei kuitenkaan toimi.

## 06-TASK

## Tehtävä raportti

Tehtävässä suoritin *Quick Sort*, *Heap Sort* algorytmin, jotka olivat eritasoisesti haastavia. *Heap Sort* algorytmin toteuttaminen oli haastavaa, mutta sen perusperiaatteen ymmärsin todella nopeasti ja paperilla se oli yksinkertainen. *Quick Sort* algorytmin toteuttaminen oli huomattavasti yksinkertaisempi, sillä se muistutti todella paljon toimintaperiaatteeltaan *Insertion Sort* algorytmiä. Toteuttamieni algorytmien aikakompleksisuudet ovat seuraavanlaiset:

- *Quick Sort* algorytmin aikakompleksisuus on O(n log n)
- *Heap Sort* algorytmin aikakompleksisuus on O(n log n)

Alhaalla näkyvissä graaffisissa esityksissä huomaamme että tekemämme algorytmit suoriutuvat lähes yhtätehokkaasti myös suurilla tietokannoilla. Quick Sort tekee kuitenkin huomattavasti vähemmän siirtoja verrattuna Heap Sort algorytmiin, joka joutuu suorittamaan keko (heapify) prosessia jatkuvasti. Kuitenkin Heap Sort on vakaampi ja johdonmukaisempi suorituksen lopputuloksen kannalta kuin Quick Sort. Loppupeleissä kumpikin toimivat todella hyvin.

## Algortmien ajalliset tulokset

### Hidas lajittelu
```
Test#	   Count	ms      	ms/element
  1	     100	       9	   0,090
  2	    1000	      70	   0,070
  3	    5000	     497	   0,099
  4	   10000	    1648	   0,165
  5	   50000	  101441	   2,029
  6	  100000	  353668	   3,537
```

![Slow Sorting](/student_images/task6_slowSorting.png)

### Quicksort - lajittelualgorytmi
```
Test#	   Count	ms      	ms/element
  1	     100	       5	   0,050
  2	    1000	       7	   0,007
  3	    5000	       8	   0,002
  4	   10000	      18	   0,002
  5	   50000	      98	   0,002
  6	  100000	     203	   0,002
  7	 1000000	    3154	   0,003
  8	 2000000	    7173	   0,004
```

![Quick Sorting](/student_images/task6_quickSort.png)

#### Heap sort - lajittelualgorytmi

```
Test#	   Count	ms      	ms/element
  1	     100	       6	   0,060
  2	    1000	       8	   0,008
  3	    5000	       9	   0,002
  4	   10000	      17	   0,002
  5	   50000	     170	   0,003
  6	  100000	     347	   0,003
  7	 1000000	    6362	   0,006
  8	 2000000	   14579	   0,007
```
![Quick Sorting](/student_images/task6_heapSort.png)

## 07-TASK

### Tehtävä raportti

Toteutin binäärisen hakupuun rakenteen "D" metodia hyödyntäen, joka oli todella iso parannus aikaisempaan in-order metodiin nähden.
Nopeamman "D" metodin aikakompleksisuus on O(log n) ja in-order:n aikakompleksisuus oli O(n). Huomasin eron esimerkiksi BSTPerformanceTests.java testauksessa, johon minulla meni enemmän aikaa mitä ikä ja terveys olisi kestänyt. Kuitenkin vertailun vuoksi kyselin kavereiltani kauanko he ajoivat koodia, niin keskiarviolta monet sanoivat 4 tunnista - 1 päivään. 

Päätin Refactoroida tämän binäärisen hakupuun ja tehdä sen vaikeamman "D" metodin. D metodin ideana oli vähän sama periaate kuin netistä löytyvästä AVL binäärisellä hakupuulla, jossa ideana oli pitää tallessa jokaisen aikuisen (parentin) alipuiden lukumäärän. Näin ollen kykenimme nopeuttamaan hakua, koska pysytimme ohittamaan tiettyjä puun osia. Luentomateriaalissa olevien pseudokoodien avulla onnistuin muuttamaan getIndex() ja indexOf() metodia onnistuneesti. 

Alla olevassa taulukossa esitetään suorituskykytestin tulokset eri operaatioille binäärisessä hakupuussa.

![Chart](/student_images/task7_bts_chart.png)


## 08-TASK

### Tehtävä raportti
Tehtävässä toteutin hajautustaulun (hashtable) joka on tietorakenne. Tämä tietorakenne mahdollistaa nopean lisäämisen, päivittämisen ja poistamisen antamalla jokaiselle objektille arvon. Tämä arvo on siis liitetty niille vastaaviin arvoihin. Opetellessani ja tutkiessani hajautustaulukon toiminta periaatetta, huomasin että toteutin suljetun hajautetuntaulukon (closed addressing). Hajautustaulu laskee törmäyksiä, luotauksia ja reallakointeja. Aikakompleksisuus tällä algorytmillä on lisäämisessä O(1), jos kollisioita ei tapahdu, mutta jos tapahtuu niin sillon O(n). Opin myös että poistaessa hajautustaulusta, ei saa oikeasti poistaa elementtiä, koska jos hajautustaulu havaitsee null arvoisen elementin niin se ymmärtää että haettavaa elementtiä ei ole taulukossa. Tämän takia poisto pitää tehdä totuusarvon avulla. 

Alla nähtävässä CVS tiedostosta saadusta data:sta voidaan tarkastella nopeutta millisekuntteina. Huomataan mm. nopeus elementtien lisäämisen ja hakemisen aikana. 

![Chart](/student_images/task8_hashcode.png)

Aluksi en oikein ollut kärryillä Hash Table:n toteutuksesta ja koodasinkin tehtävää pitkälti ajattelematta asiaa sen syvemmin. Huomasin kuitenkin tällöin ongelman joka oli törmäysten lukumäärä. Päätin refactoroida koodin ja päädyin lisäämään mm. neliöllisen törmäysten käsittelijän indexFor() metodiin. Käytän indexFor() metodissa alkuarvoja jotka parantavat uuden arvon löytämistä. Tein myös hieman muutoksia HashCode() metodiin joka on suuressa osassa tätä tehtävää. Sen toimivuus ratkaisee mm sen että kuinka paljon klusterointia eli ryhmittymistä tai hajavaisuutta tulee taulukkoon lisättäessä. Vaikka tein omasta mielestä todella tehokkaita muutoksia niin tuloksissa se ei näy sillä saan vielä todella paljon törmäyksiä esim:

```
Count: 1000000
Capacity: 1961502
Filled to: 50,98
Collisions: 441252
Pair Updates: 0
Load Factory: 0.6
Max probing: 17
-------------------
Count: 2000000
Capacity: 5021444
Filled to: 39,83
Collisions: 602950
Pair Updates: 0
Load Factory: 0.6
Max probing: 15
```

Huomataan että törmäyksien määrä on todella valtava. Myös Antin tarjoamassa testauksessa saan seuraavanlaisia tuloksia:

```
Hashtable size: 80000, UUID count: 50000, initial collisions: 49655, all collisions: 1229074727
```

Huomasin myös koodissani *Bugin* jota yritin debugata 2pv. Bugi on ikuiseen silmukkaan jääminen add() metodin yhteydessä. Se on satunnainen ja vaihtelee n. 2 yrityksestä 54 yritykseen jolloin se saattaa ilmetä kerran. En tiedä mistä se johtuu ja sen debuggaaminen oli ja on todella vaikeaa, sillä en koskaan tiedä milloin se ilmenee. Kuitenkin huomasin että hashModifier saattaa tällöin olla todella suuriluku kuten 2 miljoonaa tai 5 miljoonaa.

## 09-TASK

### Tehtävä raportti

#### Minimivaatimus:

Toteutin minimivaatimuksessa leveys- ja syvyyshaun. Opin hyödyntämään uudenlaisia kirjastoja kuten HashSet, LinkedList ja Queue. Stack ja ArrayList olivat edellisistä materiaaleista tai tehtävistä tuttuja. Minimivaatimus ei ollut mielestäni uskomattoman vaikea tai haastava ja se oli mielestäni helposti ymmärrettävissä aikaisemman BST-tehtävän takia. Ainoaksi haasteeksi koin peruslogiikan ymmärtämisen kuten mitä kaaren lisääminen tarkoittaa.

Testasin koodia pitkälti debuggaamalla, sekä rakensin ohjelmaa vaiheittain. Tämä edesauttoi todella paljon ohjelman rakenteen ymmärtämistä. Varmistin myös että algorytmi käyttäytyy oikein tiheissä kuin myös harvoissa verkoissa. 

Aikatehokkuudeltaan analyysi on riippuvainen solmujen (S) ja kaarien määrästä (K). Jotkin esim getEdges() metodi voi olla O(1) tai O(K). Leveys- ja syvyyshaku on big O-notaatiolla O(S+K), jos kaikki solmut ovat melkein yhteydessä niin kaari (K) on lähellä S^2.
tässä tilanteessa notaatio on O(n^2).

Huomasin, että verkon täyttäminen suurella aineistolla oli hitaampaa, mikä johtui todennäköisesti Map-rakenteen hakutoimintojen aikatehokkuudesta. Kun tein pieniä muutoksia, kuten käyttämällä erilaisia Map-toteutuksia, havaitsin merkittäviä parannuksia aikatehokkuudessa.

#### Graaffi
![Graaffi](./student_images/Task9-firstGraph.png)

### Lisätehtäviä

Toteutin lisätehtävänä myös Djikstra lyhyenreitin hakualgorytmin, joka mahdollistaa A:sta B:hen lyhimmän reitin.

Djikstran aikakompleksisuus on riippuvainen verkon rakenteesta ja tarkalleen sen tiheydestä/harvuudesta. Tiheässä verkossa, jossa jokainen solmupiste on yhteydessä toiseen solmupisteeseen niin aikavaatimus on O(V^2 log V), missä V on solmupisteiden määrä. Tämä on riippuvainen siitä kun jokaista solmunpisteen naapuria tarkastellaan ja päivitetään prioriteettijonossa, mikä lisää sitten suoritusaikaa.

