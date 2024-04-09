import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static List<User> userList = new ArrayList<>();

    static {
        // Adding some hardcoded users
        userList.add(new User("user1", "password1"));
        userList.add(new User("user2", "password2"));
        userList.add(new User("user3", "password3"));
    }

    public static List<User> getUserList() {
        return userList;
    }

    public static void addUser(User user) {
        userList.add(user);
    }
}
