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

public class SelectAssignmentDialog extends HBox{

    TableView table;

    public SelectAssignmentDialog(AddCompletion addCompletion) {
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label viewAssignmentLabel = new Label("View Assignment");
        viewAssignmentLabel.setFont(Font.font("Arial", 24));

        Label searchLabel = new Label("Search");
        searchLabel.setFont(Font.font("Arial", 20));

        TextField searchField = new TextField();
        searchField.setPromptText("search");

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("All", "AssignmentId", "BookingId", "WorkerId", "AssignmentStatus");
        combo.getSelectionModel().selectFirst();

        grid.add(viewAssignmentLabel, 0, 0, 4, 1);

        grid.add(searchLabel, 0, 1, 1, 1);
        grid.add(searchField, 1, 1, 1, 1);
        grid.add(combo, 2, 1, 1, 1);

        table = new TableView();

        TableColumn assignmentIdColumn = new TableColumn("Assignment Id");
        assignmentIdColumn.setCellValueFactory(new PropertyValueFactory("assignmentId"));

        TableColumn bookingIdColumn = new TableColumn("Booking Id");
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory("bookingId"));

        TableColumn workerIdColumn = new TableColumn("Worker Id");
        workerIdColumn.setCellValueFactory(new PropertyValueFactory("workerId"));

        TableColumn assignmentStatusColumn = new TableColumn("Assignment Status");
        assignmentStatusColumn.setCellValueFactory(new PropertyValueFactory("assignmentStatus"));

        table.getColumns().addAll(assignmentIdColumn,
                bookingIdColumn,
                workerIdColumn,
                assignmentStatusColumn);

        FilteredList<Assignment> list = new FilteredList<>(new DAO().getAssignment(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(assignment -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchTextLow = newValue.toLowerCase();

                String type = combo.getValue().toString();

                return validate(assignment, searchTextLow, type);
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
                Assignment assignment = (Assignment)table.getSelectionModel().getSelectedItem();
                addCompletion.setAssignment(assignment);
                addCompletion.setAssignmentFields();
                if(assignment == null)
                    throw new Exception();
            }catch(Exception ex){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Assignment Selected");
                errorAlert.setContentText("Select a Assignment");
                errorAlert.showAndWait();
                return;
            }   
            Stage stage = (Stage) SelectAssignmentDialog.this.getScene().getWindow();
            stage.close();
        });
        
        grid.add(table, 0, 2, 4, 1);
        
        grid.add(selectButton, 0, 3, 4, 1);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(viewAssignmentLabel, HPos.CENTER);

        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getSelectAssignmentDialog() {
        return this;
    }

    boolean validate(Assignment assignment, String searchTextLow, String type) {
        if (type.equals("All")) {
            if (Integer.toString(assignment.getBookingId()).contains(searchTextLow) 
                    || Integer.toString(assignment.getBookingId()).contains(searchTextLow) 
                    || Integer.toString(assignment.getWorkerId()).contains(searchTextLow)
                    || assignment.getAssignmentStatus().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("AssignmentId")) {
            if (Integer.toString(assignment.getBookingId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("BookingId")) {
            if (Integer.toString(assignment.getBookingId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("WorkerId")) {
            if (Integer.toString(assignment.getWorkerId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("AssignmentStatus")) {
            if (assignment.getAssignmentStatus().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        }
        return false;
    }
}
