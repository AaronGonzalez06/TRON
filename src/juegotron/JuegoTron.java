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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Aaron
 */
public class JuegoTron extends JFrame {

    JLabel fondoMenuPrincipal, titulo, nombrePersonaje, puntuacion, puntuacionEnemiga, unJugador, dosJugadores, VS;
    JLabel imagen, imagen2, imagen3, imagen4;
    JButton BotonEmpezar;
    JPanel PanelPersonajes, panelJuego;

    //personaje con el que voy a jugar
    String Mipersonaje;
    Personaje Minave;

    int sumador = 0;
    int sumadorEnemigo = 0;
    int sumadorSegundoJugador = 0;
    int comenzar;

    //enemigo
    Enemigo Mienemigo;
    String naveEnemiga;
    int movimientoNaveEnemigo;
    int colorMovimiento;
    int cambioDireccion = 0;
    /*
    1 - rojo
    2 - verde
    3 - gris
     */
    boolean colision;

    //fin enemigo
    //matriz para el movimiento de las naves
    JPanel[][] matriz = new JPanel[40][40];

    //estados de las casillas
    Estado[][] estados = new Estado[40][40];

    //movimientos
    boolean derechaM = false;
    boolean izquierdaM = false;
    boolean arribaM = false;
    boolean abajoM = false;

    //movimientos personaje 2
    boolean derechaM2 = false;
    boolean izquierdaM2 = false;
    boolean arribaM2 = false;
    boolean abajoM2 = false;

    //modo de juego
    int modoJuego;

    //Jugadores
    boolean primerJugador = false;
    boolean segundoJugador = false;

    Timer timer = new Timer(150, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ganador();
            naveEnemiga();
            speed();

        }
    });

    Timer jugadaDos = new Timer(150, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ganador();
            speed();
            speedSegundoPersonaje();

        }
    });

    public JuegoTron() {
        crearVentana();
        //boton();
        modoJuego();
        panelPersonajes();
        panelJuego();
        fondo();
        logica();
        logicaPersonaje2();
        accionesBotones();
        fuente();
        //timer.start();
    }

    public void crearVentana() {

        this.setSize(750, 750);
        this.setLocation(400, 100);
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

        puntuacion = new JLabel();
        puntuacion.setText("Puntuacion: " + sumador);
        puntuacion.setBounds(50, 65, 300, 50);
        this.add(puntuacion);
        puntuacion.setVisible(false);

        puntuacionEnemiga = new JLabel();
        puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
        puntuacionEnemiga.setBounds(510, 65, 300, 50);
        this.add(puntuacionEnemiga);
        puntuacionEnemiga.setVisible(false);

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
            puntuacion.setFont(sizedFontEstandar);
            puntuacionEnemiga.setFont(sizedFontEstandar);
            unJugador.setFont(sizedFontEstandar);
            dosJugadores.setFont(sizedFontEstandar);
            // VS.setFont(sizedFontEstandar);
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

    public void modoJuego() {

        unJugador = new JLabel();
        dosJugadores = new JLabel();

        unJugador.setText("Un jugador");
        dosJugadores.setText("Dos jugadores");

        unJugador.setForeground(Color.red);
        dosJugadores.setForeground(Color.red);

        unJugador.setBounds(230, 395, 200, 50);
        dosJugadores.setBounds(420, 395, 200, 50);

        this.add(unJugador);
        this.add(dosJugadores);

        /*VS = new JLabel();
        VS.setText("VS");
        VS.setForeground(Color.red);
        VS.setBounds(340, 525, 250, 50);
        this.add(VS);
        VS.setVisible(false);*/
        unJugador.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                unJugador.setVisible(false);
                dosJugadores.setVisible(false);
                PanelPersonajes.setVisible(true);
                modoJuego = 0;

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        dosJugadores.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                unJugador.setVisible(false);
                dosJugadores.setVisible(false);
                PanelPersonajes.setVisible(true);
                modoJuego = 1;
                //VS.setVisible(true);

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

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

        //PanelPersonajes.setVisible(true);
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

                if (modoJuego == 0) {

                    PanelPersonajes.setVisible(false);
                    //BotonEmpezar.setVisible(false);
                    PanelPersonajes.setVisible(false);
                    titulo.setVisible(false);
                    nombrePersonaje.setVisible(false);
                    Mipersonaje = "asset/red.png";
                    //System.out.println(Mipersonaje);
                    panelJuego.setVisible(true);

                    //nave
                    Minave = new Personaje(35, 20, Mipersonaje, true);
                    Minave.setNaveImg(Mipersonaje);
                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    //imagen.setIcon(icon);
                    //imagen.setBounds(0, 0, 22, 22);
                    matriz[35][20].add(imagen);

                    //enemigo
                    imagen4 = new JLabel();
                    int numero = (int) (Math.random() * 2 + 1);
                    if (numero == 1) {
                        naveEnemiga = "asset/gray.png";
                        puntuacionEnemiga.setForeground(Color.GRAY);
                        colorMovimiento = 3;
                    } else if (numero == 2) {
                        naveEnemiga = "asset/blue.png";
                        puntuacionEnemiga.setForeground(Color.green);
                        colorMovimiento = 2;
                    }
                    System.out.println(numero);
                    Mienemigo = new Enemigo(5, 20, naveEnemiga, true);
                    Mienemigo.setNaveImg(naveEnemiga);
                    ImageIcon imageiconEnemigo = new ImageIcon(Mienemigo.getNaveImg());
                    Icon iconEnemigo = new ImageIcon(imageiconEnemigo.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen4.setIcon(iconEnemigo);
                    imagen4.setBounds(0, 0, 22, 22);
                    matriz[5][20].add(imagen4);
                    //fin enemigo

                    puntuacion.setForeground(Color.red);
                    puntuacion.setVisible(true);
                    puntuacionEnemiga.setVisible(true);

                    timer.start();

                } else if (modoJuego == 1) {

                    if (primerJugador == false) {

                        Mipersonaje = "asset/red.png";
                        Minave = new Personaje(35, 20, Mipersonaje, true);
                        Minave.setNaveImg(Mipersonaje);
                        //ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        //Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));                        
                        //matriz[35][20].add(imagen);
                        puntuacion.setForeground(Color.red);
                        primerJugador = true;

                    } else if (segundoJugador == false) {

                        imagen4 = new JLabel();
                        naveEnemiga = "asset/red.png";
                        puntuacionEnemiga.setForeground(Color.RED);
                        colorMovimiento = 1;
                        Mienemigo = new Enemigo(5, 20, naveEnemiga, true);
                        Mienemigo.setNaveImg(naveEnemiga);
                        ImageIcon imageiconEnemigo = new ImageIcon(Mienemigo.getNaveImg());
                        Icon iconEnemigo = new ImageIcon(imageiconEnemigo.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen4.setIcon(iconEnemigo);
                        imagen4.setBounds(0, 0, 22, 22);
                        matriz[5][20].add(imagen4);

                        PanelPersonajes.setVisible(false);
                        PanelPersonajes.setVisible(false);
                        titulo.setVisible(false);
                        nombrePersonaje.setVisible(false);
                        panelJuego.setVisible(true);

                        puntuacion.setVisible(true);
                        puntuacionEnemiga.setVisible(true);

                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[35][20].add(imagen);
                        jugadaDos.start();

                    }
                }

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

                if (modoJuego == 0) {

                    PanelPersonajes.setVisible(false);
                    //BotonEmpezar.setVisible(false);
                    PanelPersonajes.setVisible(false);
                    titulo.setVisible(false);
                    nombrePersonaje.setVisible(false);
                    Mipersonaje = "asset/blue.png";
                    panelJuego.setVisible(true);

                    Minave = new Personaje(35, 20, Mipersonaje, true);
                    Minave.setNaveImg(Mipersonaje);
                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    //imagen.setIcon(icon);
                    //imagen.setBounds(0, 0, 22, 22);
                    matriz[35][20].add(imagen2);

                    //enemigo
                    imagen4 = new JLabel();
                    int numero = (int) (Math.random() * 2 + 1);
                    if (numero == 1) {
                        naveEnemiga = "asset/gray.png";
                        puntuacionEnemiga.setForeground(Color.GRAY);
                        colorMovimiento = 3;
                    } else if (numero == 2) {
                        naveEnemiga = "asset/red.png";
                        puntuacionEnemiga.setForeground(Color.RED);
                        colorMovimiento = 1;
                    }
                    System.out.println(numero);
                    Mienemigo = new Enemigo(5, 20, naveEnemiga, true);
                    Mienemigo.setNaveImg(naveEnemiga);
                    ImageIcon imageiconEnemigo = new ImageIcon(Mienemigo.getNaveImg());
                    Icon iconEnemigo = new ImageIcon(imageiconEnemigo.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen4.setIcon(iconEnemigo);
                    imagen4.setBounds(0, 0, 22, 22);
                    matriz[5][20].add(imagen4);
                    //fin enemigo

                    puntuacion.setForeground(Color.GREEN);
                    puntuacion.setVisible(true);
                    puntuacionEnemiga.setVisible(true);

                    timer.start();

                } else if (modoJuego == 1) {

                    if (primerJugador == false) {

                        Mipersonaje = "asset/blue.png";
                        Minave = new Personaje(35, 20, Mipersonaje, true);
                        Minave.setNaveImg(Mipersonaje);
                        //ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        //Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        //matriz[35][20].add(imagen);
                        puntuacion.setForeground(Color.GREEN);

                        primerJugador = true;

                    } else if (segundoJugador == false) {

                        imagen4 = new JLabel();
                        naveEnemiga = "asset/blue.png";
                        puntuacionEnemiga.setForeground(Color.GREEN);
                        colorMovimiento = 2;
                        Mienemigo = new Enemigo(5, 20, naveEnemiga, true);
                        Mienemigo.setNaveImg(naveEnemiga);
                        ImageIcon imageiconEnemigo = new ImageIcon(Mienemigo.getNaveImg());
                        Icon iconEnemigo = new ImageIcon(imageiconEnemigo.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen4.setIcon(iconEnemigo);
                        imagen4.setBounds(0, 0, 22, 22);
                        matriz[5][20].add(imagen4);

                        PanelPersonajes.setVisible(false);
                        PanelPersonajes.setVisible(false);
                        titulo.setVisible(false);
                        nombrePersonaje.setVisible(false);
                        panelJuego.setVisible(true);
                        puntuacion.setVisible(true);
                        puntuacionEnemiga.setVisible(true);

                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen2.setIcon(icon);
                        imagen2.setBounds(0, 0, 22, 22);
                        matriz[35][20].add(imagen2);
                        matriz[35][20].add(imagen2);
                        jugadaDos.start();

                    }

                }

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

                if (modoJuego == 0) {

                    PanelPersonajes.setVisible(false);
                    //BotonEmpezar.setVisible(false);
                    PanelPersonajes.setVisible(false);
                    titulo.setVisible(false);
                    nombrePersonaje.setVisible(false);
                    Mipersonaje = "asset/gray.png";
                    System.out.print(Mipersonaje);

                    panelJuego.setVisible(true);

                    Minave = new Personaje(35, 20, Mipersonaje, true);
                    Minave.setNaveImg(Mipersonaje);
                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    //imagen.setIcon(icon);
                    //imagen.setBounds(0, 0, 22, 22);
                    matriz[35][20].add(imagen3);

                    //enemigo
                    imagen4 = new JLabel();
                    int numero = (int) (Math.random() * 2 + 1);
                    if (numero == 1) {
                        naveEnemiga = "asset/red.png";
                        puntuacionEnemiga.setForeground(Color.RED);
                        colorMovimiento = 1;
                    } else if (numero == 2) {
                        naveEnemiga = "asset/blue.png";
                        puntuacionEnemiga.setForeground(Color.GREEN);
                        colorMovimiento = 2;
                    }
                    System.out.println(numero);
                    Mienemigo = new Enemigo(5, 20, naveEnemiga, true);
                    Mienemigo.setNaveImg(naveEnemiga);
                    ImageIcon imageiconEnemigo = new ImageIcon(Mienemigo.getNaveImg());
                    Icon iconEnemigo = new ImageIcon(imageiconEnemigo.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen4.setIcon(iconEnemigo);
                    imagen4.setBounds(0, 0, 22, 22);
                    matriz[5][20].add(imagen4);
                    //fin enemigo

                    puntuacion.setForeground(Color.GRAY);
                    puntuacion.setVisible(true);
                    puntuacionEnemiga.setVisible(true);

                    timer.start();

                } else if (modoJuego == 1) {

                    if (primerJugador == false) {

                        Mipersonaje = "asset/gray.png";
                        Minave = new Personaje(35, 20, Mipersonaje, true);
                        Minave.setNaveImg(Mipersonaje);
                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        //matriz[35][20].add(imagen);
                        puntuacion.setForeground(Color.GRAY);

                        primerJugador = true;

                    } else if (segundoJugador == false) {

                        imagen4 = new JLabel();
                        naveEnemiga = "asset/gray.png";
                        puntuacionEnemiga.setForeground(Color.GRAY);
                        colorMovimiento = 3;
                        Mienemigo = new Enemigo(5, 20, naveEnemiga, true);
                        Mienemigo.setNaveImg(naveEnemiga);
                        ImageIcon imageiconEnemigo = new ImageIcon(Mienemigo.getNaveImg());
                        Icon iconEnemigo = new ImageIcon(imageiconEnemigo.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen4.setIcon(iconEnemigo);
                        imagen4.setBounds(0, 0, 22, 22);
                        matriz[5][20].add(imagen4);

                        PanelPersonajes.setVisible(false);
                        PanelPersonajes.setVisible(false);
                        titulo.setVisible(false);
                        nombrePersonaje.setVisible(false);
                        panelJuego.setVisible(true);
                        puntuacion.setVisible(true);
                        puntuacionEnemiga.setVisible(true);

                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen3.setIcon(icon);
                        imagen3.setBounds(0, 0, 22, 22);
                        matriz[35][20].add(imagen3);
                        matriz[35][20].add(imagen3);

                        jugadaDos.start();

                    }
                }

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
        panelJuego.setBounds(20, 85, 690, 590);
        panelJuego.setOpaque(false);
        panelJuego.setLayout(new GridLayout(40, 40));

        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 40; y++) {
                matriz[x][y] = new JPanel();
                matriz[x][y].setBackground(Color.black);
                panelJuego.add(matriz[x][y]);
                //estado de la casilla
                estados[x][y] = new Estado(false);
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

                    try {
                        Estado proximoMovimiento = estados[ejeX][derecha];

                        if (proximoMovimiento.getEstado()) {

                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Minave.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion

                            Minave.setVivo(false);

                        } else {

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
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumador++;
                            puntuacion.setText("Puntuacion: " + sumador);
                            Minave.setEjeY(derecha);
                            derechaM = true;
                            izquierdaM = false;
                            arribaM = false;
                            abajoM = false;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Minave.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Minave.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }

                } else if (e.getKeyChar() == 'a') {

                    //izquierda 
                    int ejeX = Minave.getEjeX();
                    int ejeY = Minave.getEjeY();
                    int izquierda = Minave.getEjeY() - 1;

                    try {
                        Estado proximoMovimiento = estados[ejeX][izquierda];
                        if (proximoMovimiento.getEstado()) {
                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Minave.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion
                            Minave.setVivo(false);

                        } else {

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
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumador++;
                            puntuacion.setText("Puntuacion: " + sumador);
                            Minave.setEjeY(izquierda);
                            derechaM = false;
                            izquierdaM = true;
                            arribaM = false;
                            abajoM = false;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Minave.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Minave.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }

                } else if (e.getKeyChar() == 'w') {

                    //arriba 
                    int ejeX = Minave.getEjeX();
                    int ejeY = Minave.getEjeY();
                    int arriba = Minave.getEjeX() - 1;

                    try {
                        Estado proximoMovimiento = estados[arriba][ejeY];
                        if (proximoMovimiento.getEstado()) {
                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Minave.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion
                            Minave.setVivo(false);

                        } else {

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
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumador++;
                            puntuacion.setText("Puntuacion: " + sumador);
                            Minave.setEjeX(arriba);
                            derechaM = false;
                            izquierdaM = false;
                            arribaM = true;
                            abajoM = false;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Minave.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Minave.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }

                } else if (e.getKeyChar() == 's') {

                    //abajo 
                    int ejeX = Minave.getEjeX();
                    int ejeY = Minave.getEjeY();
                    int abajo = Minave.getEjeX() + 1;

                    try {
                        Estado proximoMovimiento = estados[abajo][ejeY];
                        if (proximoMovimiento.getEstado()) {
                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Minave.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion
                            Minave.setVivo(false);

                        } else {

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
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Minave.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumador++;
                            puntuacion.setText("Puntuacion: " + sumador);
                            Minave.setEjeX(abajo);
                            derechaM = false;
                            izquierdaM = false;
                            arribaM = false;
                            abajoM = true;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Minave.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Minave.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }
                }
            }
        });

    }

    //nave manejada por otra persona 
    public void logicaPersonaje2() {
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
                if (e.getKeyChar() == '6') {
                    //derecha   
                    int ejeX = Mienemigo.getEjeX();
                    int ejeY = Mienemigo.getEjeY();
                    int derecha = Mienemigo.getEjeY() + 1;

                    try {
                        Estado proximoMovimiento = estados[ejeX][derecha];

                        if (proximoMovimiento.getEstado()) {

                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Mienemigo.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion

                            Mienemigo.setVivo(false);

                        } else {

                            //reseteamos panel derecho
                            matriz[ejeX][derecha].removeAll();
                            matriz[ejeX][derecha].repaint();
                            JLabel imagen = new JLabel();

                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][derecha].add(imagen);
                            matriz[ejeX][derecha].setBackground(Color.black);
                            //elimina todo la posicion actual donde esta el pacman
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();

                            //pintamos de color por donde pasa
                            if (Mienemigo.getNaveImg() == "asset/red.png") {
                                matriz[ejeX][ejeY].setBackground(Color.red);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumadorSegundoJugador++;
                            puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                            Mienemigo.setEjeY(derecha);
                            derechaM2 = true;
                            izquierdaM2 = false;
                            arribaM2 = false;
                            abajoM2 = false;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Mienemigo.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Mienemigo.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }

                } else if (e.getKeyChar() == '4') {

                    //izquierda 
                    int ejeX = Mienemigo.getEjeX();
                    int ejeY = Mienemigo.getEjeY();
                    int izquierda = Mienemigo.getEjeY() - 1;

                    try {
                        Estado proximoMovimiento = estados[ejeX][izquierda];
                        if (proximoMovimiento.getEstado()) {
                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Mienemigo.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion
                            Mienemigo.setVivo(false);

                        } else {

                            //reseteamos panel derecho
                            matriz[ejeX][izquierda].removeAll();
                            matriz[ejeX][izquierda].repaint();
                            JLabel imagen = new JLabel();

                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][izquierda].add(imagen);
                            matriz[ejeX][izquierda].setBackground(Color.black);
                            //elimina todo la posicion actual donde esta el pacman
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();

                            //pintamos de color por donde pasa
                            if (Mienemigo.getNaveImg() == "asset/red.png") {
                                matriz[ejeX][ejeY].setBackground(Color.red);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumadorSegundoJugador++;
                            puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                            Mienemigo.setEjeY(izquierda);
                            derechaM2 = false;
                            izquierdaM2 = true;
                            arribaM2 = false;
                            abajoM2 = false;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Mienemigo.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Mienemigo.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }

                } else if (e.getKeyChar() == '8') {

                    //arriba 
                    int ejeX = Mienemigo.getEjeX();
                    int ejeY = Mienemigo.getEjeY();
                    int arriba = Mienemigo.getEjeX() - 1;

                    try {
                        Estado proximoMovimiento = estados[arriba][ejeY];
                        if (proximoMovimiento.getEstado()) {
                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Mienemigo.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion
                            Mienemigo.setVivo(false);

                        } else {

                            //reseteamos panel arriba
                            matriz[arriba][ejeY].removeAll();
                            matriz[arriba][ejeY].repaint();
                            JLabel imagen = new JLabel();

                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[arriba][ejeY].add(imagen);
                            matriz[arriba][ejeY].setBackground(Color.black);
                            //elimina todo la posicion actual donde esta el pacman
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();

                            //pintamos de color por donde pasa
                            if (Mienemigo.getNaveImg() == "asset/red.png") {
                                matriz[ejeX][ejeY].setBackground(Color.red);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumadorSegundoJugador++;
                            puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                            Mienemigo.setEjeX(arriba);
                            derechaM2 = false;
                            izquierdaM2 = false;
                            arribaM2 = true;
                            abajoM2 = false;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Mienemigo.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Mienemigo.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }

                } else if (e.getKeyChar() == '5') {

                    //abajo 
                    int ejeX = Mienemigo.getEjeX();
                    int ejeY = Mienemigo.getEjeY();
                    int abajo = Mienemigo.getEjeX() + 1;

                    try {
                        Estado proximoMovimiento = estados[abajo][ejeY];
                        if (proximoMovimiento.getEstado()) {
                            System.out.println("choque");

                            // explosion
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();
                            Mienemigo.setNaveImg("asset/explosion.png");
                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[ejeX][ejeY].add(imagen);
                            //fin explosion
                            Mienemigo.setVivo(false);

                        } else {

                            //reseteamos panel arriba
                            matriz[abajo][ejeY].removeAll();
                            matriz[abajo][ejeY].repaint();
                            JLabel imagen = new JLabel();

                            ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                            Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                            imagen.setIcon(icon);
                            imagen.setBounds(0, 0, 22, 22);
                            matriz[abajo][ejeY].add(imagen);
                            matriz[abajo][ejeY].setBackground(Color.black);
                            //elimina todo la posicion actual donde esta el pacman
                            matriz[ejeX][ejeY].removeAll();
                            matriz[ejeX][ejeY].repaint();

                            //pintamos de color por donde pasa
                            if (Mienemigo.getNaveImg() == "asset/red.png") {
                                matriz[ejeX][ejeY].setBackground(Color.red);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                                matriz[ejeX][ejeY].setBackground(Color.GREEN);
                                estados[ejeX][ejeY].setEstado(true);
                            } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                                matriz[ejeX][ejeY].setBackground(Color.gray);
                                estados[ejeX][ejeY].setEstado(true);
                            }

                            sumadorSegundoJugador++;
                            puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                            Mienemigo.setEjeX(abajo);
                            derechaM2 = false;
                            izquierdaM2 = false;
                            arribaM2 = false;
                            abajoM2 = true;

                        }

                    } catch (ArrayIndexOutOfBoundsException excepcio) {
                        matriz[ejeX][ejeY].removeAll();
                        matriz[ejeX][ejeY].repaint();
                        Mienemigo.setNaveImg("asset/explosion.png");
                        ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                        imagen.setIcon(icon);
                        imagen.setBounds(0, 0, 22, 22);
                        matriz[ejeX][ejeY].add(imagen);
                        Mienemigo.setVivo(false);

                    } catch (ArithmeticException excepcion) {
                        System.out.println(" Error de índice en un array");
                    } finally {
                        System.out.println("Se ejecuta finally");
                    }
                }
            }
        });

    }

    public void speed() {

        if (derechaM) {

            //derecha   
            int ejeX = Minave.getEjeX();
            int ejeY = Minave.getEjeY();
            int derecha = Minave.getEjeY() + 1;

            try {
                Estado proximoMovimiento = estados[ejeX][derecha];

                if (proximoMovimiento.getEstado()) {

                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Minave.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    Minave.setVivo(false);
                    //fin explosion

                } else {

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
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumador++;
                    puntuacion.setText("Puntuacion: " + sumador);
                    Minave.setEjeY(derecha);
                    derechaM = true;
                    izquierdaM = false;
                    arribaM = false;
                    abajoM = false;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Minave.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Minave.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        } else if (izquierdaM) {

            //izquierda 
            int ejeX = Minave.getEjeX();
            int ejeY = Minave.getEjeY();
            int izquierda = Minave.getEjeY() - 1;

            try {
                Estado proximoMovimiento = estados[ejeX][izquierda];
                if (proximoMovimiento.getEstado()) {
                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Minave.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    Minave.setVivo(false);
                    //fin explosion

                } else {

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
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumador++;
                    puntuacion.setText("Puntuacion: " + sumador);
                    Minave.setEjeY(izquierda);
                    derechaM = false;
                    izquierdaM = true;
                    arribaM = false;
                    abajoM = false;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Minave.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Minave.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        } else if (arribaM) {

            //arriba 
            int ejeX = Minave.getEjeX();
            int ejeY = Minave.getEjeY();
            int arriba = Minave.getEjeX() - 1;

            try {
                Estado proximoMovimiento = estados[arriba][ejeY];
                if (proximoMovimiento.getEstado()) {
                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Minave.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    Minave.setVivo(false);
                    //fin explosion

                } else {

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
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumador++;
                    puntuacion.setText("Puntuacion: " + sumador);
                    Minave.setEjeX(arriba);
                    derechaM = false;
                    izquierdaM = false;
                    arribaM = true;
                    abajoM = false;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Minave.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Minave.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        } else if (abajoM) {

            //abajo 
            int ejeX = Minave.getEjeX();
            int ejeY = Minave.getEjeY();
            int abajo = Minave.getEjeX() + 1;

            try {
                Estado proximoMovimiento = estados[abajo][ejeY];
                if (proximoMovimiento.getEstado()) {
                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Minave.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    //fin explosion
                    Minave.setVivo(false);

                } else {

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
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Minave.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumador++;
                    puntuacion.setText("Puntuacion: " + sumador);
                    Minave.setEjeX(abajo);
                    derechaM = false;
                    izquierdaM = false;
                    arribaM = false;
                    abajoM = true;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Minave.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Minave.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Minave.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        }

    }

    public void speedSegundoPersonaje() {

        if (derechaM2) {

            //derecha   
            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int derecha = Mienemigo.getEjeY() + 1;

            try {
                Estado proximoMovimiento = estados[ejeX][derecha];

                if (proximoMovimiento.getEstado()) {

                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Mienemigo.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    Mienemigo.setVivo(false);
                    //fin explosion

                } else {

                    //reseteamos panel derecho
                    matriz[ejeX][derecha].removeAll();
                    matriz[ejeX][derecha].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][derecha].add(imagen);
                    matriz[ejeX][derecha].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Mienemigo.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumadorSegundoJugador++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                    Mienemigo.setEjeY(derecha);
                    derechaM2 = true;
                    izquierdaM2 = false;
                    arribaM2 = false;
                    abajoM2 = false;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Mienemigo.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Mienemigo.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        } else if (izquierdaM2) {

            //izquierda 
            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int izquierda = Mienemigo.getEjeY() - 1;

            try {
                Estado proximoMovimiento = estados[ejeX][izquierda];
                if (proximoMovimiento.getEstado()) {
                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Mienemigo.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    Mienemigo.setVivo(false);
                    //fin explosion

                } else {

                    //reseteamos panel derecho
                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Mienemigo.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumadorSegundoJugador++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                    Mienemigo.setEjeY(izquierda);
                    derechaM2 = false;
                    izquierdaM2 = true;
                    arribaM2 = false;
                    abajoM2 = false;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Mienemigo.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Mienemigo.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        } else if (arribaM2) {

            //arriba 
            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int arriba = Mienemigo.getEjeX() - 1;

            try {
                Estado proximoMovimiento = estados[arriba][ejeY];
                if (proximoMovimiento.getEstado()) {
                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Mienemigo.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    Mienemigo.setVivo(false);
                    //fin explosion

                } else {

                    //reseteamos panel arriba
                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Mienemigo.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumadorSegundoJugador++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                    Mienemigo.setEjeX(arriba);
                    derechaM2 = false;
                    izquierdaM2 = false;
                    arribaM2 = true;
                    abajoM2 = false;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Mienemigo.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Mienemigo.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        } else if (abajoM2) {

            //abajo 
            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int abajo = Mienemigo.getEjeX() + 1;

            try {
                Estado proximoMovimiento = estados[abajo][ejeY];
                if (proximoMovimiento.getEstado()) {
                    System.out.println("choque");

                    // explosion
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();
                    Mienemigo.setNaveImg("asset/explosion.png");
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][ejeY].add(imagen);
                    //fin explosion
                    Mienemigo.setVivo(false);

                } else {

                    //reseteamos panel arriba
                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();
                    JLabel imagen = new JLabel();

                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    //pintamos de color por donde pasa
                    if (Mienemigo.getNaveImg() == "asset/red.png") {
                        matriz[ejeX][ejeY].setBackground(Color.red);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/blue.png") {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                        estados[ejeX][ejeY].setEstado(true);
                    } else if (Mienemigo.getNaveImg() == "asset/gray.png") {
                        matriz[ejeX][ejeY].setBackground(Color.gray);
                        estados[ejeX][ejeY].setEstado(true);
                    }

                    sumadorSegundoJugador++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorSegundoJugador);
                    Mienemigo.setEjeX(abajo);
                    derechaM2 = false;
                    izquierdaM2 = false;
                    arribaM2 = false;
                    abajoM2 = true;

                }

            } catch (ArrayIndexOutOfBoundsException excepcio) {
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();
                Mienemigo.setNaveImg("asset/explosion.png");
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][ejeY].add(imagen);
                Mienemigo.setVivo(false);

            } catch (ArithmeticException excepcion) {
                System.out.println(" Error de índice en un array");
            } finally {
                System.out.println("Se ejecuta finally");
            }

        }

    }

    public void naveEnemiga() {
        /*        
        1 --> derecha
        2 --> izquierda
        3 --> arriba
        4 --> abajo
         */
        System.out.println("X: " + Mienemigo.getEjeX() + " Y: " + Mienemigo.getEjeY());

        //if(Mienemigo.getEjeX() == 0 || Mienemigo.getEjeY() == 39 || Mienemigo.getEjeY() == 0 || Mienemigo.getEjeX() == 39  ){} 
        if (cambioDireccion == 0) {
            movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
        }

        //System.out.println(movimientoEnemigo1);
        //movimientoNaveEnemigo = 4;
        if (movimientoNaveEnemigo == 1) {
            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int derecha = Mienemigo.getEjeY() + 1;
            int izquierda = Mienemigo.getEjeY() - 1;

            try {
                Estado EstadoCritico = estados[ejeX][ejeY + 2];
                Estado EstadoColision = estados[ejeX][ejeY + 1];

                Estado EstadoCriticoIzquierda = estados[ejeX][ejeY - 2];
                Estado EstadoCriticoArriba = estados[ejeX - 2][ejeY];
                Estado EstadoCriticoAbajo = estados[ejeX + 2][ejeY];

                if (EstadoCriticoIzquierda.getEstado() && EstadoCriticoArriba.getEstado() && EstadoCriticoAbajo.getEstado()) {

                    System.out.println("muerto");

                } else if (EstadoCritico.getEstado() || EstadoColision.getEstado()) {

                    do {
                        movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);

                    } while (movimientoNaveEnemigo == 1);
                    cambioDireccion = 0;

                } else {

                    //reseteamos panel derecho
                    matriz[ejeX][derecha].removeAll();
                    matriz[ejeX][derecha].repaint();
                    JLabel imagen = new JLabel();
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][derecha].add(imagen);
                    matriz[ejeX][derecha].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    if (colorMovimiento == 1) {
                        matriz[ejeX][ejeY].setBackground(Color.RED);
                    } else if (colorMovimiento == 2) {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (colorMovimiento == 3) {
                        matriz[ejeX][ejeY].setBackground(Color.GRAY);
                    }

                    sumadorEnemigo++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                    estados[ejeX][ejeY].setEstado(true);
                    Mienemigo.setEjeY(derecha);
                    //cambiarMovimiento1++;
                    cambioDireccion++;
                    if (cambioDireccion == 6) {
                        cambioDireccion = 0;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException excepcion) {
                System.out.println("fuera de la matriz derecha");
                /*do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                } while (movimientoNaveEnemigo == 1);*/
                //cambioDireccion = 0;

                //reseteamos panel derecho
                matriz[ejeX][derecha].removeAll();
                matriz[ejeX][derecha].repaint();
                JLabel imagen = new JLabel();
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][derecha].add(imagen);
                matriz[ejeX][derecha].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();

                if (colorMovimiento == 1) {
                    matriz[ejeX][ejeY].setBackground(Color.RED);
                } else if (colorMovimiento == 2) {
                    matriz[ejeX][ejeY].setBackground(Color.GREEN);
                } else if (colorMovimiento == 3) {
                    matriz[ejeX][ejeY].setBackground(Color.GRAY);
                }

                sumadorEnemigo++;
                puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                estados[ejeX][ejeY].setEstado(true);
                Mienemigo.setEjeY(derecha);
                //cambiarMovimiento1++;
                cambioDireccion++;
                //if (cambioDireccion == 6) {
                //cambioDireccion = 0;
                //}

                do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                } while (movimientoNaveEnemigo == 2 || movimientoNaveEnemigo == 1);

            } finally {
                System.out.println("fuera del catch");
            }

        } else if (movimientoNaveEnemigo == 2) {

            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int derecha = Mienemigo.getEjeY() + 1;
            int izquierda = Mienemigo.getEjeY() - 1;

            try {
                Estado EstadoCritico = estados[ejeX][ejeY - 2];
                Estado EstadoColision = estados[ejeX][ejeY - 1];

                Estado EstadoCriticoDerecha = estados[ejeX][ejeY + 2];
                Estado EstadoCriticoArriba = estados[ejeX - 2][ejeY];
                Estado EstadoCriticoAbajo = estados[ejeX + 2][ejeY];

                if (EstadoCriticoDerecha.getEstado() && EstadoCriticoArriba.getEstado() && EstadoCriticoAbajo.getEstado()) {

                    System.out.println("muerto");

                } else if (EstadoCritico.getEstado() || EstadoColision.getEstado()) {

                    do {
                        movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);

                    } while (movimientoNaveEnemigo == 2);
                    cambioDireccion = 0;

                } else {

                    //reseteamos panel derecho
                    matriz[ejeX][izquierda].removeAll();
                    matriz[ejeX][izquierda].repaint();
                    JLabel imagen = new JLabel();
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[ejeX][izquierda].add(imagen);
                    matriz[ejeX][izquierda].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    if (colorMovimiento == 1) {
                        matriz[ejeX][ejeY].setBackground(Color.RED);
                    } else if (colorMovimiento == 2) {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (colorMovimiento == 3) {
                        matriz[ejeX][ejeY].setBackground(Color.GRAY);
                    }

                    sumadorEnemigo++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                    estados[ejeX][ejeY].setEstado(true);
                    Mienemigo.setEjeY(izquierda);
                    //cambiarMovimiento1++;

                    cambioDireccion++;
                    if (cambioDireccion == 6) {
                        cambioDireccion = 0;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException excepcion) {
                System.out.println("fuera de la matriz izquierda");
                /*do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    
                } while (movimientoNaveEnemigo == 2);*/
                //cambioDireccion = 0;

                matriz[ejeX][izquierda].removeAll();
                matriz[ejeX][izquierda].repaint();
                JLabel imagen = new JLabel();
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[ejeX][izquierda].add(imagen);
                matriz[ejeX][izquierda].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();

                if (colorMovimiento == 1) {
                    matriz[ejeX][ejeY].setBackground(Color.RED);
                } else if (colorMovimiento == 2) {
                    matriz[ejeX][ejeY].setBackground(Color.GREEN);
                } else if (colorMovimiento == 3) {
                    matriz[ejeX][ejeY].setBackground(Color.GRAY);
                }

                sumadorEnemigo++;
                puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                estados[ejeX][ejeY].setEstado(true);
                Mienemigo.setEjeY(izquierda);
                //cambiarMovimiento1++;

                cambioDireccion++;
                //if (cambioDireccion == 6) {
                //cambioDireccion = 0;
                //}
                do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                } while (movimientoNaveEnemigo == 2 || movimientoNaveEnemigo == 1);

            } finally {
                System.out.println("fuera del catch");
            }

        } else if (movimientoNaveEnemigo == 3) {

            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int derecha = Mienemigo.getEjeY() + 1;
            int arriba = Mienemigo.getEjeX() - 1;

            try {
                Estado EstadoCritico = estados[ejeX - 2][ejeY];
                Estado EstadoColision = estados[ejeX - 1][ejeY];

                Estado EstadoCriticoIzquierda = estados[ejeX][ejeY - 2];
                Estado EstadoCriticoDerecha = estados[ejeX][ejeY + 2];
                Estado EstadoCriticoAbajo = estados[ejeX + 2][ejeY];

                if (EstadoCriticoIzquierda.getEstado() && EstadoCriticoDerecha.getEstado() && EstadoCriticoAbajo.getEstado()) {

                    System.out.println("muerto");

                } else if (EstadoCritico.getEstado() || EstadoColision.getEstado()) {

                    do {
                        movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);

                    } while (movimientoNaveEnemigo == 3);
                    cambioDireccion = 0;

                } else {

                    //reseteamos panel derecho
                    matriz[arriba][ejeY].removeAll();
                    matriz[arriba][ejeY].repaint();
                    JLabel imagen = new JLabel();
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[arriba][ejeY].add(imagen);
                    matriz[arriba][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    if (colorMovimiento == 1) {
                        matriz[ejeX][ejeY].setBackground(Color.RED);
                    } else if (colorMovimiento == 2) {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (colorMovimiento == 3) {
                        matriz[ejeX][ejeY].setBackground(Color.GRAY);
                    }

                    sumadorEnemigo++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                    estados[ejeX][ejeY].setEstado(true);
                    Mienemigo.setEjeX(arriba);
                    //cambiarMovimiento1++;
                    cambioDireccion++;
                    if (cambioDireccion == 6) {
                        cambioDireccion = 0;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException excepcion) {
                System.out.println("fuera de la matriz arriba");

                /*do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                } while (movimientoNaveEnemigo == 3 || movimientoNaveEnemigo == 4 );*/
                //cambioDireccion = 0;
                //reseteamos panel derecho
                matriz[arriba][ejeY].removeAll();
                matriz[arriba][ejeY].repaint();
                JLabel imagen = new JLabel();
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[arriba][ejeY].add(imagen);
                matriz[arriba][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();

                if (colorMovimiento == 1) {
                    matriz[ejeX][ejeY].setBackground(Color.RED);
                } else if (colorMovimiento == 2) {
                    matriz[ejeX][ejeY].setBackground(Color.GREEN);
                } else if (colorMovimiento == 3) {
                    matriz[ejeX][ejeY].setBackground(Color.GRAY);
                }

                sumadorEnemigo++;
                puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                estados[ejeX][ejeY].setEstado(true);
                Mienemigo.setEjeX(arriba);
                //cambiarMovimiento1++;
                cambioDireccion++;
                //if (cambioDireccion == 6) {
                //cambioDireccion = 0;
                //}

                do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                } while (movimientoNaveEnemigo == 4 || movimientoNaveEnemigo == 3);

            } finally {
                System.out.println("fuera del catch");
            }

        } else if (movimientoNaveEnemigo == 4) {

            int ejeX = Mienemigo.getEjeX();
            int ejeY = Mienemigo.getEjeY();
            int derecha = Mienemigo.getEjeY() + 1;
            int abajo = Mienemigo.getEjeX() + 1;

            try {
                Estado EstadoCritico = estados[ejeX + 2][ejeY];
                Estado EstadoColision = estados[ejeX + 1][ejeY];

                Estado EstadoCriticoIzquierda = estados[ejeX][ejeY - 2];
                Estado EstadoCriticoDerecha = estados[ejeX][ejeY + 2];
                Estado EstadoCriticoArriba = estados[ejeX - 2][ejeY];

                if (EstadoCriticoIzquierda.getEstado() && EstadoCriticoDerecha.getEstado() && EstadoCriticoArriba.getEstado()) {

                    System.out.println("muerto");

                } else if (EstadoCritico.getEstado() || EstadoColision.getEstado()) {

                    do {
                        movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                        System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                    } while (movimientoNaveEnemigo == 4);
                    cambioDireccion = 0;

                } else {

                    //reseteamos panel derecho
                    matriz[abajo][ejeY].removeAll();
                    matriz[abajo][ejeY].repaint();
                    JLabel imagen = new JLabel();
                    ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                    Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                    imagen.setIcon(icon);
                    imagen.setBounds(0, 0, 22, 22);
                    matriz[abajo][ejeY].add(imagen);
                    matriz[abajo][ejeY].setBackground(Color.black);
                    //elimina todo la posicion actual donde esta el pacman
                    matriz[ejeX][ejeY].removeAll();
                    matriz[ejeX][ejeY].repaint();

                    if (colorMovimiento == 1) {
                        matriz[ejeX][ejeY].setBackground(Color.RED);
                    } else if (colorMovimiento == 2) {
                        matriz[ejeX][ejeY].setBackground(Color.GREEN);
                    } else if (colorMovimiento == 3) {
                        matriz[ejeX][ejeY].setBackground(Color.GRAY);
                    }

                    sumadorEnemigo++;
                    puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                    estados[ejeX][ejeY].setEstado(true);
                    Mienemigo.setEjeX(abajo);
                    //cambiarMovimiento1++;
                    cambioDireccion++;
                    if (cambioDireccion == 6) {
                        cambioDireccion = 0;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException excepcion) {
                System.out.println("fuera de la matriz abajo");
                /*do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                } while (movimientoNaveEnemigo == 4 || movimientoNaveEnemigo == 3 );*/
                //cambioDireccion = 0;

                //reseteamos panel derecho
                matriz[abajo][ejeY].removeAll();
                matriz[abajo][ejeY].repaint();
                JLabel imagen = new JLabel();
                ImageIcon imageicon = new ImageIcon(Mienemigo.getNaveImg());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
                imagen.setBounds(0, 0, 22, 22);
                matriz[abajo][ejeY].add(imagen);
                matriz[abajo][ejeY].setBackground(Color.black);
                //elimina todo la posicion actual donde esta el pacman
                matriz[ejeX][ejeY].removeAll();
                matriz[ejeX][ejeY].repaint();

                if (colorMovimiento == 1) {
                    matriz[ejeX][ejeY].setBackground(Color.RED);
                } else if (colorMovimiento == 2) {
                    matriz[ejeX][ejeY].setBackground(Color.GREEN);
                } else if (colorMovimiento == 3) {
                    matriz[ejeX][ejeY].setBackground(Color.GRAY);
                }

                sumadorEnemigo++;
                puntuacionEnemiga.setText("Puntuacion: " + sumadorEnemigo);
                estados[ejeX][ejeY].setEstado(true);
                Mienemigo.setEjeX(abajo);
                //cambiarMovimiento1++;
                cambioDireccion++;
                //if (cambioDireccion == 6) {
                //cambioDireccion = 0;
                //}
                do {
                    movimientoNaveEnemigo = (int) Math.floor(Math.random() * 4 + 1);
                    System.out.println("cambio movimiento:" + movimientoNaveEnemigo);
                } while (movimientoNaveEnemigo == 4 || movimientoNaveEnemigo == 3);

            } finally {
                System.out.println("fuera del catch");
            }

        }

    }

    public void ganador() {

        if (modoJuego == 0) {

            String[] botones = {"Salir."};

            if (Minave.isVivo() == false) {

                int ventana = JOptionPane.showOptionDialog(null,
                        "Has ganado",
                        "TRON",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,
                        botones, botones[0]);

                if (ventana == 0) {
                    System.exit(0);
                }

            } else if (Mienemigo.isVivo() == false) {

                int ventana = JOptionPane.showOptionDialog(null,
                        "Has perdido",
                        "TRON",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,
                        botones, botones[0]);

                if (ventana == 0) {
                    System.exit(0);
                }

            }

        } else if (modoJuego == 1) {            
            String[] botones = {"Salir."};

            if (Minave.isVivo() == false) {

                int ventana = JOptionPane.showOptionDialog(null,
                        "Gana jugador 2. Puntuación: " + sumadorEnemigo,
                        "TRON",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,
                        botones, botones[0]);

                if (ventana == 0) {
                    System.exit(0);
                }

            } else if (Mienemigo.isVivo() == false) {

                int ventana = JOptionPane.showOptionDialog(null,
                        "Gana jugador 1. Puntuación: " + sumador,
                        "TRON",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,
                        botones, botones[0]);

                if (ventana == 0) {
                    System.exit(0);
                }

            }

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoTron juego = new JuegoTron();
        juego.setVisible(true);
    }

}
