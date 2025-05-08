package com.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController {

    @FXML private TextField newPasswordField;
    @FXML private TextField confirmPasswordField;
    @FXML private Label errorLabel;
    
    // متغيرات للتحكم في سحب النافذة
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    protected void handleChangePassword(ActionEvent event) throws IOException {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        UserSession session = UserSession.getInstance();
        String username = session.getUsername();

        // التحقق من أن كلمات المرور متطابقة
        if (!newPassword.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        if (newPassword.isEmpty()) {
            errorLabel.setText("Please enter a new password");
            return;
        }

        // تحديث كلمة المرور في قاعدة البيانات (افتراضي)
        if (database_BankSystem.updatePassword(username, newPassword)) {
            // إذا تم تغيير كلمة المرور بنجاح، نغير حالة إعادة التعيين
            session.setPasswordReset(false); // السطر 40: إعادة تعيين الحالة بعد التغيير

            // التنقل إلى صفحة الـ Login
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(session.isDarkMode() ? "/com/example/maged/DarkMode.css" : "/com/example/maged/LightMode.css");
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } else {
            errorLabel.setText("Error updating password");
        }
    }

    @FXML
    protected void switchToLogin(ActionEvent event) throws IOException {
        UserSession session = UserSession.getInstance();
        session.setPasswordReset(false); // إعادة تعيين الحالة إذا رجع للـ Login

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/maged/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(session.isDarkMode() ? "/com/example/maged/DarkMode.css" : "/com/example/maged/LightMode.css");
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    
    // طرق التحكم في النافذة
    
    @FXML
    protected void handleMousePressed(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }
    
    @FXML
    protected void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }
    
    @FXML
    protected void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    protected void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    protected void handleMaximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }
}