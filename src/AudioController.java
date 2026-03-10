import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.swing.Timer;

public class AudioController {
    private Synthesizer synth;
    private MidiChannel[] channels;

    // Le tue impostazioni personalizzate
    final private int[] customScale = {60, 62, 63, 65, 67, 68, 70, 72, 74, 75};
    final private int flowDroneNote = 36;

    private boolean isFlowing = false;
    private Timer flowTimer;

    public AudioController() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();

            // Impostazioni Canale 0
            channels[0].programChange(4);
            channels[0].controlChange(64, 68);

            // Impostazioni Canale 1 (Drone di sottofondo)
            channels[1].programChange(94); // Halo Pad per il drone

            // Il Timer che spegne il flow dopo 1.5 secondi
            flowTimer = new Timer(1500, e -> {
                channels[1].noteOff(flowDroneNote);
                isFlowing = false;
            });
            flowTimer.setRepeats(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // L'unico metodo che verrà chiamato dall'esterno
    public void playKeystroke(char keyChar) {
        if (Character.isLetterOrDigit(keyChar) || Character.isSpaceChar(keyChar)) {
            int index = Math.abs(keyChar % 10);
            int note = customScale[index];

            // Suona la nota con la tua velocity a 20
            channels[0].noteOn(note, 20);

            // Gestione del Flow Drone
            if (!isFlowing) {
                channels[1].noteOn(flowDroneNote, 30); // Volume del drone
                isFlowing = true;
            }
            flowTimer.restart(); // Rigira la clessidra
        }
    }
}