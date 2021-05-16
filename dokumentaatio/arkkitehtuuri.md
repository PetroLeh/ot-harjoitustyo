# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne on seuraava

trackerapp.ui -> trackerapp.domain -> trackerapp.dao

## Käyttöliittymä

Käyttöliittymä sisältää kolme näkymää

- alkunäkymä
- työnäkymä
- tiedoston selaus -näkymä tallennukseen ja tiedoston avaamiseen.

Alkunäkymä ja työnäkymä on toteutettu Scene-olioina ja tiedostonkäsittelyyn tarkoitetut näkymät
on toteutettu JavaFX:n FileChooser-luokan oliona.

# Sovelluslogiikka

Sovelluksen toiminnallisuudesta vastaavat luokat _TrackerService_ ja _Player_, joista Player myös tukee paljon
TrackerServiceen ja sen yksinomaisena tehtävänä on lähinnä aloittaa ja lopettaa kappaleen läpikäynti, kun sitä toistetaan.

TrackerService tarjoaa metodit teosten käsittelyyn. Käyttöliittymässä tapahtuva teosten muokkaaminen tapahtuu TrackerService-
luokan set- (_setSelectedObject()_, _setBpm()_) ja add-metodeilla (_addObject()_, _addRow()_). Player-luokka pääsee käsiksi teoksen
objekteihin TrackerService-luokan get-metodeilla ja käyttää luokan _activate()_-metodia äänten soittamiseen.

TrackerService käyttää teosten luomiseen, avaamiseen ja tallentamiseen _trackerapp.dao_-pakkauksen _MasterpieceDao_-rajapinnan
touteuttavaa _FileMasterpieceDao_-luokkaa, joka injektoidaan sille käyttöliittymästä.

Instrumenttien hallintaan TrackerService käyttää trackerapp.dao-pakkauksen _FileInstrumentLibraryDao_-luokkaa. Instrumenttikirjastosta
lisää kohta.

# Datarakenne

teoksen rakenne:
<img src="https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/masterpiece.png">

Käyttöliittymässä käsitellään yhtä teosta kerrallaan. Teos koostuu listasta TrackContainer-olioita, joissa jokaisessa puolestaan on
taulukko TrackObject-rajapinnan toteuttavia olioita. Käytännössä tässä ohjelman versiossa se tarkoittaa InstrumentObject-olioita.
TrackObject-oliolla on id-tunnus ja metodi _activate()_, jota kutsutaan, kun teosta toistetaan. InstrumentObject-olioilla on
lisäksi niihin linkitetty AudioClip-äänileike.

Äänileikkeiden linkittämiseen käytetään InstrumentLibraryDao-luokkaa. Luokka pitää sisällään hajautustaulun, johon on
talletettu jokaisen instrumentin äänikirjastot ja niiden id-tunnukset.

# Toiminnallisuus

Seuraavassa kuvattuna objektin lisääminen sekvenssikaaviona:
<img src="https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/objektinlisaaminen.png">
