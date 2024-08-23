package turbocrew;

import java.sql.Date;
import java.time.LocalDate;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class AddCompletion extends HBox {

    private Assignment assignment;

    TextField assignmentIdField;
    TextField bookingIdField;
    TextField workerIdField;
    TextField additionalNotesField;

    public AddCompletion() {

        GridPane grid = new GridPane();

        Label addCompletionLabel = new Label("Add Completion");
        addCompletionLabel.setFont(Font.font("Arial", 24));

        Label assignmentIdLabel = new Label("Assignment ID");
        assignmentIdField = new TextField();
        assignmentIdField.setEditable(false);

        Label bookingIdLabel = new Label("Booking ID");
        bookingIdField = new TextField();
        bookingIdField.setEditable(false);

        Label workerIdLabel = new Label("Worker ID");
        workerIdField = new TextField();
        workerIdField.setEditable(false);

        Button selectAssignmentButton = new Button("Select Assignment");
        selectAssignmentButton.setOnAction(e -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("Select Assignment");
            dialog.setResizable(true);
            dialog.getDialogPane().setContent(new SelectAssignmentDialog(this).getSelectAssignmentDialog());
            Window    window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> window.hide());
            dialog.showAndWait();
        });

        Label overtimeHoursLabel = new Label("Overtime Hours");

        Spinner<Integer> spin = new TimeSpinner();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15);
        spin.setValueFactory(valueFactory);

        Label additionalNotesLabel = new Label("Additional Notes");
        additionalNotesField = new TextField();

        Button addCompletionButton = new Button("Add Completion");
        addCompletionButton.setOnAction(e -> {
            if (assignment == null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Assignment Selected");
                errorAlert.setContentText("Select a Assignment");
                errorAlert.showAndWait();
                return;
            }

            int overtimeHours = Integer.parseInt(spin.getValue().toString());

            Booking booking = new DAO().getBooking(assignment.getBookingId());

            int endTime = Integer.parseInt(booking.getEndTime().substring(0, 2));

            if ((overtimeHours + endTime) > 23) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Invalid Time");
                errorAlert.setContentText("Overtime hour exceeds service time\nMax overtime hour for this assignment can be " + (23 - endTime));
                errorAlert.showAndWait();
                return;
            }

            Service service = new DAO().getService(booking.getServiceId());

            String completionTime = String.valueOf(overtimeHours + endTime).concat(":00");

            int overtimeCost = overtimeHours * (service.getHourlyRate());

            int finalCost = booking.getTotalCost() + overtimeCost;

            int totalCost = booking.getTotalCost() + overtimeCost;
            if (new DAO().addCompletion(assignment.getAssignmentId(), completionTime, additionalNotesField.getText().toString(), overtimeHours, overtimeCost, finalCost)) {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Succesful");
                infoAlert.setContentText("Succesfully Added New Completion");
                infoAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR");
                errorAlert.setContentText("Unknow Error");
                errorAlert.showAndWait();
            }
        });

        grid.add(addCompletionLabel, 0, 0, 4, 1);

        grid.add(assignmentIdLabel, 0, 1);
        grid.add(assignmentIdField, 1, 1);

        grid.add(bookingIdLabel, 2, 1);
        grid.add(bookingIdField, 3, 1);

        grid.add(workerIdLabel, 0, 2);
        grid.add(workerIdField, 1, 2);

        grid.add(selectAssignmentButton, 0, 3, 4, 1);

        grid.add(overtimeHoursLabel, 0, 4);
        grid.add(spin, 1, 4);

        grid.add(additionalNotesLabel, 0, 5);
        grid.add(additionalNotesField, 1, 5);

        grid.add(addCompletionButton, 0, 6, 6, 1);

        GridPane.setHalignment(addCompletionLabel, HPos.CENTER);
        GridPane.setHalignment(selectAssignmentButton, HPos.CENTER);
        GridPane.setHalignment(addCompletionButton, HPos.CENTER);

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getAddCompletion() {
        return this;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void setAssignmentFields(){
        
        assignmentIdField.setText(Integer.toString(assignment.getAssignmentId()));
        bookingIdField.setText(Integer.toString(assignment.getBookingId()));
        workerIdField.setText(Integer.toString(assignment.getWorkerId()));
    }
}

