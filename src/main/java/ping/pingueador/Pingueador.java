/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ping.pingueador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class Pingueador {

    // Colores Modernos
    private static final Color COLOR_FONDO = new Color(236, 240, 241);
    private static final Color COLOR_TARJETA = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(52, 73, 94);
    private static final Color COLOR_HOVER = new Color(41, 128, 185);
    private static final Color COLOR_TEXTO = new Color(44, 62, 80);
    private static final Color COLOR_ROJO_CERRAR = new Color(192, 57, 43);

    // Variable para mover la ventana
    private static Point puntoInicial = null;

    public static void main(String[] args) {
        // Look and Feel Nativo
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        JFrame frame = new JFrame();
        frame.setUndecorated(true); // QUITA LA BARRA DE ARRIBA Y EL LOGO
        frame.setSize(950, 650);
        frame.getContentPane().setBackground(COLOR_FONDO);
        frame.setLayout(new BorderLayout());
        
        // Borde fino para que la ventana se distinga del fondo
        frame.getRootPane().setBorder(new LineBorder(new Color(200, 200, 200), 1));

        // --- LÓGICA PARA ARRASTRAR LA VENTANA ---
        MouseAdapter moverVentana = new MouseAdapter() {
            public void mousePressed(MouseEvent e) { puntoInicial = e.getPoint(); }
            public void mouseDragged(MouseEvent e) {
                Point p = e.getLocationOnScreen();
                frame.setLocation(p.x - puntoInicial.x, p.y - puntoInicial.y);
            }
        };
        frame.addMouseListener(moverVentana);
        frame.addMouseMotionListener(moverVentana);

        // --- ENCABEZADO PERSONALIZADO ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_TARJETA);
        header.setBorder(new EmptyBorder(10, 20, 10, 10));
        
        JLabel tituloApp = new JLabel("PANEL DE CONECTIVIDAD");
        tituloApp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tituloApp.setForeground(COLOR_TEXTO);
        
       JButton btnCerrar = new JButton("X");
btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
btnCerrar.setForeground(Color.GRAY);
btnCerrar.setBorder(null);
btnCerrar.setContentAreaFilled(false);
btnCerrar.setFocusPainted(false);
btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
btnCerrar.setPreferredSize(new Dimension(30, 30));

btnCerrar.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseEntered(MouseEvent e) {
        btnCerrar.setForeground(COLOR_ROJO_CERRAR);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        btnCerrar.setForeground(Color.GRAY);
    }
});

btnCerrar.addActionListener(e -> System.exit(0));


        header.add(tituloApp, BorderLayout.WEST);
        header.add(btnCerrar, BorderLayout.EAST);

        // --- DATOS ---
        Map<String, String> bases = new LinkedHashMap<>();
        bases.put("Base Piedras", "10.68.8.2");
        bases.put("Base Cochabamba", "192.168.104.2");
        bases.put("Base Luro", "172.31.190.2");
        bases.put("Base Nuñez", "172.31.192.2");
        bases.put("Base Ocampo", "172.17.5.2");
        bases.put("Seguridad Vial", "10.68.4.2");
        bases.put("CGM", "10.50.9.2");

        Map<String, String> playas = new LinkedHashMap<>();
        playas.put("Playa Tacuarí", "172.31.90.2");
        playas.put("Playa 9 de Julio", "10.42.13.3");
        playas.put("Playa Dakota", "10.215.10.2");
        playas.put("Playa Libres del Sur", "10.225.22.2");
        playas.put("Playa Quinquela", "10.69.84.2");
        playas.put("Playa Sarmiento", "172.30.110.2");
        playas.put("Pañol", "10.62.0.2");
        playas.put("Playa Río IV", "172.31.51.9");

        // --- CUERPO PRINCIPAL ---
        JPanel cuerpo = new JPanel(new GridLayout(1, 2, 20, 0));
        cuerpo.setBackground(COLOR_FONDO);
        cuerpo.setBorder(new EmptyBorder(20, 20, 20, 20));

        cuerpo.add(crearSeccion(bases, "BASES OPERATIVAS"));
        cuerpo.add(crearSeccion(playas, "PLAYAS "));

        // Montar Frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(cuerpo, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel crearSeccion(Map<String, String> datos, String titulo) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COLOR_TARJETA);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(COLOR_HOVER);
        lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        wrapper.add(lbl, BorderLayout.NORTH);

        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BoxLayout(btnContainer, BoxLayout.Y_AXIS));
        btnContainer.setBackground(COLOR_TARJETA);

        for (Map.Entry<String, String> entry : datos.entrySet()) {
            btnContainer.add(crearBoton(entry.getKey(), entry.getValue()));
            btnContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JScrollPane scroll = new JScrollPane(btnContainer);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(COLOR_TARJETA);
        wrapper.add(scroll, BorderLayout.CENTER);

        return wrapper;
    }

    private static JButton crearBoton(String nombre, String ip) {
        JButton btn = new JButton("<html><center>" + nombre + "<br><small>" + ip + "</small></center></html>");
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setFocusPainted(false);
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(5, 5, 5, 5));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(COLOR_HOVER); }
            public void mouseExited(MouseEvent e) { btn.setBackground(COLOR_BOTON); }
        });

        btn.addActionListener(e -> {
            try {
                new ProcessBuilder("cmd.exe", "/c", "start", "cmd.exe", "/k", 
                    "title " + nombre + " [" + ip + "] && ping " + ip + " -t").start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        return btn;
    }
}