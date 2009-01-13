/*
 * AddEditArchDialog.java
 *
 * Created on Streda, 2008, júl 9, 9:40
 */

package gui;

import architecture.ArchLoader;
import architecture.drawing.DrawingPanel;
import architecture.drawing.DrawingPanel.drawTool;
import architecture.drawing.Schema;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import runtime.StaticDialogs;

/**
 *
 * @author  vbmacher
 */
@SuppressWarnings("serial")
public class AddEditArchDialog extends javax.swing.JDialog implements KeyListener {
    private Schema schema;
    private boolean OOK = false;
    private DrawingPanel pan;
    private pluginModel empty_model = new pluginModel(null);
    
    private boolean buttonSelected = false;
    
    private class pluginModel implements ComboBoxModel {
        private String[] pluginNames;
        private Object selectedObject = null;
        public pluginModel(String[] wt) { this.pluginNames = wt; }
        public void setSelectedItem(Object anItem) { selectedObject = anItem; }
        public Object getSelectedItem() { return selectedObject; }
        public int getSize() { return (pluginNames == null) ? 0 : pluginNames.length; }
        public Object getElementAt(int index) { return pluginNames[index]; }
        public void addListDataListener(ListDataListener l) {}
        public void removeListDataListener(ListDataListener l) {}
    }

    private void constructor() {
        initComponents();
        String[] compilers = ArchLoader.getAllNames(ArchLoader.compilersDir,
                ".jar");
        cmbCompiler.setModel(new pluginModel(compilers));
        this.setLocationRelativeTo(null);
        pan = new DrawingPanel(this.schema, true, 30);
        scrollScheme.setViewportView(pan);
        scrollScheme.getHorizontalScrollBar().setUnitIncrement(10);
        scrollScheme.getVerticalScrollBar().setUnitIncrement(10);
        pan.addMouseListener(pan);
        pan.addMouseMotionListener(pan);
        addKeyListenerRecursively(this);        
    }
    
    /** Creates new form AddEditArchDialog */
    public AddEditArchDialog(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        this.schema = new Schema();
        constructor();
        cmbCompiler.setSelectedIndex(-1);
        this.setTitle("Add new architecture");
    }
    
    public AddEditArchDialog(javax.swing.JDialog parent, boolean modal,
            Schema schema) {
        super(parent, modal);
        this.schema = schema;
        constructor();
        cmbCompiler.setSelectedItem(schema.getCompilerName());
        txtMemorySize.setText(String.valueOf(schema.getMemorySize()));
        this.setTitle("Edit architecture");
        txtArchName.setText(schema.getConfigName());
        txtArchName.setEnabled(false);
        btnBrowse.setEnabled(false);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            pan.cancelTasks();
    }
    public void keyReleased(KeyEvent e) {}
    
    private void addKeyListenerRecursively(Component c) {
        c.addKeyListener((KeyListener) this);
        if (c instanceof Container) {
            Container cont = (Container)c;
            Component[] children = cont.getComponents();
            for(int i = 0; i < children.length; i++)
                addKeyListenerRecursively(children[i]);
        }
    }
    
    public boolean getOK() { return OOK; }
    
    public Schema getSchema() { return schema; }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        javax.swing.JToolBar jToolBar1 = new javax.swing.JToolBar();
        btnCPU = new javax.swing.JToggleButton();
        btnMemory = new javax.swing.JToggleButton();
        btnDevice = new javax.swing.JToggleButton();
        javax.swing.JToolBar.Separator jSeparator1 = new javax.swing.JToolBar.Separator();
        btnConnect = new javax.swing.JToggleButton();
        btnDelete = new javax.swing.JToggleButton();
        javax.swing.JToolBar.Separator jSeparator2 = new javax.swing.JToolBar.Separator();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        cmbElement = new javax.swing.JComboBox();
        scrollScheme = new javax.swing.JScrollPane();
        chkUseGrid = new javax.swing.JCheckBox();
        sliderGridGap = new javax.swing.JSlider();
        javax.swing.JButton btnOK = new javax.swing.JButton();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        txtArchName = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        cmbCompiler = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        txtMemorySize = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add new architecture");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        buttonGroup1.add(btnCPU);
        btnCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/emu8/cpu.gif"))); // NOI18N
        btnCPU.setFocusable(false);
        btnCPU.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCPU.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCPU.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnCPUItemStateChanged(evt);
            }
        });
        btnCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPUActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCPU);

        buttonGroup1.add(btnMemory);
        btnMemory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/emu8/ram.gif"))); // NOI18N
        btnMemory.setFocusable(false);
        btnMemory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMemory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMemory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnMemoryItemStateChanged(evt);
            }
        });
        btnMemory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemoryActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMemory);

        buttonGroup1.add(btnDevice);
        btnDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/emu8/device.gif"))); // NOI18N
        btnDevice.setFocusable(false);
        btnDevice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDevice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDevice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnDeviceItemStateChanged(evt);
            }
        });
        btnDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeviceActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDevice);
        jToolBar1.add(jSeparator1);

        buttonGroup1.add(btnConnect);
        btnConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/emu8/connector.gif"))); // NOI18N
        btnConnect.setFocusable(false);
        btnConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConnect.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnConnectItemStateChanged(evt);
            }
        });
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        jToolBar1.add(btnConnect);

        buttonGroup1.add(btnDelete);
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/emu8/delete.gif"))); // NOI18N
        btnDelete.setToolTipText("Delete element/line");
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnDeleteItemStateChanged(evt);
            }
        });
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);
        jToolBar1.add(jSeparator2);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 44));

        cmbElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbElementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cmbElement, 0, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbElement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jToolBar1.add(jPanel1);

        chkUseGrid.setSelected(true);
        chkUseGrid.setText("Use grid");
        chkUseGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkUseGridActionPerformed(evt);
            }
        });

        sliderGridGap.setMaximum(40);
        sliderGridGap.setMinimum(3);
        sliderGridGap.setMinorTickSpacing(1);
        sliderGridGap.setPaintTicks(true);
        sliderGridGap.setToolTipText("Grid gap");
        sliderGridGap.setValue(30);
        sliderGridGap.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGridGapStateChanged(evt);
            }
        });

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        jLabel2.setText("Architecture name:");

        btnBrowse.setText("Browse...");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        jLabel1.setText("Compiler:");

        jLabel3.setText("Memory size:");

        txtMemorySize.setText("65536");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
            .addComponent(scrollScheme, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkUseGrid)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sliderGridGap, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbCompiler, javax.swing.GroupLayout.Alignment.LEADING, 0, 289, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMemorySize, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                    .addComponent(txtArchName, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(523, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollScheme, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkUseGrid)
                    .addComponent(sliderGridGap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbCompiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMemorySize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(jLabel2)
                    .addComponent(btnBrowse)
                    .addComponent(txtArchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
    String s = txtArchName.getText();
    
    // todo: check name for legal file name
    if (s.equals("")) {
        StaticDialogs.showErrorMessage("Architecture name can not be empty!");
        txtArchName.grabFocus();
        return;
    }
    schema.setConfigName(s);
    
    // check for correctness of the schema
    if (schema.getCpuElement() == null || schema.getMemoryElement() == null) {
        StaticDialogs.showErrorMessage("Abstract schema must contain CPU and"
                + " MEMORY elements!");
        return;
    }
    // really??
    if (cmbCompiler.getSelectedIndex() == -1) {
        StaticDialogs.showErrorMessage("Compiler has to be selected!");
        cmbCompiler.grabFocus();
        return;
    }
    schema.setCompilerName(String.valueOf(cmbCompiler.getSelectedItem()));
    try { schema.setMemorySize(Integer.parseInt(txtMemorySize.getText())); }
    catch(Exception e) {
        StaticDialogs.showErrorMessage("Memory size has to be integer number!");
        txtMemorySize.grabFocus();
        return;
    }
    OOK = true;
    dispose();
}//GEN-LAST:event_btnOKActionPerformed

private void chkUseGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkUseGridActionPerformed
    pan.setUseGrid(chkUseGrid.isSelected());
    sliderGridGap.setEnabled(chkUseGrid.isSelected());
}//GEN-LAST:event_chkUseGridActionPerformed

private void sliderGridGapStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGridGapStateChanged
    pan.setGridGap(sliderGridGap.getValue());
}//GEN-LAST:event_sliderGridGapStateChanged

private void btnCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPUActionPerformed
    pan.cancelTasks();
    pan.setTool(drawTool.nothing, "");
    if (buttonSelected) {
        buttonGroup1.clearSelection();
        cmbElement.setModel(empty_model);
        btnCPU.setSelected(false);
        return;
    }
    buttonSelected = true;
    String[] cpus = ArchLoader.getAllNames(ArchLoader.cpusDir, ".jar");
    cmbElement.setModel(new pluginModel(cpus));
    try {
        cmbElement.setSelectedIndex(0);
    } catch(IllegalArgumentException e) {}
}//GEN-LAST:event_btnCPUActionPerformed

private void btnMemoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemoryActionPerformed
    pan.cancelTasks();
    pan.setTool(drawTool.nothing, "");
    if (buttonSelected) {
        cmbElement.setModel(empty_model);
        buttonGroup1.clearSelection();
        btnMemory.setSelected(false);
        return;
    }
    buttonSelected = true;
    String[] mems = ArchLoader.getAllNames(ArchLoader.memoriesDir, ".jar");
    cmbElement.setModel(new pluginModel(mems));
    try {
        cmbElement.setSelectedIndex(0);
    } catch(IllegalArgumentException e) {}
}//GEN-LAST:event_btnMemoryActionPerformed

private void btnDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeviceActionPerformed
    pan.cancelTasks();
    pan.setTool(drawTool.nothing, "");
    if (buttonSelected) {
        cmbElement.setModel(empty_model);
        buttonGroup1.clearSelection();
        btnDevice.setSelected(false);
        return;
    }
    buttonSelected = true;
    String[] devs = ArchLoader.getAllNames(ArchLoader.devicesDir, ".jar");
    cmbElement.setModel(new pluginModel(devs));
    try {
        cmbElement.setSelectedIndex(0);
    } catch(IllegalArgumentException e) {}
}//GEN-LAST:event_btnDeviceActionPerformed

private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
    pan.cancelTasks();
    pan.setTool(drawTool.nothing, "");
    cmbElement.setModel(empty_model);
    if (buttonSelected) {
        buttonGroup1.clearSelection();
        btnConnect.setSelected(false);
        return;
    }
    pan.setTool(drawTool.connectLine, "");
    buttonSelected = true;
}//GEN-LAST:event_btnConnectActionPerformed

private void cmbElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbElementActionPerformed
    if (cmbElement.getSelectedIndex() == -1) {
        pan.cancelTasks();
        return;
    }
    String t = (String)cmbElement.getSelectedItem();
    if (btnCPU.isSelected()) pan.setTool(drawTool.shapeCPU, t);
    else if (btnMemory.isSelected()) pan.setTool(drawTool.shapeMemory, t);
    else if (btnDevice.isSelected()) pan.setTool(drawTool.shapeDevice, t);
}//GEN-LAST:event_cmbElementActionPerformed

private void btnCPUItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnCPUItemStateChanged
    if (!btnCPU.isSelected()) buttonSelected = false;
}//GEN-LAST:event_btnCPUItemStateChanged

private void btnMemoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnMemoryItemStateChanged
    if (!btnMemory.isSelected()) buttonSelected = false;
}//GEN-LAST:event_btnMemoryItemStateChanged

private void btnDeviceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnDeviceItemStateChanged
    if (!btnDevice.isSelected()) buttonSelected = false;
}//GEN-LAST:event_btnDeviceItemStateChanged

private void btnConnectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnConnectItemStateChanged
    if (!btnConnect.isSelected()) buttonSelected = false;
}//GEN-LAST:event_btnConnectItemStateChanged

private void btnDeleteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnDeleteItemStateChanged
    if (!btnDelete.isSelected()) buttonSelected = false;
}//GEN-LAST:event_btnDeleteItemStateChanged

private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    pan.cancelTasks();
    pan.setTool(drawTool.nothing, "");
    cmbElement.setModel(empty_model);
    if (buttonSelected) {
        buttonGroup1.clearSelection();
        btnConnect.setSelected(false);
        return;
    }
    pan.setTool(drawTool.delete, "");
    buttonSelected = true;
}//GEN-LAST:event_btnDeleteActionPerformed

private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
    OpenArchDialog d = new OpenArchDialog(this,true);
    d.setVisible(true);
    if (d.getOK())
        txtArchName.setText(d.getArchName());
}//GEN-LAST:event_btnBrowseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton btnBrowse;
    javax.swing.JToggleButton btnCPU;
    javax.swing.JToggleButton btnConnect;
    javax.swing.JToggleButton btnDelete;
    javax.swing.JToggleButton btnDevice;
    javax.swing.JToggleButton btnMemory;
    javax.swing.ButtonGroup buttonGroup1;
    javax.swing.JCheckBox chkUseGrid;
    javax.swing.JComboBox cmbCompiler;
    javax.swing.JComboBox cmbElement;
    javax.swing.JScrollPane scrollScheme;
    javax.swing.JSlider sliderGridGap;
    javax.swing.JTextField txtArchName;
    javax.swing.JTextField txtMemorySize;
    // End of variables declaration//GEN-END:variables

}
