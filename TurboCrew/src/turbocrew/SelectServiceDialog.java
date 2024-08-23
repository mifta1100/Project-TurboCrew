package turbocrew;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SelectServiceDialog extends HBox{
    TableView table;
    public SelectServiceDialog(AddBooking addBooking){
        VBox vbox = new VBox();
        
        Label viewServiceLabel = new Label("View Service");
        viewServiceLabel.setFont(Font.font("Arial", 24));
        
        table = new TableView();
        
        TableColumn serviceIdColumn = new TableColumn("Service Id");
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory("serviceId"));
        
        TableColumn serviceTypeColumn = new TableColumn("Service Type");
        serviceTypeColumn.setCellValueFactory(new PropertyValueFactory("serviceType"));
        
        TableColumn hourlyRateColumn = new TableColumn("Hourly Rate");
        hourlyRateColumn.setCellValueFactory(new PropertyValueFactory("hourlyRate"));
        
        table.getColumns().addAll(serviceIdColumn,serviceTypeColumn,hourlyRateColumn);
        
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
        
        Button selectButton = new Button("Select");
        selectButton.setOnAction((ActionEvent e) -> {
            try{
                Service service = (Service)table.getSelectionModel().getSelectedItem();
                addBooking.setService(service);
                addBooking.setServiceFields();
                if(service == null)
                    throw new Exception();
            }catch(Exception ex){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Service Selected");
                errorAlert.setContentText("Select a Service");
                errorAlert.showAndWait();
                return;
            }   
            Stage stage = (Stage) SelectServiceDialog.this.getScene().getWindow();
            stage.close();
        });
        
        
        HBox hbox2 = new HBox(viewServiceLabel);
        hbox2.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(table);
        
        HBox hbox3 = new HBox(selectButton);
        hbox3.setAlignment(Pos.CENTER);
        
        vbox.getChildren().add(hbox3);
        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(vbox);
        this.setAlignment(Pos.CENTER);
        
        table.getItems().addAll(new DAO().getService());
    }
    HBox getSelectServiceDialog(){
        return this;
    }
}
