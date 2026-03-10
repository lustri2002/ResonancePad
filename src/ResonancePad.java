void main() {
    /* 1. Accendiamo il motore audio */
    AudioController audio = new AudioController();

    // 2. Costruiamo l'interfaccia dandole in pasto il motore audio
    MainFrame ui = new MainFrame(audio);

    // 3. Mostriamo l'applicazione a schermo
    ui.show();
}