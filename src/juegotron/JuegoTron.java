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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    Personaje Minave;

    //matriz para el movimiento de las naves
    JPanel[][] matriz = new JPanel[40][40];

    public JuegoTron() {
        crearVentana();
        //boton();
        panelPersonajes();
        panelJuego();
        fondo();
        logica();
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
            //BotonEmpezar.setFont(sizedFontEstandar);
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

        PanelPersonajes.setVisible(true);

    }

    public void accionesBotones() {
        /* BotonEmpezar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelPersonajes.setVisible(true);
            }

        });*/

        imagen.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PanelPersonajes.setVisible(false);
                //BotonEmpezar.setVisible(false);
                PanelPersonajes.setVisible(false);
                titulo.setVisible(false);
                nombrePersonaje.setVisible(false);
                Mipersonaje = "asset/red.png";
                //System.out.println(Mipersonaje);
                panelJuego.setVisible(true);

                Minave = new Personaje(35, 20, Mipersonaje);
                Minave.setNaveImg(Mipersonaje);
                ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                System.out.print("img : " + Minave.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[35][20].add(imagen);

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
                //BotonEmpezar.setVisible(false);
                PanelPersonajes.setVisible(false);
                titulo.setVisible(false);
                nombrePersonaje.setVisible(false);
                Mipersonaje = "asset/blue.png";
                panelJuego.setVisible(true);

                Minave = new Personaje(35, 20, Mipersonaje);
                Minave.setNaveImg(Mipersonaje);
                ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[35][20].add(imagen);

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
                //BotonEmpezar.setVisible(false);
                PanelPersonajes.setVisible(false);
                titulo.setVisible(false);
                nombrePersonaje.setVisible(false);
                Mipersonaje = "asset/gray.png";
                System.out.print(Mipersonaje);

                panelJuego.setVisible(true);

                Minave = new Personaje(35, 20, Mipersonaje);
                Minave.setNaveImg(Mipersonaje);
                ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[35][20].add(imagen);
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
                JLabel imagen = new JLabel();
                matriz[x][y].setBackground(Color.black);
            }
        }

        this.add(panelJuego);
        panelJuego.setVisible(false);

    }

    public void logica() {
        System.out.println("logica");

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == 'd') {
                    //derecha   
                    int ejeX = Minave.getEjeX();
                    int ejeY = Minave.getEjeY();
                    int derecha = Minave.getEjeY() + 1;

                    //reseteamos panel derecho
                    matriz[ejeX][derecha].removeAll();
                    matriz[ejeX][derecha].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][derecha].add(imagen);
                    matriz[ejeX][derecha].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Minave.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                    }

                    Minave.setEjeY(derecha);

                } else if (e.getKeyChar() == 'a'){
                    
                    //izquierda 
                    int ejeX = Minave.getEjeX();
                    int ejeY = Minave.getEjeY();
                    int izquierda = Minave.getEjeY() - 1;

                    //reseteamos panel derecho
                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Minave.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                    }

                    Minave.setEjeY(izquierda);
                    
                    
                
                } else if (e.getKeyChar() == 'w'){
                    
                    //arriba 
                    int ejeX = Minave.getEjeX();
                    int ejeY = Minave.getEjeY();
                    int arriba = Minave.getEjeX() - 1;

                    //reseteamos panel arriba
                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Minave.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                    }

                    Minave.setEjeX(arriba);
                    
                    
                
                } else if (e.getKeyChar() == 's'){
                    
                    //arriba 
                    int ejeX = Minave.getEjeX();
                    int ejeY = Minave.getEjeY();
                    int abajo = Minave.getEjeX() + 1;

                    //reseteamos panel arriba
                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Minave.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                    }

                    Minave.setEjeX(abajo);
                    
                    
                
                }
            }
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoTron juego = new JuegoTron();
        juego.setVisible(true);
    }

}
