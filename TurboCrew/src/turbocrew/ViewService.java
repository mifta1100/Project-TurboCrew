package turbocrew;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ViewService extends HBox{
    TableView table;
    public ViewService(){
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
        
        HBox hbox2 = new HBox(viewServiceLabel);
        hbox2.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(table);
        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(vbox);
        this.setAlignment(Pos.CENTER);
        
        table.getItems().addAll(new DAO().getService());
    }
    HBox getViewServiceView(){
        return this;
    }
}
