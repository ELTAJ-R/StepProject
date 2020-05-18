package DataBase;


import Entities.Pair;
import Entities.Triplet;
import Entities.User;

import java.sql.*;
import java.time.LocalDate;
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
    public final String htmlLocation="src/main/java/Documents/HTML";


    public Pair<Boolean, User> getNextUser(String loggedInUser) throws SQLException {
        User user = new User();
        boolean allClicked = false;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select * from userdata where name!=? and id>?";
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
           
            if (lastUserID == getMaxID()){ lastUserID = 0;
                allClicked = true;
            }
            Pair<Boolean, User> pair = new Pair<>(allClicked, user);
            return pair;
        }
    }

    public static int getMaxID() throws SQLException {
        int maxID = 0;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol);) {
            String query = "select max(id) from userdata";
            PreparedStatement stmt = cn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                maxID = res.getInt("max");
            }
            return maxID;
        }
    }

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
        if (!(likedUser == null || likedBefore(currentUser, likedUser))) {
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
    public LinkedList<Triplet> showMessagesFromMe(String sent, String received) throws SQLException {
        LinkedList<Triplet> fromMe = new LinkedList<>();
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select * from messages where sender=? and receiver=?;";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, sent);
            statement.setString(2, received);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int key = res.getInt("id");
                String sender = res.getString("sender");
                String message = res.getString("message");
                fromMe.add(new Triplet(key, sender, message));
            }
        }

        return fromMe;

    }

    //shows messages sent to logged in user from another person
    public LinkedList<Triplet> showMessagesToMe(String receiver, String sender) throws SQLException {
        return showMessagesFromMe(sender, receiver);

    }

    //puts all messages in order based on the time sent
    public List<Triplet> getAllMessages(String me, String anotherPerson) throws SQLException {
        LinkedList<Triplet> from = showMessagesFromMe(me, anotherPerson);
        LinkedList<Triplet> to = showMessagesToMe(me, anotherPerson);
        from.addAll(to);
        return from.stream().sorted((t1, t2) -> (t1.id) - (t2.id))
                .collect(Collectors.toList());
    }


    public static String listToString(List<Triplet> messages) {
        return messages.stream().map(Triplet::toString).collect(Collectors.joining("\n"));
    }

    //limits the number of shown messages to the specified number
    public String messageLimiter(int limit, String me, String anotherPerson) throws SQLException {
        List<Triplet> allMessages = db.getAllMessages(me, anotherPerson);
        boolean shouldDelete = allMessages.size() > limit;
        if (shouldDelete) {
            List<Triplet> list = IntStream.range(allMessages.size() - limit, allMessages.size())
                    .mapToObj(allMessages::get).collect(Collectors.toList());
            return listToString(list);
        } else return listToString(allMessages);
    }

    public void addMessage(String currentUser, String anotherUser, String message) throws SQLException {
        String currentTime = String.valueOf(LocalDate.now());
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "insert into messages (id, sender, receiver, message, date) values (DEFAULT,?,?,?,?)";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, currentUser);
            statement.setString(2, anotherUser);
            statement.setString(3, message);
            statement.setString(4, currentTime);
            statement.execute();
        }
    }

}

