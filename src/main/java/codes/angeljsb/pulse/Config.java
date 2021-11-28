/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse;

import javafx.util.Duration;

/**
 *
 * @author Angel
 */
public class Config {
    
    public static final double RADIO_INICIAL_BOLA = 5;
    public static final double GRAVEDAD_INICIAL = 0.5;
    public static final double VELOCIDAD_TERMINAL = 20;
    public static final double INDICE_REBOTE = 0.8;
    public static final double RADIO_IMPULSO = 80;
    public static final double FUERZA_IMPULSO = 80;
    public static final double PROBABILIDAD_BOOST = 0.5;
    
    public static final double ANCHO_CAMPO = 400;
    public static final double ALTO_CAMPO = 600;
    
    public static final double ANCHO_BOOST = 30;
    public static final double ALTO_BOOST = 15;
    public static final double VELOCIDAD_BOOST = 6;
    
    public static final Duration ANIMATION_TIME = Duration.millis(50);
    public static final Duration BOOST_TIME = Duration.seconds(3);
    public static final Duration TIEMPO_IMPULSO = Duration.millis(200);
}
