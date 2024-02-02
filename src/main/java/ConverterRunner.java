import javax.swing.*;

public class ConverterRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConverterGUI gui = new ConverterGUI();
            }
        });
    }
}