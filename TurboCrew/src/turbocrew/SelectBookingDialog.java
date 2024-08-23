/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turbocrew;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Tanvir Raiyan
 */
public class SelectBookingDialog extends HBox{
    TableView table;

    public SelectBookingDialog(AddAssignment addAssignment) {
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label viewBookingLabel = new Label("View Booking");
        viewBookingLabel.setFont(Font.font("Arial", 24));

        Label searchLabel = new Label("Search");
        searchLabel.setFont(Font.font("Arial", 20));

        TextField searchField = new TextField();
        searchField.setPromptText("search");

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("All", "BookingId", "CustomerId", "ServiceId", "BookingDate");
        combo.getSelectionModel().selectFirst();

        grid.add(viewBookingLabel, 0, 0, 4, 1);

        grid.add(searchLabel, 0, 1, 1, 1);
        grid.add(searchField, 1, 1, 1, 1);
        grid.add(combo, 2, 1, 1, 1);

        table = new TableView();

        TableColumn bookingIdIdColumn = new TableColumn("Booking Id");
        bookingIdIdColumn.setCellValueFactory(new PropertyValueFactory("bookingId"));

        TableColumn customerIdColumn = new TableColumn("Customer Id");
        customerIdColumn.setCellValueFactory(new PropertyValueFactory("customerId"));

        TableColumn serviceIdColumn = new TableColumn("Service Id");
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory("serviceId"));

        TableColumn bookingDateColumn = new TableColumn("Booking Date");
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory("bookingDate"));

        TableColumn startTimeColumn = new TableColumn("Start Time");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory("startTime"));
        
        TableColumn endTimeColumn = new TableColumn("End Time");
        endTimeColumn.setCellValueFactory(new PropertyValueFactory("endTime"));
        
        TableColumn totalHoursColumn = new TableColumn("Total Hours");
        totalHoursColumn.setCellValueFactory(new PropertyValueFactory("totalHours"));
        
        TableColumn totalCostColumn = new TableColumn("Total Cost");
        totalCostColumn.setCellValueFactory(new PropertyValueFactory("totalCost"));
        
        TableColumn bookingStatusColumn = new TableColumn("Booking Status");
        bookingStatusColumn.setCellValueFactory(new PropertyValueFactory("bookingStatus"));

        table.getColumns().addAll(bookingIdIdColumn,
                customerIdColumn,
                serviceIdColumn,
                bookingDateColumn,
                startTimeColumn,
                endTimeColumn,
                totalHoursColumn,
                totalCostColumn,
                bookingStatusColumn);

        FilteredList<Booking> list = new FilteredList<>(new DAO().getBookingAlt(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(booking -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchTextLow = newValue.toLowerCase();

                String type = combo.getValue().toString();

                return validate(booking, searchTextLow, type);
            });
        });
        
        combo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(booking -> {
                String searchText = searchField.getText().toString();
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }
                String searchTextLow = searchText.toLowerCase();

                String type = newValue.toString();

                return validate(booking, searchTextLow, type);
            });
        });

        table.setItems(list);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize() + 2).add(30));

        Button selectButton = new Button("Select");
        selectButton.setOnAction((ActionEvent e) -> {
            try{
                Booking booking = (Booking)table.getSelectionModel().getSelectedItem();
                addAssignment.setBooking(booking);
                addAssignment.setBookingFields();
                if(booking == null)
                    throw new Exception();
            }catch(Exception ex){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Booking Selected");
                errorAlert.setContentText("Select a Booking");
                errorAlert.showAndWait();
                return;
            }   
            Stage stage = (Stage) SelectBookingDialog.this.getScene().getWindow();
            stage.close();
        });
        
        grid.add(table, 0, 2, 4, 1);
        
        grid.add(selectButton, 0, 3, 4, 1);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(viewBookingLabel, HPos.CENTER);
        GridPane.setHalignment(selectButton, HPos.CENTER);

        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getSelectBookingDialog() {
        return this;
    }

    boolean validate(Booking booking, String searchTextLow, String type) {
        if (type.equals("All")) {
            if (Integer.toString(booking.getBookingId()).contains(searchTextLow) 
                    || Integer.toString(booking.getCustomerId()).contains(searchTextLow) 
                    || Integer.toString(booking.getServiceId()).contains(searchTextLow)
                    || booking.getStartTime().toLowerCase().contains(searchTextLow)
                    || booking.getEndTime().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("BookingId")) {
            if (Integer.toString(booking.getBookingId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("CustomerId")) {
            if (Integer.toString(booking.getCustomerId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("ServiceId")) {
            if (Integer.toString(booking.getServiceId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("BookingDate")) {
            if (booking.getBookingDate().toString().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        }
        return false;
    }
}
