import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ResonancePad {
    private static boolean isFlowing = false;
    public static void main(String[] args) {
        try {
            // 1. Inizializzazione Audio
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[0].programChange(4);
            channels[0].controlChange(64, 68);
            int[] pentatonicScale = {
                    57, 59, 60, 62, 64,
                    65, 67, 69, 71, 72
            };
            int flowDroneNote = 36;

            Timer flowTimer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Se il timer scatta, significa che ti sei fermato. Spegniamo il drone.
                    channels[1].noteOff(flowDroneNote);
                    isFlowing = false;
                }
            });
            flowTimer.setRepeats(false);

            JFrame frame = new JFrame("Resonance Pad - Alpha");
            frame.setSize(1000, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Creazione dell'Area di Testo
            JTextArea textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Per dare un tocco estetico più "Wow" e meno da blocco note di Windows 95
            textArea.setBackground(new java.awt.Color(30, 30, 30)); // Sfondo scuro
            textArea.setForeground(new java.awt.Color(200, 200, 200)); // Testo chiaro
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 18)); // Font grande
            textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JScrollPane scrollPane = new JScrollPane(textArea);

            // 3. IL CAMBIAMENTO È QUI: Attacchiamo il KeyListener alla JTextArea, non al JFrame!
            textArea.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    char keyChar = e.getKeyChar();

                    if (Character.isLetterOrDigit(keyChar)) {
                        int index = Math.abs(keyChar % 10);
                        int note = pentatonicScale[index];
                        channels[0].noteOn(note, 20);
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });

            // 4. Mostriamo la finestra a schermo
            frame.add(scrollPane);
            frame.setVisible(true); // frame.pack() rimpicciolirebbe la finestra, usiamo solo setVisible

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}