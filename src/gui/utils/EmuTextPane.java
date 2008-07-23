/*
 * EmuTextPane.java
 *
 * Created on Štvrtok, 2007, august 9, 15:05
 *
 * KEEP IT SIMPLE, STUPID
 * some things just: YOU AREN'T GONNA NEED IT
 *
 * This class is extended JTextPane class. Support some awesome features like
 * line numbering or syntax highlighting and other.
 * TODO: add ability to set breakpoints
 */

package gui.utils;

import gui.syntaxHighlighting.HighlightStyle;
import gui.syntaxHighlighting.HighlightThread;
import gui.syntaxHighlighting.StyledDocumentReader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Hashtable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.undo.UndoManager;
import plugins.compiler.ILexer;
import plugins.compiler.IToken;
import plugins.compiler.ITokenColor;
import runtime.StaticDialogs;

/**
 *
 * @author vbmacher
 */
public class EmuTextPane extends JTextPane {
    public static final short NUMBERS_WIDTH = 40;
    public static final short NUMBERS_HEIGHT = 4;

    private ILexer syntaxLexer = null;
    private StyledDocumentReader reader;
    private HighlightedDocument document;
    private Hashtable styles; // token styles
    private HighlightThread highlight;

    private boolean fileSaved; // if is document saved
    private File fileSource;   // opened file
    private UndoManager undo;
    private ActionListener undoStateListener;
    private ActionEvent aevt;

    /**
     * A lock for modifying the document, or for
     * actions that depend on the document not being
     * modified.
     */
    public volatile Object doclock = new Object();
    
    
    /** Creates a new instance of EmuTextPane */
    public EmuTextPane() {
        styles = new Hashtable();
        initStyles();
        document = new HighlightedDocument();
        reader = new StyledDocumentReader(document);
        this.setStyledDocument(document);
        this.setFont(new java.awt.Font("monospaced", 0, 12));
        this.setMargin(new Insets(NUMBERS_HEIGHT,NUMBERS_WIDTH,0,0));
        this.setBackground(Color.WHITE);

        fileSaved = true;
        fileSource = null;
        undo = new UndoManager();
        aevt = new ActionEvent(this,0,"");
        document.addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                if (e.getEdit().isSignificant()) {
                    undo.addEdit(e.getEdit());
                    if (undoStateListener != null)
                        undoStateListener.actionPerformed(aevt);
                }
            }
        });
        undoStateListener = null;
    }

    public void setLexer(ILexer sLexer) {
        this.syntaxLexer = sLexer;
        if (highlight != null) highlight.stopRun();
        else {
            highlight = new HighlightThread(syntaxLexer, reader,
                    document, styles,doclock);
            highlight.start();
        }
    }

    private void initStyles() {
        styles.clear();
        styles.put(IToken.COMMENT, new HighlightStyle(true,false,ITokenColor.COMMENT));
        styles.put(IToken.ERROR, new HighlightStyle(false,false,ITokenColor.ERROR));
        styles.put(IToken.IDENTIFIER, new HighlightStyle(false,false,ITokenColor.IDENTIFIER));
        styles.put(IToken.LABEL, new HighlightStyle(false,false,ITokenColor.LABEL));
        styles.put(IToken.LITERAL, new HighlightStyle(false,false,ITokenColor.LITERAL));
        styles.put(IToken.OPERATOR, new HighlightStyle(false,true,ITokenColor.OPERATOR));
        styles.put(IToken.PREPROCESSOR, new HighlightStyle(false,true,ITokenColor.PREPROCESSOR));
        styles.put(IToken.REGISTER, new HighlightStyle(false,false,ITokenColor.REGISTER));
        styles.put(IToken.RESERVED, new HighlightStyle(false,true,ITokenColor.RESERVED));
        styles.put(IToken.SEPARATOR, new HighlightStyle(false,false,ITokenColor.SEPARATOR));
    }


    /*** UNDO/REDO IMPLEMENTATION ***/
    public void setUndoStateChangedAction(ActionListener l) {
        undoStateListener = l;
    }
    public boolean canRedo() { return undo.canRedo(); }
    public boolean canUndo() { return undo.canUndo(); }
    public void undo() { undo.undo(); }
    public void redo() { undo.redo(); }
    
    /*** SYNTAX HIGHLIGHTING IMPLEMENTATION ***/
     
    public Reader getDocumentReader() { return reader; }
    
    public class HighlightedDocument extends DefaultStyledDocument { 
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            synchronized (doclock){
                fileSaved = false;
                super.insertString(offs, str, a);
                highlight.color(offs, str.length());
                reader.update(offs, str.length());
            }
        }
        public void remove(int offs, int len) throws BadLocationException {
            synchronized (doclock){
                fileSaved = false;
                super.remove(offs, len);
                highlight.color(offs, -len);
                reader.update(offs, -len);
            }
        }
    }    
   
    /*** LINE NUMBERS PAINT IMPLEMENTATION ***/
    // implements view lines numbers
    public void paint(Graphics g) {
        super.paint(g);
        int start = document.getStartPosition().getOffset();
        int end = document.getEndPosition().getOffset();

        // translate offsets to lines
        int startline = document.getDefaultRootElement().getElementIndex(start) ;
        int endline = document.getDefaultRootElement().getElementIndex(end)+1;

        int fontHeight = g.getFontMetrics(getFont()).getHeight(); // font height

        g.setColor(Color.RED);
        for (int line = startline, y = 0; line <= endline; line++, y += fontHeight)
            g.drawString(Integer.toString(line), 0, y);
        
        // paint blue thin lines
        g.setColor(Color.PINK);
        g.drawLine(NUMBERS_WIDTH-5, g.getClipBounds().y, NUMBERS_WIDTH-5, 
                g.getClipBounds().y+g.getClipBounds().height);
    }

    /*** OPENING/SAVING FILE ***/
    
    // returns true if a file was opened
    public boolean openFileDialog() {
        JFileChooser f = new JFileChooser();
        EmuFileFilter f1 = new EmuFileFilter();
        EmuFileFilter f2 = new EmuFileFilter();

        if (this.confirmSave() == true)
            return false;
        
        f1.addExtension("asm");
        f1.addExtension("txt");
        f1.setDescription("Assembler source (*.asm, *.txt)");
        f2.addExtension("*");
        f2.setDescription("All files (*.*)");
        
        f.setDialogTitle("Open a file");
        f.setAcceptAllFileFilterUsed(false);
        f.addChoosableFileFilter(f1);
        f.addChoosableFileFilter(f2);
        f.setFileFilter(f1);
        f.setApproveButtonText("Open");
        f.setSelectedFile(fileSource);

        int returnVal = f.showOpenDialog(this);
        f.setVisible(true);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            fileSource = f.getSelectedFile();
            if (fileSource.canRead() == true) {
                try {
                    FileReader vstup = new FileReader(fileSource.getAbsolutePath());
                    setText("");
                    ILexer l = this.syntaxLexer;
                    this.syntaxLexer = null;
                    getEditorKit().read(vstup, document,0);
                    this.syntaxLexer = l;
                    this.setCaretPosition(0);
                    vstup.close(); fileSaved = true;
                }  catch (java.io.FileNotFoundException ex) {
                    StaticDialogs.showErrorMessage("File not found: " 
                            + fileSource.getPath());
                    return false;
                }
                catch (Exception e) {
                    StaticDialogs.showErrorMessage("Error opening file: "
                            + fileSource.getPath());
                    return false;
                }
            } else {
                StaticDialogs.showErrorMessage("File " + fileSource.getPath()
                    + " can't be read.");
                return false;
            }
            return true;
        }
        return false;
    }
    
    // return if cancel was pressed
    public boolean confirmSave() {
        if (fileSaved == false) {
            int r = JOptionPane.showConfirmDialog(null,
                    "File is not saved yet. Do you want to save the file ?");
            if (r == JOptionPane.YES_OPTION)
                this.saveFile();
            else if (r == JOptionPane.CANCEL_OPTION)
                return true;
        }
        return false;
    }

    // return true if file was saved
    public boolean saveFile() {
        if (fileSource == null || fileSource.canWrite() == false) return saveFileDialog();
        else {
            String fn = fileSource.getAbsolutePath();
            try {
                FileWriter vystup = new FileWriter(fn);
                write(vystup);
                vystup.close();
                fileSaved = true;
            } 
            catch (Exception e) {
                StaticDialogs.showErrorMessage("Can't save file: " + fileSource.getPath());
                return false;
            }
            return true;
        }
    }
    
    // return true if file was saved
    public boolean saveFileDialog() {
        JFileChooser f = new JFileChooser();
        EmuFileFilter f1 = new EmuFileFilter();
        EmuFileFilter f2 = new EmuFileFilter();
        EmuFileFilter f3 = new EmuFileFilter();
        
        f1.addExtension("asm");
        f1.setDescription("Assembler source (*.asm)");
        f2.addExtension("txt");
        f2.setDescription("Assembler source (*.txt)");
        f3.addExtension("*");
        f3.setDescription("All files (*.*)");
        
        f.setDialogTitle("Save a file");
        f.setAcceptAllFileFilterUsed(false);
        f.addChoosableFileFilter(f1);
        f.addChoosableFileFilter(f2);
        f.addChoosableFileFilter(f3);
        f.setFileFilter(f1);
        f.setApproveButtonText("Save");
        f.setSelectedFile(fileSource);

        int returnVal = f.showSaveDialog(this);
        f.setVisible(true);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileSource = f.getSelectedFile();
            if (fileSource.canWrite() == true || fileSource.exists() == false ) {
                String fn = fileSource.getAbsolutePath();
                try {
                    EmuFileFilter fil = (EmuFileFilter)f.getFileFilter();
                    if (fil.getExtension(fileSource) == null && fil.getFirstExtension()!=null) {
                        if (!fil.getFirstExtension().equals("*"))
                            fn +="."+fil.getFirstExtension();
                    }
                    fileSource = new java.io.File(fn);
                    FileWriter vystup = new FileWriter(fn);
                    write(vystup);
                    vystup.close();
                    fileSaved = true;
                } 
                catch (Exception e) {
                    StaticDialogs.showErrorMessage("Can't save file: " + fileSource.getPath());
                    return false;
                }
            } else {
                StaticDialogs.showErrorMessage("Bad file name"); return false;
            }
            return true;
        }
        return false;
    }
    
    public boolean isFileSaved() { 
        if (this.fileSaved == true && this.fileSource != null)
            return true;
        else return false;
    }
    
    public String getFileName() {
        if (this.fileSource == null) return null;
        else return this.fileSource.getAbsolutePath();
    }
    
    // return true if new file was created
    public boolean newFile() {
        if (confirmSave() == true) return false;
        fileSource = null;
        fileSaved = true;
        this.setText("");
        return true;
    }
    
}
