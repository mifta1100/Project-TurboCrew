package turbocrew;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SelectCustomerDialog extends HBox{
    TableView table;

    public SelectCustomerDialog(AddBooking addBooking) {
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label viewCustomerLabel = new Label("Customers");
        viewCustomerLabel.setFont(Font.font("Arial", 24));

        Label searchLabel = new Label("Search");
        searchLabel.setFont(Font.font("Arial", 20));

        TextField searchField = new TextField();
        searchField.setPromptText("search");

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("All", "CustomerId", "Name", "Phone", "Address", "Email");
        combo.getSelectionModel().selectFirst();

        grid.add(viewCustomerLabel, 0, 0, 4, 1);

        grid.add(searchLabel, 0, 1, 1, 1);
        grid.add(searchField, 1, 1, 1, 1);
        grid.add(combo, 2, 1, 1, 1);

        table = new TableView();

        TableColumn customerIdColumn = new TableColumn("Customer Id");
        customerIdColumn.setCellValueFactory(new PropertyValueFactory("customerId"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn phoneColumn = new TableColumn("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory("phone"));

        TableColumn addressColumn = new TableColumn("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory("address"));

        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory("email"));

        table.getColumns().addAll(customerIdColumn,
                nameColumn,
                phoneColumn,
                addressColumn,
                emailColumn);

        FilteredList<Customer> list = new FilteredList<>(new DAO().getCustomer(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchTextLow = newValue.toLowerCase();

                String type = combo.getValue().toString();

                return validate(customer, searchTextLow, type);
            });
        });
        
        combo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(customer -> {
                String searchText = searchField.getText().toString();
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }
                String searchTextLow = searchText.toLowerCase();

                String type = newValue.toString();

                return validate(customer, searchTextLow, type);
            });
        });

        table.setItems(list);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize() + 2).add(30));

        Button selectButton = new Button("Select");
        selectButton.setOnAction((ActionEvent e) -> {
            try{
                Customer customer = (Customer)table.getSelectionModel().getSelectedItem();
                addBooking.setCustomer(customer);
                addBooking.setCustomerFields();
                if(customer == null)
                    throw new Exception();
            }catch(Exception ex){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Customer Selected");
                errorAlert.setContentText("Select a Customer");
                errorAlert.showAndWait();
                return;
            }   
            Stage stage = (Stage) SelectCustomerDialog.this.getScene().getWindow();
            stage.close();
        });
        grid.add(table, 0, 2, 4, 1);
        
        grid.add(selectButton, 0, 3, 4, 1);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(viewCustomerLabel, HPos.CENTER);
        GridPane.setHalignment(selectButton, HPos.CENTER);

        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getSelectCustomerDialog() {
        return this;
    }

    boolean validate(Customer customer, String searchTextLow, String type) {
        if (type.equals("All")) {
            if (customer.getName().toLowerCase().contains(searchTextLow)
                    || customer.getEmail().toLowerCase().contains(searchTextLow)
                    || customer.getPhone().toLowerCase().contains(searchTextLow)
                    || customer.getAddress().toLowerCase().contains(searchTextLow)
                    || Integer.toString(customer.getCustomerId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("CustomerId")) {
            if (Integer.toString(customer.getCustomerId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Name")) {
            if (customer.getName().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Phone")) {
            if (customer.getPhone().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Address")) {
            System.out.println(customer.getAddress());
            if (customer.getAddress().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Email")) {
            if (customer.getEmail().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        }
        return false;
    }
}
