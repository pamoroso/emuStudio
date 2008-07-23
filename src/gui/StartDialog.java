/*
 * StartDialog.java
 *
 * Created on Sobota, 2008, júl 5, 12:15
 */

package gui;

/**
 *
 * @author  vbmacher
 */
public class StartDialog extends javax.swing.JDialog {
    private ResponseEnum res = ResponseEnum.exit;
    
    public enum ResponseEnum  {
        newArch, openArch, exit
    }
    
    /** Creates new form StartDialog */
    public StartDialog() {
        super();
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
    }

    public ResponseEnum getRes() { return this.res; }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JButton btnNewArch = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JButton btnOpenArch = new javax.swing.JButton();
        javax.swing.JButton btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("emuStudio Platform");
        setResizable(false);

        btnNewArch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/emu8/newArch.gif"))); // NOI18N
        btnNewArch.setText("New architecture");
        btnNewArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewArchActionPerformed(evt);
            }
        });

        jLabel1.setText("Choose one of the options:");

        btnOpenArch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/emu8/openArch.gif"))); // NOI18N
        btnOpenArch.setText("Open/edit architecture");
        btnOpenArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenArchActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNewArch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(btnOpenArch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnNewArch, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOpenArch, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
    res = ResponseEnum.exit;
    dispose();
}//GEN-LAST:event_btnExitActionPerformed

private void btnOpenArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenArchActionPerformed
    res = ResponseEnum.openArch;
    dispose();
}//GEN-LAST:event_btnOpenArchActionPerformed

private void btnNewArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewArchActionPerformed
    res = ResponseEnum.newArch;
    dispose();
}//GEN-LAST:event_btnNewArchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
