package io;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
 
/**
 * Tato trida dedi z OuputStream a presmerovava vystup do JTextArea
 * 
 * @author Vlada47 & Shag0n
 */
public class CustomOutputStream extends OutputStream {
    final private JTextArea textArea;
     
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}