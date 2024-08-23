package turbocrew;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Timestamp;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AddCustomerPayment extends HBox {
    
    private Completion completion;
    
    TextField completionIdField;

    

    public AddCustomerPayment() {

        GridPane grid = new GridPane();

        Label addCustomerPaymentLabel = new Label("Add Customer Payement");
        addCustomerPaymentLabel.setFont(Font.font("Arial", 24));
        
        Label completionIdLabel = new Label("Completion Id");
        completionIdField = new TextField();
        completionIdField.setEditable(false);
        
        Button selectCompletionButton = new Button("Select Completion");
        selectCompletionButton.setOnAction(e -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("Select Completion");
            dialog.setResizable(true);
            dialog.getDialogPane().setContent(new SelectCompletionDialog(this).getSelectCompletionDialog());
            Window    window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> window.hide());
            dialog.showAndWait();
        });
        
        Label paymentDateLabel = new Label("Payment Date");

        DatePicker picker = new DatePicker();
        picker.setValue(LocalDate.now());
        picker.setMaxSize(120, USE_PREF_SIZE);
        

        Button addCustomerPaymentButton = new Button("Add Customer Payment");
        addCustomerPaymentButton.setOnAction(e -> {
            if(completion == null){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Completion Selected");
                errorAlert.setContentText("Select a Completion");
                errorAlert.showAndWait();
                return;
            }

            Date paymentDate = java.sql.Date.valueOf(picker.getValue());
            
            
            
            if(new DAO().addCustomerPayment(completion.getCompletionId(), paymentDate)){
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Succesful");
                infoAlert.setContentText("Succesfully Added New Customer Payment");
                infoAlert.showAndWait();
            }
            else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR");
                errorAlert.setContentText("Unknow Error");
                errorAlert.showAndWait();
            }
        });

        grid.add(addCustomerPaymentLabel, 0, 0, 4, 1);

        grid.add(completionIdLabel, 0, 1);
        grid.add(completionIdField, 1, 1);

        grid.add(selectCompletionButton, 0, 2, 4, 1);

        grid.add(paymentDateLabel, 0, 3);
        grid.add(picker, 1, 3);
        

        grid.add(addCustomerPaymentButton, 0, 4, 4, 1);

        GridPane.setHalignment(addCustomerPaymentLabel, HPos.CENTER);
        GridPane.setHalignment(selectCompletionButton, HPos.CENTER);
        GridPane.setHalignment(addCustomerPaymentButton, HPos.CENTER);

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getAddCustomerPayment() {
        return this;
    }

    public Completion getCompletion() {
        return completion;
    }

    public void setCompletion(Completion completion) {
        this.completion = completion;
    }
    
    public void setCompletionFields(){
        completionIdField.setText(Integer.toString(completion.getCompletionId()));
    }  
}
