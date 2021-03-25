package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class KassapaateTest {

    Kassapaate paate;

    @Before
    public void setUp() {
        paate = new Kassapaate();       
    }

    @Test
    public void luotuKassapaateOnOikein() {
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiKateisellaToimii() {
        assertEquals(200, paate.syoEdullisesti(200));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());

        assertEquals(60, paate.syoEdullisesti(300));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100240, paate.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiKateisellaToimii() {
        assertEquals(300, paate.syoMaukkaasti(300));
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());

        assertEquals(50, paate.syoMaukkaasti(450));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100400, paate.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiKortillaToimii() {
        Maksukortti kortti = new Maksukortti(300);

        assertEquals(true, paate.syoEdullisesti(kortti));
        assertEquals(60, kortti.saldo());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());

        assertEquals(false, paate.syoEdullisesti(kortti));
        assertEquals(60, kortti.saldo());
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void syoMaukkaastiKortillaToimii() {
        Maksukortti kortti = new Maksukortti(450);

        assertEquals(true, paate.syoMaukkaasti(kortti));
        assertEquals(50, kortti.saldo());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());

        assertEquals(false, paate.syoMaukkaasti(kortti));
        assertEquals(50, kortti.saldo());
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void rahanLataaminenKortilleToimiiOikein() {
        Maksukortti kortti = new Maksukortti(0);
        
        paate.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, kortti.saldo());
        
        paate.lataaRahaaKortille(kortti, 10000);
        assertEquals(110000, paate.kassassaRahaa());
        assertEquals(10000, kortti.saldo());   
    }

}
