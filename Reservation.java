import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date startDate;
    private Date endDate;
    private String name;
    private String licenseNumber;
    private String contactDetails;

    // Constructor
    public Reservation(Date startDate, Date endDate, String name, String licenseNumber, String contactDetails) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contactDetails = contactDetails;
    }

    // Getters for all fields
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getContactDetails() {
        return contactDetails;
    }
}
