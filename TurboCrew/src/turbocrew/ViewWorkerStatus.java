package turbocrew;

import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ViewWorkerStatus extends HBox{
    
    TableView table;

    public ViewWorkerStatus() {
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label viewWorkerStatusLabel = new Label("View Worker Status");
        viewWorkerStatusLabel.setFont(Font.font("Arial", 24));

        Label searchLabel = new Label("Search");
        searchLabel.setFont(Font.font("Arial", 20));

        TextField searchField = new TextField();
        searchField.setPromptText("search");

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("All", "WorkerId", "AssignmentId", "Status");
        combo.getSelectionModel().selectFirst();

        grid.add(viewWorkerStatusLabel, 0, 0, 4, 1);

        grid.add(searchLabel, 0, 1, 1, 1);
        grid.add(searchField, 1, 1, 1, 1);
        grid.add(combo, 2, 1, 1, 1);

        table = new TableView();

        TableColumn workerIdColumn = new TableColumn("Worker Id");
        workerIdColumn.setCellValueFactory(new PropertyValueFactory("workerId"));

        TableColumn assignmentIdColumn = new TableColumn("Assignment ID");
        assignmentIdColumn.setCellValueFactory(new PropertyValueFactory("assignmentId"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory("status"));

        table.getColumns().addAll(workerIdColumn,
                assignmentIdColumn,
                statusColumn);
        FilteredList<WorkerStatus> list = new FilteredList<>(new DAO().getWorkerStatus(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(workerStatus -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchTextLow = newValue.toLowerCase();

                String type = combo.getValue().toString();

                return validate(workerStatus, searchTextLow, type);
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

        grid.add(table, 0, 2, 4, 1);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(viewWorkerStatusLabel, HPos.CENTER);

        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getViewWorkerStatus() {
        return this;
    }

    boolean validate(WorkerStatus workerStatus, String searchTextLow, String type) {
        if (type.equals("All")) {
            if (Integer.toString(workerStatus.getWorkerId()).contains(searchTextLow)
                    || Integer.toString(workerStatus.getAssignmentId()).contains(searchTextLow)
                    || workerStatus.getStatus().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("CustomerId")) {
            if (Integer.toString(workerStatus.getWorkerId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("AssignmentId")) {
            if (Integer.toString(workerStatus.getAssignmentId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Status")) {
            if (workerStatus.getStatus().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        }
        return false;
    }
}
