/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse.fisicas;

/**
 *
 * @author Angel
 */
public class ObjetoFisico extends Movil {
    
    public static final int ARRIBA = 0,
            ABAJO = 1,
            DERECHA = 2,
            IZQUIERDA = 3;
    
    private Vector2D aceleracion;
    
    private double velocidadTerminal;
    private double indiceRebote;
    
    public ObjetoFisico () {
        super();
        aceleracion = new Vector2D();
    }
    
    public void setAceleracion(Vector2D aceleracion) {
        this.aceleracion = aceleracion;
    }
    
    public void setAceleracion(double x, double y) {
        this.aceleracion = new Vector2D(x, y);
    }

    public Vector2D getAceleracion() {
        return aceleracion;
    }

    public double getVelocidadTerminal() {
        return velocidadTerminal;
    }

    public void setVelocidadTerminal(double velocidadTerminal) {
        this.velocidadTerminal = velocidadTerminal;
    }

    public double getIndiceRebote() {
        return indiceRebote;
    }

    public void setIndiceRebote(double indiceRebote) {
        this.indiceRebote = indiceRebote;
    }
    
    public void chocar(int direccion){
        switch (direccion) {
            case ARRIBA:
            case ABAJO:
                this.getVelocidad().setY(this.getVelocidad().getY() * (-indiceRebote));
                break;
            case DERECHA:
            case IZQUIERDA:
                this.getVelocidad().setX(this.getVelocidad().getX() * -indiceRebote);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void actualizar() {
        super.actualizar();
        this.getVelocidad().sumar(aceleracion);
        
        if(Math.abs(this.getVelocidad().getX()) > this.velocidadTerminal) {
            this.getVelocidad().setX(this.getVelocidad().getX() < 0 ? -this.velocidadTerminal : this.velocidadTerminal);
        }
        if(Math.abs(this.getVelocidad().getY()) > this.velocidadTerminal) {
            this.getVelocidad().setY(this.getVelocidad().getY() < 0 ? -this.velocidadTerminal : this.velocidadTerminal);
        }
    }
    
}
