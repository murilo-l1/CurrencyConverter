import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

public class ConverterGUI implements EventListener {

    private ConverterController controller = new ConverterController();
    private JFrame converterWindow;

    private String fromCurrencyCode;
    private String toCurrencyCode;
    private float amount;

    private JTextField amountTextField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JButton convertButton;
    private JTextArea resultTextArea;

    public ConverterGUI() {
        createApplicationWindow();
        setComponents();
    }

    private void createApplicationWindow() {
        converterWindow = new JFrame("Currency Converter");
        converterWindow.setSize(600, 800);
        converterWindow.setLocationRelativeTo(null);
        converterWindow.setResizable(false);
        converterWindow.setLayout(null);
        converterWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        converterWindow.getContentPane().setBackground(Color.LIGHT_GRAY);
        converterWindow.setVisible(true);
    }

    private void setComponents() {
        addLogo();
        addTitle();
        addDescriptions();
        addAmountTextField();
        addCurrenciesComboBoxes();
        setConvertButton();
        addResultTextArea();
    }

    private void addLogo() {
        JLabel cConverterLogo = new JLabel(loadImage("src/main/assets/logo.png"));
        cConverterLogo.setBounds(190, 50, 200, 200);
        converterWindow.add(cConverterLogo);
        refreshWindow();
    }

    private ImageIcon loadImage(String imagePath) {
        try {
            //carregar uma imagem a partir de um dado path
            BufferedImage image = ImageIO.read(new File(imagePath));
            return new ImageIcon(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Não foi possível encontrar a imagem solicitada");
        return null;
    }

    private void refreshWindow() {
        converterWindow.revalidate();
        converterWindow.repaint();
    }

    private void addTitle() {
        JLabel converterLabel = new JLabel("Currency Converter");
        converterLabel.setBounds(120, 270, 400, 40);
        converterLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        converterWindow.add(converterLabel);
        refreshWindow();
    }

    private void addDescriptions() {
        JLabel amountTextLabel = new JLabel("Amount :");
        amountTextLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        amountTextLabel.setBounds(100, 340, 230, 35);
        converterWindow.add(amountTextLabel);
        refreshWindow();

        JLabel fromTextLabel = new JLabel("From :");
        fromTextLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        fromTextLabel.setBounds(100, 420, 230, 25);
        converterWindow.add(fromTextLabel);
        refreshWindow();

        JLabel toTextLabel = new JLabel("To :");
        toTextLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        toTextLabel.setBounds(100, 480, 230, 25);
        converterWindow.add(toTextLabel);
        refreshWindow();
    }

    private void addAmountTextField() {
        amountTextField = new JTextField();
        amountTextField.setBounds(230, 340, 200, 40);
        amountTextField.setFont(new Font("Verdana", Font.BOLD, 25));
        amountTextField.setOpaque(false);
        amountTextField.setBorder(BorderFactory.createEmptyBorder());
        converterWindow.add(amountTextField);
    }

    private void addCurrenciesComboBoxes() {
        this.fromCurrencyComboBox = createCurrencyComboBox();
        fromCurrencyComboBox.setBounds(190, 420, 230, 25);
        fromCurrencyComboBox.setMaximumRowCount(2);
        refreshWindow();
        converterWindow.add(fromCurrencyComboBox);

        this.toCurrencyComboBox = createCurrencyComboBox();
        toCurrencyComboBox.setBounds(190, 480, 230, 25);
        converterWindow.add(toCurrencyComboBox);
        refreshWindow();
    }

    //metodo para criar as duas comboBox que criarão a escolha das moedas já garantindo menos problemas com inputs para o controller
    private JComboBox<String> createCurrencyComboBox() {
        try {
            String[] currenciesList = controller.loadCurrencyList().toArray(new String[0]);
            JComboBox<String> currenciesComboBox = new JComboBox<>(currenciesList);
            return currenciesComboBox;
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(converterWindow, "Error loading currency list: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
            return new JComboBox<>();
        }
    }

    private void setConvertButton() {
        convertButton = new JButton("Convert");
        convertButton.setBounds(190, 520, 230, 55);
        convertButton.setBorder(BorderFactory.createEmptyBorder());
        makeConversionAction();
        converterWindow.add(convertButton);
    }

    private void makeConversionAction() {
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pegando e garantindo que o campo digitado em amount é um número;
                try {
                    amount = Float.parseFloat(amountTextField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(converterWindow,"Invalid amount input: " + ex.getMessage());
                    return; // caso não for um número retornamos
                }
                fromCurrencyCode = (String) fromCurrencyComboBox.getSelectedItem();
                toCurrencyCode = (String) toCurrencyComboBox.getSelectedItem();

                // Com os dados necessários podemos chamar o controlador para aplicar a conversão
                try {
                    float convertedAmount = controller.convertValues(fromCurrencyCode, toCurrencyCode, amount);
                    //inserindo o valor retornado no método na nossa área de texto
                    resultTextArea.setText(String.format("%.2f %s", convertedAmount, toCurrencyCode));
                } catch (IOException ex) {
                    System.err.println("IOException occurred: " + ex.getMessage());
                    ex.printStackTrace();
                }
                catch (CurrencyNotFoundException cne){
                    System.err.println(cne.getMessage());
                }
            }
        });
    }

    private void addResultTextArea() {
        resultTextArea = new JTextArea();
        resultTextArea.setFont(new Font("Verdana", Font.BOLD, 40));
        resultTextArea.setBounds(170, 600, 320, 140);
        resultTextArea.setEditable(false);
        resultTextArea.setOpaque(false);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setLineWrap(true);
        refreshWindow();
        converterWindow.add(resultTextArea);
    }
}