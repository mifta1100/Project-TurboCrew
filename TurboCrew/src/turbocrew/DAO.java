package turbocrew;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAO {
    private Connection myConn;
    private Statement myStmt;
    private PreparedStatement preStmt = null;
    private ResultSet myRs = null;
    int rowsAff;
    public DAO(){
        try{
            myConn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=TURBOCREW;selectMethod=cursor", "sa", "123456");
            System.out.println("DB connection succesful");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean userCheck(String user_name){
        try{
            preStmt = myConn.prepareStatement("SELECT TOP 1 * FROM admin WHERE user_name = ?");
            preStmt.setString(1,user_name);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean loginCheck(String u,String p){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT TOP 1 * FROM admin WHERE user_name = ? AND password = ?");
            preStmt.setString(1,u);
            preStmt.setString(2,p);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean insertUser(String u,String p){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO admin(user_name,password) VALUES(?,?)");
            preStmt.setString(1,u);
            preStmt.setString(2,p);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean addService(String serviceType, int hourlyRate){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO service(servicetype,hourlyrate) VALUES(?,?)");
            preStmt.setString(1,serviceType);
            preStmt.setInt(2,hourlyRate);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean addCustomer(String name, String phone, String address, String email){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO customer(name,phone,address,email) VALUES(?,?,?,?)");
            preStmt.setString(1,name);
            preStmt.setString(2,phone);
            preStmt.setString(3,address);
            preStmt.setString(4,email);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    
    public boolean addWorker(String name, String phone, String address, String specialization){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO worker(name,phone,address,specialization) VALUES(?,?,?,?)");
            preStmt.setString(1,name);
            preStmt.setString(2,phone);
            preStmt.setString(3,address);
            preStmt.setString(4,specialization);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public ArrayList<Service> getService(){
        ArrayList<Service> service = new ArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM service");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                service.add(new Service(myRs.getInt("serviceID"),myRs.getString("serviceType"),myRs.getInt("hourlyRate")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return service;
    }
    public ObservableList<Customer> getCustomer(){
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM customer");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                customer.add(new Customer(myRs.getInt("customerID"),myRs.getString("name"),myRs.getString("phone"),myRs.getString("address"),myRs.getString("email")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return customer;
    }
    public ObservableList<Worker> getWorker(){
        ObservableList<Worker> worker = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM worker");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                worker.add(new Worker(myRs.getInt("workerID"),myRs.getString("name"),myRs.getString("phone"),myRs.getString("address"),myRs.getString("specialization")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return worker;
    }
    public boolean addBooking(int customerId, int serviceId,Date BookingDate, String startTime, String endTime, int totalHours, int totalCost){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO Booking(CustomerID,ServiceID,BookingDate,StartTime,EndTime,TotalHours,TotalCost) VALUES(?,?,?,?,?,?,?)");
            preStmt.setInt(1,customerId);
            preStmt.setInt(2,serviceId);
            preStmt.setDate(3,BookingDate);
            preStmt.setString(4,startTime);
            preStmt.setString(5,endTime);
            preStmt.setInt(6,totalHours);
            preStmt.setInt(7,totalCost);
            
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public ObservableList<Booking> getBooking(){
        ObservableList<Booking> booking = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM booking");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                booking.add(new Booking(myRs.getInt("bookingID"),myRs.getInt("customerID"),myRs.getInt("serviceID"),myRs.getDate("BookingDate"),myRs.getString("StartTime"),myRs.getString("EndTime"),myRs.getInt("TotalHours"),myRs.getInt("TotalCost"),myRs.getString("BookingStatus")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return booking;
    }
    public ObservableList<Booking> getBookingAlt(){
        ObservableList<Booking> booking = FXCollections.observableArrayList();
        try{
            System.out.println("ACCESED ALT");
            preStmt = myConn.prepareStatement("SELECT * FROM booking where BookingStatus = 'Unassigned'");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                booking.add(new Booking(myRs.getInt("bookingID"),myRs.getInt("customerID"),myRs.getInt("serviceID"),myRs.getDate("BookingDate"),myRs.getString("StartTime"),myRs.getString("EndTime"),myRs.getInt("TotalHours"),myRs.getInt("TotalCost"),myRs.getString("BookingStatus")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return booking;
    }
    public boolean addAssignment(int bookingId, int workerId){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO Assignment(BookingID,WorkerID) VALUES(?,?)");
            preStmt.setInt(1,bookingId);
            preStmt.setInt(2,workerId);
            rowsAff = preStmt.executeUpdate();
            
            preStmt = myConn.prepareStatement("UPDATE Booking SET BookingStatus = 'Assigned' where BookingID = ?");
            preStmt.setInt(1,bookingId);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public ObservableList<WorkerStatus> getWorkerStatus(){
        ObservableList<WorkerStatus> workerStatus = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM worker");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                workerStatus.add(new WorkerStatus(myRs.getInt("workerID"),myRs.getInt("assignmentID"),myRs.getString("status")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return workerStatus;
    }
    public ObservableList<Assignment> getAssignment(){
        ObservableList<Assignment> assignment = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM Assignment");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                assignment.add(new Assignment(myRs.getInt("AssignmentID"),myRs.getInt("BookingID"),myRs.getInt("WorkerID"),myRs.getString("AssignmentStatus")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return assignment;
    }
    
    public Booking getBooking(int bookingId){
        Booking booking = null;
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM Booking where BookingID = ?");
            preStmt.setInt(1,bookingId);
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                booking = new Booking(myRs.getInt("bookingID"),myRs.getInt("customerID"),myRs.getInt("serviceID"),myRs.getDate("BookingDate"),myRs.getString("StartTime"),myRs.getString("EndTime"),myRs.getInt("TotalHours"),myRs.getInt("TotalCost"),myRs.getString("BookingStatus"));
                break;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return booking;
    }
    public Service getService(int serviceId){
        Service service = null;
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM Service where ServiceId = ?");
            preStmt.setInt(1,serviceId);
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                service = new Service(myRs.getInt("serviceID"),myRs.getString("serviceType"),myRs.getInt("hourlyRate"));
                break;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return service;
    }
    public boolean addCompletion(int assignmentID, String completionTime, String additionalNotes, int overtimeHours, int overtimeCost, int finalCost){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO Completion(AssignmentID,CompletionTime,AdditionalNotes,OvertimeHours,OvertimeCost,FinalCost) VALUES(?,?,?,?,?,?)");
            preStmt.setInt(1,assignmentID);
            preStmt.setString(2,completionTime);
            preStmt.setString(3,additionalNotes);
            preStmt.setInt(4,overtimeHours);
            preStmt.setInt(5,overtimeCost);
            preStmt.setInt(6,finalCost);
            
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public ObservableList<Completion> getCompletion(){
        ObservableList<Completion> completion = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM Completion");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                completion.add(new Completion(myRs.getInt("CompletionID"),myRs.getInt("AssignmentID"),myRs.getString("CompletionTime"),myRs.getString("AdditionalNotes"),myRs.getInt("OvertimeHours"),myRs.getInt("OvertimeCost"),myRs.getInt("FinalCost")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return completion;
    }
    public ObservableList<Completion> getCompletionAltCustomer(){
        ObservableList<Completion> completion = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM Completion WHERE CompletionID NOT IN (SELECT CompletionID FROM CustomerPayment)");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                completion.add(new Completion(myRs.getInt("CompletionID"),myRs.getInt("AssignmentID"),myRs.getString("CompletionTime"),myRs.getString("AdditionalNotes"),myRs.getInt("OvertimeHours"),myRs.getInt("OvertimeCost"),myRs.getInt("FinalCost")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return completion;
    }
    public ObservableList<Completion> getCompletionAltWorker(){
        ObservableList<Completion> completion = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM Completion WHERE CompletionID NOT IN (SELECT CompletionID FROM WorkerPayment)");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                completion.add(new Completion(myRs.getInt("CompletionID"),myRs.getInt("AssignmentID"),myRs.getString("CompletionTime"),myRs.getString("AdditionalNotes"),myRs.getInt("OvertimeHours"),myRs.getInt("OvertimeCost"),myRs.getInt("FinalCost")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return completion;
    }
    public boolean addCustomerPayment(int completionID, Date paymentDate){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO CustomerPayment(CompletionID,PaymentDate,PaymentStatus) VALUES(?,?,?)");
            preStmt.setInt(1,completionID);
            preStmt.setDate(2,paymentDate);
            preStmt.setString(3,"Paid");
            
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean addWorkerPayment(int completionID, Date paymentDate){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO WorkerPayment(CompletionID,PaymentDate,PaymentStatus) VALUES(?,?,?)");
            preStmt.setInt(1,completionID);
            preStmt.setDate(2,paymentDate);
            preStmt.setString(3,"Paid");
            
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public ObservableList<Transactions> getTransactions(){
        ObservableList<Transactions> transactions = FXCollections.observableArrayList();
        try{
            preStmt = myConn.prepareStatement("SELECT TransactionID,CustomerPayment.CompletionID,TransactionType,Transactions.CustomerPaymentID as 'PaymentID',Completion.FinalCost as 'Amount',PaymentDate FROM Transactions " +
"INNER JOIN CustomerPayment ON Transactions.CustomerPaymentID = CustomerPayment.CustomerPaymentID " +
"INNER JOIN Completion ON CustomerPayment.CompletionID = Completion.CompletionID " +
"UNION " +
"SELECT TransactionID,CompletionID,TransactionType,Transactions.WorkerPaymentID as 'PaymentID',WorkerPayment.Amount,PaymentDate FROM Transactions " +
"INNER JOIN WorkerPayment ON Transactions.WorkerPaymentID = WorkerPayment.WorkerPaymentID");
            myRs = preStmt.executeQuery();
            while(myRs.next()){
                transactions.add(new Transactions(myRs.getInt("TransactionID"),myRs.getInt("CompletionID"),myRs.getString("TransactionType"),myRs.getInt("PaymentID"),myRs.getInt("Amount"),myRs.getDate("PaymentDate")));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return transactions;
    }
}