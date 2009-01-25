/*
 * tableMemory.java
 *
 * Created on Nede�a, 2007, okt�ber 28, 13:06
 *
 * KEEP IT SIMPLE, STUPID
 * some things just: YOU AREN'T GONNA NEED IT
 */

package gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


/**
 *
 * @author vbmacher
 */
@SuppressWarnings("serial")
public class tableMemory extends JTable {
    private memoryTableModel memModel;
    private JScrollPane paneMemory;
    
    /** Creates a new instance of tableMemory */
    public tableMemory(memoryTableModel memModel, JScrollPane pm) {
        this.paneMemory = pm;
        this.memModel = memModel;
        this.setModel(this.memModel);
        this.setFont(new Font("Monospaced",Font.PLAIN,12));
        this.setCellSelectionEnabled(true);
        this.setFocusCycleRoot(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getTableHeader().setFont(new Font("Monospaced",Font.PLAIN,12));
        this.setDefaultRenderer(Object.class, new MemCellRenderer(this));
        
        MemoryCellEditor ed = new MemoryCellEditor();
        for (int i =0; i < memModel.getColumnCount(); i++) {
            TableColumn col = this.getColumnModel().getColumn(i);
            col.setPreferredWidth(3 * 17);
            col.setCellEditor(ed);
        }
    }
    
    // riadkovy header
    public class MemRowHeaderRenderer extends JLabel implements ListCellRenderer {
        public MemRowHeaderRenderer(JTable table) {
            JTableHeader header = table.getTableHeader();
            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(CENTER);
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(new Font("Monospaced",Font.PLAIN,11));
            this.setPreferredSize(new Dimension(4 * 17,header.getPreferredSize().height));
        }
  
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    /* farebnost a zobrazenie buniek v operacnej pamati */
	public class MemCellRenderer extends JLabel implements TableCellRenderer {
        private JList rowHeader;
        private String adresses[];
        private int currentPage;
        private tableMemory tm;

        public MemCellRenderer(tableMemory tm) {
            this.tm = tm;
            currentPage = memModel.getPage();
            adresses = new String[memModel.getRowCount()];
            for (int i = 0; i < adresses.length; i++)
                adresses[i] = String.format("%1$04Xh", 
                        memModel.getColumnCount() * i + memModel.getColumnCount()
                        * memModel.getRowCount() * currentPage);
            this.setOpaque(true);
            rowHeader = new JList(adresses);
            this.setFont(new Font("Monospaced",Font.PLAIN,11));
            
            FontMetrics fm = rowHeader.getFontMetrics(rowHeader.getFont());
            int char_width = 17;
            if (fm != null) char_width = fm.stringWidth("FF");
            
            rowHeader.setFixedCellWidth(char_width * 4);
            rowHeader.setFixedCellHeight(getRowHeight());
            rowHeader.setCellRenderer(new MemRowHeaderRenderer(this.tm));
            setHorizontalAlignment(CENTER);
            paneMemory.setRowHeaderView(rowHeader);
        }
        
        private void remakeAdresses() {
            if (currentPage == memModel.getPage()) return;
            currentPage = memModel.getPage();
            for (int i = 0; i < adresses.length; i++)
                adresses[i] = String.format("%1$04Xh",
                        memModel.getColumnCount() * i + memModel.getColumnCount()
                        * memModel.getRowCount() * currentPage);
            rowHeader.setListData(adresses);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                this.setBackground(tm.getSelectionBackground());
                this.setForeground(tm.getSelectionForeground());
            } else {
                if (memModel.isROMAt(row,column))
                    this.setBackground(Color.RED);
                else if (memModel.isAtBANK(row, column))
                    this.setBackground(Color.decode("0xFFE6BF"));
                else this.setBackground(Color.WHITE); 
                this.setForeground(Color.BLACK); 
            }
            remakeAdresses();
            setText(value.toString());
            return this;
        }
    }

    private class MemoryCellEditor extends AbstractCellEditor implements TableCellEditor {
        // This is the component that will handle the editing of the cell value
        JTextField component;
        
        public MemoryCellEditor() {
            component = new JTextField();
            FontMetrics fm = getFontMetrics(getFont());
            if (fm != null) {
                component.setSize(fm.stringWidth("0xFFFFFF"), fm.getHeight()+10);
                component.setBorder(null);
            }
        }
    
        // This method is called when a cell value is edited by the user.
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int rowIndex, int vColIndex) {
            // 'value' is value contained in the cell located at (rowIndex, vColIndex)
            if (isSelected == false) return null;
            component.setText("0x" + (String)value);
            return component;
        }
        public Object getCellEditorValue() {
            return component.getText();
        }
    }

    
}
