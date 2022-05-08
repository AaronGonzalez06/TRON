/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package juegotron;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
/**
 *
 * @author Aaron
 */
public class JuegoTron extends JFrame {
    
    JLabel fondoMenuPrincipal,titulo;
    JButton BotonEmpezar;

    public JuegoTron() {
        crearVentana();        
        fondo();
        boton();
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
    
    public void fuente(){
    File fuente = new File("fuente/Tr2n.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(65f);
            Font sizedFontEstandar = font.deriveFont(15f);
            titulo.setFont(sizedFont);
            BotonEmpezar.setFont(sizedFontEstandar);
        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }
    
    
    
    }
    
    public void fondo(){
        
        fondoMenuPrincipal = new JLabel();        
        String nombre = "img/fondo.jpg";
        ImageIcon imageFondo = new ImageIcon(nombre);
        Icon icon = new ImageIcon(imageFondo.getImage().getScaledInstance(750, 750, Image.SCALE_DEFAULT));
        //se añade la imagen a la etiqueta
        fondoMenuPrincipal.setIcon(icon);
        fondoMenuPrincipal.setBounds(0, 0, 750, 750);
        this.add(fondoMenuPrincipal);
    
    }

    public void boton(){
        
        BotonEmpezar = new JButton();
        BotonEmpezar.setText("Jugar");
        BotonEmpezar.setForeground(Color.red);
        BotonEmpezar.setBounds(320, 400, 110, 20);
        this.add(BotonEmpezar);
    
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoTron juego = new JuegoTron();
        juego.setVisible(true);
    }

}
