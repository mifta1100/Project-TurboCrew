package turbocrew;

public class WorkerStatus {
    private int workerId;
    private int assignmentId;
    private String status;

    public WorkerStatus(int workerId, int assignmentId, String status) {
        this.workerId = workerId;
        this.assignmentId = assignmentId;
        this.status = status;
    }

    public WorkerStatus() {
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

}
