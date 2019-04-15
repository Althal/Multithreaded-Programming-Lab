package pw;

import java.math.BigInteger;
import java.util.ArrayList;

public class Lab3 {
    static ArrayList<String> passwords = new ArrayList<>();
    static String alphabet = "abcdefghijklmnoprstuwvxyz";
    
    static String password;
    static int passwordLength;
    
    private static class Producer {        
        public static Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                generate(alphabet, passwordLength, "", alphabet.length());
                Bufor.moreElements = false;
            }
        });
        
        private static void generate(String alphabet, int i, String s, int len) 
        { 
            if (i==0){ 
                if(s == null) System.out.println("wtf");
                passwords.add(s);
                return; 
            } 
            for (int j=0; j<len; j++) {
                if(Bufor.foundedPassword == true) return;
                String appended = s + alphabet.charAt(j); 
                generate(alphabet, i - 1, appended, len); 
            } 
        }
    } 
    
    private static class Bufor {
        private static int lastElement = 0;
        public static boolean moreElements = true;
        private static boolean foundedPassword = false;
        
        public static String getElement(){
            if(lastElement >= passwords.size() && moreElements) {
                //System.out.println("Oczekiwanie na produkcję...");
                System.out.print("");
                return "";
            }
            else if(lastElement >= passwords.size() && !moreElements) {
                System.out.print("");
                return "X";
            }
            else {
                return passwords.get(lastElement++);                               
            }
        }
    }
    
    private static class Consumer {
        public static Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String pass = ""; 
                while(!pass.equals("X")) {
                    if(checkPassword(pass)){
                        Bufor.foundedPassword = true;
                        System.out.println("Znaleziono hasło: " + pass);
                        System.out.println("Wygenerowano kombinacji: " + passwords.size());
                        System.out.println("Wszystkich kombinacji: " + (BigInteger.valueOf((int)alphabet.length()).pow(passwordLength)).toString());
                        break;
                    }

                    pass = Bufor.getElement();
                    if(pass == null) pass = "";

                }
                System.out.println("Zakończono działanie wątku konsumenta.");
            }
        });
        
        private static boolean checkPassword(String pass){
            return password.equals(pass);
        }
    }
    
    private static void setPassword(String password){
        Lab3.password = password.toLowerCase();
        Lab3.passwordLength = password.length();
    }
    
    public static void main(String[] args) throws InterruptedException{
        long start = System.nanoTime();
        
        setPassword("zzzzz");
        
        Producer.mainThread.start();
        Consumer.mainThread.start();
        
        Producer.mainThread.join();
        Consumer.mainThread.join();
        
        
        long stop = System.nanoTime();
        
        System.out.println("Czas wykonywania: " + (stop-start)/1000000);
    }
}
