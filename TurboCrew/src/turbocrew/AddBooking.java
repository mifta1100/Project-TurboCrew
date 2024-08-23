package turbocrew;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.sql.Timestamp;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AddBooking extends HBox {
    
    private Customer customer;
    private Service service;
    
    TextField customerIdField;
    TextField customerNameField;
    TextField customerPhoneField;
    TextField customerAddressField;
    TextField customerEmailField;
    
    
    TextField serviceIdField;
    TextField serviceTypeField;
    TextField hourlyRateField;
    

    public AddBooking() {

        GridPane grid = new GridPane();

        Label addBookingLabel = new Label("Add Booking");
        addBookingLabel.setFont(Font.font("Arial", 24));

        Button selectCustomerButton = new Button("Select Customer");
        selectCustomerButton.setOnAction(e -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("Select Customer");
            dialog.setResizable(true);
            dialog.getDialogPane().setContent(new SelectCustomerDialog(this).getSelectCustomerDialog());
            Window    window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> window.hide());
            dialog.showAndWait();
        });

        Label customerIdLabel = new Label("Customer Id");
        customerIdField = new TextField();
        customerIdField.setEditable(false);

        Label customerNameLabel = new Label("Name");
        customerNameField = new TextField();
        customerNameField.setEditable(false);

        Label customerPhoneLabel = new Label("Phone");
        customerPhoneField = new TextField();
        customerPhoneField.setEditable(false);

        Label customerAddressLabel = new Label("Address");
        customerAddressField = new TextField();
        customerAddressField.setEditable(false);

        Label customerEmailLabel = new Label("Email");
        customerEmailField = new TextField();
        customerEmailField.setEditable(false);

        Button selectServiceButton = new Button("Select Service");
        selectServiceButton.setOnAction(e -> {
            Dialog dialog = new Dialog();
            dialog.setTitle("Select Customer");
            dialog.setResizable(true);
            dialog.getDialogPane().setContent(new SelectServiceDialog(this).getSelectServiceDialog());
            Window    window = dialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> window.hide());
            dialog.showAndWait();
        });

        Label serviceIdLabel = new Label("Service Id");
        serviceIdField = new TextField();
        serviceIdField.setEditable(false);

        Label serviceTypeLabel = new Label("Service Type");
        serviceTypeField = new TextField();
        serviceTypeField.setEditable(false);

        Label hourlyRateLabel = new Label("Hourly Rate");
        hourlyRateField = new TextField();
        hourlyRateField.setEditable(false);

        Label bookingDateLabel = new Label("Booking Date");

        DatePicker picker = new DatePicker();
        picker.setValue(LocalDate.now());
        picker.setMaxSize(120, USE_PREF_SIZE);
        
        Label startTimeLabel = new Label("Start Time");

        Spinner<Integer> spin = new TimeSpinner();

        ComboBox combo = new ComboBox();
        combo.getItems().addAll("AM", "PM");
        combo.getSelectionModel().selectFirst();

        Label endTimeLabel = new Label("End Time");

        Spinner<Integer> spin2 = new TimeSpinner();

        ComboBox combo2 = new ComboBox();
        combo2.getItems().addAll("AM", "PM");
        combo2.getSelectionModel().selectFirst();

        Button addBookingButton = new Button("Add Booking");
        addBookingButton.setOnAction(e -> {
            if(customer == null){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Customer Selected");
                errorAlert.setContentText("Select a Customer");
                errorAlert.showAndWait();
                return;
            }
            if(service == null){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("No Service Selected");
                errorAlert.setContentText("Select a Service");
                errorAlert.showAndWait();
                return;
            }

            Date bookingDate = java.sql.Date.valueOf(picker.getValue());
            
            int hour = Integer.parseInt(spin.getValue().toString());
            
            if(combo.getValue().toString().equals("AM") && hour==12){
                hour = 0;
            }
            
            if(combo.getValue().toString().equals("PM") && hour!=12){
                hour+= 12;
            }
            
            System.out.println("hour = "+hour);
            
            if(hour<7 || hour>22){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Invalid Time");
                errorAlert.setContentText("Start Time has to be between 7am and 10pm");
                errorAlert.showAndWait();
                return;
            }
            
            String startTime = String.valueOf(hour).concat(":00");
            
            int hour2 = Integer.parseInt(spin2.getValue().toString());
            
            if(combo2.getValue().toString().equals("AM") && hour==12){
                hour2 = 0;
            }
            
            if(combo2.getValue().toString().equals("PM") && hour!=12){
                hour2+= 12;
            }
            
            System.out.println("hour2 = "+hour2);
            
            if(hour2<8 || hour2>23){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Invalid Time");
                errorAlert.setContentText("End Time has to be between 8am and 11pm");
                errorAlert.showAndWait();
                return;
            }
            
            String endTime = String.valueOf(hour2).concat(":00");
            
            int totalHours = hour2 - hour;
            
            if(totalHours <= 0){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Invalid Time");
                errorAlert.setContentText("End Time cant be less than or equal Start Time");
                errorAlert.showAndWait();
                return;
            }
            
            
            int totalCost = totalHours * service.getHourlyRate();
            if(new DAO().addBooking(customer.getCustomerId(), service.getServiceId(), bookingDate, startTime, endTime, totalHours, totalCost)){
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Succesful");
                infoAlert.setContentText("Succesfully Added New Booking");
                infoAlert.showAndWait();
            }
            else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR");
                errorAlert.setContentText("Unknow Error");
                errorAlert.showAndWait();
            }
        });

        grid.add(addBookingLabel, 0, 0, 4, 1);

        grid.add(customerIdLabel, 0, 1);
        grid.add(customerIdField, 1, 1);

        grid.add(customerNameLabel, 2, 1);
        grid.add(customerNameField, 3, 1);

        grid.add(customerPhoneLabel, 0, 2);
        grid.add(customerPhoneField, 1, 2);

        grid.add(customerAddressLabel, 2, 2);
        grid.add(customerAddressField, 3, 2);

        grid.add(customerEmailLabel, 0, 3);
        grid.add(customerEmailField, 1, 3);

        grid.add(selectCustomerButton, 0, 4, 4, 1);

        grid.add(serviceIdLabel, 0, 5);
        grid.add(serviceIdField, 1, 5);

        grid.add(serviceTypeLabel, 2, 5);
        grid.add(serviceTypeField, 3, 5);

        grid.add(hourlyRateLabel, 0, 6);
        grid.add(hourlyRateField, 1, 6);

        grid.add(selectServiceButton, 0, 7, 4, 1);

        grid.add(bookingDateLabel, 0, 8);
        grid.add(picker, 1, 8);
        
        
        grid.add(startTimeLabel, 0, 9);
        grid.add(spin, 1, 9);
        grid.add(combo, 2, 9);

        grid.add(endTimeLabel, 0, 10);
        grid.add(spin2, 1, 10);
        grid.add(combo2, 2, 10);

        grid.add(addBookingButton, 0, 11, 4, 1);

        GridPane.setHalignment(addBookingLabel, HPos.CENTER);
        GridPane.setHalignment(selectCustomerButton, HPos.CENTER);
        GridPane.setHalignment(selectServiceButton, HPos.CENTER);
        GridPane.setHalignment(addBookingButton, HPos.CENTER);
        
        GridPane.setHalignment(combo, HPos.CENTER);
        GridPane.setHalignment(combo2, HPos.CENTER);

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 10, 0, 10));

        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }

    HBox getAddBooking() {
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    
    
    public void setCustomerFields(){
        customerIdField.setText(Integer.toString(customer.getCustomerId()));
        customerNameField.setText(customer.getName());
        customerPhoneField.setText(customer.getPhone());
        customerAddressField.setText(customer.getAddress());
        customerEmailField.setText(customer.getEmail());
    }
    
    public void setServiceFields(){
        serviceIdField.setText(Integer.toString(service.getServiceId()));
        serviceTypeField.setText(service.getServiceType());
        hourlyRateField.setText(Integer.toString(service.getHourlyRate()));
    }
    
    
}
