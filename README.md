# Ohjelmistotekniikan harjoitustyö

Pienimuotoinen musisointi/sämpläys-ohjelma. Suppea instrumenttivalikoima (ainakin toistaiseksi), mutta
instrumenttikirjastoa voi vaikka itse laajentaa. Äänitiedostot, joita ohjelma käyttää sijatsevat hakemiston audio/_instrumentinNimi_/ alla.
Itse kirjastotiedosto on instruments.csv.

## Testaus

Testit saa suoritettua komennolla

```
mvn test
```

Testikattavuusraportin saa komennolla

```
mvn jacoco:report
```

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

## releaset

 - [viikko5](https://github.com/PetroLeh/ot-harjoitustyo/releases)

## dokumentaatio
 - [vaatimusmäärittely](https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
 - [työaikakirjanpito](https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
 - [luokkakaavio](https://github.com/PetroLeh/ot-harjoitustyo/blob/master/dokumentaatio/trackerLuokkakaavio.jpg)
