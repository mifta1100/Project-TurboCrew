package turbocrew;

public class Worker {
    private int workerId;
    private String name;
    private String phone;
    private String address;
    private String specialization;

    public Worker(int workerId, String name, String phone, String address, String specialization) {
        this.workerId = workerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.specialization = specialization;
    }
    
    public Worker(){
        
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    
}
