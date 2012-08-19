/*
 * ViewComputerDialog.java
 * 
 * Copyright (C) 2012, Peter Jakubčo
 * 
 * KISS, YAGNI, DRY
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package emustudio.gui;

import emulib.annotations.PluginType;
import emulib.plugins.Plugin;
import emustudio.architecture.ArchLoader;
import emustudio.architecture.Computer;
import emustudio.architecture.drawing.PreviewPanel;
import emustudio.main.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Swing dialog showing informations about all plug-ins and abstract schema.
 * 
 * @author Peter Jakubčo
 */
public class ViewComputerDialog extends javax.swing.JDialog {
    private final static Logger logger = LoggerFactory.getLogger(ViewComputerDialog.class);
    private final static String CPU_CONFIG_DIR = ArchLoader.CPUS_DIR + File.separator;
    private final static String COMPILER_CONFIG_DIR = ArchLoader.COMPILERS_DIR + File.separator;
    private final static String MEMORY_CONFIG_DIR = ArchLoader.MEMORIES_DIR + File.separator;
    private final static String DEVICE_CONFIG_DIR = ArchLoader.DEVICES_DIR + File.separator;
    
    private Computer computer;
    private PreviewPanel panelSchema;
    
    private File fileComputerInfo;
    
    /**
     * Creates new form ViewComputerDialog
     */
    public ViewComputerDialog(JFrame parent) {
        super(parent, true);
        initComponents();
        setLocationRelativeTo(parent);
        lblComputerName.setText(Main.currentArch.getComputerName());
        this.computer = Main.currentArch.getComputer();

        // load devices
        int j = computer.getDeviceCount();
        for (int i = 0; i < j; i++) {
            cmbDevice.addItem(Main.currentArch.getDeviceName(i));
        }
        
        cmbDevice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int i = cmbDevice.getSelectedIndex();
                if (i < 0) {
                    setVisibleInfo(false);
                    return;
                }
                try {
                    setInfo(computer.getDevice(i), DEVICE_CONFIG_DIR + Main.currentArch.getDeviceName(i));
                } catch (Exception ex) {
                }
            }
            
        });
        panelSchema = new PreviewPanel(Main.currentArch.getSchema());
        scrollPane.setViewportView(panelSchema);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        
        if (computer.getCompiler() == null) {
            btnCompiler.setEnabled(false);
        }
        if (computer.getMemory() == null) {
            btnMemory.setEnabled(false);
        }
        if (computer.getDeviceCount() == 0) {
            btnDevice.setEnabled(false);
        }
        
        // Select default info
        lblSelectDevice.setVisible(false);
        cmbDevice.setVisible(false);
        setInfo(computer.getCPU(), CPU_CONFIG_DIR + Main.currentArch.getCPUName());
    }
    
    private void setInfo(Plugin plugin, String fileName) {
        if (plugin == null) {
            setVisibleInfo(false);
            return;
        } else {
            setVisibleInfo(true);
        }
        PluginType pluginType = plugin.getClass().getAnnotation(PluginType.class);
        
        lblName.setText(pluginType.title());
        lblFileName.setText(fileName + ".jar");
        lblCopyright.setText(pluginType.copyright());
        lblVersion.setText(pluginType.version());
        txtDescription.setText(pluginType.description());
    }
    
    private void setVisibleInfo(boolean visible) {
        if (!visible) {
            lblName.setText("Plug-in is not available. Please select another one.");
        }
        lblCopyright.setVisible(visible);
        lblVersion.setVisible(visible);
        lblFileName.setVisible(visible);
        txtDescription.setVisible(visible);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.ButtonGroup buttonGroup1 = new javax.swing.ButtonGroup();
        lblComputerName = new javax.swing.JLabel();
        javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();
        javax.swing.JPanel panelTabInfo = new javax.swing.JPanel();
        javax.swing.JToolBar jToolBar1 = new javax.swing.JToolBar();
        btnCompiler = new javax.swing.JToggleButton();
        btnCPU = new javax.swing.JToggleButton();
        btnMemory = new javax.swing.JToggleButton();
        btnDevice = new javax.swing.JToggleButton();
        javax.swing.JToolBar.Separator jSeparator1 = new javax.swing.JToolBar.Separator();
        javax.swing.JButton btnExport = new javax.swing.JButton();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        lblSelectDevice = new javax.swing.JLabel();
        cmbDevice = new javax.swing.JComboBox();
        lblName = new javax.swing.JLabel();
        lblFileName = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblCopyright = new javax.swing.JLabel();
        javax.swing.JPanel panelDescription = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JToolBar jToolBar2 = new javax.swing.JToolBar();
        javax.swing.JButton btnSaveSchema = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Computer information preview");

        lblComputerName.setFont(lblComputerName.getFont().deriveFont(lblComputerName.getFont().getStyle() | java.awt.Font.BOLD, lblComputerName.getFont().getSize()+3));
        lblComputerName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblComputerName.setText("computer_name");

        jTabbedPane1.setFont(jTabbedPane1.getFont().deriveFont(jTabbedPane1.getFont().getStyle() & ~java.awt.Font.BOLD));

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setDoubleBuffered(true);

        buttonGroup1.add(btnCompiler);
        btnCompiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/compile.png"))); // NOI18N
        btnCompiler.setToolTipText("Compiler information");
        btnCompiler.setFocusable(false);
        btnCompiler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompiler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCompiler);

        buttonGroup1.add(btnCPU);
        btnCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/cpu.gif"))); // NOI18N
        btnCPU.setSelected(true);
        btnCPU.setToolTipText("CPU information");
        btnCPU.setFocusable(false);
        btnCPU.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCPU.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPUActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCPU);

        buttonGroup1.add(btnMemory);
        btnMemory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/ram.gif"))); // NOI18N
        btnMemory.setToolTipText("Memory information");
        btnMemory.setFocusable(false);
        btnMemory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMemory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMemory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemoryActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMemory);

        buttonGroup1.add(btnDevice);
        btnDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/device.png"))); // NOI18N
        btnDevice.setToolTipText("Devices information");
        btnDevice.setFocusable(false);
        btnDevice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDevice.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeviceActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDevice);
        jToolBar1.add(jSeparator1);

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/document-save.png"))); // NOI18N
        btnExport.setToolTipText("Export information to a file");
        btnExport.setFocusable(false);
        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        jToolBar1.add(btnExport);

        lblSelectDevice.setFont(lblSelectDevice.getFont().deriveFont(lblSelectDevice.getFont().getStyle() & ~java.awt.Font.BOLD));
        lblSelectDevice.setText("Select device:");

        cmbDevice.setFont(cmbDevice.getFont().deriveFont(cmbDevice.getFont().getStyle() & ~java.awt.Font.BOLD));

        lblName.setFont(lblName.getFont().deriveFont(lblName.getFont().getStyle() | java.awt.Font.BOLD));
        lblName.setText("plugin_name");

        lblFileName.setFont(lblFileName.getFont().deriveFont(lblFileName.getFont().getStyle() & ~java.awt.Font.BOLD));
        lblFileName.setText("plugin_file_name");

        lblVersion.setFont(lblVersion.getFont().deriveFont(lblVersion.getFont().getStyle() & ~java.awt.Font.BOLD));
        lblVersion.setText("plugin_version");

        lblCopyright.setFont(lblCopyright.getFont().deriveFont(lblCopyright.getFont().getStyle() & ~java.awt.Font.BOLD));
        lblCopyright.setText("plugin_copyright");

        panelDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Short description", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N

        txtDescription.setColumns(20);
        txtDescription.setEditable(false);
        txtDescription.setLineWrap(true);
        txtDescription.setRows(5);
        txtDescription.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescription);

        javax.swing.GroupLayout panelDescriptionLayout = new javax.swing.GroupLayout(panelDescription);
        panelDescription.setLayout(panelDescriptionLayout);
        panelDescriptionLayout.setHorizontalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDescriptionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        panelDescriptionLayout.setVerticalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDescriptionLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblSelectDevice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbDevice, 0, 377, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName)
                            .addComponent(lblVersion)
                            .addComponent(lblCopyright)
                            .addComponent(lblFileName))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectDevice)
                    .addComponent(cmbDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFileName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblVersion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCopyright)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelTabInfoLayout = new javax.swing.GroupLayout(panelTabInfo);
        panelTabInfo.setLayout(panelTabInfoLayout);
        panelTabInfoLayout.setHorizontalGroup(
            panelTabInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTabInfoLayout.setVerticalGroup(
            panelTabInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTabInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Computer info", panelTabInfo);

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setRollover(true);

        btnSaveSchema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/document-save.png"))); // NOI18N
        btnSaveSchema.setToolTipText("Save schema image");
        btnSaveSchema.setFocusable(false);
        btnSaveSchema.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveSchema.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveSchema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveSchemaActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSaveSchema);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Abstract schema", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(lblComputerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblComputerName)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilerActionPerformed
        lblSelectDevice.setVisible(false);
        cmbDevice.setVisible(false);
        setInfo(computer.getCompiler(), COMPILER_CONFIG_DIR + Main.currentArch.getCompilerName());        
    }//GEN-LAST:event_btnCompilerActionPerformed

    private void btnCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPUActionPerformed
        lblSelectDevice.setVisible(false);
        cmbDevice.setVisible(false);
        setInfo(computer.getCPU(), CPU_CONFIG_DIR + Main.currentArch.getCPUName());
    }//GEN-LAST:event_btnCPUActionPerformed

    private void btnMemoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemoryActionPerformed
        lblSelectDevice.setVisible(false);
        cmbDevice.setVisible(false);
        setInfo(computer.getMemory(), MEMORY_CONFIG_DIR + Main.currentArch.getMemoryName());
    }//GEN-LAST:event_btnMemoryActionPerformed

    private void btnDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeviceActionPerformed
        lblSelectDevice.setVisible(true);
        cmbDevice.setVisible(true);
        setVisibleInfo(false);
        if (cmbDevice.getItemCount() > 0) {
            cmbDevice.setSelectedIndex(0);
            setInfo(computer.getDevice(0), DEVICE_CONFIG_DIR + Main.currentArch.getDeviceName(0));
        } else {
            cmbDevice.setEnabled(false);
        }
    }//GEN-LAST:event_btnDeviceActionPerformed

    private void btnSaveSchemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSchemaActionPerformed
        panelSchema.saveSchemaImage();
    }//GEN-LAST:event_btnSaveSchemaActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        JFileChooser f = new JFileChooser();

        f.setDialogTitle("Save computer information");
        f.setAcceptAllFileFilterUsed(false);

        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getName().toLowerCase().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Text files (*.txt)";
            }
            
        };
        f.addChoosableFileFilter(filter);
        f.addChoosableFileFilter(f.getAcceptAllFileFilter());
        f.setFileFilter(filter);
        f.setSelectedFile(fileComputerInfo);
        if (fileComputerInfo != null) {
            f.setCurrentDirectory(fileComputerInfo.getParentFile()); 
        } else {
            f.setCurrentDirectory(new File(System.getProperty("user.dir")));
        }
        f.setSelectedFile(null);

        int returnVal = f.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedFile = f.getSelectedFile();
        FileFilter selectedFileFilter = (FileFilter)f.getFileFilter();
        
        if (selectedFileFilter != filter) {
            fileComputerInfo = selectedFile;
        } else {
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                fileComputerInfo = new File(selectedFile.getAbsolutePath() + ".txt");
            }
        }
        saveComputerInfo();
    }//GEN-LAST:event_btnExportActionPerformed

    private void savePluginInfo(PrintWriter out, Plugin plugin) {
        PluginType pluginType = plugin.getClass().getAnnotation(PluginType.class);

        String title = pluginType.title();
        String version = pluginType.version();
        String copyright = pluginType.copyright();
        String description = pluginType.description();
        out.print(title);
        out.print(", version: ");
        out.println(version);
        out.println(copyright);
        out.println(description);
        out.println();
    }
    
    private void saveComputerInfo() {
        if (fileComputerInfo == null) {
            return;
        }
        try {
            FileWriter outFile = new FileWriter(fileComputerInfo);
            PrintWriter out = new PrintWriter(outFile);
            
            out.println("Computer name: " + Main.currentArch.getComputerName() + "\n");
            Plugin plugin = computer.getCompiler();
            if (plugin != null) {
                out.println("Compiler\n########\n");
                out.print("File name: ");
                out.print("[");
                out.print(COMPILER_CONFIG_DIR);
                out.print(Main.currentArch.getCompilerName());
                out.println(".jar]");
                savePluginInfo(out, plugin);
            }
            out.println("CPU\n###\n");
            out.print("File name: ");
            out.print("[");
            out.print(CPU_CONFIG_DIR);
            out.print(Main.currentArch.getCPUName());
            out.println(".jar]");
            savePluginInfo(out, computer.getCPU());
    
            plugin = computer.getMemory();
            if (plugin != null) {
                out.println("Memory\n######\n");
                out.print("File name: ");
                out.print("[");
                out.print(MEMORY_CONFIG_DIR);
                out.print(Main.currentArch.getMemoryName());
                out.println(".jar]");
                savePluginInfo(out, plugin);
            }
            int j = computer.getDeviceCount();
            for (int i = 0; i < j; i++) {
                plugin = computer.getDevice(i);
                out.println("Device [" + i + "]\n###########\n");
                out.print("File name: ");
                out.print("[");
                out.print(DEVICE_CONFIG_DIR);
                out.print(Main.currentArch.getDeviceName(i));
                out.println(".jar]");
                savePluginInfo(out, plugin);
            }

            out.close();
        } catch (IOException e) {
            logger.error("Could not save computer information", e);
            Main.tryShowErrorMessage("Could not save computer information. For details, please see log file.");
        }
    }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnCPU;
    private javax.swing.JToggleButton btnCompiler;
    private javax.swing.JToggleButton btnDevice;
    private javax.swing.JToggleButton btnMemory;
    private javax.swing.JComboBox cmbDevice;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblComputerName;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSelectDevice;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextArea txtDescription;
    // End of variables declaration//GEN-END:variables
}
