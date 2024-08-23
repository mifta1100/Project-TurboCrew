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

public class ViewWorker extends HBox {

    TableView table;

    public ViewWorker() {
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label viewWorkerLabel = new Label("View Worker");
        viewWorkerLabel.setFont(Font.font("Arial", 24));

        Label searchLabel = new Label("Search");
        searchLabel.setFont(Font.font("Arial", 20));

        TextField searchField = new TextField();
        searchField.setPromptText("search");

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("All", "WorkerId", "Name", "Phone", "Address", "Specialization");
        combo.getSelectionModel().selectFirst();

        grid.add(viewWorkerLabel, 0, 0, 4, 1);

        grid.add(searchLabel, 0, 1, 1, 1);
        grid.add(searchField, 1, 1, 1, 1);
        grid.add(combo, 2, 1, 1, 1);

        table = new TableView();

        TableColumn workerIdColumn = new TableColumn("Worker Id");
        workerIdColumn.setCellValueFactory(new PropertyValueFactory("workerId"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn phoneColumn = new TableColumn("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory("phone"));

        TableColumn addressColumn = new TableColumn("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory("address"));

        TableColumn specializationColumn = new TableColumn("Specialization");
        specializationColumn.setCellValueFactory(new PropertyValueFactory("specialization"));

        table.getColumns().addAll(workerIdColumn,
                nameColumn,
                phoneColumn,
                addressColumn,
                specializationColumn);
        FilteredList<Worker> list = new FilteredList<>(new DAO().getWorker(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(worker -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchTextLow = newValue.toLowerCase();

                String type = combo.getValue().toString();

                return validate(worker, searchTextLow, type);
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
        GridPane.setHalignment(viewWorkerLabel, HPos.CENTER);

        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getViewWorker() {
        return this;
    }

    boolean validate(Worker worker, String searchTextLow, String type) {
        if (type.equals("All")) {
            if (worker.getName().toLowerCase().contains(searchTextLow)
                    || worker.getSpecialization().toLowerCase().contains(searchTextLow)
                    || worker.getPhone().toLowerCase().contains(searchTextLow)
                    || worker.getAddress().toLowerCase().contains(searchTextLow)
                    || Integer.toString(worker.getWorkerId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("CustomerId")) {
            if (Integer.toString(worker.getWorkerId()).contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Name")) {
            if (worker.getName().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Phone")) {
            if (worker.getPhone().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Address")) {
            if (worker.getAddress().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        } else if (type.equals("Specialization")) {
            if (worker.getSpecialization().toLowerCase().contains(searchTextLow)) {
                return true;
            }
        }
        return false;
    }
}
