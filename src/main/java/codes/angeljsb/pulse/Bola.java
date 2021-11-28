/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import codes.angeljsb.pulse.fisicas.ObjetoFisico;

/**
 *
 * @author Angel
 */
public class Bola extends ObjetoFisico {
    
    private Circle forma;
    
    public Bola() {
        forma = new Circle(Config.RADIO_INICIAL_BOLA, Color.BEIGE);
        super.getChildren().add(forma);
        super.setAceleracion(0, Config.GRAVEDAD_INICIAL);
        super.setVelocidadTerminal(Config.VELOCIDAD_TERMINAL);
        super.setIndiceRebote(Config.INDICE_REBOTE);
    }

    public Circle getForma() {
        return forma;
    }

    public void setForma(Circle forma) {
        this.forma = forma;
    }
    
    public double getRadio() {
        return this.forma.getRadius();
    }
    
    public double getDiametro() {
        return this.getRadio() * 2;
    }
    
    public double getCentroX() {
        return this.getTranslateX() + this.getRadio();
    }
    
    public double getCentroY() {
        return this.getTranslateY() + this.getRadio();
    }
    
}
