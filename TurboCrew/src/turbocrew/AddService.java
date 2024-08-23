package turbocrew;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class AddService extends GridPane {
    HBox hbox;
    public AddService() {
        hbox = new HBox();
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label addServiceLabel = new Label("Add Service");
        addServiceLabel.setFont(Font.font("Arial", 24));

        Label serviceTypeLabel = new Label("Service Type");
        TextField serviceTypeField = new TextField();

        Label hourlyRateLabel = new Label("Hourly Rate");
        NumberTextField hourlyRateField = new NumberTextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String serviceType = serviceTypeField.getText().trim();
                String hourlyRateStr = hourlyRateField.getText().trim();

                if (serviceType.equals("") || hourlyRateStr.equals("")) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Service Type or Hourly Rate cant't be empty");
                    errorAlert.showAndWait();
                    return;
                }
                if (hourlyRateStr.startsWith("0")) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Hourly Rate can't start with or be 0");
                    errorAlert.showAndWait();
                    return;
                }
                int hourlyRate = Integer.parseInt(hourlyRateStr);
                if(new DAO().addService(serviceType, hourlyRate)){
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setHeaderText("Succesful");
                    infoAlert.setContentText("Succesfully Added New Service");
                    infoAlert.showAndWait();
                }
                else{
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error");
                    errorAlert.setContentText("Service Already Exists");
                    errorAlert.showAndWait();
                }
            }
        });

        grid.add(addServiceLabel, 0, 0, 2, 1);

        grid.add(serviceTypeLabel, 0, 1);
        grid.add(serviceTypeField, 1, 1);

        grid.add(hourlyRateLabel, 0, 2);
        grid.add(hourlyRateField, 1, 2);

        grid.add(addButton, 1, 3, 1, 1);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(addServiceLabel, HPos.CENTER);

        hbox.getChildren().add(grid);
        hbox.setAlignment(Pos.CENTER);
    }
    public HBox getAddService(){
        return hbox;
    }
}
