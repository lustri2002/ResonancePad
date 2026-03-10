import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame {
    private JFrame frame;
    private JTextArea textArea;
    private final AudioController audioController; // Il ponte verso il suono

    // Passiamo il controller audio quando creiamo la finestra
    public MainFrame(AudioController audioController) {
        this.audioController = audioController;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Resonance Pad - Versione Modulare");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(30, 30, 30));
        textArea.setForeground(new Color(200, 200, 200));
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);

        // Il "Microfono" per la tastiera
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                // Invece di fare logica MIDI qui, passiamo semplicemente la lettera all'AudioController!
                audioController.playKeystroke(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        frame.add(scrollPane);
    }

    // Metodo per mostrare la finestra
    public void show() {
        frame.setVisible(true);
    }
}