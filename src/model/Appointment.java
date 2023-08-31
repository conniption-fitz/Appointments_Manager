/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private int customerID;
    private int userID;
    private int contactID;
    private Timestamp start;
    private Timestamp end;
    private ZonedDateTime startZoned;
    private ZonedDateTime endZoned;
    private String startZonedString;
    private String endZonedString;
    private ZoneId zone;


    public Appointment(int appointmentID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        zone = ZoneId.of("UTC");
        setStartZoned(zone);
        setEndZoned(zone);
        setStartZonedString();
        setEndZonedString();
    }

    /**
     * @return the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title - the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description - the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location - the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type - the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start Timestamp
     */
    public Timestamp getStartTimestamp() {
        return start;
    }

    /**
     * @return the start LocalDateTime
     */
    public LocalDateTime getStartLocalDateTime() {
        return start.toLocalDateTime();
    }

    /**
     * @param start - the start timestamp
     */
    public void setStartTimestamp(Timestamp start) {
        this.start = start;
    }

    /**
     * @param zone - the local timezone
     */
    public void setStartZoned(ZoneId zone) {
        startZoned = start.toInstant().atZone(zone);
    }

    /**
     * Formats the display String for the zoned start time.
     */
    public void setStartZonedString() {
        startZonedString = startZoned.toLocalDate() + " " + startZoned.toLocalTime();
    }

    public String getStartZonedString() {
        return startZonedString;
    }

    /**
     * @return the end Timestamp
     */
    public Timestamp getEndTimestamp() {
        return end;
    }

    /**
     * @return the end LocalDateTime
     */
    public LocalDateTime getEndLocalDateTime() {
        return end.toLocalDateTime();
    }

    /**
     * @param end - the end timestamp
     */
    public void setEndTimestamp(Timestamp end) {
        this.end = end;
    }

    /**
     * @param zone - the local timezone
     */
    public void setEndZoned(ZoneId zone) {
        endZoned = end.toInstant().atZone(zone);
    }

    /**
     * Formats the display String for the zoned end time.
     */
    public void setEndZonedString() {
        endZonedString = endZoned.toLocalDate() + " " + endZoned.toLocalTime();
    }

    public String getEndZonedString() {
        return endZonedString;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID - the customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return the contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID - the contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
