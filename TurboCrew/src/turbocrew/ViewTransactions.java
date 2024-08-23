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

public class ViewTransactions extends HBox{
    TableView table;

    public ViewTransactions() {
        GridPane grid = new GridPane();

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        Label viewTransactionsLabel = new Label("View Transactions");
        viewTransactionsLabel.setFont(Font.font("Arial", 24));

        Label searchLabel = new Label("Search");
        searchLabel.setFont(Font.font("Arial", 20));

        TextField searchField = new TextField();
        searchField.setPromptText("search");

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("All", "AssignmentId", "BookingId", "WorkerId", "AssignmentStatus");
        combo.getSelectionModel().selectFirst();

        grid.add(viewTransactionsLabel, 0, 0, 4, 1);

        grid.add(searchLabel, 0, 1, 1, 1);
        grid.add(searchField, 1, 1, 1, 1);
        grid.add(combo, 2, 1, 1, 1);

        table = new TableView();

        TableColumn transactionIdColumn = new TableColumn("Transaction Id");
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory("transactionId"));

        TableColumn completionIdColumn = new TableColumn("Completion Id");
        completionIdColumn.setCellValueFactory(new PropertyValueFactory("completionId"));

        TableColumn transactionTypeColumn = new TableColumn("Transaction Type");
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory("transactionType"));

        TableColumn paymentIdColumn = new TableColumn("Payment Id");
        paymentIdColumn.setCellValueFactory(new PropertyValueFactory("paymentId"));
        
        TableColumn amountColumn = new TableColumn("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory("amount"));
        
        TableColumn paymentDateColumn = new TableColumn("Payment Date");
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory("paymentDate"));

        table.getColumns().addAll(transactionIdColumn,
                completionIdColumn,
                transactionTypeColumn,
                paymentIdColumn,
                amountColumn,
                paymentDateColumn);

        FilteredList<Transactions> list = new FilteredList<>(new DAO().getTransactions(), b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(transactions -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchTextLow = newValue.toLowerCase();

                String type = combo.getValue().toString();

                return validate(transactions, searchTextLow, type);
            });
        });
        
        combo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            list.setPredicate(transactions -> {
                String searchText = searchField.getText().toString();
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }
                String searchTextLow = searchText.toLowerCase();

                String type = newValue.toString();

                return validate(transactions, searchTextLow, type);
            });
        });

        table.setItems(list);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize() + 2).add(30));

        grid.add(table, 0, 2, 4, 1);

        grid.setGridLinesVisible(false);
        GridPane.setHalignment(viewTransactionsLabel, HPos.CENTER);

        this.setPadding(new Insets(20, 10, 0, 10));
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getViewTransactions() {
        return this;
    }

    boolean validate(Transactions transactions, String searchTextLow, String type) {
//        if (type.equals("All")) {
//            if (Integer.toString(assignment.getBookingId()).contains(searchTextLow) 
//                    || Integer.toString(assignment.getBookingId()).contains(searchTextLow) 
//                    || Integer.toString(assignment.getWorkerId()).contains(searchTextLow)
//                    || assignment.getAssignmentStatus().toLowerCase().contains(searchTextLow)) {
//                return true;
//            }
//        } else if (type.equals("AssignmentId")) {
//            if (Integer.toString(assignment.getBookingId()).contains(searchTextLow)) {
//                return true;
//            }
//        } else if (type.equals("BookingId")) {
//            if (Integer.toString(assignment.getBookingId()).contains(searchTextLow)) {
//                return true;
//            }
//        } else if (type.equals("WorkerId")) {
//            if (Integer.toString(assignment.getWorkerId()).contains(searchTextLow)) {
//                return true;
//            }
//        } else if (type.equals("AssignmentStatus")) {
//            if (assignment.getAssignmentStatus().toLowerCase().contains(searchTextLow)) {
//                return true;
//            }
//        }
        return false;
    }
}
