package Task2.Backend.Controllers;

import Task2.Backend.Database;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    public record User(long id, String name, String lastName, String patronymic, String dob, int group) {}

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody String userJson) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode rootNode;

        try {
            rootNode = objectMapper.readTree(userJson);
            System.out.println(rootNode.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        String sql = " INSERT INTO users(user_name, last_name, patronymic, group_num, date_of_birth)\n" +
                " VALUES (?, ?, ?, ?, ?);";

        Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, rootNode.get("name").asText());
        statement.setString(2, rootNode.get("lastName").asText());
        statement.setString(3, rootNode.get("patronymic").asText());
        statement.setInt(4, rootNode.get("group").asInt());
        statement.setString(5, rootNode.get("dob").asText());

        int rows = statement.executeUpdate();
        System.out.printf("%d rows added", rows);

    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/deleteUser")
    public void deleteUser(@RequestParam String userId) throws SQLException {

        String sql = "DELETE FROM Users where id = ?";
        Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userId);
        statement.executeUpdate();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAllUsers")
    public List<User> getAllUsers() throws SQLException {

        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Users");
        ArrayList<User> users = new ArrayList<>();

        while (result.next()) {
            long id = result.getLong(1);
            String userName = result.getString(2);
            String lastName = result.getString(3);
            String patronymic = result.getString(4);
            int group = result.getInt(5);
            String DOB = result.getString(6);
            users.add(new User(id, userName, lastName, patronymic, DOB, group));
        }

        return users;
    }

}