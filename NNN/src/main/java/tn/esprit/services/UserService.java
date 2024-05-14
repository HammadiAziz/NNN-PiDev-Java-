package tn.esprit.services;

    import tn.esprit.entities.User;
    import tn.esprit.utils.DataSource;
    import org.mindrot.jbcrypt.BCrypt;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Random;


public class UserService {
    static Connection cnx = DataSource.getInstance().getCnx();


    public static boolean emailExists(String email) {
        try {
            String query = "SELECT COUNT(*) FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String query = "SELECT * FROM user";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPassword(resultSet.getString("password"));
                        user.setNom(resultSet.getString("first_name"));
                        user.setPrenom(resultSet.getString("last_name"));
                        user.setPhone(resultSet.getInt("phone"));
                        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
                        user.setStatus(resultSet.getString("status"));
                        user.setPhoto(resultSet.getString("photo"));
                        user.setBlock_reason(resultSet.getString("block_reason"));
                        user.setId_budge(resultSet.getInt("id_budge"));
                        user.setWallet(resultSet.getInt("wallet"));


                        String rolesStr = resultSet.getString("roles");
                        if (rolesStr != null && !rolesStr.isEmpty()) {
                            String[] rolesArray = rolesStr.split(",");
                            user.setRoles(Arrays.asList(rolesArray));
                        }

                        userList.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public boolean updateResetToken(String email, String resetToken) {
        try {
            String query = "UPDATE user SET reset_token = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, resetToken);
                preparedStatement.setString(2, email);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Reset token updated successfully");
                    SessionManager.setSession("resetToken", resetToken);
                    return true;
                } else {
                    System.out.println("Failed to update reset token");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getEmailByResetToken(String resetToken) {
        try {
            String query = "SELECT email FROM user WHERE reset_token = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, resetToken);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("email");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean changePassword(String email, String newPassword) {
        try {
            // Hash the new password
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Update the user's password in the database
            String query = "UPDATE user SET password = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, hashedPassword);
                preparedStatement.setString(2, email);

                int rowsUpdated = preparedStatement.executeUpdate();

                // Check if any rows were updated
                if (rowsUpdated > 0) {
                    System.out.println("Password updated successfully");
                    return true;
                } else {
                    System.out.println("Failed to update password");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateUserStatus(int userId, String status, String blockReason) {
        try {
            String query = "UPDATE user SET status = ?, block_reason = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, blockReason);
                preparedStatement.setInt(3, userId);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("User status updated successfully.");
                    return true;
                } else {
                    System.out.println("Failed to update user status.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean registerUser(User user,   String email,String nom,String password) throws SQLException {
        boolean userCreated = false;

        try {
            // Check if user already exists
            String checkQuery = "SELECT * FROM user WHERE email=?";
            PreparedStatement checkPst = DataSource.getInstance().getCnx().prepareStatement(checkQuery);
            checkPst.setString(1, email);
            ResultSet checkResult = checkPst.executeQuery();

            if (checkResult.next()) {

                System.out.println("User with email " + email + " already exists!");
            } else {

                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

                String sql =  "INSERT INTO `user`(`email`, `password`, `first_name`, `last_name`, `phone`, `birthday`, `status`, `roles`) VALUES (?,?,?,?,?,?,?,?)";

                PreparedStatement ps =cnx.prepareStatement(sql);
                ps.setString(1, user.getEmail());
                ps.setString(2, hashedPassword);
                ps.setString(3, user.getNom());
                ps.setString(4, user.getPrenom());
                ps.setInt(5, user.getPhone());
                ps.setDate(6, java.sql.Date.valueOf(user.getBirthday()));
                ps.setString(7, "active");
                ps.setString(8, String.join(",",user.getRoles()));
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {

                    System.out.println("User with email " + email + " created successfully!");
                    userCreated = true;
                } else {
                    System.out.println("Failed to create user with email " + email + "!");
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return userCreated;
    }

    public String loginUser(String email, String password) {
        try {
            String query = "SELECT id, password, roles, status, block_reason FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("password");
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            // Password is correct, check user roles
                            String roles = resultSet.getString("roles");
                            int sessionValue;
                            if (roles.contains("Livreur") || roles.contains("[\"ROLE_SIMPLE_USER\"]")) {
                                // User is coach or player
                                sessionValue = -1;
                            } else if (roles.contains("[\"ROLE_ADMIN\"]")) {
                                // User is admin
                                sessionValue = 1;
                            } else {
                                // User has no defined role
                                // You can handle this case as needed
                                return "User role not defined";
                            }

                            // Check user status
                            String status = resultSet.getString("status");
                            if ("blocked".equals(status)) {
                                String blockReason = resultSet.getString("block_reason");
                                // User is blocked, return block reason
                                return blockReason;
                            }

                            int userId = resultSet.getInt("id");
                            SessionManager.setSession("userId", userId);
                            SessionManager.setSession("email", email);
                            SessionManager.setSession("role", sessionValue);
                            return null; // Login successful
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return "Invalid email or password"; // Login failed
    }
    public String getBlockReason(String email) {
        try {
            String query = "SELECT block_reason FROM user WHERE email = ? AND status = 'blocked'";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("block_reason");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No block reason found
    }

    public User fetchPlayerData() {
        User user = null;
        try {
            int userId = (int) SessionManager.getSession("userId");

            String query = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setNom(resultSet.getString("first_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPrenom(resultSet.getString("last_name"));
                        user.setPhone(resultSet.getInt("phone"));
                        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
                        user.setWallet(resultSet.getInt("wallet"));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getPhoneByEmail(String email) {
        try {
            String query = "SELECT phone FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("phone");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void generateAndStoreVerificationCode(String email) {
        String verificationCode = generateVerificationCode();

        try {
            String query = "UPDATE user SET verification_code = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, verificationCode);
                preparedStatement.setString(2, email);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Verification code generated and stored successfully");
                    sendVerificationCodeViaSMS(email, verificationCode); // Send the verification code via SMS
                } else {
                    System.out.println("Failed to generate and store verification code");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendVerificationCodeViaSMS(String email, String verificationCode) {
        // Get the user's phone number using the email
        String phoneNumber = getPhoneByEmail(email);

        if (phoneNumber != null) {
            // Prepend "+216" to the phone number
            phoneNumber = "+216" + phoneNumber;

            String message = "Your verification code for login: " + verificationCode;
            SmsSender.sendSMS(phoneNumber, message);
            System.out.println("Verification code sent via SMS to " + phoneNumber);
        } else {
            System.out.println("Failed to retrieve phone number for sending SMS");
        }
    }

    public boolean verifyVerificationCode(String email, String verificationCode) {
        try {
            String query = "SELECT verification_code FROM user WHERE email = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedVerificationCode = resultSet.getString("verification_code");
                        return verificationCode.equals(storedVerificationCode);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit code
        return String.valueOf(code);
    }
    public boolean editUserInfo(User user) {
        try {
            // Retrieve the user ID from the session
            int userId = (int) SessionManager.getSession("userId");

            // Check if the user ID is valid
            if (userId <= 0) {
                System.out.println("Invalid session");
                return false;
            }


            String query = "UPDATE user SET first_name = ?, last_name = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, user.getNom());
                preparedStatement.setString(2, user.getPrenom());


                preparedStatement.setInt(3, userId);

                int rowsUpdated = preparedStatement.executeUpdate();

                // Check if any rows were updated
                if (rowsUpdated > 0) {
                    System.out.println("User information updated successfully");
                    return true;
                } else {
                    System.out.println("Failed to update user information");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean purchaseBadge(int userId, String badgeName, BadgeService badgeService) {
        try {
            int badgePrice = badgeService.getBadgePrice(badgeName);
            if (badgePrice == -1) {
                System.out.println("Badge not found");
                return false;
            }

            int userWallet = getUserWallet(userId);
            if (userWallet < badgePrice) {
                System.out.println("Insufficient funds");
                return false;
            }

            // Update user's wallet balance
            int newWalletBalance = userWallet - badgePrice;
            updateWallet(userId, newWalletBalance);


            int badgeId = badgeService.getBadgeId(badgeName);
            if (badgeId == -1) {
                System.out.println("Badge not found");
                return false;
            }

            createRelation(userId, badgeId);

            System.out.println("Badge purchased successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private int getUserWallet(int userId) throws SQLException {
        String query = "SELECT wallet FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("wallet");
                }
            }
        }
        return -1;
    }

    private void updateWallet(int userId, int newWalletBalance) throws SQLException {
        String query = "UPDATE user SET wallet = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, newWalletBalance);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        }
    }

    private void createRelation(int userId, int badgeId) throws SQLException {
        String query = "UPDATE user SET id_budge = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, badgeId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        }
    }

    public boolean deleteUserAccount(int userId) {
        try {
            String query = "DELETE FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("User account deleted successfully");
                    return true;
                } else {
                    System.out.println("Failed to delete user account");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getPlayerById(int playerId) {
        User user = null;
        try {
            String query = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, playerId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setNom(resultSet.getString("first_name"));
                        user.setPrenom(resultSet.getString("last_name"));
                        user.setPhone(resultSet.getInt("phone"));
                        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
                        // You can set other attributes of the user here
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return user;
    }

    public boolean updatePlayerById(int playerId, User updatedUserData) {
        try {
            String query = "UPDATE user SET email= ?, first_name = ?, last_name = ?, WHERE id = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, updatedUserData.getEmail());
                preparedStatement.setString(2, updatedUserData.getNom());
                preparedStatement.setString(3, updatedUserData.getPrenom());
                preparedStatement.setInt(4, playerId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Update successful
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return false; // Update failed
    }



        }














