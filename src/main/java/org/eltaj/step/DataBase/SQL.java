package org.eltaj.step.DataBase;


import lombok.extern.log4j.Log4j2;
import org.eltaj.step.Entities.Pair;
import org.eltaj.step.Entities.User;
import org.eltaj.step.Entities.Message;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class SQL {
    //Please add UserName, Password and URL as environment variables in order to access db.
    private final static HerokuEnv credentials = new HerokuEnv();
    private final static String uname = credentials.jdbc_username();
    private final static String parol = credentials.jdbc_password();
    private final static String URL = credentials.jdbc_url();
    private final Methods mixedMethods;
    public final String htmlLocation = "src/main/resources/StaticContent/HTML";
    private static int lastUserID = 0;


    public SQL(Methods mixedMethods) {
        this.mixedMethods = mixedMethods;
    }


    //This function gets new user each time from db. when /users/* get request is made
    public Optional<Pair<Boolean, User>> getNextUser(String loggedInUser) {
        log.info("trying to get the next user for /users/*");
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
                user = User.builder()
                        .name(name)
                        .surname(surname)
                        .picture(photo)
                        .id(lastUserID)
                        .build();
                break;
            }
            int maxID = getMaxID(loggedInUser).orElse(0);
            if (lastUserID == maxID) {
                lastUserID = 0;
                allClicked = true;
            }
            Pair<Boolean, User> pair = new Pair<>(allClicked, user);
            return Optional.of(pair);
        } catch (SQLException e) {
            log.error("SQL exception was thrown while getting the next user: " + e);
            return Optional.empty();
        }
    }

    public Optional<Integer> getMaxID(String curr) {
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select max(id) from userdata where name!=?";
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setString(1, curr);
            ResultSet res = stmt.executeQuery();
            int maxID = Integer.MAX_VALUE;
            while (res.next()) {
                maxID = res.getInt("max");
            }
            return Optional.of(maxID);
        } catch (SQLException e) {
            log.error("Could not find the max id due to SQL exception:  ", e);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Could not find the max id due to unexpected exception:  ", e);
            return Optional.empty();
        }
    }


    //this function checks if user is authorized or not
    public boolean login(String user, String password) {
        log.info("Inside Login");
        boolean isLoggedIn = false;
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "select id from userdata where username=? and password=?";
            PreparedStatement stmt = cn.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, password);
            ResultSet res = stmt.executeQuery();

            isLoggedIn = res.next();
        } catch (SQLException e) {
            log.error("SQL exception occurred while logging in" + e);
        }
        return isLoggedIn;
    }


    //this method puts the latest time user logged in into db.
    public void updateLoginDate(String curr) {
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "update userdata set lastlogin=? where username=?";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, mixedMethods.now());
            statement.setString(2, curr);
            statement.execute();
        } catch (SQLException e) {
            log.error("SQL exception occured while updating last login date" + e);
        }
    }


    // checks which users have been liked by the logged in user and returns them as User object

    public List<User> getAll(String currentUser) {
        LinkedList<String> allUsers = findAllLikes(currentUser);
        List<User> allLikedUsers = new LinkedList<>();
        for (String name : allUsers) {
            User user = getUserByName(name)
                    .orElse(User.builder().name("No such user").build());
            allLikedUsers.add(user);
        }
        return allLikedUsers;
    }


    public Optional<User> getUserByName(String name) {
        log.info("Getting user by name: " + name);
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
                user = User.builder()
                        .name(name)
                        .surname(surname)
                        .lastLogin(loginDate)
                        .id(id)
                        .picture(photo)
                        .build();
            }

            return Optional.of(user);
        } catch (SQLException e) {
            log.error("SQL exception while getting user with the specified name: " + e);
            return Optional.empty();
        } catch (Exception e) {
            log.error("unexpected exception happened while getting user by specified name: " + e);
            return Optional.empty();

        }

    }


    public static LinkedList<String> findAllLikes(String currentUser) {
        log.info("Getting all users liked by:  "+currentUser);
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
        } catch (SQLException e) {
            log.error("Could not get liked profiles due to the following error:  "+e);}
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
    public Optional<List<Message>> getAllMessages(String me, String anotherPerson) {
        try {
            LinkedList<Message> from = showMessagesFromMe(me, anotherPerson, "true");
            LinkedList<Message> to = showMessagesToMe(me, anotherPerson, "false");
            from.addAll(to);
            List<Message> allMessages = from.stream().sorted((t1, t2) -> (t1.getId()) - (t2.getId()))
                    .collect(Collectors.toList());
            return Optional.of(allMessages);
        } catch (SQLException e) {
            log.error("could not load messages due to SQL exception: " + e);
            return Optional.empty();
        }
    }


    public static String listToString(List<Message> messages) {
        return messages.stream().map(Message::toString).collect(Collectors.joining("\n"));
    }

    //limits the number of shown messages to the specified number
    public List<Message> messageLimiter(int limit, String me, String anotherPerson) {
        List<Message> allMessages = getAllMessages(me, anotherPerson).get();
        boolean shouldDelete = allMessages.size() > limit;
        if (shouldDelete) {
            List<Message> list = IntStream.range(allMessages.size() - limit, allMessages.size())
                    .mapToObj(allMessages::get).collect(Collectors.toList());
            return list;
        } else return allMessages;
    }


    public void addMessage(String currentUser, String anotherUser, String message){
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String query = "insert into messages (id, sender, receiver, message, date) values (DEFAULT,?,?,?,?)";
            PreparedStatement statement = cn.prepareStatement(query);
            statement.setString(1, currentUser);
            statement.setString(2, anotherUser);
            statement.setString(3, message);
            statement.setString(4, mixedMethods.now());
            statement.execute();
        }
        catch (SQLException e){log.error("SQL exception occured while trying to send a message:  "+e);}
    }

    public boolean register(User user) {
        log.info("trying to register");
        try (Connection cn = DriverManager.getConnection(URL, uname, parol)) {
            String command = "insert into userdata (id,username, password, email, name, surname, photo) VALUES (DEFAULT,?,?,?,?,?,?)";
            PreparedStatement statement = cn.prepareStatement(command);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurname());
            statement.setString(6, user.getPicture());
            statement.execute();
            return true;
        } catch (SQLException e) {
            log.error("unsuccessful registration due to SQL exception: " + e);
            return false;
        } catch (Exception e) {
            log.error("unexpected exception happened during registration: " + e);
            return false;
        }
    }
}


