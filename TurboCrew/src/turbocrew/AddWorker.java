package turbocrew;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class AddWorker extends GridPane{
    HBox hbox;
    public AddWorker() {
        hbox = new HBox();
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label addCustomerLabel = new Label("Add Worker");
        addCustomerLabel.setFont(Font.font("Arial", 24));

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();

        Label phoneLabel = new Label("Phone");
        NumberTextField phoneField = new NumberTextField();
        
        Label addressLabel = new Label("Address");
        TextField addressField = new TextField();

        Label specializationLabel = new Label("Specialization");
        //TextField specializationField = new TextField();
        ComboBox specBox = new ComboBox();
        specBox.setPromptText("Choose");
        
        ArrayList<Service> arr = new DAO().getService();
        
        ArrayList<String> arr2 = new ArrayList();
        
        for(Service service: arr)
        {
            System.out.println("HERE!!!!");
            System.out.println(service.getServiceType());
            arr2.add(service.getServiceType());
        }
        specBox.getItems().addAll(arr2);

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();
                //String specialization = specBox.getSelectionModel().getSelectedItem().toString();
                String specialization;
                try{
                    specialization = specBox.getValue().toString();
                }catch(Exception ex){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Required fields cant't be empty");
                    errorAlert.showAndWait();
                    return;
                }
                if (name.equals("") || phone.equals("") || phone.equals("")) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Required fields cant't be empty");
                    errorAlert.showAndWait();
                    return;
                }
                if(new DAO().addWorker(name,phone,address,specialization)){
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setHeaderText("Succesful");
                    infoAlert.setContentText("Succesfully Added New Worker");
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

        grid.add(specializationLabel, 0, 4);
        grid.add(specBox, 1, 4);

        grid.add(addButton, 1, 5);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(addCustomerLabel, HPos.CENTER);

        hbox.getChildren().add(grid);
        hbox.setAlignment(Pos.CENTER);
    }
    public HBox getAddWorker(){
        return hbox;
    }
}
