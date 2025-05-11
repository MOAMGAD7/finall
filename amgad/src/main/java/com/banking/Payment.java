package com.banking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
public class Payment {
    public Label TrToLb;
    public Label AmLb;
    public Label StLb;
    public Label DtLb;
    public Label CtgLb;
    public Label NoHsLb;// Balance description/heading label
    public AnchorPane HsPn;
    public AnchorPane payback;
    public VBox MyHs;
    public AnchorPane HsPn1;
    public Label TrToLb1;
    public Label AmLb1;
    public Label StLb1;
    public Label CtgLb1;
    public Label DtLb1;
    public AnchorPane HsPn11;
    public Label TrToLb11;
    public Label AmLb11;
    public Label StLb11;
    public Label DtLb11;
    public Label CtgLb11;
    public AnchorPane HsPn111;
    public Label TrToLb111;
    public Label AmLb111;
    public Label StLb111;
    public Label DtLb111;
    public Label CtgLb111;
    public AnchorPane HsPn1111;
    public Label TrToLb1111;
    public Label AmLb1111;
    public Label StLb1111;
    public Label CtgLb1111;
    public Label DtLb1111;
    public AnchorPane HsPn11111;
    public Label TrToLb11111;
    public Label AmLb11111;
    public Label StLb11111;
    public Label DtLb11111;
    public Label CtgLb11111;
    public int c=0;
    public ImageView BlUp;
    public ImageView BlDn;
    public Label BlINPy;
    public double bln=1000;
    public Button ToBills;
    public Button ToMobile;
    public Button ToCard;
    public Button ToGov;
    public Button ToDon;
    public Button ToEdu;
    public Button ToIns;
    public Button ToOthr;
    @FXML
    private Button toTransfer;

    @FXML
    public void onBillPaymentClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Bills");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void switchToPage2(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/Transfer.fxml"));
        Parent page2 = fxmlLoader.load();

        // Load background image - validate resource path
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(page2);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        Stage stage = (Stage) toTransfer.getScene().getWindow();
        stage.setScene(scene);
    }
    // Declare these as class variables to access in setColors method


    public void addHistory(String s1, String s2, String s3) {
        // Calculate the new balance by subtracting s2 from current balance in BlInPy
        try {
            // Get the current balance text
            String currentBalanceText = BlINPy.getText();

            // Extract the numeric part (remove currency symbols, commas, etc.)
            String numericText = currentBalanceText.replaceAll("[^0-9.]", "");

            // Parse the current balance and transfer amount as doubles
            double currentBalance = Double.parseDouble(numericText);
            double transferAmount = Double.parseDouble(s2.replaceAll("[^0-9.]", ""));

            // Calculate the new balance
            double newBalance = currentBalance - transferAmount;

            // Format the new balance to match the original format (with currency symbol if present)
            String currencySymbol = "";
            if (currentBalanceText.contains("$")) {
                currencySymbol = "$";
            } else if (currentBalanceText.contains("€")) {
                currencySymbol = "€";
            } else if (currentBalanceText.contains("£")) {
                currencySymbol = "£";
            }

            // Format the new balance with appropriate formatting
            String formattedNewBalance = String.format("%s%.2f", currencySymbol, newBalance);

            // Update the balance display
            BlINPy.setText(formattedNewBalance);
            if (BlINPy != null) {
                BlINPy.setText(formattedNewBalance+" EGP");
            }

            System.out.println("Updated balance: " + formattedNewBalance);
        } catch (Exception e) {
            System.err.println("Error updating balance: " + e.getMessage());
            e.printStackTrace();
        }
        BlDn.setVisible(true);
        TrToLb.setText(s1);
        AmLb.setText("- " + s2);
        StLb.setText("Success");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, hh:mm a");
        String formatted = now.format(formatter);
        DtLb.setText(formatted);
        CtgLb.setText(s3);
        NoHsLb.setVisible(false);
    }
    public void addHistory1(String s1, String s2, String s3) {
        BlINPy.setText(String.valueOf(Integer.parseInt(BlINPy.getText())-Integer.parseInt(s2)));
        BlDn.setVisible(true);
        TrToLb1.setText(TrToLb.getText());
        AmLb1.setText(AmLb.getText());
        StLb1.setText(StLb.getText());
        DtLb1.setText(DtLb.getText());
        CtgLb1.setText(CtgLb.getText());
        TrToLb.setText(s1);
        AmLb.setText("- " + s2);
        StLb.setText("Success");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, hh:mm a");
        String formatted = now.format(formatter);
        DtLb.setText(formatted);
        CtgLb.setText(s3);
    }
    public void addHistory2(String s1, String s2, String s3) {
        BlINPy.setText(String.valueOf(Integer.parseInt(BlINPy.getText())-Integer.parseInt(s2)));
        BlDn.setVisible(true);
        TrToLb11.setText(TrToLb1.getText());
        AmLb11.setText(AmLb1.getText());                // انا مش سامعكوا
        StLb11.setText(StLb1.getText());
        DtLb11.setText(DtLb1.getText());
        CtgLb11.setText(CtgLb1.getText());
        TrToLb1.setText(TrToLb.getText());
        AmLb1.setText(AmLb.getText());
        StLb1.setText(StLb.getText());
        DtLb1.setText(DtLb.getText());
        CtgLb1.setText(CtgLb.getText());
        TrToLb.setText(s1);
        AmLb.setText("- " + s2);
        StLb.setText("Success");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, hh:mm a");
        String formatted = now.format(formatter);
        DtLb.setText(formatted);
        CtgLb.setText(s3);
    }
    public void addHistory3(String s1, String s2, String s3) {
        BlINPy.setText(String.valueOf(Integer.parseInt(BlINPy.getText())-Integer.parseInt(s2)));
        BlDn.setVisible(true);
        TrToLb111.setText(TrToLb11.getText());
        AmLb111.setText(AmLb11.getText());
        StLb111.setText(StLb11.getText());
        DtLb111.setText(DtLb11.getText());
        CtgLb111.setText(CtgLb11.getText());
        TrToLb11.setText(TrToLb1.getText());
        AmLb11.setText(AmLb1.getText());
        StLb11.setText(StLb1.getText());
        DtLb11.setText(DtLb1.getText());
        CtgLb11.setText(CtgLb1.getText());
        TrToLb1.setText(TrToLb.getText());
        AmLb1.setText(AmLb.getText());
        StLb1.setText(StLb.getText());
        DtLb1.setText(DtLb.getText());
        CtgLb1.setText(CtgLb.getText());
        TrToLb.setText(s1);
        AmLb.setText("- " + s2);
        StLb.setText("Success");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, hh:mm a");
        String formatted = now.format(formatter);
        DtLb.setText(formatted);
        CtgLb.setText(s3);
    }
    public void addHistory4(String s1, String s2, String s3) {
        // Calculate the new balance by subtracting s2 from current balance in BlInPy
        try {
            // Get the current balance text
            String currentBalanceText = BlINPy.getText();

            // Extract the numeric part (remove currency symbols, commas, etc.)
            String numericText = currentBalanceText.replaceAll("[^0-9.]", "");

            // Parse the current balance and transfer amount as doubles
            double currentBalance = Double.parseDouble(numericText);
            double transferAmount = Double.parseDouble(s2.replaceAll("[^0-9.]", ""));

            // Calculate the new balance
            double newBalance = currentBalance - transferAmount;

            // Format the new balance to match the original format (with currency symbol if present)
            String currencySymbol = "";
            if (currentBalanceText.contains("$")) {
                currencySymbol = "$";
            } else if (currentBalanceText.contains("€")) {
                currencySymbol = "€";
            } else if (currentBalanceText.contains("£")) {
                currencySymbol = "£";
            }

            // Format the new balance with appropriate formatting
            String formattedNewBalance = String.format("%s%.2f", currencySymbol, newBalance);

            // Update the balance display
            BlINPy.setText(formattedNewBalance);
            if (BlINPy != null) {
                BlINPy.setText(formattedNewBalance);
            }

            System.out.println("Updated balance: " + formattedNewBalance);
        } catch (Exception e) {
            System.err.println("Error updating balance: " + e.getMessage());
            e.printStackTrace();
        }
        BlDn.setVisible(true);
        TrToLb1111.setText(TrToLb111.getText());
        AmLb1111.setText(AmLb111.getText());
        StLb1111.setText(StLb111.getText());
        DtLb1111.setText(DtLb111.getText());
        CtgLb1111.setText(CtgLb111.getText());
        TrToLb111.setText(TrToLb11.getText());
        AmLb111.setText(AmLb11.getText());
        StLb111.setText(StLb11.getText());
        DtLb111.setText(DtLb11.getText());
        CtgLb111.setText(CtgLb11.getText());
        TrToLb11.setText(TrToLb1.getText());
        AmLb11.setText(AmLb1.getText());
        StLb11.setText(StLb1.getText());
        DtLb11.setText(DtLb1.getText());
        CtgLb11.setText(CtgLb1.getText());
        TrToLb1.setText(TrToLb.getText());
        AmLb1.setText(AmLb.getText());
        StLb1.setText(StLb.getText());
        DtLb1.setText(DtLb.getText());
        CtgLb1.setText(CtgLb.getText());
        TrToLb.setText(s1);
        AmLb.setText("- " + s2);
        StLb.setText("Success");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, hh:mm a");
        String formatted = now.format(formatter);
        DtLb.setText(formatted);
        CtgLb.setText(s3);
    }
    public void addHistory5(String s1, String s2, String s3) {
        // Calculate the new balance by subtracting s2 from current balance in BlInPy
        try {
            // Get the current balance text
            String currentBalanceText = BlINPy.getText();

            // Extract the numeric part (remove currency symbols, commas, etc.)
            String numericText = currentBalanceText.replaceAll("[^0-9.]", "");

            // Parse the current balance and transfer amount as doubles
            double currentBalance = Double.parseDouble(numericText);
            double transferAmount = Double.parseDouble(s2.replaceAll("[^0-9.]", ""));

            // Calculate the new balance
            double newBalance = currentBalance - transferAmount;

            // Format the new balance to match the original format (with currency symbol if present)
            String currencySymbol = "";
            if (currentBalanceText.contains("$")) {
                currencySymbol = "$";
            } else if (currentBalanceText.contains("€")) {
                currencySymbol = "€";
            } else if (currentBalanceText.contains("£")) {
                currencySymbol = "£";
            }

            // Format the new balance with appropriate formatting
            String formattedNewBalance = String.format("%s%.2f", currencySymbol, newBalance);

            // Update the balance display
            BlINPy.setText(formattedNewBalance);
            if (BlINPy != null) {
                BlINPy.setText(formattedNewBalance);
            }

            System.out.println("Updated balance: " + formattedNewBalance);
        } catch (Exception e) {
            System.err.println("Error updating balance: " + e.getMessage());
            e.printStackTrace();
        }
        BlDn.setVisible(true);
        TrToLb11111.setText(TrToLb1111.getText());
        AmLb11111.setText(AmLb1111.getText());
        StLb11111.setText(StLb11111.getText());
        DtLb11111.setText(DtLb11111.getText());
        CtgLb11111.setText(CtgLb11111.getText());
        TrToLb1111.setText(TrToLb111.getText());
        AmLb1111.setText(AmLb111.getText());
        StLb1111.setText(StLb111.getText());
        DtLb1111.setText(DtLb111.getText());
        CtgLb1111.setText(CtgLb111.getText());
        TrToLb111.setText(TrToLb11.getText());
        AmLb111.setText(AmLb11.getText());
        StLb111.setText(StLb11.getText());
        DtLb111.setText(DtLb11.getText());
        CtgLb111.setText(CtgLb11.getText());
        TrToLb11.setText(TrToLb1.getText());
        AmLb11.setText(AmLb1.getText());
        StLb11.setText(StLb1.getText());
        DtLb11.setText(DtLb1.getText());
        CtgLb11.setText(CtgLb1.getText());
        TrToLb1.setText(TrToLb.getText());
        AmLb1.setText(AmLb.getText());
        StLb1.setText(StLb.getText());
        DtLb1.setText(DtLb.getText());
        CtgLb1.setText(CtgLb.getText());
        TrToLb.setText(s1);
        AmLb.setText("- " + s2);
        StLb.setText("Success");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, hh:mm a");
        String formatted = now.format(formatter);
        DtLb.setText(formatted);
        CtgLb.setText(s3);
    }
    public void setColors(){
        if(Objects.equals(CtgLb.getText(), "Transfer")){
            CtgLb.setTextFill(Paint.valueOf("#008cff"));
            HsPn.setStyle("-fx-background-color: rgba(0, 140, 255, 0.3);");
        }
        else{
            CtgLb.setTextFill(Paint.valueOf("#FF3232"));
            HsPn.setStyle("-fx-background-color: rgba(255, 50, 50, 0.3);");
        }
        AmLb.setStyle("-fx-text-fill: rgba(255, 0, 0);");
    }
    public void setColors1(){
        if(Objects.equals(CtgLb1.getText(), "Transfer")){
            CtgLb1.setTextFill(Paint.valueOf("#008cff"));
            HsPn1.setStyle("-fx-background-color: rgba(0, 140, 255, 0.3);");
        }
        else{
            CtgLb1.setTextFill(Paint.valueOf("#FF3232"));
            HsPn1.setStyle("-fx-background-color: rgba(255, 50, 50, 0.3);");
        }
        AmLb1.setStyle("-fx-text-fill: rgba(255, 0, 0);");
    }
    public void setColors2(){
        if(Objects.equals(CtgLb11.getText(), "Transfer")){
            CtgLb11.setTextFill(Paint.valueOf("#008cff"));
            HsPn11.setStyle("-fx-background-color: rgba(0, 140, 255, 0.3);");
        }
        else{
            CtgLb11.setTextFill(Paint.valueOf("#FF3232"));
            HsPn11.setStyle("-fx-background-color: rgba(255, 50, 50, 0.3);");
        }
        AmLb11.setStyle("-fx-text-fill: rgba(255, 0, 0);");
    }
    public void setColors3(){
        if(Objects.equals(CtgLb111.getText(), "Transfer")){
            CtgLb111.setTextFill(Paint.valueOf("#008cff"));
            HsPn111.setStyle("-fx-background-color: rgba(0, 140, 255, 0.3);");
        }
        else{
            CtgLb111.setTextFill(Paint.valueOf("#FF3232"));
            HsPn111.setStyle("-fx-background-color: rgba(255, 50, 50, 0.3);");
        }
        AmLb111.setStyle("-fx-text-fill: rgba(255, 0, 0);");
    }
    public void setColors4(){
        if(Objects.equals(CtgLb1111.getText(), "Transfer")){
            CtgLb1111.setTextFill(Paint.valueOf("#008cff"));
            HsPn1111.setStyle("-fx-background-color: rgba(0, 140, 255, 0.3);");
        }
        else{
            CtgLb1111.setTextFill(Paint.valueOf("#FF3232"));
            HsPn1111.setStyle("-fx-background-color: rgba(255, 50, 50, 0.3);");
        }
        AmLb1111.setStyle("-fx-text-fill: rgba(255, 0, 0);");
    }
    public void setColors5(){
        if(Objects.equals(CtgLb11111.getText(), "Transfer")){
            CtgLb11111.setTextFill(Paint.valueOf("#008cff"));
            HsPn11111.setStyle("-fx-background-color: rgba(0, 140, 255, 0.3);");
        }
        else{
            CtgLb11111.setTextFill(Paint.valueOf("#FF3232"));
            HsPn11111.setStyle("-fx-background-color: rgba(255, 50, 50, 0.3);");
        }
        AmLb11111.setStyle("-fx-text-fill: rgba(255, 0, 0);");
    }

    public void onMobileClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Mobile Top-Up");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }

    public void onCardClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Credit Card");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }

    public void onGovClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Government Service");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }

    public void onDonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Donation");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }

    public void onEduClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Education Payments");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }

    public void onInsClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Insurance Payments");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }

    public void OnOtherClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/PaymentPage.fxml"));
        Parent paymentPage = fxmlLoader.load();
        PaymentPage paymentPage1 =fxmlLoader.getController();
        paymentPage1.setPaymentCategory("Other Payments");
        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
            if (backgroundImage.isError()) {
                System.err.println("Error loading background image: " + backgroundImage.getException().getMessage());
                // Use a fallback color if image fails to load
                backgroundImage = null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create stack pane for layering content
        StackPane stackPane = new StackPane();

        // Add background and overlay if image loaded successfully
        if (backgroundImage != null) {
            ImageView backgroundView = new ImageView(backgroundImage);
            // Fit background to screen
            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);
            backgroundView.setPreserveRatio(false);
            backgroundView.setEffect(new GaussianBlur(20));

            // Create a transparent blue overlay
            Region blueOverlay = new Region();
            blueOverlay.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 120, 255, 0.2),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            blueOverlay.setEffect(new GaussianBlur(20));
            blueOverlay.setPrefSize(screenWidth, screenHeight);

            // Add background and overlay to stack
            stackPane.getChildren().addAll(backgroundView, blueOverlay);
        } else {
            // Create a fallback blue gradient background if image failed to load
            Region fallbackBackground = new Region();
            fallbackBackground.setBackground(new Background(new BackgroundFill(
                    Color.rgb(10, 60, 120, 1.0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            fallbackBackground.setPrefSize(screenWidth, screenHeight);
            stackPane.getChildren().add(fallbackBackground);
        }

        // Add the UI content on top
        stackPane.getChildren().add(paymentPage);

        // Create the scene
        Scene scene = new Scene(stackPane, 1200, 700);

        // Get the current stage and set the new scene
        Stage stage = (Stage) ToBills.getScene().getWindow();
        stage.setScene(scene);
    }
}
