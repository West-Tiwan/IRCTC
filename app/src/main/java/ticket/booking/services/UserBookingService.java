package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String USERS_PATH = "../localDb/user.json";
    public UserBookingService(User userp) throws IOException {
        this.user = userp;
        File users = new File(USERS_PATH);
        userList = OBJECT_MAPPER.readValue(users,new TypeReference<List<User>>(){});
    }
}
