package reportmenu;

import java.util.Arrays;

public class UserManager {

    private String[][] users;
    private String currentUser;

    public UserManager() {
        // Initially, only 'admin' exists with 'admin' role and password 'java'
        this.users = new String[][]{{"admin", "java", "Admin"}};
    }

    public boolean login(String username, String password) {
        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                this.currentUser = username;
                return true;
            }
        }
        return false;
    }

    public void addUser(String username, String password, String role) {
        String[] newUser = {username, password, role};
        this.users = Arrays.copyOf(this.users, this.users.length + 1);
        this.users[this.users.length - 1] = newUser;
    }

    public boolean deleteUser(String username) {
        int userIndex = findUserIndex(username);
        if (userIndex != -1) {
            String[][] newUsers = new String[this.users.length - 1][3];
            for (int i = 0, j = 0; i < this.users.length; i++) {
                if (i != userIndex) {
                    newUsers[j++] = this.users[i];
                }
            }
            this.users = newUsers;
            return true;
        }
        return false;
    }

    public boolean modifyUserRole(String username, String newRole) {
        int userIndex = findUserIndex(username);
        if (userIndex != -1) {
            this.users[userIndex][2] = newRole;
            return true;
        }
        return false;
    }

    public String getCurrentUserRole() {
        for (String[] user : this.users) {
            if (user[0].equals(this.currentUser)) {
                return user[2];
            }
        }
        return null;
    }

    private int findUserIndex(String username) {
        for (int i = 0; i < this.users.length; i++) {
            if (this.users[i][0].equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public void logout() {
        this.currentUser = null;
    }

    public String[][] getUsers() {
        return users;
    }
    
    public String getCurrentUser() {
        return this.currentUser;
    }
    
    public boolean changeUsername(String oldUsername, String newUsername) {
        int userIndex = findUserIndex(oldUsername);
        if (userIndex != -1) {
            // Check if the new username already exists
            if (findUserIndex(newUsername) == -1) {
                this.users[userIndex][0] = newUsername;
                // Update currentUser if they changed their own username
                if (this.currentUser.equals(oldUsername)) {
                    this.currentUser = newUsername;
                }
                return true;
            } else {
                System.out.println("New username already exists. Please choose a different username.");
                return false;
            }
        }
        return false;
    }

    public boolean changePassword(String username, String newPassword) {
        int userIndex = findUserIndex(username);
        if (userIndex != -1) {
            this.users[userIndex][1] = newPassword;
            return true;
        }
        return false;
    }
}
