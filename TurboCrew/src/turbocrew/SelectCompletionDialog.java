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

public class SelectCompletionDialog extends HBox{
    TableView table;

    public SelectCompletionDialog(AddCustomerPayment addCustomerPayment) {
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label viewCompletionLabel = new Label("Select Completion");
        viewCompletionLabel.setFont(Font.font("Arial", 24));

        Label searchLabel = new Label("Search");
        searchLabel.setFont(Font.font("Arial", 20));

        TextField searchField = new TextField();
        searchField.setPromptText("search");

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("All", "CompletionID", "AssignmentID", "CompletionTime", "AdditionalNotes", "OvertimeHours", "OvertimeCost", "FinalCost");
        combo.getSelectionModel().selectFirst();

        grid.add(viewCompletionLabel, 0, 0, 4, 1);

        grid.add(searchLabel, 0, 1, 1, 1);
        grid.add(searchField, 1, 1, 1, 1);
        grid.add(combo, 2, 1, 1, 1);

        table = new TableView();

        TableColumn completionIdColumn = new TableColumn("Completion ID");
        completionIdColumn.setCellValueFactory(new PropertyValueFactory("completionId"));

        TableColumn assignmentIdColumn = new TableColumn("Assignment ID");
        assignmentIdColumn.setCellValueFactory(new PropertyValueFactory("assignmentId"));

        TableColumn completionTimeColumn = new TableColumn("Completion Time");
        completionTimeColumn.setCellValueFactory(new PropertyValueFactory("completionTime"));

        TableColumn additionalNotesColumn = new TableColumn("Additional Notes");
        additionalNotesColumn.setCellValueFactory(new PropertyValueFactory("additionalNotes"));

        TableColumn overtimeHoursColumn = new TableColumn("Overtime Hours");
        overtimeHoursColumn.setCellValueFactory(new PropertyValueFactory("overtimeHours"));
        
        TableColumn overtimeCostColumn = new TableColumn("Overtime Cost");
        overtimeCostColumn.setCellValueFactory(new PropertyValueFactory("overtimeCost"));
        
        TableColumn finalCostColumn = new TableColumn("Final Cost");
        finalCostColumn.setCellValueFactory(new PropertyValueFactory("finalCost"));

        table.getColumns().addAll(completionIdColumn,
                assignmentIdColumn,
                completionTimeColumn,
                additionalNotesColumn,
                overtimeHoursColumn,
                overtimeCostColumn,
                finalCostColumn);
        FilteredList<Completion> list = new FilteredList<>(new DAO().getCompletionAltCustomer(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(completion -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchTextLow = newValue.toLowerCase();

                String type = combo.getValue().toString();

                return validate(completion, searchTextLow, type);
            });
        });

        combo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(completion -> {
                String searchText = searchField.getText().toString();
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }
                String searchTextLow = searchText.toLowerCase();

                String type = newValue.toString();

                return validate(completion, searchTextLow, type);
            });
        });

        table.setItems(list);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize() + 2).add(30));
        
        Button selectButton = new Button("Select");
        selectButton.setOnAction((ActionEvent e) -> {
            try{
                Completion completion = (Completion)table.getSelectionModel().getSelectedItem();
                addCustomerPayment.setCompletion(completion);
                addCustomerPayment.setCompletionFields();
                if(completion == null)
                    throw new Exception();
            }catch(Exception ex){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Completion Selected");
                errorAlert.setContentText("Select a Completion");
                errorAlert.showAndWait();
                return;
            }   
            Stage stage = (Stage) SelectCompletionDialog.this.getScene().getWindow();
            stage.close();
        });

        grid.add(table, 0, 2, 4, 1);
        
        grid.add(selectButton, 0, 3, 4, 1);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(viewCompletionLabel, HPos.CENTER);
        GridPane.setHalignment(selectButton, HPos.CENTER);

        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getSelectCompletionDialog() {
        return this;
    }

    boolean validate(Completion completion, String searchTextLow, String type) {
        if (type.equals("All")) {
            if (Integer.toString(completion.getCompletionId()).contains(searchTextLow)
                    || Integer.toString(completion.getAssignmentId()).contains(searchTextLow)
                    || completion.getCompletionTime().toLowerCase().contains(searchTextLow)
                    || completion.getAdditionalNotes().toLowerCase().contains(searchTextLow)
                    || Integer.toString(completion.getOvertimeHours()).contains(searchTextLow)
                    || Integer.toString(completion.getOvertimeCost()).contains(searchTextLow)
                    || Integer.toString(completion.getFinalCost()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("CompletionID")) {
            if (Integer.toString(completion.getCompletionId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("AssignmentID")) {
            if (Integer.toString(completion.getAssignmentId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("CompletionTime")) {
            if (completion.getCompletionTime().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("AdditionalNotes")) {
            if (completion.getAdditionalNotes().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("OvertimeHours")) {
            if (Integer.toString(completion.getOvertimeHours()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("OvertimeCost")) {
            if (Integer.toString(completion.getOvertimeCost()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("FinalCost")) {
            if (Integer.toString(completion.getFinalCost()).contains(searchTextLow)) {
                return true;
            }
        }
        return false;
    }
}
