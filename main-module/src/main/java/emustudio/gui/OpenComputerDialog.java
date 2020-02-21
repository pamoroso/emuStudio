/*
 * KISS, YAGNI, DRY
 *
 * (c) Copyright 2006-2017, Peter Jakubčo
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package emustudio.gui;

import emulib.runtime.StaticDialogs;
import emustudio.Constants;
import emustudio.architecture.ComputerConfig;
import emustudio.architecture.Configuration;
import emustudio.architecture.ReadConfigurationException;
import emustudio.drawing.PreviewPanel;
import emustudio.drawing.Schema;
import emustudio.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * This dialog manages the virtual computers. It offers a list of all
 * available virtual computers and allows to the user to select one for
 * emulation.
 * <p>
 * It is also available to create a new computer, delete or edit one.
 */
public class OpenComputerDialog extends javax.swing.JDialog {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpenComputerDialog.class);

    private String archName;
    private boolean OOK = false;
    private final ConfigurationsListModel amodel;
    private final PreviewPanel preview;

    public OpenComputerDialog() {
        initComponents();
        amodel = new ConfigurationsListModel();
        lstConfig.setModel(amodel);
        super.setModal(true);
        super.setLocationRelativeTo(null);

        preview = new PreviewPanel();
        scrollPreview.setViewportView(preview);
    }

    /**
     * Existing configurations list model
     */
    private class ConfigurationsListModel extends AbstractListModel<String> {
        private String[] allModels;

        ConfigurationsListModel() {
            allModels = ComputerConfig.getAllConfigFiles();
        }

        @Override
        public String getElementAt(int index) {
            return allModels[index];
        }

        @Override
        public int getSize() {
            return allModels.length;
        }

        void update() {
            allModels = ComputerConfig.getAllConfigFiles();
            this.fireContentsChanged(this, -1, -1);
        }
    }

    /**
     * Updates ArchList model
     */
    void update() {
        amodel.update();
        lblPreview.setText(archName);
        lstConfigValueChanged(null);
    }

    /**
     * Determine whether the user pressed "OK" button.
     *
     * @return true if user has pressed OK, false otherwise
     */
    public boolean getOK() {
        return OOK;
    }

    /**
     * Get the name of selected virtual computer.
     *
     * @return string name of selected virtual configuration
     */
    public String getArchName() {
        return archName;
    }

    /**
     * Set the name of selected virtual computer.
     *
     * @param archName new name of the virtual computer
     */
    void setArchName(String archName) {
        this.archName = archName;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JSplitPane splitConfig = new javax.swing.JSplitPane();
        javax.swing.JPanel panelConfig = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        lstConfig = new javax.swing.JList<>();
        javax.swing.JToolBar toolConfig = new javax.swing.JToolBar();
        JButton btnAdd = new JButton();
        JButton btnDelete = new JButton();
        JButton btnEdit = new JButton();
        JButton btnSaveSchemaImage = new JButton();
        javax.swing.JPanel panelPreview = new javax.swing.JPanel();
        scrollPreview = new javax.swing.JScrollPane();
        javax.swing.JToolBar toolPreview = new javax.swing.JToolBar();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JToolBar.Separator jSeparator1 = new javax.swing.JToolBar.Separator();
        lblPreview = new javax.swing.JLabel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JButton btnOpen = new javax.swing.JButton();
        JButton btnClose = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("emuStudio - Open virtual computer");

        splitConfig.setDividerLocation(200);
        splitConfig.setMinimumSize(new java.awt.Dimension(50, 102));
        splitConfig.setPreferredSize(new java.awt.Dimension(300, 299));

        panelConfig.setPreferredSize(new java.awt.Dimension(200, 297));

        lstConfig.setFont(Constants.MONOSPACED_PLAIN_12);
        lstConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstConfigMouseClicked(evt);
            }
        });
        lstConfig.addListSelectionListener(this::lstConfigValueChanged);
        jScrollPane1.setViewportView(lstConfig);

        toolConfig.setFloatable(false);
        toolConfig.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/list-add.png"))); // NOI18N
        btnAdd.setToolTipText("Create new computer...");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(this::btnAddActionPerformed);
        btnAdd.setBorderPainted(false);
        toolConfig.add(btnAdd);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/list-remove.png"))); // NOI18N
        btnDelete.setToolTipText("Remove computer");
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        btnDelete.setBorderPainted(false);
        toolConfig.add(btnDelete);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/computer.png"))); // NOI18N
        btnEdit.setToolTipText("Edit existing computer...");
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(this::btnEditActionPerformed);
        btnEdit.setBorderPainted(false);
        toolConfig.add(btnEdit);

        btnSaveSchemaImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emustudio/gui/document-save.png"))); // NOI18N
        btnSaveSchemaImage.setToolTipText("Save schema image");
        btnSaveSchemaImage.setFocusable(false);
        btnSaveSchemaImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveSchemaImage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveSchemaImage.addActionListener(this::btnSaveSchemaImageActionPerformed);
        btnSaveSchemaImage.setBorderPainted(false);
        toolConfig.add(btnSaveSchemaImage);

        javax.swing.GroupLayout panelConfigLayout = new javax.swing.GroupLayout(panelConfig);
        panelConfig.setLayout(panelConfigLayout);
        panelConfigLayout.setHorizontalGroup(
            panelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(toolConfig, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
        panelConfigLayout.setVerticalGroup(
            panelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfigLayout.createSequentialGroup()
                    .addComponent(toolConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
        );

        splitConfig.setLeftComponent(panelConfig);

        toolPreview.setFloatable(false);
        toolPreview.setRollover(true);

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() & ~java.awt.Font.BOLD));
        jLabel2.setText("Computer preview:");
        toolPreview.add(jLabel2);

        jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        toolPreview.add(jSeparator1);

        lblPreview.setFont(lblPreview.getFont().deriveFont(lblPreview.getFont().getStyle() | java.awt.Font.BOLD));
        toolPreview.add(lblPreview);

        javax.swing.GroupLayout panelPreviewLayout = new javax.swing.GroupLayout(panelPreview);
        panelPreview.setLayout(panelPreviewLayout);
        panelPreviewLayout.setHorizontalGroup(
            panelPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addGroup(panelPreviewLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(toolPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
        );
        panelPreviewLayout.setVerticalGroup(
            panelPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelPreviewLayout.createSequentialGroup()
                    .addComponent(toolPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
        );

        splitConfig.setRightComponent(panelPreview);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() & ~java.awt.Font.BOLD));
        jLabel1.setText("Please select a virtual configuration that will be emulated:");

        btnOpen.setFont(btnOpen.getFont().deriveFont(btnOpen.getFont().getStyle() | java.awt.Font.BOLD));
        btnOpen.setText("Open");
        btnOpen.addActionListener(this::btnOpenActionPerformed);

        btnClose.setFont(btnClose.getFont().deriveFont(btnClose.getFont().getStyle() & ~java.awt.Font.BOLD));
        btnClose.setText("Close");
        btnClose.addActionListener(this::btnCloseActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(splitConfig, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(btnClose)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(splitConfig, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOpen)
                        .addComponent(btnClose))
                    .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstConfigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstConfigMouseClicked
        if (evt.getClickCount() == 2) {
            btnOpenActionPerformed(null);
        }
    }//GEN-LAST:event_lstConfigMouseClicked

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        if (lstConfig.getSelectedIndex() == -1) {
            Main.tryShowErrorMessage("A computer has to be selected!");
            return;
        }
        archName = lstConfig.getSelectedValue();
        OOK = true;
        dispose();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (lstConfig.getSelectedIndex() == -1) {
            Main.tryShowErrorMessage("A computer has to be selected!");
            return;
        }
        archName = lstConfig.getSelectedValue();
        try {
            Configuration configuration = ComputerConfig.read(archName);
            Schema schema = configuration.loadSchema();
            SchemaEditorDialog d = new SchemaEditorDialog(this, schema);
            d.setVisible(true);
        } catch (ReadConfigurationException e) {
            LOGGER.error("Could not load computer configuration", e);
            Main.tryShowErrorMessage("Could not load computer configuration!");
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (lstConfig.getSelectedIndex() == -1) {
            Main.tryShowErrorMessage("A computer has to be selected!");
            return;
        }
        int r = StaticDialogs.confirmMessage("Do you really want to delete"
            + " selected computer?", "Delete architecture");
        archName = lstConfig.getSelectedValue();
        if (r == StaticDialogs.YES_OPTION) {
            boolean re = ComputerConfig.delete(archName);
            lstConfig.clearSelection();
            if (re) {
                archName = "";
                update();
            } else {
                Main.tryShowErrorMessage("Computer could not be deleted.");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        SchemaEditorDialog di = new SchemaEditorDialog(this);
        di.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void lstConfigValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstConfigValueChanged
        int i = lstConfig.getSelectedIndex();
        if ((i == -1) || (i >= lstConfig.getModel().getSize())) {
            preview.clearScreen();
            return;
        }

        archName = lstConfig.getSelectedValue();
        try {
            Configuration configuration = ComputerConfig.read(archName);
            Schema s = configuration.loadSchema();

            preview.setSchema(s);
            preview.repaint();
        } catch (ReadConfigurationException e) {
            LOGGER.error("Could not update computer preview.", e);
        }

        lblPreview.setText(archName);
    }//GEN-LAST:event_lstConfigValueChanged

    private void btnSaveSchemaImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSchemaImageActionPerformed
        if (lstConfig.getSelectedIndex() == -1) {
            Main.tryShowErrorMessage("A computer has to be selected!");
            return;
        }
        preview.saveSchemaImage();
    }//GEN-LAST:event_btnSaveSchemaImageActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    private javax.swing.JLabel lblPreview;
    private javax.swing.JList<String> lstConfig;
    private javax.swing.JScrollPane scrollPreview;
    // End of variables declaration                   

}
