
package pw;

import java.util.ArrayList;
import java.util.Random;

public class Lab5 {
    private static double rate = 0.5;
    private static int[][] g1, g2;
    private static boolean izo = false;
    
    private static int rand(){
        Random rand = new Random();
        int r = rand.nextInt()%100;
        
        if(r>rate*100.0) return 1;
        else return 0;
    }
    
    private static void initGraphs(int n){
        g1 = new int[n][n];
        g2 = new int[n][n];
        
        for(int i=0; i<n; i++) for(int j=0; j<n; j++) g1[i][j] = rand();
        for(int i=0; i<n; i++) for(int j=0; j<n; j++) g2[i][j] = rand();
    }
    
    private static boolean nextPerm(int n,  int[] p) {
        int i,j,x;
        i=n-1;

        while (i>0) { 
            if (p[i]>p[i-1]) { 
                j=n-1;
                
                while (p[j] < p[i-1]) j--;
                
                x=p[j]; p[j]=p[i-1]; p[i-1]=x;
                
                while (i<n) {
                    x=p[i]; p[i]=p[n-1]; p[n-1]=x;
                    i++; n--;
                }
                
                return true;
            }
            i--;
        }
        return false;
    }
    
    private static void ind(int n, int[] p, int[][] g3) {
        int i,j;
        for (i=0;i<n;i++) 
            for (j=0;j<n;j++)
                g3[i][j] = g2[p[i]][p[j]];
    }

    private static int greaterThanG1(int n, int[][] g3) {
        int i,j;
        for (i=0;i<n-1;i++) 
            for (j=i+1;j<n;j++)
                if (g3[i][j]<g1[i][j]) return 1; else 
                  if (g3[i][j]>g1[i][j]) return -1;
        return 0;
    }

    
    private static boolean checkIzomorphismSimple(int n){
        int[][] g3 = new int[n][n];
        for(int i=0; i<n; i++) System.arraycopy(g1[i], 0, g3[i], 0, n);
        
        int[] perm = new int[n];
        for(int i=0; i<n; i++) perm[i] = i;
        
        do {
            ind(n,perm,g3);
            if (greaterThanG1(n,g3)==0) {
                return true;
            }
        } while(nextPerm(n,perm));
        
        return false;
    }
    
    private static boolean checkIzomorphismMultithreaded(int n) throws InterruptedException{
        int[][] g3 = new int[n][n];
        for(int i=0; i<n; i++) System.arraycopy(g1[i], 0, g3[i], 0, n);
        
        int[] perm = new int[n];
        for(int i=0; i<n; i++) perm[i] = i;
        
        ArrayList<Thread> t = new ArrayList<>();
        int threadIndex = 0;
        do {
            if(izo) return true;
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    int[] p = new int[n];
                    System.arraycopy(perm, 0, p, 0, n);
                    
                    ind(n,p,g3);
                    if (greaterThanG1(n,g3)==0) izo = true;                
                }
            });
            t.add(th);
            t.get(threadIndex++).start();
        } while (nextPerm(n,perm));
        
        for(int i=0; i<t.size(); i++) t.get(i).join();
        
        return false;
    }
    
    
    public static void main(String[] args) throws InterruptedException{
        int edges = 5;

        boolean izo = true;
        while(izo){
            initGraphs(edges);
            
            // Wielowątkowo
            long mthStart = System.nanoTime();
            boolean b2 = checkIzomorphismMultithreaded(edges);
            long mthStop = System.nanoTime();
            
            // Jednowątkowo
            long smpStart = System.nanoTime();
            boolean b1 = checkIzomorphismSimple(edges);
            long smpStop = System.nanoTime();
                       
            // Czasy
            long timeMth = (mthStop-mthStart)/1000;
            long timeSmp = (smpStop-smpStart)/1000;

            System.out.println(timeMth + " " + timeSmp);
            izo = !b1;
        }
    }
}
