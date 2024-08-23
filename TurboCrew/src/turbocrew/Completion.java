package turbocrew;

public class Completion {
    private int completionId;
    private int assignmentId;
    private String completionTime;
    private String additionalNotes;
    private int overtimeHours;
    private int overtimeCost;
    private int finalCost;

    public Completion(int completionId, int assignmentId, String completionTime, String additionalNotes, int overtimeHours, int overtimeCost, int finalCost) {
        this.completionId = completionId;
        this.assignmentId = assignmentId;
        this.completionTime = completionTime;
        this.additionalNotes = additionalNotes;
        this.overtimeHours = overtimeHours;
        this.overtimeCost = overtimeCost;
        this.finalCost = finalCost;
    }

    public Completion() {
    }

    public int getCompletionId() {
        return completionId;
    }

    public void setCompletionId(int completionId) {
        this.completionId = completionId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public int getOvertimeCost() {
        return overtimeCost;
    }

    public void setOvertimeCost(int overtimeCost) {
        this.overtimeCost = overtimeCost;
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }

    
    
    
}
