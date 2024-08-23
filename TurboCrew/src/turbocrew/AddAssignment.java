package turbocrew;

import java.sql.Date;
import java.time.LocalDate;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class AddAssignment extends HBox {

    private Booking booking;
    private Worker worker;

    TextField bookingIdField;
    TextField customerIdField;
    TextField serviceIdField;
    TextField bookingDateField;
    TextField startTimeField;
    TextField endTimeField;
    TextField totalHoursField;
    TextField totalCostField;

    TextField workerIdField;
    TextField nameField;
    TextField phoneField;
    TextField addressField;
    TextField specializationField;

    public AddAssignment() {

        GridPane grid = new GridPane();

        Label addAssignmentLabel = new Label("Add Assignment");
        addAssignmentLabel.setFont(Font.font("Arial", 24));

        Label bookingIdLabel = new Label("Booking Id");
        bookingIdField = new TextField();
        bookingIdField.setEditable(false);

        Label customerIdLabel = new Label("Customer ID");
        customerIdField = new TextField();
        customerIdField.setEditable(false);

        Label serviceIdLabel = new Label("Service ID");
        serviceIdField = new TextField();
        serviceIdField.setEditable(false);

        Label bookingDateLabel = new Label("Booking Date");
        bookingDateField = new TextField();
        bookingDateField.setEditable(false);

        Label startTimeLabel = new Label("Start Time");
        startTimeField = new TextField();
        startTimeField.setEditable(false);

        Label endTimeLabel = new Label("End Time");
        endTimeField = new TextField();
        endTimeField.setEditable(false);

        Label totalHoursLabel = new Label("Total Hours");
        totalHoursField = new TextField();
        totalHoursField.setEditable(false);

        Label totalCostLabel = new Label("Total Cost");
        totalCostField = new TextField();
        totalCostField.setEditable(false);

        Button selectBookingButton = new Button("Select Booking");
        selectBookingButton.setOnAction(e -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("Select Booking");
            dialog.setResizable(true);
            dialog.getDialogPane().setContent(new SelectBookingDialog(this).getSelectBookingDialog());
            Window    window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> window.hide());
            dialog.showAndWait();
        });

        Label workerIdLabel = new Label("Worker ID");
        workerIdField = new TextField();

        Label nameLabel = new Label("Name");
        nameField = new TextField();

        Label phoneLabel = new Label("Phone");
        phoneField = new NumberTextField();

        Label addressLabel = new Label("Address");
        addressField = new TextField();

        Label specializationLabel = new Label("Specialization");
        specializationField = new TextField();

        Button selectWorkerButton = new Button("Select Worker");
        selectWorkerButton.setOnAction(e -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("Select Customer");
            dialog.setResizable(true);
            dialog.getDialogPane().setContent(new SelectWorkerDialog(this).getSelectWorkerDialog());
            Window    window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> window.hide());   
            dialog.showAndWait();
        });

        Button addAssignmentButton = new Button("Add Assignment");
        addAssignmentButton.setOnAction(e -> {
            if(booking == null){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Booking Selected");
                errorAlert.setContentText("Select a Booking");
                errorAlert.showAndWait();
                return;
            }
            if(worker == null){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Worker Selected");
                errorAlert.setContentText("Select a Worker");
                errorAlert.showAndWait();
                return;
            }
            if(new DAO().addAssignment(booking.getBookingId(),worker.getWorkerId())){
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Succesful");
                infoAlert.setContentText("Succesfully Added New Assignment");
                infoAlert.showAndWait();
            }
            else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR");
                errorAlert.setContentText("Unknow Error");
                errorAlert.showAndWait();
            }
        });

        grid.add(addAssignmentLabel, 0, 0, 4, 1);

        grid.add(bookingIdLabel, 0, 1);
        grid.add(bookingIdField, 1, 1);

        grid.add(customerIdLabel, 2, 1);
        grid.add(customerIdField, 3, 1);

        grid.add(serviceIdLabel, 0, 2);
        grid.add(serviceIdField, 1, 2);

        grid.add(bookingDateLabel, 2, 2);
        grid.add(bookingDateField, 3, 2);

        grid.add(startTimeLabel, 0, 3);
        grid.add(startTimeField, 1, 3);

        grid.add(endTimeLabel, 2, 3);
        grid.add(endTimeField, 3, 3);
        
        grid.add(totalHoursLabel, 0, 4);
        grid.add(totalHoursField, 1, 4);

        grid.add(totalCostLabel, 2, 4);
        grid.add(totalCostField, 3, 4);
        

        grid.add(selectBookingButton, 0, 5, 4, 1);
        
        
        
        grid.add(workerIdLabel, 0, 6);
        grid.add(workerIdField, 1, 6);

        grid.add(nameLabel, 2, 6);
        grid.add(nameField, 3, 6);

        grid.add(phoneLabel, 0, 7);
        grid.add(phoneField, 1, 7);

        grid.add(addressLabel, 2, 7);
        grid.add(addressField, 3, 7);
        
        grid.add(specializationLabel, 0, 8);
        grid.add(specializationField, 1, 8);
        
        
        grid.add(selectWorkerButton, 0, 9, 4, 1);

        
        
        grid.add(addAssignmentButton, 0, 10, 4, 1);

        GridPane.setHalignment(addAssignmentLabel, HPos.CENTER);
        GridPane.setHalignment(selectBookingButton, HPos.CENTER);
        GridPane.setHalignment(selectWorkerButton, HPos.CENTER);
        GridPane.setHalignment(addAssignmentButton, HPos.CENTER);

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getAddAssignment() {
        return this;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setBookingFields() {
        bookingIdField.setText(Integer.toString(booking.getBookingId()));
        customerIdField.setText(Integer.toString(booking.getCustomerId()));
        serviceIdField.setText(Integer.toString(booking.getServiceId()));
        bookingDateField.setText(booking.getBookingDate().toString());
        startTimeField.setText(booking.getStartTime());
        endTimeField.setText(booking.getEndTime());
        totalHoursField.setText(Integer.toString(booking.getTotalHours()));
        totalCostField.setText(Integer.toString(booking.getTotalCost()));
    }

    public void setWorkerFields() {
        workerIdField.setText(Integer.toString(worker.getWorkerId()));
        nameField.setText(worker.getName());
        phoneField.setText(worker.getPhone());
        addressField.setText(worker.getAddress());
        specializationField.setText(worker.getSpecialization());
    }
}
