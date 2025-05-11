package com.banking;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
public class TransferConfirm {
    public Label AmCn;
    public Label AmFeCn;
    public Button TrCnf;
    public Label Lb1;
    public Label Lb2;
    public Label lb3;
    @FXML
    Label TransToCn;
    String str1,str2,str3;
    public void LabelText(String s1,String s2,String s3,String s4){
        TransToCn.setText(s1);
        AmCn.setText(s2);
        AmFeCn.setText(s3);
        if(s4.equals("Transfer")){
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Bills")){
            Lb1.setText("Bill Type:");
            Lb2.setText("Customer ID:");
            lb3.setText("Bill Amount:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Mobile Top-Up")){
            Lb1.setText("Network:");
            Lb2.setText("Mobile Number:");
            lb3.setText("Top-Up Amount:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Credit Card")){
            Lb1.setText("Chosen Card:");
            Lb2.setText("Amount:");
            lb3.setText("Amount with Fees:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Government Service")){
            Lb1.setText("Chosen Service:");
            Lb2.setText("Amount:");
            lb3.setText("Amount with Fees:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Donation")){
            Lb1.setText("Chosen Charity:");
            Lb2.setText("Donated Amount:");
            lb3.setText("Amount with Fees:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Education Payments")){
            Lb1.setText("Chosen Facility:");
            Lb2.setText("Student ID:");
            lb3.setText("Amount with Fees:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Insurance Payments")){
            Lb1.setText("Insurance Provider:");
            Lb2.setText("Policy Number:");
            lb3.setText("Amount with Fees:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
        else if(s4.equals("Other Payments")){
            Lb1.setText("Payment Category:");
            Lb2.setText("Payee Name:");
            lb3.setText("Amount with Fees:");
            this.str1=s1;
            this.str2=s3;
            this.str3=s4;
        }
    }
    public void Clicking3(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maged/ConfirmPassword.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            ConfirmPassword confirmPassword =fxmlLoader.getController();
            confirmPassword.getTxt(str1,str2,str3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Password Confirmation");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }
}

