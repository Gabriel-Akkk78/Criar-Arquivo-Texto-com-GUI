import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;

public class CreateTextFileGUI extends JFrame {
    private Formatter output;
    private JTextField accountField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField balanceField;
    private JTextArea displayArea;
    private JButton addButton;
    private JButton closeButton;
    
    public CreateTextFileGUI() {
        super("Create Text File");

        // Set up the GUI components
        setLayout(new FlowLayout());

        accountField = new JTextField(10);
        firstNameField = new JTextField(10);
        lastNameField = new JTextField(10);
        balanceField = new JTextField(10);
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        addButton = new JButton("Adicionar Dados");
        closeButton = new JButton("Salvar Mudanças");

        add(new JLabel("Número da Conta:"));
        add(accountField);
        add(new JLabel("Primeiro Nome:"));
        add(firstNameField);
        add(new JLabel("Sobrenome:"));
        add(lastNameField);
        add(new JLabel("Saldo:"));
        add(balanceField);
        add(addButton);
        add(closeButton);
        add(new JScrollPane(displayArea));

        openFile();
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                addRecord();
            }    
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                closeFile();
            }
        });
        
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void openFile() {
            try {
                output = new Formatter("clientes.txt");
                displayArea.append("Arquivo Aberto.\n");
            } catch (SecurityException securityException) {
                JOptionPane.showMessageDialog(this, "You do not have write access to this file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
            } catch (FileNotFoundException fileNotFoundException) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir ou  ao criar arquivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
            }
    }


    public void addRecord() {
        try {
            int account = Integer.parseInt(accountField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            double balance = Double.parseDouble(balanceField.getText());

            if (account > 0) {
                output.format("%d %s %s %.2f\n", account, firstName, lastName, balance);
                displayArea.append(String.format("Dados Adicionados: %d %s %s %.2f\n",
                            account, firstName, lastName, balance));
                clearFields();
                } else {
                    displayArea.append("Número da conta tem que ser maior que 0.\n");
                }
        } catch (FormatterClosedException formatterClosedException) {
            displayArea.append("Erro ao gravar no arquivo.\n");
        } catch (NoSuchElementException | NumberFormatException elementException) {
            displayArea.append("Entrada inválida. Tente Novamente.\n");
        }
    }
    
    public void closeFile() {
        if (output != null) {
            output.close();
            displayArea.append("Arquivo fechado com sucesso.\n");
        }
    }
    
    private void clearFields() {
        accountField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        balanceField.setText("");
    }
    
    public static void main(String[] args) {
        new CreateTextFileGUI();
    }
}
