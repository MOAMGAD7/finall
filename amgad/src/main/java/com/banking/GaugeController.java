package com.banking;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Translate;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.*;

public class GaugeController {

    @FXML
    private Label AccountUser1;


    @FXML
    private PieChart donutChart;

    @FXML
    private StackPane chartPane;

    @FXML
    private AnimatedCircularProgressBar progressBar1;

    @FXML
    private AnimatedCircularProgressBar progressBar2;

    @FXML
    private AnimatedCircularProgressBar progressBar3;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private VBox root;

    private final XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    private final XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    private final LinkedList<Integer> values1 = new LinkedList<>();
    private final LinkedList<Integer> values2 = new LinkedList<>();
    private final Random rand = new Random();
    private int index = 1;


    @FXML
    private StackPane chart;
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
    //-------------------------------------------------------------------------------------------------------------//

    @FXML
    public void initialize() {

        //Data Base
        UserSession session = UserSession.getInstance();
        String username = session.getUsername();
        AccountUser1.setText(username);

        // ضبط القيم الأولية للـ progress bars
        progressBar1.setTarget(30);
        progressBar2.setTarget(70);
        progressBar3.setTarget(50);

        // إضافة تأثير الـ hover عشان يعيد الأنيميشن
        progressBar1.setOnMouseClicked(event -> restartProgressBarAnimation(progressBar1));
        progressBar2.setOnMouseClicked(event -> restartProgressBarAnimation(progressBar2));
        progressBar3.setOnMouseClicked(event -> restartProgressBarAnimation(progressBar3));

        // تغيير اللون باستخدام LinearGradient
        LinearGradient gradient1 = new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ff7e5f")), new Stop(1, Color.web("#feb47b")));
        LinearGradient gradient2 = new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#6a11cb")), new Stop(1, Color.web("#2575fc")));
        LinearGradient gradient3 = new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#00c6ff")), new Stop(1, Color.web("#0072ff")));

        // تطبيق التدرجات اللونية على progressArc
        progressBar1.getProgressArc().setStroke(gradient1);
        progressBar2.getProgressArc().setStroke(gradient2);
        progressBar3.getProgressArc().setStroke(gradient3);

        // إضافة بيانات الـ Donut Chart
        donutChart.getData().addAll(
                new PieChart.Data("Food", 25),
                new PieChart.Data("Travel", 20),
                new PieChart.Data("Shopping", 20),
                new PieChart.Data("Other", 10)
        );

        // ضبط الرسم البياني
        donutChart.setLegendVisible(false);
        donutChart.setLabelsVisible(false);
        donutChart.setStartAngle(90);

        // إضافة تأثير Hover وLabel لكل قطاع
        donutChart.getData().forEach(data -> {
            Node node = data.getNode();
            if (node != null) {
                node.setOnMouseEntered(event -> {
                    node.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.5, 0, 2);");
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
                    scaleTransition.setToX(1.1);
                    scaleTransition.setToY(1.1);
                    scaleTransition.play();

                    Label percentageLabel = new Label(data.getName() + " " + data.getPieValue() + "%");
                    String textColor = getColorForSegment(data.getName());
                    percentageLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #ffffff; -fx-text-fill: " + textColor + "; -fx-background-radius: 5; -fx-padding: 5; -fx-border-color: #ccc; -fx-border-radius: 5;");

                    percentageLabel.setTranslateX(event.getX() - 40);
                    percentageLabel.setTranslateY(event.getY() - 30);
                    chartPane.getChildren().add(percentageLabel);

                    node.getProperties().put("percentageLabel", percentageLabel);
                });

                node.setOnMouseExited(event -> {
                    node.setStyle("-fx-effect: none;");
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
                    scaleTransition.setToX(1.0);
                    scaleTransition.setToY(1.0);
                    scaleTransition.play();

                    Label percentageLabel = (Label) node.getProperties().get("percentageLabel");
                    if (percentageLabel != null) {
                        chartPane.getChildren().remove(percentageLabel);
                        node.getProperties().remove("percentageLabel");
                    }
                });
            }
        });

        // تعديل النص في الـ Donut Chart باستخدام النص المعرف في FXML
        Text centerText = (Text) chartPane.lookup("#centerText");
        if (centerText != null) {
            centerText.setText("50%");
            centerText.setFill(Color.WHITE);
            centerText.setFont(Font.font("System", FontWeight.BOLD, 24));
        }



        addTypingEffect(progressBar1, "Withdrawals for this month: 30%");
        addTypingEffect(progressBar2, "Earnings for this month: 70%");
        addTypingEffect(progressBar3, "Deposits for this month: 50%");

        // Create data series for sales performance
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Profit/Loss (USD)");

        root.setStyle("-fx-background-color: rgba(50, 50, 50, 0.2);");

        // إعداد الرسم البياني
        lineChart.setLegendVisible(false);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        lineChart.setHorizontalGridLinesVisible(true);
        lineChart.setVerticalGridLinesVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setStyle("-fx-background-color: transparent;");
        Platform.runLater(() -> {
            Node background = lineChart.lookup(".chart-plot-background");
            if (background != null) {
                background.setStyle("-fx-background-color: transparent;");
            }
        });


        lineChart.getData().addAll(series1, series2);
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis(10, 100, 2); // [من 40 إلى 60] بخطوة 2

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Line Chart واضح");
        lineChart.setAnimated(false); // علشان يمنع التلاعب التلقائي بالمحور

        // اسماء السلاسل
        series1.setName("Dollar");
        series2.setName("Gold");

        // بيانات مبدئية
        for (int i = 0; i < 10; i++) {
            values1.add(rand.nextInt(50) + 50);
            values2.add(rand.nextInt(50) + 50);
        }

        updateChart();

        // تغيير ألوان الخطوط
        Platform.runLater(() -> {
            Node line1 = series1.getNode().lookup(".chart-series-line");
            if (line1 != null) {
                line1.setStyle("-fx-stroke: green; -fx-stroke-width: 3px;");
            }
            Node line2 = series2.getNode().lookup(".chart-series-line");
            if (line2 != null) {
                line2.setStyle("-fx-stroke: gold; -fx-stroke-width: 3px;");
            }
        });

        // تحديث كل ثانية
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            values1.removeFirst();
            values2.removeFirst();

            int newVal1 = values1.getLast() + (rand.nextInt(41) - 20);
            int newVal2 = values2.getLast() + (rand.nextInt(41) - 20);

            newVal1 = Math.max(30, Math.min(100, newVal1));
            newVal2 = Math.max(30, Math.min(100, newVal2));

            values1.add(newVal1);
            values2.add(newVal2);

            updateChart();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
//----------------------------------------------------------------------------------------------------------------------------------//
        double[] values = {160, 120, 140, 180, 130, 150, 110};
        String[] labels = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};

        Paint[] fillGradients = {
                new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#ff7e5f")), new Stop(1, Color.web("#feb47b"))),
                new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#6a11cb")), new Stop(1, Color.web("#2575fc"))),
                new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#00c6ff")), new Stop(1, Color.web("#0072ff"))),
                new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#43e97b")), new Stop(1, Color.web("#38f9d7"))),
                new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#fa709a")), new Stop(1, Color.web("#fee140"))),
                new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#f6d365")), new Stop(1, Color.web("#fda085"))),
                new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#a1c4fd")), new Stop(1, Color.web("#c2e9fb")))
        };

        double chartHeight = 300;
        double maxValue = 200;

        Pane axisPane = new Pane();
        axisPane.setPrefSize(800, chartHeight);



        for (int i = 0; i <= 5; i++) {
            double y = chartHeight - i * (chartHeight / 5)+ 0;
            Line gridLine = new Line(50, y, 600, y);
            gridLine.setStroke(Color.DARKGRAY);
            gridLine.setStrokeWidth(0.5);

            Text label = new Text((i * 20) + "%");
            label.setFill(Color.WHITE);
            label.setFont(Font.font(10));
            label.setX(10);
            label.setY(y + 5);

            axisPane.getChildren().addAll(gridLine, label);
        }

        Line xaxis = new Line(50, chartHeight + 0, 0, chartHeight + 0);
        xaxis.setStroke(Color.GRAY);
        xaxis.setStrokeWidth(2);
        axisPane.getChildren().add(xAxis);

        HBox bars = new HBox(30);
        bars.setAlignment(Pos.BOTTOM_LEFT);
        bars.setPadding(new Insets(0, 0, 0, 70));

        for (int i = 0; i < values.length; i++) {
            double value = values[i];
            double fillHeight = (value / maxValue) * chartHeight;

            Rectangle base = new Rectangle(40, chartHeight);
            base.setArcWidth(10);
            base.setArcHeight(10);
            base.setFill(Color.LIGHTGRAY);

            Rectangle fill = new Rectangle(40, fillHeight);
            fill.setArcWidth(10);
            fill.setArcHeight(10);
            fill.setFill(fillGradients[i]);

            StackPane bar = new StackPane(base, fill);
            bar.setAlignment(Pos.BOTTOM_CENTER);

            // إضافة Tooltip لعرض النسبة عند المرور بالماوس
            Tooltip tooltip = new Tooltip(value/2 + "%");  // تأكد من إضافة النسبة المئوية
            Tooltip.install(bar, tooltip); // ربط Tooltip بالعمود

//            // حدث للماوس لتكبير العمود عند المرور
//            bar.setOnMouseEntered(event -> {
//                fill.setScaleY(1.1);  // تكبير العمود الملون عند المرور
//                fill.setScaleX(1.1);
//                base.setScaleY(1.1);  // تكبير العمود الرمادي أيضًا
//                base.setScaleX(1.1);
//            });
//
//            // حدث للماوس لإرجاع الحجم الطبيعي عند الخروج
//            bar.setOnMouseExited(event -> {
//                fill.setScaleY(1);  // إرجاع العمود الملون إلى حجمه الطبيعي
//                fill.setScaleX(1);
//                base.setScaleY(1);  // إرجاع العمود الرمادي إلى حجمه الطبيعي
//                base.setScaleX(1);
//            });

            Text dayLabel = new Text(labels[i]);
            dayLabel.setFill(Color.LIGHTGRAY);
            dayLabel.setFont(Font.font(13));

            VBox column = new VBox(5, bar, dayLabel);
            column.setAlignment(Pos.BOTTOM_CENTER);

            bars.getChildren().add(column);
        }
        chart.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        chart.setStyle("-fx-background-radius:15;");
        chart.getChildren().addAll(axisPane, bars);
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
    //__________________________________________________________________________________________//
    private void restartProgressBarAnimation(AnimatedCircularProgressBar progressBar) {
        progressBar.restartAnimation();
    }

    private String getColorForSegment(String segmentName) {
        switch (segmentName) {
            case "Food":
                return "#ff5733";
            case "Travel":
                return "#ffc107";
            case "Shopping":
                return "#28a745";
            case "Other":
                return "#17a2b8";
            default:
                return "#000000";
        }
    }


    private void addTypingEffect(AnimatedCircularProgressBar progressBar, String message) {
        Label typingLabel = new Label();
        typingLabel.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-padding: 5;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );

// إضافة ظل أبيض خفيف
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(1);
        shadow.setOffsetY(1);
        shadow.setColor(Color.rgb(0, 0, 0, 0.6));  // ظل أسود شفاف
        typingLabel.setEffect(shadow);

        typingLabel.setVisible(false);

        ((Pane) progressBar.getParent()).getChildren().add(typingLabel);

        progressBar.setOnMouseClicked(e -> {
            typingLabel.setText("");
            typingLabel.setVisible(true);

            typingLabel.setLayoutX(progressBar.getLayoutX() + progressBar.getWidth() / 2 - 40);
            typingLabel.setLayoutY(progressBar.getLayoutY() - 25);

            Timeline timeline = new Timeline();
            for (int i = 0; i < message.length(); i++) {
                final int index = i;
                KeyFrame keyFrame = new KeyFrame(Duration.millis(60 * i), ev -> {
                    typingLabel.setText(typingLabel.getText() + message.charAt(index));
                });
                timeline.getKeyFrames().add(keyFrame);
            }

            timeline.setOnFinished(ev -> {
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(finish -> {
                    typingLabel.setVisible(false);
                });
                pause.play();
            });

            timeline.play();
        });
    }
    private void updateChart() {
        series1.getData().clear();
        series2.getData().clear();
        for (int i = 0; i < values1.size(); i++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(index + i), values1.get(i)));
            series2.getData().add(new XYChart.Data<>(String.valueOf(index + i), values2.get(i)));
        }
        index++;
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

    @FXML
    protected void ToHelp(MouseEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Help.fxml"));
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


