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
    private String naveExp;

    public Personaje(int ejeX, int ejeY, String naveImg, String naveExp) {
        this.ejeX = ejeX;
        this.ejeY = ejeY;
        this.naveImg = naveImg;
        this.naveExp = naveExp;
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

    public String getNaveExp() {
        return naveExp;
    }

    public void setNaveExp(String naveExp) {
        this.naveExp = naveExp;
    }
    
    
    
}
