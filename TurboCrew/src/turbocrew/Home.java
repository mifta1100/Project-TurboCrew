package turbocrew;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Home extends Stage {
    DAO dao;
    public Home() {
        dao = new DAO();
        this.setWidth(1280);
        this.setHeight(720);
        this.setMinWidth(640);
        this.setMinHeight(360);

        BorderPane mainPanel = new BorderPane();

        Menu serviceMenu = new Menu("Service");
        Menu workerMenu = new Menu("Worker");
        Menu bookingMenu = new Menu("Booking");
        Menu assignmentMenu = new Menu("Assignment");
        Menu customerMenu = new Menu("Customer");
        Menu completionMenu = new Menu("Completion");
        Menu paymentMenu = new Menu("Payment");
        Menu transactionMenu = new Menu("Transaction");

        //Service Menu Items
        MenuItem addServiceItem = new MenuItem("Add Service");
        addServiceItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddService().getAddService());
            }
        });

        MenuItem viewServiceItem = new MenuItem("View Service");
        viewServiceItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new ViewService().getViewServiceView());
            }
        });

        serviceMenu.getItems().add(addServiceItem);
        serviceMenu.getItems().add(viewServiceItem);

        //Worker Menu Item
        MenuItem addWorkerItem = new MenuItem("Add Worker");
        addWorkerItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddWorker().getAddWorker());
            }
        });
        
        MenuItem viewWorkerItem = new MenuItem("View Worker Details");
        viewWorkerItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new ViewWorker().getViewWorker());
            }
        });
//        MenuItem viewWorkerStatusItem = new MenuItem("View Worker Status");
//        viewWorkerStatusItem.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                mainPanel.setCenter(new ViewWorkerStatus().getViewWorkerStatus());
//            }
//        });

        workerMenu.getItems().add(addWorkerItem);
        workerMenu.getItems().add(viewWorkerItem);
//        workerMenu.getItems().add(viewWorkerStatusItem);

        //Customer Menu Item
        MenuItem addCustomerItem = new MenuItem("Add Customer");
        addCustomerItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddCustomer().getAddCustomer());
            }
        });
        MenuItem viewCustomerItem = new MenuItem("View Customer");
        viewCustomerItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new ViewCustomer().getViewCustomer());
            }
        });

        customerMenu.getItems().add(addCustomerItem);
        customerMenu.getItems().add(viewCustomerItem);

        //Booking Menu Item
        MenuItem addBookingItem = new MenuItem("Add Booking");
        addBookingItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddBooking().getAddBooking());
            }
        });
        
        MenuItem viewBookingItem = new MenuItem("View Booking");
        viewBookingItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new ViewBooking().getViewBooking());
            }
        });

        bookingMenu.getItems().add(addBookingItem);
        bookingMenu.getItems().add(viewBookingItem);

        //Assignment Menu Item
        MenuItem addAssignmentItem = new MenuItem("Add Assignment");
        addAssignmentItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddAssignment().getAddAssignment());
            }
        });
        MenuItem viewAssignmentItem = new MenuItem("View Assignment");
        viewAssignmentItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new ViewAssignment().getViewAssignment());
            }
        });
        
        assignmentMenu.getItems().add(addAssignmentItem);
        assignmentMenu.getItems().add(viewAssignmentItem);
        
        //Completion Menu Item
        MenuItem addCompletionItem = new MenuItem("Add Completion");
        addCompletionItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddCompletion().getAddCompletion());
            }
        });
        MenuItem viewCompletionItem = new MenuItem("View Completion");
        viewCompletionItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new ViewCompletion().getViewCompletion());
            }
        });
        
        completionMenu.getItems().addAll(addCompletionItem,viewCompletionItem);
        
        //Payment Menu Item
        MenuItem addCustomerPaymentItem = new MenuItem("Add Customer Payment");
        addCustomerPaymentItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddCustomerPayment().getAddCustomerPayment());
            }
        });
        MenuItem addWorkerPaymentItem = new MenuItem("Add Worker Payment");
        addWorkerPaymentItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new AddWorkerPayment().getAddWorkerPayment());
            }
        });
        
        paymentMenu.getItems().addAll(addCustomerPaymentItem,addWorkerPaymentItem);
        
        //Payment Menu Item
        MenuItem viewTransactionItem = new MenuItem("View Transactions");
        viewTransactionItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                mainPanel.setCenter(new ViewTransactions().getViewTransactions());
            }
        });
        
        transactionMenu.getItems().addAll(viewTransactionItem);
        
        
        
        
        //Creating Menubar and adding all menubar items

        MenuBar menuBar = new MenuBar();
        
        menuBar.getMenus().addAll(serviceMenu,customerMenu,workerMenu,bookingMenu,assignmentMenu,completionMenu,paymentMenu,transactionMenu);

        //sk;;
       
        mainPanel.setTop(menuBar);

        Label text = new Label("Hello TanRai");

        text.setFont(Font.font("Arial", 24));

        mainPanel.setCenter(text);

        Scene scene = new Scene(mainPanel);
        this.setScene(scene);
        this.show();
    }
}
