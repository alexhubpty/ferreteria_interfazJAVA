/**
 * Clase FrameBase: Contenedor principal y base para todas las ventanas de la
 * aplicación "Ferretería El Polaco". Hereda de JFrame y debe ser programada
 * completamente a mano para asegurar la máxima flexibilidad y compatibilidad
 * con VS Code y Copilot (evitando el diseñador de NetBeans).
 *
 * Requisitos de diseño basados en la imagen proporcionada (el contenedor gris
 * con el borde redondeado azul claro):
 * 1.  El frame debe inicializarse maximizado (MAXIMIZED_BOTH).
 * 2.  Debe usar un BorderLayout como gestor principal.
 * 3.  Crear y añadir un JPanel principal llamado 'contenedorPrincipal' al
 * CENTRO del JFrame.
 * 4.  El 'contenedorPrincipal' debe tener un color de fondo gris claro (similar
 * al borde exterior de tu diseño) y usar un gestor de diseño nulo (null layout)
 * para posicionar un panel interno.
 * 5.  Crear un JPanel interno llamado 'panelFondo' con un color azul claro
 * (#E5F0FA), que contenga el diseño real de las vistas (Login, Dashboard,
 * etc.).
 * 6.  El 'panelFondo' debe ser centrado y tener dimensiones fijas (ej.
 * 1280x720) dentro del 'contenedorPrincipal' para simular el efecto de tarjeta
 * centrada con bordes redondeados (aunque los bordes redondeados serán
 * simplificados).
 *
 * Objetivo: Crear la estructura visual que servirá como base para todas las
 * demás pantallas.
 */
     
import java.awt.*;
import javax.swing.*;

public class FrameBase extends JFrame {

  private JPanel contenedorPrincipal;
  private JPanel panelFondo; // El panel azul claro redondeado

  public FrameBase(String titulo) {
    super(titulo);

    // 1. Configuración principal de la ventana
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar

    // El BorderLayout es fundamental para que el panel principal se estire
    setLayout(new BorderLayout());

    initComponents(); // Llamada al método de inicialización

    setVisible(true);
  }

  private void initComponents() {

    // --- Contenedor Principal (El panel gris) ---
    contenedorPrincipal = new JPanel();
    // Usamos un color gris claro para simular el borde exterior de tu diseño
    contenedorPrincipal.setBackground(new Color(230, 230, 230));

    // Usamos GridBagLayout en el principal para centrar el panel interno
    contenedorPrincipal.setLayout(new GridBagLayout());

    // --- Panel de Fondo (El panel azul claro con el contenido) ---
    panelFondo = new JPanel();
    // Color azul claro de tu diseño
    panelFondo.setBackground(new Color(229, 240, 250));
    panelFondo.setPreferredSize(
        new Dimension(1280, 720)); // Tamaño fijo para centrarlo
        // NOTA: Para las esquinas redondeadas, necesitarías crear una clase
        // JPanel personalizada, pero por ahora nos enfocaremos en la estructura
        // para la flexibilidad.

        // Añadir el panel de fondo al contenedor principal, centrándolo
        contenedorPrincipal.add(panelFondo, new GridBagConstraints());

    // Añadir el contenedor principal al centro del JFrame (se estirará para
    // llenar toda la pantalla)
    add(contenedorPrincipal, BorderLayout.CENTER);

    // Aquí puedes agregar un método para cargar la vista inicial (ej. el Login)
    cargarVistaInicial();
  }

  // Este método es donde cargaremos el contenido específico (ej. el formulario
  // de Login)
  public void setContenido(JPanel nuevaVista) {
    panelFondo.removeAll(); // Elimina el contenido anterior
    panelFondo.setLayout(
        new BorderLayout()); // Usa un layout flexible para el contenido interno
    panelFondo.add(nuevaVista, BorderLayout.CENTER);
    panelFondo.revalidate();
    panelFondo.repaint();
  }

  private void cargarVistaInicial() {
    // Copilot te ayudará a instanciar y cargar tu primer formulario (ej. el
    // Login) Por ahora, solo añadimos una etiqueta de prueba:
    JLabel etiquetaPrueba =
        new JLabel("Cargando formulario de Login...", SwingConstants.CENTER);
    panelFondo.setLayout(new BorderLayout());
    panelFondo.add(etiquetaPrueba, BorderLayout.CENTER);
  }

  // --- Método Main para ejecutar la ventana ---
  public static void main(String[] args) {
    // Es buena práctica ejecutar la GUI en el Event Dispatch Thread (EDT)
    SwingUtilities.invokeLater(() -> {
      new FrameBase("Ferretería El Polaco - Base").setVisible(true);
    });
  }
}
