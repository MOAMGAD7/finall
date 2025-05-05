package com.banking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database tables
        database_BankSystem.createTables();

        // Load the initial scene (e.g., Login page)
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Login.fxml"));
        Scene scene = new Scene(root, 1024 , 600);
        scene.getStylesheets().add(UserSession.getInstance().isDarkMode() ? "/com/example/maged/DarkMode.css" : "/com/example/maged/LightMode.css");
        primaryStage.setScene(scene   );
        primaryStage.setTitle("Bank App");
        primaryStage.show();
//        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/nasser1.jpg"));
//        BackgroundImage background = new BackgroundImage(
//                backgroundImage,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                new BackgroundSize(100, 100, true, true, true, true)
//        );
//        root.setStyle("-fx-background-image: url('/images/nasser1.jpg');" +
//                "-fx-background-size: cover;" +
//                "-fx-background-position: center center;" +
//                "-fx-background-repeat: no-repeat;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}