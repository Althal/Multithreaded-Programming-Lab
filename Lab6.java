
package pw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.Number;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Lab6 {
    
    private static Random rand = new Random();
    private static int maxValue;
    private static int size;
    private static int[][] matrixFirst;
    private static int[][] matrixSecond;
    private static ArrayList<TimesResult> times = new ArrayList<>();
    
    @Data
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    private static class TimesResult{
        int multithread;
        int simple;
        int size;
    }
    
    private static void init(int s, int max){
        size = s;
        maxValue = max;
        matrixFirst = getMatrix();
        matrixSecond = getMatrix();
    }
    
    private static int[][] getMatrix(){
        int[][] ret = new int[size][size];
        for(int i=0; i<size*size; i++){
            ret[i/size][i%size] = rand.nextInt() % maxValue;
        }
        return ret;
    }
    
    private static void dispMatrix(int[][] matrix){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private static int unitMultiplication(int[] row, int[] column){
        int ret = 0;
        for(int i=0; i<size; i++){
            ret += row[i]*column[i];
        }
        return ret;        
    }
    
    private static int[][] multiplicationMultithread() throws InterruptedException{
        int[][] ret = new int[size][size];
        Thread[] t = new Thread[size*size];
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                final int i1 = i;
                final int j1 = j;
                t[i*size+j] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int[] column = new int[size];
                        for(int k=0; k<size; k++) column[k] = matrixSecond[k][j1];
                        
                        int result = unitMultiplication(matrixFirst[i1],column);
                        ret[i1][j1] = result;
                    }
                });
            }
        }
        
        for(Thread thread : t) thread.start();
        for(Thread thread : t) thread.join();
        
        return ret;
    }
    
    private static int[][] multiplicationSimple() throws InterruptedException{
        int[][] ret = new int[size][size];
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                int[] column = new int[size];
                for(int k=0; k<size; k++) column[k] = matrixSecond[k][j];

                int result = unitMultiplication(matrixFirst[i],column);
                ret[i][j] = result;
            }
        }

        return ret;
    }
    
    private static void exportToXLS(String path) throws IOException, WriteException{
        WritableWorkbook workbook = Workbook.createWorkbook(new File(path));
        WritableSheet sheet = workbook.createSheet("", 0);

        Label label = new Label(0,0,"Rozmiar");
        sheet.addCell(label);

        Label label1 = new Label(1,0,"Czas wielowątkowo");
        sheet.addCell(label1);

        Label label2 = new Label(2,0,"Czas jednowątkowo");
        sheet.addCell(label2);

        for(int i=0;i<times.size();i++){
            Number n1 = new Number(0,i+1,times.get(i).getSize());
            sheet.addCell(n1);

            Number n2 = new Number(1,i+1,times.get(i).getMultithread());
            sheet.addCell(n2);

            Number n3 = new Number(2,i+1,times.get(i).getSimple());
            sheet.addCell(n3);
        }

        workbook.write();
        workbook.close();
    }
    
    public static void main(String[] args) throws InterruptedException, IOException, WriteException{
        long start = System.nanoTime();
        
        for(int i=10; i<=1000; i+=10){
            init(i,1000);
            
            // Wielowątkowo
            long mthStart = System.nanoTime();
            multiplicationMultithread();
            long mthStop = System.nanoTime();
            
            // Jednowątkowo
            long smpStart = System.nanoTime();
            multiplicationSimple();
            long smpStop = System.nanoTime();
                       
            // Czasy
            long timeMth = (mthStop-mthStart)/1000;
            long timeSmp = (smpStop-smpStart)/1000;
            
            TimesResult t = new TimesResult((int)timeMth, (int)timeSmp, i);
            times.add(t);
            
            System.out.println(i);
        }
        
        exportToXLS("D:\\Test\\aaa.xls");

        long stop = System.nanoTime();        
        System.out.println("Czas wykonywania: " + (stop-start)/1000000);
    }
}
