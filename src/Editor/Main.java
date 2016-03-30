package Editor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.*;

/**
 * @author mmspaeth
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            LogManager lm = LogManager.getLogManager();
            Logger logger;
            FileHandler fh = new FileHandler("main-log.txt");

            logger = Logger.getLogger("MainLogger");

            lm.addLogger(logger);
            logger.setLevel(Level.ALL);
            fh.setFormatter(new XMLFormatter());
            logger.addHandler(fh);

            logger.log(Level.INFO, "Start application ...");

            SvgImageLoaderFactory.install();
            Pane root = FXMLLoader.load(getClass().getResource("Editor.fxml"));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

            logger.log(Level.INFO, "Application ready...");
            fh.close();
        } catch (Exception e) {
            System.out.println("Exception thrown: " + e);
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
