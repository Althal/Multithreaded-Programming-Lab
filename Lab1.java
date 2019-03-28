/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

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
                
                System.out.println("Czas stworzenia w¹tku " + (i+1) + ": " + (stop - start));
            }
        }
        
        public void addThreadToFactory(){
            threads.add(new ThreadExample());
        }
    }
    
    private static class TimeComparator {
        private int edges;
        private double sequenceTime;
        private double multithreadTime;
        private double acceleration;

        public TimeComparator(int edges, double sequenceTime, double multithreadTime) {
            this.edges = edges;
            this.sequenceTime = Math.round(sequenceTime*100)/100000000.0;
            this.multithreadTime = Math.round(multithreadTime*100)/100000000.0;
            this.acceleration = acceleration();
        }        

        public final double acceleration(){
            return Math.round(((sequenceTime-multithreadTime) / sequenceTime)*10000)/100.0;
        }
        
        public double getAcceleration(){
            return acceleration;
        }
        
        public int getEdges(){
            return edges;
        }
        
        @Override
        public String toString(){
            StringBuilder ret = new StringBuilder();
            ret.append("Krawêdzi:          ").append(edges).append("\n");
            ret.append("Czas sekwencyjnie: ").append(sequenceTime).append(" ms\n");
            ret.append("Czas wielow¹tkowo: ").append(multithreadTime).append(" ms\n");
            ret.append("Zysk czasowy:      ").append(acceleration()).append("%\n\n");
            return ret.toString();
        }
    }

    private static int countEdgesSequence(int n, boolean[][] A){
        int i,j,k = 0;
        for(i=0;i<n-1;i++) for(j=i+1;j<n;j++) if(A[i][j]) k++;
        return k;
    }
    
    private static int countEdgesMultithreaded(int n, boolean[][] A) throws InterruptedException{
        Thread[] t = new Thread[n-1];
        int[] c = new int[n-1];
        for(int i=n-2;i>=0;i--) {
            final int i2 = i;
            t[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    int count = 0;
                    for(int j=i2+1;j<n;j++) if(A[i2][j]) count++;
                    c[i2] = count;
                }
            });
        }
        for(Thread th : t) th.start();
        for(Thread th : t) th.join();
        
        int k = 0;
        for(int p : c) k+=p;
        
        return k;
    }
    
    private static void exportToXLS(ArrayList<TimeComparator> t, String path) throws IOException, WriteException{
        WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
        WritableSheet sheet = workbook.createSheet("Przyspieszenia", 0);
        
        Label label = new Label(0,0,"Liczba krawêdzi");
        sheet.addCell(label);
        
        Label label1 = new Label(1,0,"Przyspieszenie (%)");
        sheet.addCell(label1);
        
        for(int i=0;i<t.size();i++){
            Number n1 = new Number(0,i+1,t.get(i).getEdges());
            sheet.addCell(n1);
            
            Number n2 = new Number(1,i+1,t.get(i).getAcceleration());
            sheet.addCell(n2);
        }
        
        workbook.write();
        workbook.close();
    }
    
    private static int inc(int d){
        if(d<1000) return d+=10;
        else return d+=100;
    }

    
    public static void main(String[] args) throws InterruptedException {
        int numerZadania = 1;
        
        if(numerZadania == 0){
            int i = 20;
            ThreadFactory t = new ThreadFactory(i); 

            while(true){
                t.addThreadToFactory();
                i++;
                System.out.println(i);
            }
        }
        
        else if(numerZadania == 1){
            final int n = 30000;
            boolean[][] A = new boolean[n][n];
            
            // Losowanie krawêdzi
            long rand = System.currentTimeMillis();
            for(int i=0;i<(n*n);i++){
                if(i == 0) A[0][0] = true;
                else {if(rand % i != 0) A[i/n][i%n] = true;}
            }
            
            int resultSeq = 0;
            int resultMth = 0;
            
            // Metoda
            ArrayList<TimeComparator> times = new ArrayList<>();
            int d = 10;
            while(d <= n){
                
                long start = System.nanoTime();
                for(int i=0; i<10; i++){
                    resultSeq = countEdgesSequence(n,A);
                }
                long stop = System.nanoTime();
                
                long start1 = System.nanoTime();
                for(int i=0; i<10; i++){
                    resultMth = countEdgesMultithreaded(n,A);
                }
                long stop1 = System.nanoTime();
                
                TimeComparator t = new TimeComparator(d,(stop-start)/10,(stop1-start1)/10);
                //System.out.println(t);
                times.add(t);
                
                d = inc(d);
            }
            
            try {
                exportToXLS(times,"D:\\Test\\test.xls");
            } catch (IOException | WriteException ex) {
                Logger.getLogger(Lab1.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }   
}
