package edu.eci.arsw.primefinder;

import edu.eci.arsw.math.MathUtilities;
import edu.eci.arsw.primefinder.thread.PrimeFinderThread;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinder {

    

    public static void findPrimesoriginal(BigInteger _a, BigInteger _b, PrimesResultSet prs) {

        BigInteger a = _a;
        BigInteger b = _b;
        MathUtilities mt = new MathUtilities();

        int itCount = 0;

        BigInteger i = a;
        while (i.compareTo(b) <= 0) {
            itCount++;
            if (mt.isPrime(i)) {
                prs.addPrime(i);
            }

            i = i.add(BigInteger.ONE);
        }

    }

    public static void findPrimes(BigInteger _a, BigInteger _b, PrimesResultSet prs) throws InterruptedException {
        System.out.println("entra");
        BigInteger nThreads = new BigInteger("4");
        BigInteger portions = _b.divide(nThreads);
        BigInteger residuo = _b.mod(nThreads);
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger three = new BigInteger("3");
        BigInteger four = new BigInteger("4");
        LinkedList<BigInteger> respuesta = new LinkedList<>();
        ArrayList<PrimeFinderThread> pFThreads = new ArrayList<>();
        
        PrimeFinderThread pft1 = new PrimeFinderThread(_a, portions);
        PrimeFinderThread pft2 = new PrimeFinderThread(portions.add(one), portions.multiply(two));
        PrimeFinderThread pft3 = new PrimeFinderThread(portions.multiply(two).add(one), portions.multiply(three));
        PrimeFinderThread pft4 = new PrimeFinderThread(portions.multiply(three).add(one), portions.multiply(four).add(residuo));

        pFThreads.add(pft1);
        pFThreads.add(pft2);
        pFThreads.add(pft3);
        pFThreads.add(pft4);
        System.out.println("inicia threads");
        pft1.start();
        System.out.println("estado : " + pft1.getState());
        pft2.start();
        pft3.start();
        pft4.start();
        System.out.println("joins");
        pft1.join();
        pft2.join();
        pft3.join();
        pft4.join();

        boolean state = true;
        while (state) {
            if (pft1.getState() == Thread.State.TERMINATED                    
                    && pft2.getState() == Thread.State.TERMINATED
                    && pft3.getState() == Thread.State.TERMINATED
                    && pft4.getState() == Thread.State.TERMINATED) {
                System.out.println("Entra if ciclo");
                for(PrimeFinderThread pft : pFThreads){
                    System.out.println("size pft list : " + pft.primePortion.size());
                    respuesta.addAll(pft.primePortion);
                }
                state = false;
            }
            Thread.sleep(1000);
        }
        for (BigInteger i : respuesta){
            System.out.println("agregar :"+i);
            prs.addPrime(i);
        }
       
    }

    public static LinkedList<BigInteger> findPrimesPortion(BigInteger _a, BigInteger _b) {

        LinkedList<BigInteger> retorno = new LinkedList<>();
        BigInteger a = _a;
        BigInteger b = _b;
        MathUtilities mt = new MathUtilities();

        int itCount = 0;

        BigInteger i = a;
        while (i.compareTo(b) <= 0) {
            itCount++;
            if (mt.isPrime(i)) {
                System.out.println("entra if findprime : "+i);
                retorno.add(i);
            }

            i = i.add(BigInteger.ONE);
        }
        return retorno;
    }

}
