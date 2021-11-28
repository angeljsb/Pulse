/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse.fisicas;

import javafx.scene.Parent;
import codes.angeljsb.pulse.IActualizable;

/**
 *
 * @author Angel
 */
public class Movil extends Parent implements IActualizable {
    
    private Vector2D velocidad;
    
    public Movil() {
        velocidad = new Vector2D();
    }
    
    public void setVelocidad(Vector2D velocidad) {
        this.velocidad = velocidad;
    }
    
    public void setVelocidad(double x, double y) {
        this.velocidad = new Vector2D(x, y);
    }

    public Vector2D getVelocidad() {
        return velocidad;
    }
    
    @Override
    public void actualizar() {
        this.setTranslateX(this.getTranslateX() + this.getVelocidad().getX());
        this.setTranslateY(this.getTranslateY() + this.getVelocidad().getY());
    }
    
}
