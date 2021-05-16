# Ohjelmistotekniikan harjoitustyö

Pienimuotoinen musisointi/sämpläys-ohjelma. Suppea instrumenttivalikoima (ainakin toistaiseksi), mutta
instrumenttikirjastoa voi vaikka itse laajentaa. Äänitiedostot, joita ohjelma käyttää sijatsevat hakemiston audio/_instrumentinNimi_/ alla.
Itse kirjastotiedosto on instruments.csv.

## dokumentaatio
 - [vaatimusmäärittely](https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
 - [Arkkitehtuurikuvaus](https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
 - [Testausdokumentti](https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)
 - [Työaikakirjanpito](https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
 
## releaset

 - [viikko5](https://github.com/PetroLeh/ot-harjoitustyo/releases/tag/viikko5)
 - [viikko6](https://github.com/PetroLeh/ot-harjoitustyo/releases/tag/viikko6)
 
## Komentorivitoiminnot

### Testaus

Testit saa suoritettua komennolla

```
mvn test
```

Testikattavuusraportin saa komennolla

```
mvn jacoco:report
```

(alustavan) JavaDocin generointi komennolla

```
mvn javadoc:javadoc
```

### Ohjelman suoritus

Suoritettavan jarin saa generoitua komennolla

```
mvn package
```

Ohjelman saa käynnistettyä komentoriviltä ohjelman juurihakemistosta (jossa tiedosto pom.xml sijaitsee) komennolla

```
java -jar TrackerApp-1.0-SNAPSHOT.jar
```
tai
```
java -jar target/TrackerApp-1.0-SNAPSHOT.jar
```
Ohjelma tulee siis käynnistää sen juurihakemistosta, joka sisältää myös sen käyttämiä tiedostoja.




