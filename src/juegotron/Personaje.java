/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegotron;

/**
 *
 * @author Aaron
 */
public class Personaje {
    
    private int ejeX;
    private int ejeY;
    private String naveImg;
    private boolean vivo;

    public Personaje(int ejeX, int ejeY, String naveImg, boolean vivo) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.naveImg = naveImg;
        this.vivo = vivo;
    }

    public int getEjeX() {
        return ejeX;
    }

    public void setEjeX(int ejeX) {
        this.ejeX = ejeX;
    }

    public int getEjeY() {
        return ejeY;
    }

    public void setEjeY(int ejeY) {
        this.ejeY = ejeY;
    }

    public String getNaveImg() {
        return naveImg;
    }

    public void setNaveImg(String naveImg) {
        this.naveImg = naveImg;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }


    
    
    
    
}
