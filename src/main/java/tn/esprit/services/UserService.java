package tn.esprit.services;

    import tn.esprit.entities.User;
    import tn.esprit.utils.DataSource;
    import org.mindrot.jbcrypt.BCrypt;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;



    public class UserService {
        static Connection cnx = DataSource.getInstance().getCnx();


        public boolean emailExists(String email) {
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
                e.printStackTrace(); // Handle the exception appropriately
            }
            return false;
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
                   // User already exists
                   System.out.println("User with email " + email + " already exists!");
               } else {
                   // User does not exist, create new user
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
                       // User created successfully
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
        public boolean loginUser(String email, String password) {
            try {
                String query = "SELECT id, password FROM user WHERE email = ?";
                try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                    preparedStatement.setString(1, email);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String hashedPassword = resultSet.getString("password");
                            if (BCrypt.checkpw(password, hashedPassword)) {
                                // Password is correct, set user session
                                int userId = resultSet.getInt("id");
                                SessionManager.setSession("userId", userId);
                                return true; // Login successful
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
            return false; // Login failed
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
                            // You can set other attributes of the user here
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
            return user;
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














