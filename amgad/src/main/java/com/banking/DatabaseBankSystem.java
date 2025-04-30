package com.banking;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class database_BankSystem {
    private static final String DB_URL = "jdbc:sqlite:bank.db";
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int CODE_LENGTH = 6;

    public static class UserDetails {
        private String fullName;
        private String email;
        private String mobile;
        private String nationalId;
        private double totalBalance;
        private String profileImage;

        public UserDetails(String fullName, String email, String mobile, String nationalId, double totalBalance, String profileImage) {
            this.fullName = fullName;
            this.email = email;
            this.mobile = mobile;
            this.nationalId = nationalId;
            this.totalBalance = totalBalance;
            this.profileImage = profileImage;
        }

        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getMobile() { return mobile; }
        public String getNationalId() { return nationalId; }
        public double getTotalBalance() { return totalBalance; }
        public String getProfileImage() { return profileImage; }
    }

    public static class Transaction {
        private int id;
        private String type;
        private double amount;
        private String date;

        public Transaction(int id, String type, double amount, String date) {
            this.id = id;
            this.type = type;
            this.amount = amount;
            this.date = date;
        }

        public int getId() { return id; }
        public String getType() { return type; }
        public double getAmount() { return amount; }
        public String getDate() { return date; }

        @Override
        public String toString() {
            return "Transaction ID: " + id + ", Type: " + type + ", Amount: " + amount + ", Date: " + date;
        }
    }

    public static class Transfer {
        private int id;
        private String fromUser;
        private String toUser;
        private double amount;
        private String status;
        private String date;

        public Transfer(int id, String fromUser, String toUser, double amount, String status, String date) {
            this.id = id;
            this.fromUser = fromUser;
            this.toUser = toUser;
            this.amount = amount;
            this.status = status;
            this.date = date;
        }

        public int getId() { return id; }
        public String getFromUser() { return fromUser; }
        public String getToUser() { return toUser; }
        public double getAmount() { return amount; }
        public String getStatus() { return status; }
        public String getDate() { return date; }

        @Override
        public String toString() {
            return "Transfer ID: " + id + ", From: " + fromUser + ", To: " + toUser + ", Amount: " + amount + ", Status: " + status + ", Date: " + date;
        }
    }

    public static void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String usersTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL,
                        salt TEXT NOT NULL,
                        full_name TEXT,
                        email TEXT,
                        mobile TEXT,
                        national_id TEXT,
                        profile_image TEXT,
                        total_balance REAL DEFAULT 0,
                        last_login TEXT,
                        is_verified BOOLEAN NOT NULL DEFAULT 0
                    )
                    """;

            String verificationCodesTable = """
                    CREATE TABLE IF NOT EXISTS verification_codes (
                        username TEXT PRIMARY KEY,
                        code TEXT NOT NULL,
                        FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
                    )
                    """;

            String transactionsTable = """
                    CREATE TABLE IF NOT EXISTS transactions (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id INTEGER,
                        type TEXT,
                        amount REAL,
                        date TEXT DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY(user_id) REFERENCES users(id)
                    )
                    """;

            String transfersTable = """
                    CREATE TABLE IF NOT EXISTS transfers (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        from_user TEXT NOT NULL,
                        to_user TEXT NOT NULL,
                        amount REAL NOT NULL,
                        status TEXT DEFAULT 'pending',
                        date TEXT DEFAULT CURRENT_TIMESTAMP
                    )
                    """;

            String investmentsTable = """
                    CREATE TABLE IF NOT EXISTS investments (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id INTEGER,
                        type TEXT,
                        amount REAL,
                        date TEXT DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY(user_id) REFERENCES users(id)
                    )
                    """;

            stmt.execute(usersTable);
            stmt.execute(verificationCodesTable);
            stmt.execute(transactionsTable);
            stmt.execute(transfersTable);
            stmt.execute(investmentsTable);

            addColumnIfNotExists(conn, "users", "profile_image", "TEXT");
            addColumnIfNotExists(conn, "users", "total_balance", "REAL DEFAULT 0");
            addColumnIfNotExists(conn, "users", "salt", "TEXT");
            addColumnIfNotExists(conn, "users", "last_login", "TEXT");
            addColumnIfNotExists(conn, "users", "is_verified", "BOOLEAN NOT NULL DEFAULT 0");

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addColumnIfNotExists(Connection conn, String tableName, String columnName, String columnType) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getColumns(null, null, tableName, columnName);
            if (!rs.next()) {
                try (Statement stmt = conn.createStatement()) {
                    String alterQuery = String.format("ALTER TABLE %s ADD COLUMN %s %s;", tableName, columnName, columnType);
                    stmt.executeUpdate(alterQuery);
                    System.out.println("Added column " + columnName + " to table " + tableName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding column " + columnName + ": " + e.getMessage());
        }
    }

    public static boolean registerUser(String username, String password, String name, String email,
                                       String mobile, String nationalId, String imagePath, double initialBalance) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return false;
        }
        if (initialBalance < 0) {
            System.out.println("Initial balance cannot be negative.");
            return false;
        }

        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO users (username, password, salt, full_name, email, mobile, national_id, profile_image, total_balance, is_verified) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, saltBase64);
            pstmt.setString(4, name);
            pstmt.setString(5, email);
            pstmt.setString(6, mobile);
            pstmt.setString(7, nationalId);
            pstmt.setString(8, imagePath);
            pstmt.setDouble(9, initialBalance);
            pstmt.setBoolean(10, false);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("User " + username + " registered successfully. Rows affected: " + rowsAffected);
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String getUsernameByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ Cannot retrieve username: Email is null or empty.");
            return null;
        }

        String sql = "SELECT username FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    System.out.println("✅ Retrieved username for email " + email + ": " + username);
                    return username;
                } else {
                    System.out.println("❌ No user found with email: " + email);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving username by email: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String getEmailByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Cannot retrieve email: Username is null or empty.");
            return null;
        }

        String sql = "SELECT email FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    if (email != null) {
                        System.out.println("✅ Retrieved email for username " + username + ": " + email);
                        return email;
                    } else {
                        System.out.println("❌ Email is null for username: " + username);
                        return null;
                    }
                } else {
                    System.out.println("❌ No user found with username: " + username);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving email by username: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        if (username == null || username.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            System.out.println("❌ Cannot update password: Username or new password is null or empty.");
            return false;
        }

        if (!userExists(username)) {
            System.out.println("❌ User does not exist: " + username);
            return false;
        }

        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(newPassword, salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        String sql = "UPDATE users SET password = ?, salt = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, saltBase64);
            pstmt.setString(3, username);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Password updated successfully for user: " + username);
                return true;
            } else {
                System.out.println("❌ Failed to update password: No rows affected for user " + username);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating password: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String generateAndSaveVerificationCode(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Cannot generate verification code: Username is null or empty.");
            return null;
        }

        if (!userExists(username)) {
            System.out.println("❌ Cannot generate verification code: User " + username + " does not exist in the database.");
            return null;
        }

        SecureRandom random = new SecureRandom();
        String code = String.valueOf(random.nextInt(999999 - 100000 + 1) + 100000);

        String sql = "INSERT OR REPLACE INTO verification_codes (username, code) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, code);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Generated and saved verification code for " + username + ": " + code);
                return code;
            } else {
                System.out.println("❌ Failed to save verification code for " + username);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error saving verification code: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String getVerificationCode(String username) {
        String sql = "SELECT code FROM verification_codes WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String code = rs.getString("code");
                    System.out.println("✅ Retrieved verification code for " + username + ": " + code);
                    return code;
                } else {
                    System.out.println("❌ No verification code found for " + username);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving verification code: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static void deleteVerificationCode(String username) {
        String sql = "DELETE FROM verification_codes WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            System.out.println("✅ Deleted verification code for " + username);
        } catch (SQLException e) {
            System.out.println("❌ Error deleting verification code: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean verifyCode(String username, String code) {
        String storedCode = getVerificationCode(username);
        if (storedCode == null) {
            System.out.println("❌ No valid verification code found for " + username);
            return false;
        }

        if (storedCode.equals(code)) {
            // Mark the user as verified in the users table
            String updateSql = "UPDATE users SET is_verified = ? WHERE username = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setBoolean(1, true);
                pstmt.setString(2, username);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("✅ User " + username + " marked as verified");
                } else {
                    System.out.println("❌ Failed to mark user " + username + " as verified: No rows affected");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("❌ Error marking user as verified: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

            // Delete the verification code after successful verification
            deleteVerificationCode(username);
            System.out.println("✅ Verification successful for " + username);
            return true;
        } else {
            System.out.println("❌ Verification code mismatch for " + username + ". Entered: " + code + ", Stored: " + storedCode);
            return false;
        }
    }

    public static boolean isUserVerified(String username) {
        String sql = "SELECT is_verified FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_verified");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error checking verification status: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean loginUser(String username, String password) {
        String sql = "SELECT password, salt, is_verified FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    boolean isVerified = rs.getBoolean("is_verified");
                    if (!isVerified) {
                        System.out.println("User " + username + " has not verified their email.");
                        return false;
                    }

                    String storedHash = rs.getString("password");
                    String saltBase64 = rs.getString("salt");
                    if (saltBase64 == null) {
                        return false;
                    }
                    byte[] salt = Base64.getDecoder().decode(saltBase64);
                    String hashedInputPassword = hashPassword(password, salt);
                    return storedHash.equals(hashedInputPassword);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error logging in: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static String hashPassword(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    public static UserDetails getUserDetails(String username) {
        String sql = "SELECT full_name, email, mobile, national_id, total_balance, profile_image FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new UserDetails(
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("mobile"),
                            rs.getString("national_id"),
                            rs.getDouble("total_balance"),
                            rs.getString("profile_image")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving user details: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static UserDetails getUserDetailsById(int userId) {
        String sql = "SELECT full_name, email, mobile, national_id, total_balance, profile_image FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new UserDetails(
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("mobile"),
                            rs.getString("national_id"),
                            rs.getDouble("total_balance"),
                            rs.getString("profile_image")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving user details by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static double getBalance(String username) {
        String sql = "SELECT total_balance FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_balance");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving balance: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Transaction> getRecentTransactions(String username, int limit) {
        List<Transaction> transactions = new ArrayList<>();
        int userId = getUserId(username);
        if (userId == -1) {
            System.out.println("❌ User not found: " + username);
            return transactions;
        }

        String sql = "SELECT id, type, amount, date FROM transactions WHERE user_id = ? ORDER BY date DESC LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getDouble("amount"),
                            rs.getString("date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }

    public static List<Transfer> getRecentTransfers(String username, int limit) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT id, from_user, to_user, amount, status, date FROM transfers WHERE from_user = ? OR to_user = ? ORDER BY date DESC LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, username);
            pstmt.setInt(3, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transfers.add(new Transfer(
                            rs.getInt("id"),
                            rs.getString("from_user"),
                            rs.getString("to_user"),
                            rs.getDouble("amount"),
                            rs.getString("status"),
                            rs.getString("date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving transfers: " + e.getMessage());
            e.printStackTrace();
        }
        return transfers;
    }

    public static boolean deposit(String username, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }
        if (!userExists(username)) {
            System.out.println("User does not exist: " + username);
            return false;
        }
        double current = getBalance(username);
        if (current < 0) return false;
        boolean balanceUpdated = updateBalance(username, current + amount);
        if (balanceUpdated) {
            int userId = getUserId(username);
            return recordTransaction(userId, "deposit", amount);
        }
        return false;
    }

    public static boolean withdraw(String username, double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (!userExists(username)) {
            System.out.println("User does not exist: " + username);
            return false;
        }
        double current = getBalance(username);
        if (current < 0 || current < amount) {
            System.out.println("Insufficient funds or user not found.");
            return false;
        }
        boolean balanceUpdated = updateBalance(username, current - amount);
        if (balanceUpdated) {
            int userId = getUserId(username);
            return recordTransaction(userId, "withdraw", amount);
        }
        return false;
    }

    public static boolean transfer(String fromUsername, String toUsername, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return false;
        }
        if (!userExists(fromUsername) || !userExists(toUsername)) {
            System.out.println("One or both users do not exist.");
            return false;
        }
        double fromBalance = getBalance(fromUsername);
        if (fromBalance < 0 || fromBalance < amount) {
            System.out.println("Insufficient funds for user: " + fromUsername);
            return false;
        }

        double toBalance = getBalance(toUsername);
        if (toBalance < 0) {
            System.out.println("Error retrieving balance for user: " + toUsername);
            return false;
        }

        // Update balances
        boolean fromUpdated = updateBalance(fromUsername, fromBalance - amount);
        if (!fromUpdated) {
            System.out.println("Failed to update balance for user: " + fromUsername);
            return false;
        }

        boolean toUpdated = updateBalance(toUsername, toBalance + amount);
        if (!toUpdated) {
            // Rollback the first update
            updateBalance(fromUsername, fromBalance);
            System.out.println("Failed to update balance for user: " + toUsername + ". Rolled back.");
            return false;
        }

        // Record the transfer
        return recordTransfer(fromUsername, toUsername, amount, "completed");
    }

    private static boolean updateBalance(String username, double newBalance) {
        String sql = "UPDATE users SET total_balance = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, username);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error updating balance: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("❌ Error checking user existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean updateUserDetails(String username, String fullName, String email, String mobile, String profileImagePath) {
        if (!userExists(username)) {
            System.out.println("User does not exist: " + username);
            return false;
        }
        String sql = "UPDATE users SET full_name = ?, email = ?, mobile = ?, profile_image = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, mobile);
            pstmt.setString(4, profileImagePath);
            pstmt.setString(5, username);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error updating user details: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean recordTransaction(int userId, String type, double amount) {
        String sql = "INSERT INTO transactions (user_id, type, amount) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error recording transaction: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean recordTransfer(String fromUsername, String toUsername, double amount, String status) {
        if (!userExists(fromUsername) || !userExists(toUsername)) {
            System.out.println("One or both users do not exist.");
            return false;
        }
        String sql = "INSERT INTO transfers (from_user, to_user, amount, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fromUsername);
            pstmt.setString(2, toUsername);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error recording transfer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateLastLogin(String username) {
        if (!userExists(username)) {
            System.out.println("User does not exist: " + username);
            return false;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            addColumnIfNotExists(conn, "users", "last_login", "TEXT");
        } catch (SQLException e) {
            System.out.println("❌ Error ensuring last_login column exists: " + e.getMessage());
            return false;
        }

        String sql = "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error updating last login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String getLastLogin(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            addColumnIfNotExists(conn, "users", "last_login", "TEXT");
        } catch (SQLException e) {
            System.out.println("❌ Error ensuring last_login column exists: " + e.getMessage());
            return null;
        }

        String sql = "SELECT last_login FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("last_login");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving last login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}