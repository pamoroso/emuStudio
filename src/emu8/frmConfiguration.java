/*
 * frmConfiguration.java
 *
 * Created on Streda, 2007, august 8, 8:45
 */

package emu8;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.swing.text.*;

/**
 *
 * @author  vbmacher
 */
public class frmConfiguration extends javax.swing.JFrame {
    private Main cMain;
    private File selectedConfig;
    
    /** Creates new form frmConfiguration */
    public frmConfiguration(Main cMain) {
        this.cMain = cMain;
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    // existing configurations list model
    private class lstModel implements ListModel {
        private String[] allModels;
        public lstModel(int index) {
            emuConfiguration e = cMain.emuConfig;
            if (index == 0) allModels = e.getAllNames(e.configsDir, ".props");
            else allModels = e.getAllNames(e.devicesDir, ".jar");
        }
        public void addListDataListener(ListDataListener l) {}
        public Object getElementAt(int index) {
            return allModels[index].substring(0, allModels[index].lastIndexOf("."));
        }
        public int getSize() {
            if (allModels != null) return allModels.length;
            else return 0;
        }
        public void removeListDataListener(ListDataListener l) {}
    }
    private class cmbModel extends DefaultComboBoxModel {
        private String[] allModels;
        public cmbModel(int index) {
            emuConfiguration e = cMain.emuConfig;
            if (index == 0) allModels = e.getAllNames(e.cpusDir, ".jar");
            else if (index == 1) allModels = e.getAllNames(e.memoriesDir, ".jar");
            else allModels = e.getAllNames(e.compilersDir, ".jar");
        }
        public int getSize() {
            if (allModels == null) return 0;
            return allModels.length;
        }
        public Object getElementAt(int index) {
            return allModels[index].substring(0, allModels[index].lastIndexOf("."));
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneTab = new javax.swing.JTabbedPane();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        lstConfigs = new javax.swing.JList();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        cmbCPUType = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        cmbMemoryType = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
        txtMemorySize = new javax.swing.JTextField();
        javax.swing.JLabel jLabel9 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
        txtConfigName = new javax.swing.JTextField();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        lstDevices = new javax.swing.JList();
        javax.swing.JButton jButton3 = new javax.swing.JButton();
        javax.swing.JButton jButton4 = new javax.swing.JButton();
        javax.swing.JButton jButton5 = new javax.swing.JButton();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        cmbCompiler = new javax.swing.JComboBox();
        javax.swing.JButton buttonOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Microcomputer configuration control");
        setResizable(false);

        lstConfigs.setModel(new lstModel(0));
        lstConfigs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstConfigs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstConfigsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstConfigs);

        jLabel3.setText("Choose one from following configurations:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );

        paneTab.addTab("Existing configuration", jPanel1);

        jLabel1.setText("Create new or edit existing microcomputer architecture configuration.");

        jLabel2.setText("Select CPU type:");

        cmbCPUType.setModel(new cmbModel(0));

        jLabel5.setText("Select memory:");

        cmbMemoryType.setModel(new cmbModel(1));

        jLabel8.setText("Type:");

        jLabel10.setText("Size (in bytes):");

        txtMemorySize.setText("65536");

        jLabel9.setText("Select devices:");

        jLabel11.setText("Enter config file name:");

        lstDevices.setModel(new lstModel(1));
        jScrollPane3.setViewportView(lstDevices);

        jButton3.setText("Clear selection");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Select all");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Browse...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setText("Select compiler:");

        cmbCompiler.setModel(new cmbModel(2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMemorySize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cmbCompiler, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbCPUType, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtConfigName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbMemoryType, 0, 165, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
                        .addGap(31, 31, 31)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtConfigName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCPUType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbCompiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(cmbMemoryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMemorySize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addGap(84, 84, 84))
        );

        paneTab.addTab("New/edit configuration", jPanel2);

        buttonOK.setText("OK");
        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(453, Short.MAX_VALUE)
                .addComponent(buttonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneTab, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(paneTab, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonOK)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
        if (paneTab.getSelectedIndex() == 0) {
            if (lstConfigs.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null,
                    "Error: No configuration is selected.","Chyba",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            if (txtConfigName.getText().equals("")) {
                JOptionPane.showMessageDialog(null,
                    "Error: Configuration name can't be empty.","Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            } else if (cmbCPUType.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null,
                    "Error: CPU have to be selected.","Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            } else if (cmbCompiler.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null,
                    "Error: Compiler have to be selected.","Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            } else if (cmbMemoryType.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null,
                    "Error: Memory type have to be selected.","Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            cMain.emuConfig.nowName = txtConfigName.getText();
            cMain.emuConfig.nowCPU = (String)cmbCPUType.getSelectedItem();
            cMain.emuConfig.nowCompiler = (String)cmbCompiler.getSelectedItem();
            cMain.emuConfig.nowMemory = (String)cmbMemoryType.getSelectedItem();
            cMain.emuConfig.nowMemorySize = Integer.valueOf(txtMemorySize.getText());
            cMain.emuConfig.nowDevices.clear();
            for (int i = 0; i < lstDevices.getSelectedValues().length; i++)
                cMain.emuConfig.nowDevices.add(lstDevices.getSelectedValues()[i]);
            cMain.emuConfig.writeConfig(txtConfigName.getText());
        }
        cMain.emuConfig.loadConfig();
        frmStudio stud = new frmStudio(cMain.emuConfig);
        this.dispose();
        stud.setVisible(true);
}//GEN-LAST:event_buttonOKActionPerformed

    private void lstConfigsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstConfigsValueChanged
        try {
            if (cMain.emuConfig.readConfig(
                    (String)lstConfigs.getSelectedValue()) == false) {
                lstConfigs.clearSelection();
                return;
            }
            txtConfigName.setText(cMain.emuConfig.nowName);
            cmbCPUType.setSelectedItem(cMain.emuConfig.nowCPU);
            cmbCompiler.setSelectedItem(cMain.emuConfig.nowCompiler);
            cmbMemoryType.setSelectedItem(cMain.emuConfig.nowMemory);
            txtMemorySize.setText(String.valueOf(cMain.emuConfig.nowMemorySize));
            int j;
            for (int i = 0; i < cMain.emuConfig.nowDevices.size(); i++) {
                j = lstDevices.getNextMatch((String)cMain.emuConfig.nowDevices.get(i),
                        0,Position.Bias.Forward);
                if (j != -1)
                    if (lstDevices.getModel().
                            getElementAt(j).equals(cMain.emuConfig.nowDevices.get(i))) {
                        lstDevices.addSelectionInterval(j,j);
                    }
            }
        } catch (Exception e) {}
    }//GEN-LAST:event_lstConfigsValueChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JFileChooser f = new JFileChooser();
        emuFileFilter propsFilter = new emuFileFilter();

        propsFilter.addExtension("props");
        propsFilter.setDescription("Microcomputer configurations");
        
        f.setCurrentDirectory(new File(System.getProperty("user.dir")
            + File.separator + cMain.emuConfig.configsDir));
        f.setAcceptAllFileFilterUsed(false);
        f.addChoosableFileFilter(propsFilter);
        f.setFileFilter(propsFilter);

        int returnVal = f.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            selectedConfig = f.getSelectedFile();
            // if file extension is not null
            txtConfigName.setText(selectedConfig.getName().
                    substring(0,selectedConfig.getName().lastIndexOf(".")));
            try {
                cMain.emuConfig.readConfig(txtConfigName.getText());
                cmbCPUType.setSelectedItem(cMain.emuConfig.nowCPU);
                cmbMemoryType.setSelectedItem(cMain.emuConfig.nowMemory);
                txtMemorySize.setText(String.valueOf(cMain.emuConfig.nowMemorySize));
                int j;
                for (int i = 0; i < cMain.emuConfig.nowDevices.size(); i++) {
                    j = lstDevices.getNextMatch((String)cMain.emuConfig.nowDevices.get(i),
                            0,Position.Bias.Forward);
                    if (j != -1)
                        if (lstDevices.getModel().
                                getElementAt(j).equals(cMain.emuConfig.nowDevices.get(i))) {
                            lstDevices.addSelectionInterval(j,j);
                        }
                }
            } catch (Exception e) {}
        }
        f.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        lstDevices.clearSelection();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        lstDevices.setSelectionInterval(0,lstDevices.getModel().getSize()-1);
    }//GEN-LAST:event_jButton4ActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JComboBox cmbCPUType;
    javax.swing.JComboBox cmbCompiler;
    javax.swing.JComboBox cmbMemoryType;
    javax.swing.JList lstConfigs;
    javax.swing.JList lstDevices;
    javax.swing.JTabbedPane paneTab;
    javax.swing.JTextField txtConfigName;
    javax.swing.JTextField txtMemorySize;
    // End of variables declaration//GEN-END:variables
    
}
