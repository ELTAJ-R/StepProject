package DataBase;


import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SQL {
    //Please add UserName, Password and URL in order to access db.
    private static int iterator = 0;
    public static String userOnView;
    private final static String uname = "";
    private final static String parol = "";
    private final static String URL = "";


    public static HashMap<String, String> getMap(String currentUser) throws SQLException {
        LinkedList<Integer> range = findIDRange(currentUser);
        HashMap<String, String> hashMap = new HashMap<>();
        Connection cn = DriverManager.getConnection(URL, uname, parol);
        String query = "select * from userdata where id=?";
        PreparedStatement stmt = cn.prepareStatement(query);
        stmt.setInt(1, range.get(iterator));
        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            String name = res.getString("name");
            String surname = res.getString("surname");
            String photo = res.getString("photo");
            userOnView = name;
            hashMap.put("name", name);
            hashMap.put("surname", surname);
            hashMap.put("photo", photo);
        }
        if (iterator < range.size() - 1) iterator++;
        else iterator = 0;
        return hashMap;
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

    public static LinkedList<Integer> findIDRange(String currentUser) throws SQLException {
        LinkedList<Integer> result = new LinkedList<>();
        try (Connection cn = DriverManager.getConnection(URL, uname, parol);) {
            String query = "select id  from userdata where name!=?";
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setString(1, currentUser);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                result.add(res.getInt("id"));
            }
            return result;
        }
    }


    public static LinkedList<String> findAllLikes(String currentUser) throws SQLException {
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

    public HashMap<Integer, String> showMessagesFromMe(String sent, String received) throws SQLException {
        HashMap<Integer, String> fullChatFromMe = new HashMap<>();
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select * from messages where sender=? and receiver=?;";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, sent);
            statement.setString(2, received);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int key = res.getInt("id");
                String message = res.getString("message");
                fullChatFromMe.put(key, message);
            }
        }

        return fullChatFromMe;

    }

    public HashMap<Integer, String> showMessagesToMe(String receiver, String sender) throws SQLException {
        return showMessagesFromMe(sender, receiver);

    }

    public String getAllMessages(String me, String anotherPerson) throws SQLException {
        HashMap<Integer, String> from = showMessagesFromMe(me, anotherPerson);
        HashMap<Integer, String> to = showMessagesToMe(me, anotherPerson);
        from.putAll(to);
        return from.entrySet().stream().sorted((o1, o2) -> o1.getKey() - o2.getKey())
                .map(Map.Entry::getValue).collect(Collectors.joining("\n"));


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


    public boolean clickedAll(String currentUser) throws SQLException {
        return iterator == findIDRange(currentUser).size() - 1;

    }
// The following methods might be used in the future.
    //    public int getIDByName(String username) throws SQLException {
//        int id = 0;
//        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
//            String query = "select id from userdata where name=?;";
//            PreparedStatement statement = cn.prepareStatement(query);
//            statement.setString(1, username);
//            ResultSet result = statement.executeQuery();
//            try {
//                id = result.getInt("id");
//            } catch (Exception e) {
//            }
//
//        }
//        return id;
//    }
//
//    public String getNameByID(int id) throws SQLException {
//        String name = "No such profile";
//        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
//            String query = "select name from userdata where id=?;";
//            PreparedStatement statement = cn.prepareStatement(query);
//            statement.setInt(1, id);
//            ResultSet result = statement.executeQuery();
//            try {
//                name = result.getString("name");
//            } catch (Exception e) {
//            }
//
//        }
//        return name;
//    }


}

