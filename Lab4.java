package pw;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Lab4 extends javax.swing.JFrame {

    private static int n;
    private static BigInteger nStrVal;
    private static BigInteger countedN;
    private static MyThread pbt;
    private static MyThread ctt;
    private static boolean pause = true;
    private static long timeFromStart = 0;
    private static Lab4 frame = new Lab4();
    
    public Lab4() {
        initComponents();
        progress.setMinimum(0);
        progress.setMaximum(100);
    }
    
    public void setValueOnPrograssBar(int val){
        progress.setValue(val);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        progress = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nVal = new javax.swing.JTextField();
        nStr = new javax.swing.JLabel();
        timeFromBeginning = new javax.swing.JLabel();
        timeToEnd = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jButton3.setText("Start");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("n:");

        jLabel2.setText("n! :");

        nVal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        timeFromBeginning.setText("Czas od rozpoczęcia:");

        timeToEnd.setText("Czas do zakończenia: ");

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Stop");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nVal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timeToEnd)
                                    .addComponent(timeFromBeginning)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nStr)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nStr))
                .addGap(18, 18, 18)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timeFromBeginning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timeToEnd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            n = Integer.parseInt(nVal.getText());
            countedN = BigInteger.valueOf(0);
            pause = false;
            ctt.start();
            pbt.start();
            timeFromStart = 0;
            
            BigInteger str = BigInteger.valueOf(1);
            for(long i = 1; i<n; i++){
                str = str.multiply(BigInteger.valueOf(i));
            }

            nStrVal = str;
            nStr.setText(str.toString());
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
            jButton4.setEnabled(true);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Podana wartość nie jest liczbą!");
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        pause = !pause;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        pause = true;
        pbt = new ProgressBarThread(frame);
        ctt = new CalculatingThread(frame);
        pause = false;
        
        nStrVal = BigInteger.valueOf(0);
        nVal.setText("");
        countedN = BigInteger.valueOf(0);
        setValueOnPrograssBar(0);
        
        timeFromBeginning.setText("Czas od rozpoczęcia: ");
        timeToEnd.setText("Czas od rozpoczęcia: ");
        
        jButton1.setEnabled(true);
        jButton4.setEnabled(false);
        jButton2.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lab4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lab4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lab4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lab4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //Lab4 frame = new Lab4();
                frame.setVisible(true);  
                pbt = new ProgressBarThread(frame);
                ctt = new CalculatingThread(frame);
                frame.jButton4.setEnabled(false);
                frame.jButton2.setEnabled(false);
                frame.progress.setStringPainted(true);
            }
        });
    }
    
    public abstract static class MyThread {
        public Lab4 frame;
        public Thread mainThread;
        
        public MyThread(Lab4 frame) {
            this.frame = frame;
        }
        
        protected void setThread(Thread t){
            mainThread = t;
        }
        
        public void start(){
            if(mainThread != null) mainThread.start();
        }
    }

    public static class CalculatingThread extends MyThread {

        public CalculatingThread(Lab4 frame) {
            super(frame);
            super.setThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    while(countedN.compareTo(nStrVal) == -1) {
                        try {
                            Thread.sleep(50);
                            if(!pause) countedN = countedN.add(BigInteger.valueOf(1));
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Lab4.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }));
        }
    }
    
    public static class ProgressBarThread extends MyThread {

        public ProgressBarThread(Lab4 frame) {
            super(frame);
            super.setThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    while(countedN.compareTo(nStrVal) == -1) {
                        if(!pause){
                            try {
                                frame.timeFromBeginning.setText("Czas od rozpoczęcia: " + timeFromStart + " [s]");
                                timeFromStart++;

                                int progress = (countedN.multiply(BigInteger.valueOf(100)).divide(nStrVal)).intValue();
                                if(progress != 0) frame.timeToEnd.setText("Czas do zakończenia: " + (int)(timeFromStart*(100-progress)/progress) + " [s]");

                                System.out.println(countedN + " " +  nStrVal);
                                frame.setValueOnPrograssBar(progress);
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Lab4.class.getName()).log(Level.SEVERE, null, ex);
                            }   
                        }
                    }
                }
            }));
        }       
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel nStr;
    private javax.swing.JTextField nVal;
    private javax.swing.JProgressBar progress;
    private javax.swing.JLabel timeFromBeginning;
    private javax.swing.JLabel timeToEnd;
    // End of variables declaration//GEN-END:variables
}
