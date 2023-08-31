/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package model;

public class Contact {

    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    @Override
    public String toString() {
        return contactName;
    }

    /**
     * @return the contactID
     */
    public int getContactID() {
        return contactID;
    }
}
