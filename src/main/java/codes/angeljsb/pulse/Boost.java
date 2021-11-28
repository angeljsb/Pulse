/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse;

import codes.angeljsb.pulse.fisicas.Movil;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Angel
 */
public class Boost extends Movil implements Cloneable {
    
    private String nombre;
    private String texto;
    private Runnable accion;
    
    private Text contenido;
    
    public Boost(String texto) {
        this.texto = texto;
        
        Group root = new Group();
        Rectangle borde = new Rectangle(Config.ANCHO_BOOST, Config.ALTO_BOOST);
        borde.setStroke(Color.WHITE);
        borde.setStrokeWidth(2);
        borde.setFill(Color.TRANSPARENT);
        contenido = new Text();
        contenido.setText(texto);
        contenido.setFill(Color.WHITE);
        contenido.setFont(new Font("Arial", 13));
        contenido.setTextAlignment(TextAlignment.CENTER);
        contenido.setTextOrigin(VPos.TOP);
        contenido.setVisible(true);
        root.getChildren().addAll(borde, contenido);
        super.getChildren().add(root);
    }
    
    public Boost(String texto, String nombre, Runnable accion) {
        this(texto);
        this.nombre = nombre;
        this.accion = accion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        contenido.setText(texto);
        this.texto = texto;
    }

    public Runnable getAccion() {
        return accion;
    }

    public void setAccion(Runnable accion) {
        this.accion = accion;
    }
    
    public void golpear() {
        this.accion.run();
    }
    
    public Boost clonar() {
        return new Boost(texto, nombre, accion);
    }
}
