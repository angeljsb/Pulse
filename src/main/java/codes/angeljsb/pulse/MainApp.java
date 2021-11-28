/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codes.angeljsb.pulse;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static codes.angeljsb.pulse.Config.ALTO_CAMPO;
import static codes.angeljsb.pulse.Config.ANCHO_CAMPO;
import javafx.scene.image.Image;

/**
 *
 * @author Angel
 */
public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) {
        Juego juego = new Juego();
        
        Group group = new Group();
        group.getChildren().add(juego);
        
        Image icon = new Image(MainApp.class.getResourceAsStream("/assets/ondas.jpg"));
        
        Scene scene = new Scene(group);
        scene.setFill(Color.BLACK);

        stage.setScene(scene);
        stage.setTitle("Pulse!!!");
        stage.setWidth(ANCHO_CAMPO);
        stage.setHeight(ALTO_CAMPO);
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
