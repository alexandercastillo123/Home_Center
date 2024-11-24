import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.time.*;
import java.time.format.*;

public class LoginHomeCenter extends JFrame {
    private JTextField operatorField;
    private JPasswordField passwordField;
    private JLabel dateTimeLabel;
    private JLabel statusLabel;
    private JLabel passwordLabel;
    private JLabel operatorErrorLabel;  // Etiqueta de error para ID de operador
    private static final Color DARK_RED = new Color(128, 0, 0);
    private JTextField activeField = null;
    private static final String VALID_OPERATOR_PASSWORD = "12345";
    private static final String VALID_OPERATOR_ID = "12345";  // ID válido de operador
    private static final Color LIGHT_CELESTE = new Color(173, 216, 230);  // Celeste claro

    public LoginHomeCenter() {
        setTitle("LoginHomeCenter_Maestro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new GridLayout(1, 2, 2, 0));
        getContentPane().setBackground(Color.WHITE);

        // Left Panel
        JPanel leftPanel = createLeftPanel();

        // Right Panel
        JPanel rightPanel = createRightPanel();

        // Add main panels to frame
        add(leftPanel);
        add(rightPanel);

        // Initialize clock update
        initializeDateTime();

        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 2));
        panel.setBackground(DARK_RED);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // Image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(0 , 250));
        imagePanel.setBackground(DARK_RED);

        // Load and scale image
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/HomeCenter.jpeg"));

        // Escalar la imagen a un tamaño más pequeño (por ejemplo, 200x150)
        Image scaledImage = imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // White separator after image
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        imagePanel.add(separator, BorderLayout.SOUTH);

        // Login fields panel
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));  // Cambiado a FlowLayout horizontal
        loginPanel.setBackground(DARK_RED);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel operatorLabel = new JLabel("ID de operador:");
        operatorLabel.setForeground(Color.WHITE);
        operatorField = new JTextField();
        operatorField.setEditable(true);
        operatorField.setPreferredSize(new Dimension(200, 30)); // Ajusta el tamaño aquí
        setupField(operatorField, 5);  // Limitar a 5 dígitos

        // Inicializar contraseña y etiqueta como invisibles
        passwordLabel = new JLabel("Contraseña:");  
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField();
        passwordField.setEditable(false);  // No editable hasta "Entrar"
        passwordField.setPreferredSize(new Dimension(200, 30)); // Ajusta el tamaño aquí
        setupField(passwordField, 5);  // Limitar a 5 dígitos

        // Inicialmente no se muestran
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);

        // Error label for invalid operator ID
        operatorErrorLabel = new JLabel();
        operatorErrorLabel.setForeground(Color.RED);
        operatorErrorLabel.setVisible(false);  // Initially hidden

        // Añadir los campos y etiquetas al loginPanel
        loginPanel.add(operatorLabel);
        loginPanel.add(operatorField);
        loginPanel.add(operatorErrorLabel);  // Agregar la etiqueta de error debajo del campo
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        // Footer panel with Registrar001 and date/time
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(DARK_RED);

        statusLabel = new JLabel("Registrar001", SwingConstants.RIGHT);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 20));

        dateTimeLabel = new JLabel();
        dateTimeLabel.setForeground(Color.WHITE);
        dateTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dateTimeLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));

        footerPanel.add(dateTimeLabel, BorderLayout.WEST);
        footerPanel.add(statusLabel, BorderLayout.EAST);

        panel.add(imagePanel, BorderLayout.NORTH);
        panel.add(loginPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void setupField(JTextField field, int maxLength) {
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                activeField = field;
            }
        });

        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                if ((fb.getDocument().getLength() + text.length()) <= maxLength) {
                    super.insertString(fb, offset, text, attr);
                }
                updateFieldColor();
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if ((fb.getDocument().getLength() - length + text.length()) <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
                updateFieldColor();
            }
        });
    }

    private void updateFieldColor() {
        if (operatorField.getText().length() == 5) {
            operatorField.setBackground(LIGHT_CELESTE);
        } else {
            operatorField.setBackground(Color.WHITE);
        }
        
        if (passwordField.getText().length() == 5) {
            passwordField.setBackground(LIGHT_CELESTE);
        } else {
            passwordField.setBackground(Color.WHITE);
        }
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 2));
        panel.setBackground(DARK_RED);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // Numeric keypad
        JPanel keypadPanel = new JPanel(new GridLayout(4, 4, 2, 2));
        keypadPanel.setBackground(Color.WHITE);
        keypadPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 10));

        // Fuente personalizada para los botones
        Font buttonFont = new Font("Arial", Font.PLAIN, 10);  // Fuente con tamaño 20

        String[] buttons = {
            "7", "8", "9", "Cancelar",
            "4", "5", "6", "Borrar",
            "1", "2", "3", "Atrás",
            " ", "0", "00", "Entrar"
        };

        for (String label : buttons) {
            if (!label.isEmpty()) {
                JButton button = createButton(label);
                keypadPanel.add(button);
            } else {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setBackground(DARK_RED);
                keypadPanel.add(emptyPanel);
            }
        }

        // Bottom panel with help and submit buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 0));
        bottomPanel.setBackground(DARK_RED);

        // Ayuda Button
        JButton helpButton = new JButton();
        ImageIcon helpIcon = new ImageIcon(getClass().getResource("/img/ayuda.png"));
        Image helpImage = helpIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Tamaño reducido
        helpButton.setIcon(new ImageIcon(helpImage));
        styleActionButton(helpButton);
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sistema de Ayuda: Debe ingresar su id de operario y continuar con su contraseña para trabajar."));
        helpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
        
        // Submit Button
        JButton submitButton = new JButton();
        ImageIcon submitIcon = new ImageIcon(getClass().getResource("/img/submit.png"));
        Image submitImage = submitIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Tamaño reducido
        submitButton.setIcon(new ImageIcon(submitImage));
        styleActionButton(submitButton);
        submitButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Por nada no se te paga ingresa cuenta y a trabajar se ah dicho."));
        submitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(DARK_RED);
        buttonPanel.add(helpButton);
        buttonPanel.add(submitButton);

        panel.add(keypadPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 20));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeField != null) {
                    String currentText = activeField.getText();
                    if (label.equals("Entrar")) {
                        validateIp();
                    } else if (label.equals("Cancelar")) {
                        operatorField.setText("");
                        passwordField.setText("");
                        passwordLabel.setVisible(false);
                        passwordField.setVisible(false);
                    } else if (label.equals("Borrar")) {
                        if (activeField.getText().length() > 0) {
                            activeField.setText(currentText.substring(0, currentText.length() - 1));
                        }
                    } else {
                        activeField.setText(currentText + label);
                    }
                }
            }
        });

        return button;
    }

    private void styleActionButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(DARK_RED);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(100, 50));  // You can adjust the size if needed
    }

    private void validateIp() {
        String enteredOperatorId = operatorField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        // Only show the password field after verifying the operator ID
        if (enteredOperatorId.equals(VALID_OPERATOR_ID)) {
            passwordLabel.setVisible(true);
            passwordField.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "ID de operador incorrecto.");
            return;  // Stop further action if operator ID is incorrect
        }

        // Verify the password
        if (enteredPassword.equals(VALID_OPERATOR_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Bienbenido a Maestro ahora a trabajar.");
            // Proceed to next step
        } else {
            JOptionPane.showMessageDialog(this, "Contraseña incorrecta.");
        }
    }

    private void initializeDateTime() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                dateTimeLabel.setText(now.format(formatter));
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginHomeCenter().setVisible(true);
            }
        });
    }
}
