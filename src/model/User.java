/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package model;

public class User {

    private int userID;
    private String userName;
    private String password;
    private boolean loggedIn;

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        loggedIn = false;
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the loggedIn status
     */
    public boolean getLoggedInStatus() {
        return loggedIn;
    }

    /**
     * @param status - the loggedIn status
     */
    public void setLoggedInStatus(boolean status) {
        loggedIn = status;
        System.out.println(userName + " login status is " + loggedIn);
    }
}
