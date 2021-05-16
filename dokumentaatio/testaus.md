# Testausdokumentti

Ohjelmaa on testattu automaattisesti JUnit-yksikkötestein sekä manuaalisesti järjestelmätestein.

## Yksikkötestaus

Ohjelman graafinen käyttöliittymä on jätetty yksikkötestauksen ulkopuolelle.

### Sovelluslogiikka

Sovelluslogiikasta vastaa pääosin TrackerService-luokka, jota testataan TestTrackerService-luokalla.
Kappaleen soittamista hallinnoivalle Player-luokkalle, joka perii JavaFX:n AnimationTimer-luokan ei ole
kirjoitettu yksikkötestejä, minulle osoittautui haastavaksi keksiä millaisia testejä sille kirjoittaisin.
Sekin käyttää kuitenkin TrackerService-luokan palveluja.

### Testauskattavuus

Graafista käyttöliittymää lukuunottamatta testauksen rivikattavuus on 73% ja haaratumakattavuus on 70%.



## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti Linux-ympäristössä.


## Sovellukseen jääneet laatuongelmat

- Uuden teoksen avaamisen/luomisen yhteydessä tai suljettaessa sovellus ei kysy halutaanko muutokset 
työn alla olleeseen teokseen tallentaa, vaan kaikki tallentamaton työ katoaa.
- Käyttöliittymä jäi kömpelöksi.
