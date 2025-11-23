package com.ferreteria.view;
//LoginFrame - Interfaz visual de Login con imagen de fondo blur en panel rojo.

import com.ferreteria.controller.ControladorLogin;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
// import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Vista de Login - Interfaz gráfica
 * 
 * @author TÚ (Frontend)
 * 
 *         MODIFICACIONES NECESARIAS PARA MVC:
 *         - Añadir métodos getUsername() y getPassword()
 *         - Añadir método setControlador()
 *         - Conectar eventos de botones al controlador
 */
public class LoginFrame extends JPanel {
  private ImageRoundedPanel panelRojo;
  private JPanel panelFormulario;
  private JTextField campoUsuario;
  private JPasswordField campoContrasena;
  private JButton botonAcceder;
  private JButton botonCerrar;

  // NUEVO: Referencia al controlador
  private ControladorLogin controlador;

  public LoginFrame() {
    setOpaque(false);
    setLayout(new BorderLayout());
    initComponents();
  }

  // NUEVO: Método para inyectar el controlador
  public void setControlador(ControladorLogin controlador) {
    this.controlador = controlador;
    configurarEventos();
  }

  // NUEVO: Configurar eventos de botones
  private void configurarEventos() {
    botonAcceder.addActionListener(e -> {
      if (controlador != null) {
        controlador.intentarLogin();
      }
    });

    botonCerrar.addActionListener(e -> {
      if (controlador != null) {
        controlador.cerrarAplicacion();
      }
    });

    // Enter en password ejecuta login
    campoContrasena.addActionListener(e -> {
      if (controlador != null) {
        controlador.intentarLogin();
      }
    });
  }

  // NUEVO: Métodos públicos para que el controlador acceda a los datos
  public String getUsername() {
    String texto = campoUsuario.getText();
    // Evitar retornar el placeholder
    if (texto.equals("felix123ok")) {
      return "";
    }
    return texto;
  }

  public String getPassword() {
    String texto = new String(campoContrasena.getPassword());
    // Evitar retornar el placeholder
    if (texto.equals("******")) {
      return "";
    }
    return texto;
  }

  // NUEVO: Métodos para interactuar con la vista desde el controlador
  public void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public void mostrarMensaje(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
  }

  public void limpiarCampos() {
    campoUsuario.setText("");
    campoContrasena.setText("");
  }

  public void setLoginEnProceso(boolean enProceso) {
    botonAcceder.setEnabled(!enProceso);
    campoUsuario.setEnabled(!enProceso);
    campoContrasena.setEnabled(!enProceso);
    if (enProceso) {
      botonAcceder.setText("Validando...");
    } else {
      botonAcceder.setText("Acceder");
    }
  }

  // ... resto del código existente de initComponents(), crearPanelFormulario(),
  // etc.
  // (TODO EL CÓDIGO QUE YA TIENES SE MANTIENE IGUAL)

  private void initComponents() {
    JPanel inner = new JPanel(new GridBagLayout());
    inner.setOpaque(false);
    inner.setBorder(new EmptyBorder(50, 70, 50, 70));
    add(inner, BorderLayout.CENTER);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;

    // PANEL FORMULARIO (IZQUIERDA) - 35% del ancho
    panelFormulario = crearPanelFormulario();
    gbc.gridx = 0;
    gbc.weightx = 0.35;
    gbc.insets = new Insets(0, 0, 0, 50);
    inner.add(panelFormulario, gbc);

    // PANEL ROJO CON IMAGEN DE FONDO (DERECHA) - 65% del ancho
    panelRojo = new ImageRoundedPanel(60, new Color(70, 70, 70), "/resources/imagenes/fondo02.jpg");
    panelRojo.setLayout(null);
    gbc.gridx = 1;
    gbc.weightx = 0.65;
    gbc.insets = new Insets(0, 0, 0, 0);
    inner.add(panelRojo, gbc);

    // Branding en la parte inferior izquierda del panel rojo
    JLabel marca1 = new JLabel("Ferretería");
    marca1.setFont(new Font("SansSerif", Font.PLAIN, 36));
    marca1.setForeground(Color.WHITE);

    JLabel marca2 = new JLabel("El Polaco");
    marca2.setFont(new Font("SansSerif", Font.BOLD, 40));
    marca2.setForeground(Color.WHITE);

    JPanel marcaBox = new JPanel();
    marcaBox.setOpaque(false);
    marcaBox.setLayout(new BoxLayout(marcaBox, BoxLayout.Y_AXIS));
    marca1.setAlignmentX(Component.LEFT_ALIGNMENT);
    marca2.setAlignmentX(Component.LEFT_ALIGNMENT);
    marcaBox.add(marca1);
    marcaBox.add(Box.createVerticalStrut(2));
    marcaBox.add(marca2);

    panelRojo.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int margenX = 50;
        int margenY = 80;
        int x = margenX;
        int y = panelRojo.getHeight() - margenY - 80;
        marcaBox.setBounds(x, y, 350, 100);
      }
    });
    panelRojo.add(marcaBox);
  }

  private JPanel crearPanelFormulario() {
    JPanel p = new JPanel();
    p.setOpaque(false);
    p.setLayout(new BorderLayout());

    // Encabezado
    JPanel top = new JPanel();
    top.setOpaque(false);
    top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

    // Botón cerrar
    JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    closePanel.setOpaque(false);
    closePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
    botonCerrar = crearBotonCircularCerrar();
    closePanel.add(botonCerrar);
    top.add(closePanel);
    top.add(Box.createVerticalStrut(16));

    // Título y subtítulo
    JLabel lblTitulo = new JLabel("Login", SwingConstants.CENTER);
    lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 50));
    lblTitulo.setForeground(new Color(20, 20, 20));
    lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
    top.add(lblTitulo);
    top.add(Box.createVerticalStrut(50));

    // Subtítulo
    JLabel lblSub = new JLabel("Introduce tus credenciales", SwingConstants.CENTER);
    lblSub.setFont(new Font("SansSerif", Font.PLAIN, 26));
    lblSub.setForeground(new Color(60, 60, 60));
    lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblSub.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
    top.add(lblSub);

    p.add(top, BorderLayout.NORTH);

    // Formulario - alineado a la izquierda
    JPanel formWrapper = new JPanel();
    formWrapper.setOpaque(false);
    formWrapper.setLayout(new GridBagLayout());

    JPanel form = new JPanel();
    form.setOpaque(false);
    form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(40, 0, 0, 0);

    form.add(crearLabelCampo("Usuario"));
    form.add(Box.createVerticalStrut(8));
    campoUsuario = crearTextFieldConPlaceholder("felix123ok");
    form.add(campoUsuario);
    form.add(Box.createVerticalStrut(30));

    form.add(crearLabelCampo("Contraseña"));
    form.add(Box.createVerticalStrut(8));
    campoContrasena = crearPasswordFieldConPlaceholder("******");
    form.add(campoContrasena);
    form.add(Box.createVerticalStrut(45));

    botonAcceder = crearBotonAcceder();
    form.add(botonAcceder);

    // para dimensionar bien los campos password y text

    for (Component c : form.getComponents()) {
      if (c instanceof JComponent jc) {
        jc.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension d = jc.getPreferredSize();
        jc.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.height));
      }
    }

    formWrapper.add(form, gbc);
    p.add(formWrapper, BorderLayout.CENTER);
    return p;
  }

  private JLabel crearLabelCampo(String txt) {
    JLabel l = new JLabel(txt);
    l.setFont(new Font("SansSerif", Font.PLAIN, 30));
    l.setForeground(new Color(40, 40, 40));
    l.setAlignmentX(Component.LEFT_ALIGNMENT);
    return l;
  }

  private JTextField crearTextFieldConPlaceholder(String placeholder) {
    JTextField t = new RoundedTextField(500, 70, 20, new Color(215, 216, 220), new Color(215, 216, 220));
    aplicarPlaceholder(t, placeholder, false);
    t.setFont(new Font("SansSerif", Font.PLAIN, 24));
    return t;
  }

  private JPasswordField crearPasswordFieldConPlaceholder(String placeholder) {
    JPasswordField p = new RoundedPasswordField(500, 70, 20, new Color(215, 216, 220), new Color(215, 216, 220));
    aplicarPlaceholder(p, placeholder, true);
    p.setFont(new Font("SansSerif", Font.PLAIN, 24));
    return p;
  }

  private void aplicarPlaceholder(JTextComponent c, String text, boolean password) {
    Color placeholderColor = new Color(130, 130, 130);
    Color activeColor = new Color(30, 30, 30);
    c.setText(text);
    c.setForeground(placeholderColor);

    c.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        if (c.getForeground().equals(placeholderColor)) {
          c.setText("");
          c.setForeground(activeColor);
          if (password && c instanceof JPasswordField jf)
            jf.setEchoChar('•');
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (c.getText().isEmpty()) {
          c.setForeground(placeholderColor);
          c.setText(text);
          if (password && c instanceof JPasswordField jf)
            jf.setEchoChar((char) 0);
        }
      }
    });

    if (password && c instanceof JPasswordField jf)
      jf.setEchoChar((char) 0);
  }

  private JButton crearBotonAcceder() {
    JButton b = new JButton("Acceder") {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        super.paintComponent(g);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(300, 60);
      }
    };
    b.setFont(new Font("SansSerif", Font.BOLD, 30));
    b.setBackground(new Color(45, 45, 45));
    b.setForeground(Color.WHITE);
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    b.setContentAreaFilled(false);
    b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    b.setAlignmentX(Component.LEFT_ALIGNMENT);

    b.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        b.setBackground(new Color(90, 120, 90));
        b.repaint();
      }

      @Override
      public void mouseExited(MouseEvent e) {
        b.setBackground(new Color(45, 45, 45));
        b.repaint();
      }
    });

    return b;
  }

  private JButton crearBotonCircularCerrar() {
    JButton b = new JButton("✕");
    b.setFont(new Font("SansSerif", Font.BOLD, 40));
    b.setForeground(new Color(70, 70, 70));
    b.setBackground(new Color(210, 210, 210));
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    b.setContentAreaFilled(false);
    b.setPreferredSize(new Dimension(48, 48));
    b.setMaximumSize(new Dimension(48, 48));

    b.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(60, 60, 60));
        b.repaint();
      }

      @Override
      public void mouseExited(MouseEvent e) {
        b.setForeground(new Color(70, 70, 70));
        b.setBackground(new Color(210, 210, 210));
        b.repaint();
      }
    });

    b.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
      @Override
      public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(b.getBackground());
        g2.fillOval(0, 0, c.getWidth(), c.getHeight());
        g2.setColor(b.getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int x = (c.getWidth() - fm.stringWidth(b.getText())) / 2;
        int y = (c.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(b.getText(), x, y);
        g2.dispose();
      }
    });
    return b;
  }

  private static class RoundedTextField extends JTextField {
    private final int prefW, prefH, radius;
    private final Color bg;
    private final Color borderColor;

    public RoundedTextField(int prefW, int prefH, int radius, Color bg, Color borderColor) {
      this.prefW = prefW;
      this.prefH = prefH;
      this.radius = radius;
      this.bg = bg;
      this.borderColor = borderColor;
      setOpaque(false);
      setForeground(new Color(130, 130, 130));
      setFont(new Font("SansSerif", Font.PLAIN, 48));// que hace esta linea??? es innecesaria?? que es ese 48?
      setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(prefW, prefH);
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(bg);
      g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
      super.paintComponent(g2);
      g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(borderColor);
      g2.setStroke(new BasicStroke(2f));
      g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
      g2.dispose();
    }
  }

  private static class RoundedPasswordField extends JPasswordField {
    private final int prefW, prefH, radius;
    private final Color bg;
    private final Color borderColor;

    public RoundedPasswordField(int prefW, int prefH, int radius, Color bg, Color borderColor) {
      this.prefW = prefW;
      this.prefH = prefH;
      this.radius = radius;
      this.bg = bg;
      this.borderColor = borderColor;
      setOpaque(false);
      setForeground(new Color(130, 130, 130));
      setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(prefW, prefH);
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(bg);
      g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
      super.paintComponent(g2);
      g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(borderColor);
      g2.setStroke(new BasicStroke(2f));
      g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
      g2.dispose();
    }
  }

  // Panel redondeado con imagen de fondo blur y overlay de color.

  private static class ImageRoundedPanel extends JPanel {
    private final int arc;
    private final Color overlayColor;
    private BufferedImage imagenBlur;

    public ImageRoundedPanel(int arc, Color overlayColor, String rutaImagen) {
      this.arc = arc;
      this.overlayColor = overlayColor;
      setOpaque(false);
      cargarYProcesarImagen(rutaImagen);
    }

    private void cargarYProcesarImagen(String ruta) {
      try {
        BufferedImage original = ImageIO.read(getClass().getResourceAsStream(ruta));
        imagenBlur = aplicarBlur(original, 25); // Radio de blur: 25
      } catch (IOException | IllegalArgumentException e) {
        System.err.println("No se pudo cargar imagen: " + ruta);
        imagenBlur = null;
      }
    }

    private BufferedImage aplicarBlur(BufferedImage img, int radius) {
      int w = img.getWidth();
      int h = img.getHeight();
      BufferedImage blurred = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

      // aplicar convolución de desenfoque gaussiano
      float weight = 1.0f / (radius * radius);
      float[] data = new float[radius * radius];
      for (int i = 0; i < data.length; i++) {
        data[i] = weight;
      }

      Kernel kernel = new Kernel(radius, radius, data);
      ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
      return op.filter(img, blurred);
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

      // Recortar a bordes redondeados
      g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));

      // Dibujar imagen blur escalada y recortada (cover)
      if (imagenBlur != null) {
        int imgW = imagenBlur.getWidth();
        int imgH = imagenBlur.getHeight();
        int panelW = getWidth();
        int panelH = getHeight();

        double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);
        int scaledW = (int) (imgW * scale);
        int scaledH = (int) (imgH * scale);
        int x = (panelW - scaledW) / 2;
        int y = (panelH - scaledH) / 2;

        g2.drawImage(imagenBlur, x, y, scaledW, scaledH, null);
      }

      // Overlay semitransparente
      g2.setColor(new Color(overlayColor.getRed(), overlayColor.getGreen(),
          overlayColor.getBlue(), 180));
      g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

      g2.dispose();
      super.paintComponent(g);
    }
  }
}