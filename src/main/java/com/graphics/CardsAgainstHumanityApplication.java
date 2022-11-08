package com.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CardsAgainstHumanityApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CardsAgainstHumanityApplication.class.getResource("/CardsAgainstHumanityView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(getClass().getResource("/fonts/fontstyle.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}