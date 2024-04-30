import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextEdit extends JFrame implements ActionListener{

    public static JTextArea area;
    public static JFrame frame;
    public static int returnValue = 0;

    public static void main(String[] args) {
        TextEdit runner = new TextEdit();
    }

    public TextEdit(){
        run();
    }

    public void run(){
        frame = new JFrame("Text Edit");

        //look and feel *PESQUISAR*
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException ex){
            Logger.getLogger(TextEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        //atributos da janela
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640, 480);
        frame.setVisible(true);

        //construir o menu
        JMenuBar menuMain = new JMenuBar();
        JMenu menuFile = new JMenu("file");
        JMenuItem menuItemNew = new JMenuItem("new");
        JMenuItem menuItemSave = new JMenuItem("save");
        JMenuItem menuItemOpen = new JMenuItem("open");
        JMenuItem menuItemClose = new JMenuItem("close");

        menuItemNew.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemOpen.addActionListener(this);
        menuItemClose.addActionListener(this);

        menuMain.add(menuFile);

        menuFile.add(menuItemNew);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemOpen);
        menuFile.add(menuItemClose);
        
        frame.setJMenuBar(menuMain);
    }

    public void actionPerformed(ActionEvent e) {
        String ingest = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();
        switch(ae){
            case "open":
                returnValue = jfc.showOpenDialog(null);
                 if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File f = new File(jfc.getSelectedFile().getAbsolutePath());
                    try{
                        FileReader reader = new FileReader(f);
                        Scanner scan = new Scanner(reader);
                        while(scan.hasNextLine()){
                            String line = scan.nextLine() + "\n";
                            ingest = ingest + line;
                        }
                        scan.close();
                        area.setText(ingest);
                    }
                    catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    break;
                 }
            case "save":
                 returnValue = jfc.showSaveDialog(null);
                 try{
                    File f = new File(jfc.getSelectedFile().getAbsolutePath());
                    FileWriter writer = new FileWriter(f);
                    writer.write(area.getText());
                    writer.close(); 
                 }
                 catch (FileNotFoundException ex) {
                    Component f = null;
                    JOptionPane.showMessageDialog(f,"File not found.");
                 }
                 catch (IOException ex) {
                    Component f = null;
                    JOptionPane.showMessageDialog(f,"Error.");
                 }
                 break;
            case "new":
                 area.setText("");
                 break;
            case "close":
                 System.exit(0);
        }

    }

}