/*
 * BreakpointDialog.java
 *
 * Created on ${date}, 10:04
 * hold to: KISS, YAGNI
 *
 */

package gui;

import gui.utils.NiceButton;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import runtime.StaticDialogs;

/**
 *
 * @author  vbmacher
 */
@SuppressWarnings("serial")
public class BreakpointDialog extends JDialog {
    private static int adr = -1; // if adr == -1 then it means cancel
    private static boolean set = false;
    
    public static int getAdr() { return adr; }
    public static boolean getSet() { return set; }
    
    /** Creates new form BreakpointDialog */
    public BreakpointDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        txtAddress.grabFocus();
    }
    
    private void initComponents() {

        lblSetUnset = new JLabel();
        txtAddress = new JTextField();
        btnSet = new NiceButton();
        btnUnset = new NiceButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Set/unset breakpoint");
        setResizable(false);

        lblSetUnset.setText("Set/unset breakpoint to address:");

        txtAddress.setText("0");

        btnSet.setText("Set");
        btnSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetActionPerformed(evt);
            }
        });

        btnUnset.setText("Unset");
        btnUnset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnsetActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addComponent(lblSetUnset)
        		.addComponent(txtAddress)
        		.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        				.addComponent(btnUnset)
        				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        				.addComponent(btnSet)
        				.addContainerGap())
        		);
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addContainerGap()
        		.addComponent(lblSetUnset)
        		.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        		.addComponent(txtAddress,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
        		          GroupLayout.PREFERRED_SIZE)
        		.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        				.addComponent(btnUnset)
        				.addComponent(btnSet))
        		);
        

        pack();
    }
    
    private void btnSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetActionPerformed
        try {
            BreakpointDialog.adr = Integer.decode(txtAddress.getText());
        } catch (NumberFormatException e) {
            StaticDialogs.showErrorMessage("Invalid address, try again !");
            txtAddress.grabFocus();
            return;
        }
        BreakpointDialog.set = true;
        dispose();
}//GEN-LAST:event_btnSetActionPerformed

    private void btnUnsetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnsetActionPerformed
        try {
            BreakpointDialog.adr = Integer.decode(txtAddress.getText());
        } catch (NumberFormatException e) {
            StaticDialogs.showErrorMessage("Invalid address, try again !");
            txtAddress.grabFocus();
            return;
        }
        BreakpointDialog.set = false;
        dispose();        
    }//GEN-LAST:event_btnUnsetActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private NiceButton btnSet;
    private NiceButton btnUnset;
    private JLabel lblSetUnset;
    private JTextField txtAddress;
    // End of variables declaration//GEN-END:variables
    
}
