import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

public class ConverterGUI {

    private ConverterController controller;
    private JFrame converterWindow;
    private EventListener eventListener;

    private JTextField amountTextField;
    private JTextField fromCurrencyTextField;
    private JTextField toCurrencyTextField;


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
     - fromC, toC (com a lista da API) e botão de convert;
     - implementar eventListener que chama o metodo de 'convertValues' quando clicar no botão de convert
    */

    private void setComponents(){
        JLabel cConverterLogo = new JLabel(loadImage("src/main/assets/logo.png"));
        cConverterLogo.setBounds(180, 20, 200, 200);
        refreshWindow();
        converterWindow.add(cConverterLogo);

        fromCurrencyTextField = new JTextField();
        fromCurrencyTextField.setBounds(180,10,100,20);
        refreshWindow();
        converterWindow.add(fromCurrencyTextField);


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
        ConverterGUI converterGUI = new ConverterGUI();
    }
}
