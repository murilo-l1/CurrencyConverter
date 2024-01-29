import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

public class ConverterGUI implements EventListener{

    private ConverterController controller = new ConverterController();
    private JFrame converterWindow;
    private ActionEvent e;

    private String fromCurrencyCode;
    private String toCurrencyCode;
    private float amount;

    private JTextField amountTextField;
    private JButton convertButton;
    private JTextArea resultTextArea;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;


    public ConverterGUI() {
        createApplicationWindow();
        setComponents();
    }

    private void createApplicationWindow(){
        converterWindow = new JFrame("Currency Converter");
        converterWindow.setSize(600,800);
        converterWindow.setLocationRelativeTo(null);
        converterWindow.setResizable(false);
        converterWindow.setLayout(null);
        converterWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        converterWindow.setVisible(true);
    }

    /*
    TODO:
     - implementar campo de amount e resultado;
     - implementar campos de identacao : from, to, result - addRefereceTexts
     -  botão de convert;
     - implementar eventListener que chama o metodo de 'convertValues' quando clicar no botão de convert
    */

    private void setComponents() {
        addLogo();
        addTitle();
        addDescription();
        addAmount();
        setCurrenciesComboBoxes();
        setConvertButton();
        while (true){
            addAmount();
            System.out.println(amount);
        }
    }

    private void addLogo() {
        JLabel cConverterLogo = new JLabel(loadImage("src/main/assets/logo.png"));
        cConverterLogo.setBounds(190, 50, 200, 200);
        converterWindow.add(cConverterLogo);
        refreshWindow();
    }

    private void addTitle() {
        JLabel converterLabel = new JLabel("Currency Converter");
        converterLabel.setBounds(120, 270, 400, 40);
        converterLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        converterWindow.add(converterLabel);
        refreshWindow();
    }

    private void addDescription(){
        JLabel amountTextLabel = new JLabel("Amount :");
        amountTextLabel.setFont(new Font("Verdana", Font.PLAIN,20));
        amountTextLabel.setBounds(100,340,230,35);
        converterWindow.add(amountTextLabel);
        refreshWindow();

        JLabel fromTextLabel = new JLabel("From :");
        fromTextLabel.setFont(new Font("Verdana", Font.PLAIN,20));
        fromTextLabel.setBounds(100,420,230,25);
        converterWindow.add(fromTextLabel);
        refreshWindow();

        JLabel toTextLabel = new JLabel("To :");
        toTextLabel.setFont(new Font("Verdana", Font.PLAIN,20));
        toTextLabel.setBounds(100,480,230,25);
        converterWindow.add(toTextLabel);
        refreshWindow();
    }

    private void setCurrenciesComboBoxes() {
        this.fromCurrencyComboBox = createCurrencyComboBox();
        fromCurrencyComboBox.setEditable(true);
        fromCurrencyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromCurrencyCode = (String) fromCurrencyComboBox.getSelectedItem();
            }
        });
        fromCurrencyComboBox.setBounds(190,420,230,25);
        converterWindow.add(fromCurrencyComboBox);

        this.toCurrencyComboBox = createCurrencyComboBox();
        toCurrencyComboBox.setEditable(true);
        toCurrencyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toCurrencyCode = (String) toCurrencyComboBox.getSelectedItem();
            }
        });
        toCurrencyComboBox.setBounds(190,480,230,25);
        converterWindow.add(toCurrencyComboBox);
        refreshWindow();
    }

    private void addAmount(){
        amountTextField = new JTextField();
        amountTextField.setBounds(200, 345, 200, 40);

        if(!amountTextField.getText().trim().isEmpty()){ // Trim to remove leading/trailing spaces
            try {
                this.amount = Float.parseFloat(amountTextField.getText());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input: " + e.getMessage());
                return;
            }
        }
        converterWindow.add(amountTextField);
    }


    private void setConvertButton(){
        convertButton = new JButton("Convert");
        convertButton.setBounds(190, 520, 200, 60);
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.convertValues(fromCurrencyCode, toCurrencyCode, amount);
                }catch (IOException ioe){

                }
                catch (CurrencyNotFoundException cne){

                }

            }
        });
        converterWindow.add(convertButton);
    }

    //metodo para criar as duas comboBox que criarão a escolha das moedas
    private JComboBox<String> createCurrencyComboBox(){
        //try {
            //String[] currenciesList = controller.loadCurrencyList().toArray(new String[0]);
            String[] currenciesList = {"BRL", "USD", "EUR", "BTC"};
            JComboBox<String> currenciesComboBox= new JComboBox<>(currenciesList);
            return currenciesComboBox;
        /*} catch (IOException ioe){
            JOptionPane.showMessageDialog(converterWindow, "Error loading currency list: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
            return new JComboBox<>();
        }*/
    }

    private void refreshWindow(){
        converterWindow.revalidate();
        converterWindow.repaint();
    }

    private ImageIcon loadImage(String imagePath){
        try{
            //carregar uma imagem a partir de um dado path
            BufferedImage image = ImageIO.read(new File(imagePath));
            return new ImageIcon(image);

        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Não foi possível encontrar a imagem solicitada");
        return null;
    }

    public static void main(String[] args) {
        ConverterGUI gui = new ConverterGUI();
    }
}
