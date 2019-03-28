package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Lab2 {
    private static int value;
    private static boolean valueFounded = false;
    private static int[] tableOneDim;
    private static int[][] tableTwoDim;
    
    private static class TimeComparator {
        private int edges;
        private double[] times;
        private double acceleration;

        public TimeComparator(int edges, double times[]) {
            this.edges = edges;
            
            double[] pom = new double[10];
            for(int i=0;i<10;i++) pom[i]= Math.round(times[i]*100)/100000000.0;
            this.times = pom;
                    
            this.acceleration = acceleration();
        }    
        
        private double getBestTime(){
            double min = times[1];
            double[] pom = Arrays.copyOfRange(times,2,10);           
            for(double e : pom) if (e<min) min = e;
            return min;
        }
        
        private int getBestThreadValue(){
            double min = times[1];
            int value = 1;
            double[] pom = Arrays.copyOfRange(times,2,10);           
            for(int i = 1; i<pom.length;i++){ 
                if (pom[i]<min) min = pom[i];
                value = i;
            }
            
            return value+1;
        }

        public final double acceleration(){
            double min = getBestTime();
            return Math.round(((times[0]-min) / times[0])*10000)/100.0;
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
            
            double min = getBestTime();
            
            ret.append("Krawêdzi:          ").append(edges).append("\n");
            ret.append("Czas sekwencyjnie: ").append(times[0]).append(" ms\n");
            ret.append("Czas wielow¹tkowo: ").append(min).append(" ms\n");
            ret.append("Zysk czasowy:      ").append(acceleration()).append("%\n\n");
            return ret.toString();
        }
    }
    
    private static void setValue(int val){
        value=val;
    }
    
    private static void setTableOneDim(int elements, int maxValue){
        Random r = new Random();
        
        int[] integers = new int[elements];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = r.nextInt(maxValue);
        }
        
        tableOneDim = integers;
    }
    
    private static void checkValue(int value, int[] table){
        for(int i : table){
            //System.out.println(i);
            if(valueFounded) return;
            if(i == value){
                valueFounded = true;
                //System.out.println("Znaleziono");
                return;
            }
        } 
    }
    
    private static void searchOneDim(int threadCount) throws InterruptedException{
        if(threadCount == 0 && threadCount == 1) checkValue(value, tableOneDim);
        else{
            Thread[] threads = new Thread[threadCount];
            
            int elements = tableOneDim.length;
            int perThread = (elements/threadCount)+1;
            
            int disposedElements = 0;
            
            // Tworzenie w¹tków
            for(int j=0; j<threadCount; j++){
                int[] arg = (j!=threadCount-1)? 
                                Arrays.copyOfRange(tableOneDim, disposedElements, (disposedElements + perThread)) : Arrays.copyOfRange(tableOneDim, disposedElements, tableOneDim.length);
                
                threads[j] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkValue(value,arg);
                        //System.out.println("Koñczê dzia³anie");
                    }
                });
                
                disposedElements += perThread;
            }
            
            // Uruchamianie w¹tków
            for(int j=0; j<threadCount; j++){
                threads[j].start();
            }
        }
    }
    
    private static void exportToXLS(ArrayList<TimeComparator> t, String path) throws IOException, WriteException{
        WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
        WritableSheet sheet = workbook.createSheet("Przyspieszenia", 0);
        
        Label label = new Label(0,0,"Liczba krawêdzi");
        sheet.addCell(label);
        
        Label label1 = new Label(1,0,"Przyspieszenie (%)");
        sheet.addCell(label1);
        
        Label label2 = new Label(2,0,"Opt. liczba w¹tków");
        sheet.addCell(label2);
        
        for(int i=0;i<t.size();i++){
            Number n1 = new Number(0,i+1,t.get(i).getEdges());
            sheet.addCell(n1);
            
            Number n2 = new Number(1,i+1,t.get(i).getAcceleration());
            sheet.addCell(n2);
            
            Number n3 = new Number(2,i+1,t.get(i).getBestThreadValue());
            sheet.addCell(n3);
        }
        
        workbook.write();
        workbook.close();
    }
    
    public static void main(String[] args) throws InterruptedException{
        setValue(53);

        int elements = 100;
        final int maxElements = 100000;
        
        // Metoda
        ArrayList<TimeComparator> times = new ArrayList<>();
        while(elements <= maxElements){
            setTableOneDim(elements,1000);
            double[] tim = new double[10];
            
            for(int i=1; i<=10; i++){
                long start = System.nanoTime();
                
                for(int j=0; j<10; j++) {
                    searchOneDim(i);
                }
                
                long stop = System.nanoTime();
                tim[i-1] = (stop - start)/10;
            }

            TimeComparator t = new TimeComparator(elements,tim);
            //System.out.println(t);
            times.add(t);

            elements += 100;
        }
        
        try {
            exportToXLS(times,"D:\\Test\\testL2D1.xls");
        } catch (IOException | WriteException ex) {
            Logger.getLogger(Lab1.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}
