/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;

public class JavaApplication1 {

    private static class ThreadExample implements Runnable{
        @Override
        public void run(){}                  
    }
    
    private static class ThreadFactory {
        
        private ArrayList<ThreadExample> threads = new ArrayList<>();
        
        public ThreadFactory(int count){
            for(int i=0;i<count;i++){
                
                long start = System.nanoTime();                
                threads.add(new ThreadExample());               
                long stop = System.nanoTime();
                
                System.out.println("Czas stworzenia wątku " + (i+1) + ": " + (stop - start));
            }
        }
    }
    
    public static void main(String[] args) {
        new ThreadFactory(20);       
    }   
}
