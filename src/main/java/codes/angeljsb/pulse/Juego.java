/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import static codes.angeljsb.pulse.Config.ALTO_CAMPO;
import static codes.angeljsb.pulse.Config.ANCHO_CAMPO;
import static codes.angeljsb.pulse.Config.RADIO_INICIAL_BOLA;
import codes.angeljsb.pulse.fisicas.ObjetoFisico;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Angel
 */
public class Juego extends Parent {

    private Group root;
    private ArrayList<IActualizable> moviles = new ArrayList();
    private ArrayList<Boost> boosts = new ArrayList();
    private Boost[] boostsPosibles;

    private Bola bola;
    private Timeline timeline;
    private Timeline timelineBoost;
    private Rectangle background;
    private StackPane pantallaPausa;
    private Text mostradorGolpes;

    private Text textoBoost;
    private Timeline opacidadBoost;

    private long golpes;
    private double radioImpulso = Config.RADIO_IMPULSO;
    private double fuerzaImpulso = Config.FUERZA_IMPULSO;
    private double probabilidadBoost = Config.PROBABILIDAD_BOOST;

    public Juego() {
        root = new Group();
        super.getChildren().add(root);
        this.inicializar();
    }

    private void inicializar() {
        mostradorGolpes = new Text();
        mostradorGolpes.setText("Golpes: " + golpes);
        mostradorGolpes.setFill(Color.WHITE);
        mostradorGolpes.setTextOrigin(VPos.TOP);
        mostradorGolpes.setFont(new Font("Arial", 14));

        bola = new Bola();
        bola.setTranslateY(RADIO_INICIAL_BOLA);
        bola.setTranslateX(ANCHO_CAMPO / 2);

        background = new Rectangle(ANCHO_CAMPO, ALTO_CAMPO, Color.BLACK);
        background.setTranslateX(0);
        background.setTranslateY(0);
        background.setTranslateZ(-1);

        moviles.add(bola);

        iniciarTimeline();
        inicializarBoost();

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                crearImpulso(event.getX(), event.getY());
            }
        });

        pantallaPausa = new StackPane();
        Rectangle pausaBack = new Rectangle(ANCHO_CAMPO, ALTO_CAMPO);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        Text instruccion = new Text();
        instruccion.setText("No dejes que la bola caiga");
        instruccion.setFill(Color.WHITE);
        instruccion.setTextOrigin(VPos.TOP);
        instruccion.setFont(new Font("Arial", 14));
        Text text = new Text();
        text.setText("Click para comenzar");
        text.setFill(Color.WHITE);
        text.setTextOrigin(VPos.TOP);
        text.setFont(new Font("Arial", 20));
        box.getChildren().addAll(instruccion, text);
        pantallaPausa.getChildren().addAll(pausaBack, box);
        pantallaPausa.setTranslateZ(10);

        pantallaPausa.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(pantallaPausa);
                start();
            }
        });

        textoBoost = new Text();
        textoBoost.setFill(Color.WHITE);
        textoBoost.setTextOrigin(VPos.TOP);
        textoBoost.setFont(new Font("Arial", 10));
        textoBoost.setOpacity(0);

        opacidadBoost = new Timeline();
        opacidadBoost.setAutoReverse(true);
        opacidadBoost.setCycleCount(2);
        opacidadBoost.getKeyFrames().add(
                new KeyFrame(Config.BOOST_TEXT_TIME, new KeyValue(textoBoost.opacityProperty(), 1))
        );

        root.getChildren().addAll(background, bola, pantallaPausa, mostradorGolpes, textoBoost);

    }

    private void iniciarTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (IActualizable movil : moviles) {
                    movil.actualizar();
                }

                if (bola.getTranslateY() + bola.getDiametro() > Config.ALTO_CAMPO) {
                    perder();
                }

                if (bola.getTranslateX() + bola.getDiametro() > Config.ANCHO_CAMPO) {
                    bola.setTranslateX(Config.ANCHO_CAMPO - bola.getDiametro());
                    bola.chocar(ObjetoFisico.DERECHA);
                }
                if (bola.getTranslateY() < 0) {
                    bola.setTranslateY(0);
                    bola.chocar(ObjetoFisico.ARRIBA);
                }
                if (bola.getTranslateX() < 0) {
                    bola.setTranslateX(0);
                    bola.chocar(ObjetoFisico.IZQUIERDA);
                }

                Boost borrar = null;
                for (Boost boost : boosts) {
                    if (boostChoca(boost)) {
                        activarBoost(boost);
                        borrar = boost;
                        break;
                    }
                    if (boost.getTranslateY() > Config.ALTO_CAMPO) {
                        borrar = boost;
                    }
                }
                removerBoost(borrar);
            }
        });
        timeline.getKeyFrames().add(kf);
    }

    private boolean boostChoca(Boost b) {
        double cX = bola.getCentroX(), cY = bola.getCentroY();
        double iX = bola.getCentroX() - bola.getVelocidad().getX(),
                iY = bola.getCentroY() - bola.getVelocidad().getY();
        double maxX = b.getTranslateX() + Config.ANCHO_BOOST,
                maxY = b.getTranslateY() + Config.ALTO_BOOST;
        return !(Math.max(cX, iX) < b.getTranslateX()
                || Math.min(cX, iX) > maxX
                || Math.max(cY, iY) < b.getTranslateY()
                || Math.min(cY, iY) > maxY);
    }

    private void activarBoost(Boost b) {
        b.golpear();

        textoBoost.setText(b.getNombre());
        textoBoost.setTranslateX(bola.getTranslateX());
        textoBoost.setTranslateY(bola.getTranslateY());
        opacidadBoost.play();
    }

    public void inicializarBoost() {

        boostsPosibles = new Boost[]{
            new Boost("+PG", "Poder de golpe aumentado", new Runnable() {
                @Override
                public void run() {
                    fuerzaImpulso += 10;
                }
            }),
            new Boost("+GR", "Gravedad aumentada", new Runnable() {
                @Override
                public void run() {
                    bola.getAceleracion().sumar(0, 0.1);
                }
            }),
            new Boost("+RG", "Radio golpe aumentado", new Runnable() {
                @Override
                public void run() {
                    radioImpulso += 10;
                }
            }),
            new Boost("-RG", "Radio golpe disminuido", new Runnable() {
                @Override
                public void run() {
                    radioImpulso -= 10;
                }
            }),
            new Boost("-GR", "Gravedad disminuida", new Runnable() {
                @Override
                public void run() {
                    if (bola.getAceleracion().getY() > 0) {
                        bola.getAceleracion().sumar(0, -0.1);
                    }
                }
            }),
            new Boost("-PG", "Poder de golpe disminuido", new Runnable() {
                @Override
                public void run() {
                    fuerzaImpulso -= 10;
                }
            })
        };

        timelineBoost = new Timeline();
        timelineBoost.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.BOOST_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Math.random() < probabilidadBoost) {
                    Boost b = boostsPosibles[(int) Math.floor(Math.random() * boostsPosibles.length)].clonar();
                    b.setTranslateX(Math.random() * (Config.ANCHO_CAMPO - Config.ANCHO_BOOST));
                    b.setTranslateY(0);
                    b.setVelocidad(0, Config.VELOCIDAD_BOOST);

                    agregarBoost(b);
                }
            }
        });
        timelineBoost.getKeyFrames().add(kf);
    }

    private void crearImpulso(double x, double y) {
        Timeline tiempoOnda = new Timeline();

        final Circle onda = new Circle();
        onda.setRadius(0);
        onda.setStroke(Color.WHITE);
        onda.setStrokeWidth(2);
        onda.setFill(Color.TRANSPARENT);
        onda.setTranslateX(x);
        onda.setTranslateY(y);

        root.getChildren().add(onda);

        KeyFrame kf = new KeyFrame(Config.TIEMPO_IMPULSO, new KeyValue(onda.radiusProperty(), radioImpulso));
        KeyFrame kf2 = new KeyFrame(Config.TIEMPO_IMPULSO, new EventHandler() {
            @Override
            public void handle(Event a) {
                root.getChildren().remove(onda);
            }
        }, new KeyValue(onda.opacityProperty(), 0));

        tiempoOnda.getKeyFrames().addAll(kf, kf2);
        tiempoOnda.play();

        golpearBola(x, y);
    }

    private void golpearBola(double x, double y) {
        double distanciaX = bola.getCentroX() - x;
        double distanciaY = bola.getCentroY() - y;
        double distancia = Math.hypot(distanciaX, distanciaY);

        if (distancia < radioImpulso) {
            double fuerzaReal = ((radioImpulso - distancia) / radioImpulso) * fuerzaImpulso;

            double mvX, mvY;

            mvX = (fuerzaReal * ((radioImpulso - Math.abs(distanciaY)) / radioImpulso));
            mvY = (fuerzaReal * ((radioImpulso - Math.abs(distanciaX)) / radioImpulso));

            mvX = (distanciaX < 0 ? -mvX : mvX);
            mvY = (distanciaY < 0 ? -mvY : mvY);

            bola.getVelocidad().sumar(mvX, mvY);

            golpes++;
            mostradorGolpes.setText("Golpes: " + golpes);
        }
    }

    public void perder() {
        timeline.stop();
        timelineBoost.stop();
        bola.setTranslateX(ANCHO_CAMPO / 2);
        bola.setTranslateY(RADIO_INICIAL_BOLA);
        bola.setVelocidad(0, 0);
        bola.setAceleracion(0, Config.GRAVEDAD_INICIAL);

        while (!boosts.isEmpty()) {
            removerBoost(boosts.get(0));
        }
        fuerzaImpulso = Config.FUERZA_IMPULSO;
        radioImpulso = Config.RADIO_IMPULSO;

        final StackPane pantallaDerrota = new StackPane();
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        Rectangle pausaBack = new Rectangle(ANCHO_CAMPO, ALTO_CAMPO);

        Text text = new Text();
        text.setText("¡Fin del Juego!");
        text.setFill(Color.WHITE);
        text.setFont(new Font("Arial", 18));
        Text puntos = new Text();
        puntos.setText("Total: " + golpes + " golpes");
        puntos.setFill(Color.WHITE);
        puntos.setFont(new Font("Arial", 24));

        box.getChildren().addAll(text, puntos);
        pantallaDerrota.getChildren().addAll(pausaBack, box);

        pantallaDerrota.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(pantallaDerrota);
                start();
            }
        });

        root.getChildren().add(pantallaDerrota);
    }

    public void start() {
        golpes = 0;
        //fuerzaImpulso = Config.FUERZA_IMPULSO;
        mostradorGolpes.setText("Golpes: " + golpes);
        timeline.play();
        timelineBoost.play();
    }

    public void agregarBoost(Boost boost) {
        boosts.add(boost);
        moviles.add(boost);
        root.getChildren().add(boost);
    }

    public void removerBoost(Boost boost) {
        boosts.remove(boost);
        moviles.remove(boost);
        root.getChildren().remove(boost);
    }

}
