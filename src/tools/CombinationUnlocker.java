package tools;
import java.awt.TextField;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;

import org.apache.pdfbox.pdmodel.PDDocument;

public class CombinationUnlocker {

    private File file;
    private File wordList = null;
    private Integer startLength = 1;
    private Integer endLength = 10;
    private JLabel tryJLabel;
    List<Thread> threads;

    public CombinationUnlocker(String filePath, String wordListPath, JLabel tryJLabel) {
        this.file = new File(filePath);
        this.wordList = new File(wordListPath);
        this.tryJLabel = tryJLabel;
    }
    public CombinationUnlocker(File file, File wordList,  JLabel tryJLabel) {
        this.file = file;
        this.wordList = wordList;
        this.tryJLabel = tryJLabel;
    }

    public CombinationUnlocker(File selectedFile, int startLength, int endLength, JLabel tryJLabel) {
        this.file = selectedFile;
        this.startLength = startLength;
        this.endLength = endLength;
        this.tryJLabel = tryJLabel;
    }

    public CombinationUnlocker(File file, Integer startLength, Integer endLength) {
        this.file = file;
        this.startLength = startLength;
        this.endLength = endLength;
    }

    public void unlock() {
        if(this.wordList != null) {
            try {
                Scanner scanner = new Scanner(this.wordList);
                while(scanner.hasNextLine()) {
                    String word = scanner.nextLine();
                    try {
                        tryJLabel.setText("Trying " + word);
                        PDDocument document = PDDocument.load(this.file, word);
                        if (document.isEncrypted()) {
                            document.setAllSecurityToBeRemoved(true);
                            document.save(this.file);
                            document.close();
                            tryJLabel.setText("Password Found : " + word);
                            return;
                        }
                    }catch(Exception e) {
                        
                    }
                }
            }catch(Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        } else {
            generateWords();
        }
    }

    public void generateWords() {
        try {
            this.threads = new ArrayList<Thread>();
            for (int i = this.startLength; i <= this.endLength; i++) {
                CombinationThread ct = new CombinationThread(file, i, "abcdefghijklmnopqrstuvwxyz", tryJLabel);
                ct.start();
                threads.add(ct);
            }
            Iterator<Thread> iterable = threads.iterator();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public void terminateThread() {
        Iterator<Thread> iterable = threads.iterator();
        while(iterable.hasNext()) {
            Thread t = iterable.next();
            t.interrupt();  // new way to stop thread
        }
    }
    
}
