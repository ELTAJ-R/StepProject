package DataBase;


import Entities.Pair;
import Entities.Message;
import Entities.User;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class SQL {
    //Please add UserName, Password and URL in order to access db.
    private final static String uname = "postgres";
    private final static String parol = "9149";
    private final static String URL = "jdbc:postgresql://localhost:5432/iba-lessons";
    private static SQL db = new SQL();
    private static int lastUserID = 0;
    public final String htmlLocation = "src/main/java/Documents/HTML";
    public static Methods methods = new Methods();

    //This function gets new user each time from db. when /users/* get request is made
    public Pair<Boolean, User> getNextUser(String loggedInUser) throws SQLException {
        User user = new User();
        boolean allClicked = false;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select * from userdata where name!=? and id>? order by id";
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setString(1, loggedInUser);
            stmt.setInt(2, lastUserID);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                lastUserID = res.getInt("id");
                String name = res.getString("name");
                String surname = res.getString("surname");
                String photo = res.getString("photo");
                user = new User(lastUserID, name, surname, photo);
                break;
            }

            if (lastUserID == getMaxID(loggedInUser)) {
                lastUserID = 0;
                allClicked = true;
            }
            Pair<Boolean, User> pair = new Pair<>(allClicked, user);
            return pair;
        }
    }

    public static int getMaxID(String curr) throws SQLException {
        int maxID = 0;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select max(id) from userdata where name!=?";
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setString(1, curr);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                maxID = res.getInt("max");
            }
            return maxID;
        }
    }


    //this function checks if user is authorized or not

    public boolean login(String user, String password) throws SQLException {
        boolean isLoggedIn = false;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select id from userdata where username=? and password=?";
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, password);
            ResultSet res = stmt.executeQuery();
            try {
                isLoggedIn = res.next();
            } catch (Exception e) {
            }
            return isLoggedIn;
        }
    }


    //this method puts the latest time user logged in into db.
    public void updateLoginDate(String curr) {
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "update userdata set lastlogin=? where username=?";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, methods.now());
            statement.setString(2, curr);
            statement.execute();
        } catch (Exception e) {
        }
    }


    // checks which users have been liked by the logged in user and returns them as User object

    public List<User> getAll(String currentUser) throws SQLException {
        LinkedList<String> allUsers = findAllLikes(currentUser);
        List<User> allLikedUsers = new LinkedList<>();
        for (String name : allUsers) {
            allLikedUsers.add(getUserByName(name));
        }
        return allLikedUsers;
    }


    public static User getUserByName(String name) throws SQLException {
        User user = new User();
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select * from userdata where name=?";
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                int id = res.getInt("id");
                String surname = res.getString("surname");
                String photo = res.getString("photo");
                String lastLogin = res.getString("lastlogin");
                String loginDate = lastLogin != null ? lastLogin : "not available";
                user = new User(id, name, surname, photo, loginDate);
            }
        }
        return user;
    }


    public static LinkedList<String> findAllLikes(String currentUser) {
        LinkedList<String> allLikedProfiles = new LinkedList<>();
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select liked from likes where current=?";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, currentUser);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String profile = resultSet.getString("liked");
                allLikedProfiles.add(profile);
            }
        } catch (Exception e) {
        }
        return allLikedProfiles;
    }


    //makes sure user currently on view was not liked before.

    public static boolean likedBefore(String currentUser, String likedUser) throws SQLException {
        boolean likedOrNotBefore = false;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select * from likes where current=? and liked=?;";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, currentUser);
            statement.setString(2, likedUser);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                likedOrNotBefore = true;
            }
            return likedOrNotBefore;
        }
    }

    public void addLike(String currentUser, String likedUser) throws SQLException {
        if (!likedBefore(currentUser, likedUser)) {
            try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
                String query = "insert into likes (current, liked) values (?,?);";
                PreparedStatement statement = cn.prepareStatement(query);
                statement.setString(1, currentUser);
                statement.setString(2, likedUser);
                statement.execute();
            }
        }
    }


    //shows messages sent by logged in user to another person
    public LinkedList<Message> showMessagesFromMe(String sent, String received, String isSent) throws SQLException {
        LinkedList<Message> fromMe = new LinkedList<>();
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select * from messages where sender=? and receiver=?;";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, sent);
            statement.setString(2, received);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Message message = new Message(res.getInt("id"),
                        res.getString("sender"),
                        res.getString("message"),
                        isSent, res.getString("date"));
                fromMe.add(message);
            }
        }

        return fromMe;

    }

    //shows messages sent to logged in user from another person
    public LinkedList<Message> showMessagesToMe(String receiver, String sender, String isSent) throws SQLException {
        return showMessagesFromMe(sender, receiver, isSent);


    }

    //puts all messages in order based on the time sent
    public List<Message> getAllMessages(String me, String anotherPerson) throws SQLException {
        LinkedList<Message> from = showMessagesFromMe(me, anotherPerson, "true");
        LinkedList<Message> to = showMessagesToMe(me, anotherPerson, "false");
        from.addAll(to);
        return from.stream().sorted((t1, t2) -> (t1.id) - (t2.id))
                .collect(Collectors.toList());
    }


    public static String listToString(List<Message> messages) {
        return messages.stream().map(Message::toString).collect(Collectors.joining("\n"));
    }

    //limits the number of shown messages to the specified number
    public List<Message> messageLimiter(int limit, String me, String anotherPerson) throws SQLException {
        List<Message> allMessages = db.getAllMessages(me, anotherPerson);
        boolean shouldDelete = allMessages.size() > limit;
        if (shouldDelete) {
            List<Message> list = IntStream.range(allMessages.size() - limit, allMessages.size())
                    .mapToObj(allMessages::get).collect(Collectors.toList());
            return list;
        } else return allMessages;
    }



    public void addMessage(String currentUser, String anotherUser, String message) throws SQLException {
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "insert into messages (id, sender, receiver, message, date) values (DEFAULT,?,?,?,?)";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, currentUser);
            statement.setString(2, anotherUser);
            statement.setString(3, message);
            statement.setString(4, methods.now());
            statement.execute();
        }
    }

    public boolean register(User user) {
        boolean created = false;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String command = "insert into userdata (id,username, password, email, name, surname, photo) VALUES (DEFAULT,?,?,?,?,?,?)";
            PreparedStatement statement = cn.prepareStatement(command);
            statement.setString(1, user.name);
            statement.setString(2, user.password);
            statement.setString(3, user.email);
            statement.setString(4, user.name);
            statement.setString(5, user.surname);
            statement.setString(6, user.picture);
            statement.execute();
            created = true;
        } catch (Exception e) {
        }

        return created;
    }
}


