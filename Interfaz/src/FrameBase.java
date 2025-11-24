/**
 * Clase FrameBase
 * Panel interno celeste con esquinas redondeadas y separación 40px.
 * Fondo gris reemplazado por imagen escalada.
 */
import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FrameBase extends JFrame {

  private ImagePanel contenedorPrincipal;
  private RoundedPanel panelFondo;

  public FrameBase(String titulo) {
    super(titulo);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    initComponents();
    ajustarATamanoMonitor(0);

    addComponentListener(new java.awt.event.ComponentAdapter() {
      @Override
      public void componentMoved(java.awt.event.ComponentEvent e) {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Rectangle b = gc.getBounds();
        setBounds(b);
      }
    });

    setVisible(true);
  }

  private void initComponents() {
    // Panel con imagen de fondo en lugar de color gris
    contenedorPrincipal = new ImagePanel("/resources/imagenes/fondo.jpg");
    contenedorPrincipal.setLayout(null);

    panelFondo = new RoundedPanel(60, new Color(229, 240, 250));
    panelFondo.setLayout(new BorderLayout());

    contenedorPrincipal.add(panelFondo);

    contenedorPrincipal.addComponentListener(new java.awt.event.ComponentAdapter() {
      @Override
      public void componentResized(java.awt.event.ComponentEvent e) {
        recalcularBoundsPanelFondo();
      }
    });

    add(contenedorPrincipal, BorderLayout.CENTER);
    cargarVistaInicial();
  }

  private void recalcularBoundsPanelFondo() {
    int margen = 40;
    int w = contenedorPrincipal.getWidth() - margen * 2;
    int h = contenedorPrincipal.getHeight() - margen * 2;
    if (w < 0) w = 0;
    if (h < 0) h = 0;
    panelFondo.setBounds(margen, margen, w, h);
    panelFondo.revalidate();
    panelFondo.repaint();
  }

  private void ajustarATamanoMonitor(int monitorIndex) {
    GraphicsDevice[] devices =
        GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    if (monitorIndex < 0 || monitorIndex >= devices.length) monitorIndex = 0;
    GraphicsDevice gd = devices[monitorIndex];
    Rectangle bounds = gd.getDefaultConfiguration().getBounds();
    setBounds(bounds);
  }

  public void setContenido(JPanel nuevaVista) {
    panelFondo.removeAll();
    panelFondo.add(nuevaVista, BorderLayout.CENTER);
    panelFondo.revalidate();
    panelFondo.repaint();
  }

  private void cargarVistaInicial() {
    // Cambiar según la vista que quieres probar:
    setContenido(new LoginFrame());
    //setContenido(new VentaFrame());
    //setContenido(new ResumenFacturaFrame());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new FrameBase("Ferretería El Polaco - Base"));
  }
}

/**
 * Panel con imagen de fondo escalada.
 */
class ImagePanel extends JPanel {
  private Image imagen;

  public ImagePanel(String rutaImagen) {
    try {
      imagen = ImageIO.read(getClass().getResourceAsStream(rutaImagen));
    } catch (IOException | IllegalArgumentException e) {
      System.err.println("No se pudo cargar la imagen: " + rutaImagen);
      setBackground(new Color(230, 230, 230)); // Fallback al gris
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (imagen != null) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
      g2.dispose();
    }
  }
}

/**
 * Panel redondeado que pinta su fondo con antialias.
 */
class RoundedPanel extends JPanel {
  private final int arc;
  private final Color fill;

  public RoundedPanel(int arc, Color fill) {
    this.arc = arc;
    this.fill = fill;
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(fill);
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
    g2.dispose();
    super.paintComponent(g);
  }
}
