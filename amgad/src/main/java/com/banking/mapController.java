package com.banking;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class mapController {
    @FXML
    private  Label AccountUser;

    @FXML
    private ImageView worldMap;

    @FXML
    private Pane pinContainer;

    @FXML
    private Group mapGroup;

    @FXML
    private StackPane mapContainer;


    @FXML
    private Button zoomInBtn, zoomOutBtn;

    private double zoomFactor = 1.0;
    private Label locationLabel;

    private double dragStartX;
    private double dragStartY;
    private double initialTranslateX;
    private double initialTranslateY;


    //------------------------------------------------------------------------------------------------------------------------------------------//
    //sidebar
    @FXML
    private FontAwesomeIconView homeIcon;

    @FXML
    private Label homeLabel;

    @FXML
    private FontAwesomeIconView userIcon;
    @FXML
    private Label userLabel;

    @FXML
    private FontAwesomeIconView exchangeIcon;
    @FXML
    private Label exchangeLabel;

    @FXML
    private FontAwesomeIconView moneyIcon;
    @FXML
    private Label moneyLabel;

    @FXML
    private FontAwesomeIconView chartIcon;
    @FXML
    private Label chartLabel;

    @FXML
    private FontAwesomeIconView mapIcon;
    @FXML
    private Label mapLabel;

    @FXML
    private FontAwesomeIconView cogIcon;
    @FXML
    private Label cogLabel;

    @FXML
    private FontAwesomeIconView helpIcon;
    @FXML
    private Label helpLabel;

    @FXML
    private FontAwesomeIconView commentIcon;
    @FXML
    private Label commentLabel;

    @FXML
    private FontAwesomeIconView searchIcon;

    @FXML
    private FontAwesomeIconView bellIcon;

    @FXML
    private ImageView homeGif;
    //-------------------------------------------------------------------------------------------------------------

    @FXML
    public void initialize() {
        
        //Data Base
        UserSession session = UserSession.getInstance();
        String username = session.getUsername();
        AccountUser.setText(username);

        // تحميل صورة الخريطة
        worldMap.setImage(new Image(getClass().getResource("/MAP.png").toExternalForm()));
        worldMap.setOpacity(.85);
        worldMap.setStyle("-fx-background-color: linear-gradient(to bottom, white, blue);");

        List<PinData> pins = Arrays.asList(
                new PinData(80, 150, "Egypt",0),
                new PinData(100, 400, "Saudi Arabia", 0),
                new PinData(330, 270,  "Germany", 0),
                new PinData(500, 100, "USA", 0),
                new PinData(445, 270,  "Japan", 0)
        );

        for (PinData pin : pins) {
            addPin(pin);
        }

        zoomInBtn.setOnAction(e -> {
            mapGroup.setScaleX(mapGroup.getScaleX() * 1.1);
            mapGroup.setScaleY(mapGroup.getScaleY() * 1.1);
        });

        zoomOutBtn.setOnAction(e -> {
            mapGroup.setScaleX(mapGroup.getScaleX() / 1.1);
            mapGroup.setScaleY(mapGroup.getScaleY() / 1.1);
        });

        // إضافة خاصية السحب (Dragging)
        setupDragging();

        Platform.runLater(() -> {
            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(mapContainer.getWidth(), mapContainer.getHeight());
            mapContainer.setClip(clip);
        });

        //-------------------------------------------------------------------------------------------------------------------------------------------//
        //sidebar
        setupHomeAnimation(homeIcon, homeLabel);
        setupUserAnimation(userIcon, userLabel);
        setupExchangeAnimation(exchangeIcon, exchangeLabel);
        setupMoneyAnimation(moneyIcon, moneyLabel);
        setupChartAnimation(chartIcon, chartLabel);
        setupMapAnimation(mapIcon, mapLabel);
        setupCogAnimation(cogIcon, cogLabel);
        setupHelpAnimation(helpIcon, helpLabel);
        setupCommentAnimation(commentIcon, commentLabel);
        // Top bar icons
        if (searchIcon != null) {
            setupSearchAnimation(searchIcon);
        } else {
            System.out.println("Warning: searchIcon is null");
        }
        if (bellIcon != null) {
            setupBellAnimation(bellIcon);
        } else {
            System.out.println("Warning: bellIcon is null");
        }
        // GIF animation
        if (homeGif != null) {
            setupGifAnimation(homeGif);
        } else {
            System.out.println("Warning: homeGif is null");
        }
        //---------------------------------------------------------------------------------------------------------------------------------------------//



    }
    //---------------------------------------------------------------------------------------------------------------------------------------------//
    //sidebar
    private void setupHomeAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: homeIcon or homeLabel is null");
            return;
        }
        Rotate rotate = new Rotate(0, 0, icon.getLayoutY(), 0, Rotate.Y_AXIS);
        icon.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(300), new KeyValue(rotate.angleProperty(), 60)),
                new KeyFrame(Duration.millis(600), new KeyValue(rotate.angleProperty(), 60)),
                new KeyFrame(Duration.millis(900), new KeyValue(rotate.angleProperty(), 0))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            rotate.setAngle(0);
        });
    }

    private void setupUserAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: userIcon or userLabel is null");
            return;
        }
        Scale scale = new Scale(1, 1);
        icon.getTransforms().add(scale);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(scale.xProperty(), 1), new KeyValue(scale.yProperty(), 1)),
                new KeyFrame(Duration.millis(300), new KeyValue(scale.xProperty(), 1.3), new KeyValue(scale.yProperty(), 1.3)),
                new KeyFrame(Duration.millis(600), new KeyValue(scale.xProperty(), 1.3), new KeyValue(scale.yProperty(), 1.3)),
                new KeyFrame(Duration.millis(900), new KeyValue(scale.xProperty(), 1), new KeyValue(scale.yProperty(), 1))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            scale.setX(1);
            scale.setY(1);
        });
    }

    private void setupExchangeAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: exchangeIcon or exchangeLabel is null");
            return;
        }
        Translate translate = new Translate(0, 0);
        icon.getTransforms().add(translate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(translate.xProperty(), 0)),
                new KeyFrame(Duration.millis(300), new KeyValue(translate.xProperty(), 10)),
                new KeyFrame(Duration.millis(600), new KeyValue(translate.xProperty(), 10)),
                new KeyFrame(Duration.millis(900), new KeyValue(translate.xProperty(), 0))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            translate.setX(0);
        });
    }

    private void setupMoneyAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: moneyIcon or moneyLabel is null");
            return;
        }
        Rotate rotate = new Rotate(0, Rotate.Z_AXIS);
        icon.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(300), new KeyValue(rotate.angleProperty(), 180)),
                new KeyFrame(Duration.millis(600), new KeyValue(rotate.angleProperty(), 180)),
                new KeyFrame(Duration.millis(900), new KeyValue(rotate.angleProperty(), 360))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            rotate.setAngle(0);
        });
    }

    private void setupChartAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: chartIcon or chartLabel is null");
            return;
        }
        Translate translate = new Translate(0, 0);
        icon.getTransforms().add(translate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(translate.yProperty(), 0)),
                new KeyFrame(Duration.millis(300), new KeyValue(translate.yProperty(), -10)),
                new KeyFrame(Duration.millis(600), new KeyValue(translate.yProperty(), -10)),
                new KeyFrame(Duration.millis(900), new KeyValue(translate.yProperty(), 0))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            translate.setY(0);
        });
    }

    private void setupMapAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: mapIcon or mapLabel is null");
            return;
        }
        Scale scale = new Scale(1, 1);
        icon.getTransforms().add(scale);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(scale.xProperty(), 1),
                        new KeyValue(scale.yProperty(), 1),
                        new KeyValue(icon.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(scale.xProperty(), 1.2),
                        new KeyValue(scale.yProperty(), 1.2),
                        new KeyValue(icon.opacityProperty(), 0.7)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(scale.xProperty(), 1.2),
                        new KeyValue(scale.yProperty(), 1.2),
                        new KeyValue(icon.opacityProperty(), 0.7)),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(scale.xProperty(), 1),
                        new KeyValue(scale.yProperty(), 1),
                        new KeyValue(icon.opacityProperty(), 1.0))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            scale.setX(1);
            scale.setY(1);
            icon.setOpacity(1.0);
        });
    }

    private void setupCogAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: cogIcon or cogLabel is null");
            return;
        }
        Rotate rotate = new Rotate(0, Rotate.Z_AXIS);
        icon.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(300), new KeyValue(rotate.angleProperty(), 180)),
                new KeyFrame(Duration.millis(600), new KeyValue(rotate.angleProperty(), 180)),
                new KeyFrame(Duration.millis(900), new KeyValue(rotate.angleProperty(), 360))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            rotate.setAngle(0);
        });
    }

    private void setupHelpAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: helpIcon or helpLabel is null");
            return;
        }
        Translate translate = new Translate(0, 0);
        icon.getTransforms().add(translate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(translate.yProperty(), 0)),
                new KeyFrame(Duration.millis(150), new KeyValue(translate.yProperty(), -8)),
                new KeyFrame(Duration.millis(300), new KeyValue(translate.yProperty(), 0)),
                new KeyFrame(Duration.millis(450), new KeyValue(translate.yProperty(), -8)),
                new KeyFrame(Duration.millis(600), new KeyValue(translate.yProperty(), 0)),
                new KeyFrame(Duration.millis(750), new KeyValue(translate.yProperty(), -8)),
                new KeyFrame(Duration.millis(900), new KeyValue(translate.yProperty(), 0))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            translate.setY(0);
        });
    }

    private void setupCommentAnimation(FontAwesomeIconView icon, Label label) {
        if (icon == null || label == null) {
            System.out.println("Warning: commentIcon or commentLabel is null");
            return;
        }
        Translate translate = new Translate(0, 0);
        Scale scale = new Scale(1, 1);
        icon.getTransforms().addAll(translate, scale);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(translate.yProperty(), 0),
                        new KeyValue(scale.xProperty(), 1),
                        new KeyValue(scale.yProperty(), 1)),
                new KeyFrame(Duration.millis(150),
                        new KeyValue(translate.yProperty(), -6),
                        new KeyValue(scale.xProperty(), 1.1),
                        new KeyValue(scale.yProperty(), 1.1)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(translate.yProperty(), 0),
                        new KeyValue(scale.xProperty(), 1.0),
                        new KeyValue(scale.yProperty(), 1.0)),
                new KeyFrame(Duration.millis(450),
                        new KeyValue(translate.yProperty(), -6),
                        new KeyValue(scale.xProperty(), 1.1),
                        new KeyValue(scale.yProperty(), 1.1)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(translate.yProperty(), 0),
                        new KeyValue(scale.xProperty(), 1.0),
                        new KeyValue(scale.yProperty(), 1.0)),
                new KeyFrame(Duration.millis(750),
                        new KeyValue(translate.yProperty(), -6),
                        new KeyValue(scale.xProperty(), 1.1),
                        new KeyValue(scale.yProperty(), 1.1)),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(translate.yProperty(), 0),
                        new KeyValue(scale.xProperty(), 1.0),
                        new KeyValue(scale.yProperty(), 1.0))
        );
        timeline.setCycleCount(1);

        label.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        label.setOnMouseExited(event -> {
            icon.setEffect(null);
            translate.setY(0);
            scale.setX(1);
            scale.setY(1);
        });
    }

    private void setupSearchAnimation(FontAwesomeIconView icon) {
        if (icon == null) {
            System.out.println("Warning: searchIcon is null");
            return;
        }
        Scale scale = new Scale(1, 1);
        Rotate rotate = new Rotate(0, Rotate.Z_AXIS);
        icon.getTransforms().addAll(scale, rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(scale.xProperty(), 1),
                        new KeyValue(scale.yProperty(), 1),
                        new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(scale.xProperty(), 1.5),
                        new KeyValue(scale.yProperty(), 1.5),
                        new KeyValue(rotate.angleProperty(), 15)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(scale.xProperty(), 1.5),
                        new KeyValue(scale.yProperty(), 1.5),
                        new KeyValue(rotate.angleProperty(), 15)),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(scale.xProperty(), 1),
                        new KeyValue(scale.yProperty(), 1),
                        new KeyValue(rotate.angleProperty(), 0))
        );
        timeline.setCycleCount(1);

        icon.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        icon.setOnMouseExited(event -> {
            icon.setEffect(null);
            scale.setX(1);
            scale.setY(1);
            rotate.setAngle(0);
        });
    }

    private void setupBellAnimation(FontAwesomeIconView icon) {
        if (icon == null) {
            System.out.println("Warning: bellIcon is null");
            return;
        }
        Rotate rotate = new Rotate(0, Rotate.Z_AXIS);
        icon.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(150), new KeyValue(rotate.angleProperty(), 15)),
                new KeyFrame(Duration.millis(300), new KeyValue(rotate.angleProperty(), -15)),
                new KeyFrame(Duration.millis(450), new KeyValue(rotate.angleProperty(), 10)),
                new KeyFrame(Duration.millis(600), new KeyValue(rotate.angleProperty(), -10)),
                new KeyFrame(Duration.millis(750), new KeyValue(rotate.angleProperty(), 5)),
                new KeyFrame(Duration.millis(900), new KeyValue(rotate.angleProperty(), 0))
        );
        timeline.setCycleCount(1);

        icon.setOnMouseEntered(event -> {
            icon.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        icon.setOnMouseExited(event -> {
            icon.setEffect(null);
            rotate.setAngle(0);
        });
    }

    private void setupGifAnimation(ImageView gif) {
        if (gif == null) {
            System.out.println("Warning: homeGif is null");
            return;
        }
        Scale scale = new Scale(1, 1);
        gif.getTransforms().add(scale);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(scale.xProperty(), 1),
                        new KeyValue(scale.yProperty(), 1)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(scale.xProperty(), 1.1),
                        new KeyValue(scale.yProperty(), 1.1)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(scale.xProperty(), 1.1),
                        new KeyValue(scale.yProperty(), 1.1)),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(scale.xProperty(), 1),
                        new KeyValue(scale.yProperty(), 1))
        );
        timeline.setCycleCount(1);

        gif.setOnMouseEntered(event -> {
            gif.setEffect(new DropShadow(10, Color.GRAY));
            timeline.playFromStart();
        });
        gif.setOnMouseExited(event -> {
            gif.setEffect(null);
            scale.setX(1);
            scale.setY(1);
        });
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------//


    private void addPin(PinData data) {
        ImageView pin = new ImageView(new Image(getClass().getResource("/Location-pin.png").toExternalForm()));
        pin.setFitHeight(30);
        pin.setFitWidth(30);
        pin.setTranslateX(data.getX());
        pin.setTranslateY(data.getY());
        pin.setRotate(data.getRotation());

        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        pin.setEffect(shadow);

        TranslateTransition bounce = new TranslateTransition(Duration.millis(700), pin);
        bounce.setFromY(data.getY());
        bounce.setToY(data.getY() - 15);
        bounce.setAutoReverse(true);
        bounce.setCycleCount(TranslateTransition.INDEFINITE);
        bounce.play();

        pin.setOnMouseEntered(e -> showLocationMessage(data));
        pin.setOnMouseExited(e -> hideLocationMessage());
        pin.setOnMouseClicked(e -> showCountryName(data));

        pinContainer.getChildren().add(pin);
    }

    private void showCountryName(PinData data) {
        Label countryLabel = new Label();
        countryLabel.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 5; -fx-background-radius: 5; -fx-border-radius: 5;");

        countryLabel.setTranslateX(data.getX() - 10);
        countryLabel.setTranslateY(data.getY() - 40);

        pinContainer.getChildren().add(countryLabel);

        Timeline timeline = new Timeline();
        for (int i = 0; i < data.getCountry().length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(100 * i), ev -> {
                countryLabel.setText(countryLabel.getText() + data.getCountry().charAt(index));
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), countryLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        timeline.setOnFinished(finish -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(finishPause -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), countryLabel);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(e -> pinContainer.getChildren().remove(countryLabel));
                fadeOut.play();
            });
            pause.play();
        });

        timeline.play();
    }

    private void showLocationMessage(PinData data) {
        if (locationLabel == null) {
            locationLabel = new Label();
            locationLabel.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 5; -fx-background-radius: 5; -fx-border-radius: 5;");
            pinContainer.getChildren().add(locationLabel);
        }


        locationLabel.setTranslateX(data.getX());
        locationLabel.setTranslateY(data.getY() - 30);
        locationLabel.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), locationLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private void hideLocationMessage() {
        if (locationLabel != null) {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), locationLabel);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> pinContainer.getChildren().remove(locationLabel));
            fadeOut.play();
            locationLabel = null;
        }
    }

    private void zoom(double factor) {
        zoomFactor *= factor;
        double pivotX = mapContainer.getWidth() / 2;
        double pivotY = mapContainer.getHeight() / 2;

        mapGroup.setScaleX(zoomFactor);
        mapGroup.setScaleY(zoomFactor);

        mapGroup.setTranslateX((1 - zoomFactor) * pivotX);
        mapGroup.setTranslateY((1 - zoomFactor) * pivotY);
    }

    private void setupDragging() {
        mapContainer.setOnMousePressed(event -> {
            dragStartX = event.getSceneX();
            dragStartY = event.getSceneY();
            initialTranslateX = mapGroup.getTranslateX();
            initialTranslateY = mapGroup.getTranslateY();
        });

        mapContainer.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - dragStartX;
            double offsetY = event.getSceneY() - dragStartY;

            mapGroup.setTranslateX(initialTranslateX + offsetX);
            mapGroup.setTranslateY(initialTranslateY + offsetY);
        });
    }
    @FXML
    protected void ToHome2(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Home2.fxml"));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // الحصول على حجم الشاشة
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // إعداد الخلفية
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(false);
        backgroundView.setEffect(new GaussianBlur(20));

        // طبقة شفافة زرقاء
        Region blueOverlay = new Region();
        blueOverlay.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 120, 255, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        blueOverlay.setEffect(new GaussianBlur(20));
        blueOverlay.setPrefSize(screenWidth, screenHeight);

        // تجميع في StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundView, blueOverlay, root);

        // إنشاء المشهد
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().clear();

        // الحصول على الـ stage الحالي
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Payment");
        stage.setWidth(1124);
        stage.setHeight(700);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    protected void ToAccount(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Account.fxml"));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // الحصول على حجم الشاشة
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // إعداد الخلفية
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(false);
        backgroundView.setEffect(new GaussianBlur(20));

        // طبقة شفافة زرقاء
        Region blueOverlay = new Region();
        blueOverlay.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 120, 255, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        blueOverlay.setEffect(new GaussianBlur(20));
        blueOverlay.setPrefSize(screenWidth, screenHeight);

        // تجميع في StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundView, blueOverlay, root);

        // إنشاء المشهد
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().clear();

        // الحصول على الـ stage الحالي
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("FindUs");
        stage.setWidth(1550);
        stage.setHeight(840);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    protected void ToPayment(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Payment.fxml"));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // الحصول على حجم الشاشة
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // إعداد الخلفية
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(false);
        backgroundView.setEffect(new GaussianBlur(20));

        // طبقة شفافة زرقاء
        Region blueOverlay = new Region();
        blueOverlay.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 120, 255, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        blueOverlay.setEffect(new GaussianBlur(20));
        blueOverlay.setPrefSize(screenWidth, screenHeight);

        // تجميع في StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundView, blueOverlay, root);

        // إنشاء المشهد
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().clear();

        // الحصول على الـ stage الحالي
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Payment");
        stage.setWidth(1550);
        stage.setHeight(840);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    protected void ToDashBoard(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Gauge.fxml"));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // الحصول على حجم الشاشة
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // إعداد الخلفية
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(false);
        backgroundView.setEffect(new GaussianBlur(20));

        // طبقة شفافة زرقاء
        Region blueOverlay = new Region();
        blueOverlay.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 120, 255, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        blueOverlay.setEffect(new GaussianBlur(20));
        blueOverlay.setPrefSize(screenWidth, screenHeight);

        // تجميع في StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundView, blueOverlay, root);

        // إنشاء المشهد
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().clear();

        // الحصول على الـ stage الحالي
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("FindUs");
        stage.setWidth(1550);
        stage.setHeight(840);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    protected void ToFindUs(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Map.fxml"));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // الحصول على حجم الشاشة
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // إعداد الخلفية
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(false);
        backgroundView.setEffect(new GaussianBlur(20));

        // طبقة شفافة زرقاء
        Region blueOverlay = new Region();
        blueOverlay.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 120, 255, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        blueOverlay.setEffect(new GaussianBlur(20));
        blueOverlay.setPrefSize(screenWidth, screenHeight);

        // تجميع في StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundView, blueOverlay, root);

        // إنشاء المشهد
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().clear();

        // الحصول على الـ stage الحالي
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("FindUs");
        stage.setWidth(1550);
        stage.setHeight(840);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    protected void ToChat(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Chatbot.fxml"));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // الحصول على حجم الشاشة
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // إعداد الخلفية
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(false);
        backgroundView.setEffect(new GaussianBlur(20));

        // طبقة شفافة زرقاء
        Region blueOverlay = new Region();
        blueOverlay.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 120, 255, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        blueOverlay.setEffect(new GaussianBlur(20));
        blueOverlay.setPrefSize(screenWidth, screenHeight);

        // تجميع في StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundView, blueOverlay, root);

        // إنشاء المشهد
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().clear();

        // الحصول على الـ stage الحالي
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("FindUs");
        stage.setWidth(1550);
        stage.setHeight(840);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    protected void ToSettings(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Settings.fxml"));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/back.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // الحصول على حجم الشاشة
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // إعداد الخلفية
        backgroundView.setFitWidth(screenWidth);
        backgroundView.setFitHeight(screenHeight);
        backgroundView.setPreserveRatio(false);
        backgroundView.setEffect(new GaussianBlur(20));

        // طبقة شفافة زرقاء
        Region blueOverlay = new Region();
        blueOverlay.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 120, 255, 0.2),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        blueOverlay.setEffect(new GaussianBlur(20));
        blueOverlay.setPrefSize(screenWidth, screenHeight);

        // تجميع في StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundView, blueOverlay, root);

        // إنشاء المشهد
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().clear();

        // الحصول على الـ stage الحالي
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("FindUs");
        stage.setWidth(1550);
        stage.setHeight(840);
        stage.centerOnScreen();
        stage.show();
    }


}

