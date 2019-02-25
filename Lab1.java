/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;

public class Lab1 {

    private static class ThreadExample implements Runnable{
        @Override
        public void run(){while(true){}}                  
    }
    
    private static class ThreadFactory {
        
        private ArrayList<ThreadExample> threads = new ArrayList<>();
        
        public ThreadFactory(int count){
            for(int i=0;i<count;i++){
                
                long start = System.nanoTime();                
                threads.add(new ThreadExample());
                new Thread(threads.get(i)).start();
                long stop = System.nanoTime();
                
                System.out.println("Czas stworzenia wątku " + (i+1) + ": " + (stop - start));
            }
        }
        
        public void addThreadToFactory(){
            threads.add(new ThreadExample());
        }
    }
    
    public static void main(String[] args) {
        int i = 20;
        ThreadFactory t = new ThreadFactory(i); 
        
        while(true){
            t.addThreadToFactory();
            i++;
            System.out.println(i);
        }
    }   
}
