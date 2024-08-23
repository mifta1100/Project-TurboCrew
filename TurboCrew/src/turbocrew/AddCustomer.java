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

public class AddCustomer extends GridPane {
        HBox hbox;
    public AddCustomer() {
        hbox = new HBox();
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label addCustomerLabel = new Label("Add Customer");
        addCustomerLabel.setFont(Font.font("Arial", 24));

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();

        Label phoneLabel = new Label("Phone");
        NumberTextField phoneField = new NumberTextField();
        
        Label addressLabel = new Label("Address");
        TextField addressField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();
                String email = emailField.getText().trim();

                if (name.equals("") || phone.equals("") || phone.equals("") || email.equals("") ) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Required fields cant't be empty");
                    errorAlert.showAndWait();
                    return;
                }
                if(new DAO().addCustomer(name,phone,address,email)){
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setHeaderText("Succesful");
                    infoAlert.setContentText("Succesfully Added New Customer");
                    infoAlert.showAndWait();
                }
                else{
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error");
                    errorAlert.setContentText("Unknown Error");
                    errorAlert.showAndWait();
                }
            }
        });

        grid.add(addCustomerLabel, 0, 0, 2, 1);

        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(phoneLabel, 0, 2);
        grid.add(phoneField, 1, 2);

        grid.add(addressLabel, 0, 3);
        grid.add(addressField, 1, 3);

        grid.add(emailLabel, 0, 4);
        grid.add(emailField, 1, 4);

        grid.add(addButton, 1, 5);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(addCustomerLabel, HPos.CENTER);

        hbox.getChildren().add(grid);
        hbox.setAlignment(Pos.CENTER);
    }
    public HBox getAddCustomer(){
        return hbox;
    }
}
