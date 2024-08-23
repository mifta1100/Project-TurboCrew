package turbocrew;

public class Assignment {
    private int assignmentId;
    private int bookingId;
    private int workerId;
    private String assignmentStatus;

    public Assignment(int assignmentId, int bookingId, int workerId, String assignmentStatus) {
        this.assignmentId = assignmentId;
        this.bookingId = bookingId;
        this.workerId = workerId;
        this.assignmentStatus = assignmentStatus;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    
    
    
}
