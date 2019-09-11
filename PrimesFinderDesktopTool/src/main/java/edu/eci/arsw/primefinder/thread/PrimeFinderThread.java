/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder.thread;

import edu.eci.arsw.primefinder.PrimeFinder;
import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author 2121915
 */
public class PrimeFinderThread extends Thread{
    public static Collection<BigInteger> primePortion = new LinkedList<>();
    BigInteger desde;
    BigInteger hasta;
    
    public PrimeFinderThread(BigInteger desde, BigInteger hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public void run() {
        primePortion = PrimeFinder.findPrimesPortion(hasta, desde);        
    }
    
    
}
