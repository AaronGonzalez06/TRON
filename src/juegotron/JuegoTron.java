/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package juegotron;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Aaron
 */
public class JuegoTron extends JFrame {

    JLabel fondoMenuPrincipal, titulo, nombrePersonaje;
    JLabel imagen, imagen2, imagen3;
    JButton BotonEmpezar;
    JPanel PanelPersonajes, panelJuego;

    //personaje con el que voy a jugar
    String Mipersonaje;

    //matriz para el movimiento de las naves
    JPanel[][] matriz = new JPanel[40][40];

    public JuegoTron() {
        crearVentana();

        boton();

        panelPersonajes();
        panelJuego();
        fondo();
        accionesBotones();
        fuente();
    }

    public void crearVentana() {

        this.setSize(750, 750);
        //pone titulo a la ventana
        setTitle("TRON");
        //nos hace el cierre de la ventana 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(750, 750));
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        //quita el layout
        this.setLayout(null);

        titulo = new JLabel();
        titulo.setText("TRON");
        titulo.setForeground(Color.red);
        titulo.setBounds(280, 310, 400, 120);
        this.add(titulo);

    }

    public void fuente() {
        File fuente = new File("fuente/Tr2n.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(65f);
            Font sizedFontEstandar = font.deriveFont(15f);
            titulo.setFont(sizedFont);
            BotonEmpezar.setFont(sizedFontEstandar);
            nombrePersonaje.setFont(sizedFontEstandar);
        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }

    }

    public void fondo() {

        fondoMenuPrincipal = new JLabel();
        String nombre = "img/fondo.jpg";
        ImageIcon imageFondo = new ImageIcon(nombre);
        Icon icon = new ImageIcon(imageFondo.getImage().getScaledInstance(750, 750, Image.SCALE_DEFAULT));
        //se añade la imagen a la etiqueta
        fondoMenuPrincipal.setIcon(icon);
        fondoMenuPrincipal.setBounds(0, 0, 750, 750);
        this.add(fondoMenuPrincipal);

    }

    public void boton() {

        BotonEmpezar = new JButton();
        BotonEmpezar.setText("Jugar");
        BotonEmpezar.setForeground(Color.red);
        BotonEmpezar.setBounds(320, 400, 110, 20);
        this.add(BotonEmpezar);

    }

    public void panelPersonajes() {

        PanelPersonajes = new JPanel();
        PanelPersonajes.setBackground(Color.black);
        PanelPersonajes.setOpaque(false);
        PanelPersonajes.setBounds(230, 450, 300, 50);
        this.add(PanelPersonajes);
        PanelPersonajes.setVisible(false);
        //panel acabado

        //personaje 1
        imagen = new JLabel();
        String nombre = "asset/red.png";
        ImageIcon imageicon = new ImageIcon(nombre);
        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(80, 40, Image.SCALE_DEFAULT));
        imagen.setIcon(icon);
        //imagen.setBounds(0, 0, 80, 40);
        PanelPersonajes.add(imagen);

        //personaje 2
        imagen2 = new JLabel();
        String nombre2 = "asset/blue.png";
        ImageIcon imageicon2 = new ImageIcon(nombre2);
        Icon icon2 = new ImageIcon(imageicon2.getImage().getScaledInstance(80, 40, Image.SCALE_DEFAULT));
        imagen2.setIcon(icon2);
        //imagen.setBounds(0, 0, 80, 40);
        PanelPersonajes.add(imagen2);

        //personaje 3
        imagen3 = new JLabel();
        String nombre3 = "asset/gray.png";
        ImageIcon imageicon3 = new ImageIcon(nombre3);
        Icon icon3 = new ImageIcon(imageicon3.getImage().getScaledInstance(80, 40, Image.SCALE_DEFAULT));
        imagen3.setIcon(icon3);
        //imagen.setBounds(0, 0, 80, 40);
        PanelPersonajes.add(imagen3);

        nombrePersonaje = new JLabel();
        //nombrePersonaje.setText("aa");
        nombrePersonaje.setForeground(Color.red);
        nombrePersonaje.setBounds(350, 490, 300, 50);
        this.add(nombrePersonaje);

    }

    public void accionesBotones() {
        BotonEmpezar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelPersonajes.setVisible(true);
            }

        });

        imagen.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PanelPersonajes.setVisible(false);
                BotonEmpezar.setVisible(false);
                PanelPersonajes.setVisible(false);
                titulo.setVisible(false);
                nombrePersonaje.setVisible(false);
                Mipersonaje = "rojo";
                System.out.print(Mipersonaje);

                panelJuego.setVisible(true);

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nombrePersonaje.setForeground(Color.red);
                nombrePersonaje.setText("Rojo");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        imagen2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PanelPersonajes.setVisible(false);
                BotonEmpezar.setVisible(false);
                PanelPersonajes.setVisible(false);
                titulo.setVisible(false);
                nombrePersonaje.setVisible(false);
                Mipersonaje = "verde";
                System.out.print(Mipersonaje);

                panelJuego.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nombrePersonaje.setForeground(Color.GREEN);
                nombrePersonaje.setText("Verde");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        imagen3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PanelPersonajes.setVisible(false);
                BotonEmpezar.setVisible(false);
                PanelPersonajes.setVisible(false);
                titulo.setVisible(false);
                nombrePersonaje.setVisible(false);
                Mipersonaje = "gris";
                System.out.print(Mipersonaje);

                panelJuego.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nombrePersonaje.setForeground(Color.GRAY);
                nombrePersonaje.setText("Gris");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    public void panelJuego() {

        panelJuego = new JPanel();
        panelJuego.setBounds(0, 0, 690, 590);
        panelJuego.setOpaque(false);
        panelJuego.setLayout(new GridLayout(40, 40));

        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 40; y++) {
                matriz[x][y] = new JPanel();
                matriz[x][y].setBackground(Color.black);
                panelJuego.add(matriz[x][y]);

                if (x == 35 && y == 20) {

                    JLabel imagen = new JLabel();
                    String nombre = "asset/red.png";
                    ImageIcon imageicon = new ImageIcon(nombre);
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[x][y].add(imagen);
                    matriz[x][y].setBackground(Color.black);

                    //creamos el objeto Enemigo1
                    //enemigo1 = new Enemigo(x, y);


                }

            }
        }

        this.add(panelJuego);
        panelJuego.setVisible(false);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoTron juego = new JuegoTron();
        juego.setVisible(true);
    }

}
