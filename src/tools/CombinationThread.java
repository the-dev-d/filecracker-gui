package tools;

import java.io.File;

import javax.swing.JLabel;

import org.apache.pdfbox.pdmodel.PDDocument;

public class CombinationThread extends Thread{


    int length;
    File file;
    String characterSet;
    private JLabel label;
    private boolean found = false;

    CombinationThread(File file, int i, String characterSet, JLabel label) {
        super();
        this.file = file;
        this.characterSet = characterSet;
        this.length = i;
        this.label = label;
    }

    public void generateWordsOfSize(File file, int size, String word, String alphabet) {
        if (size == 0) {
            try {
                System.out.print("\033[H\033[2J");
                label.setText("Trying " + word);
                PDDocument document = PDDocument.load(file, word);
                if (document.isEncrypted()) {
                    document.setAllSecurityToBeRemoved(true);
                    document.save(file);
                    document.close();
                    label.setText("Password Found : " + word);  
                    this.found = true;
                    return;
                }
            }catch(Exception e) {
                
            }
        } else {
            for (int i = 0; i < alphabet.length(); i++) {
                if (!found)
                    generateWordsOfSize(file, size - 1, word + alphabet.charAt(i), alphabet);
            }
        }
    }

    public void run() {
        this.generateWordsOfSize(this.file, this.length, "", this.characterSet);
    }
}
