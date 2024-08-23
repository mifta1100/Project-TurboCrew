package turbocrew;

public class Service {

    private int serviceId;
    private String serviceType;
    private int hourlyRate;
    
    public Service(){
        //this.serviceId = 0;
        this.serviceType = "";
        this.hourlyRate = 0;
    }
    public Service(int serviceId, String serviceType, int hourlyRate){
        this.serviceId = serviceId;
        this.serviceType = serviceType;
        this.hourlyRate = hourlyRate;
    }
    
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
