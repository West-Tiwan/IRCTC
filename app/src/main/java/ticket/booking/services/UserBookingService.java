package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;

    private List<User> userList;

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String USERS_PATH = "app\\src\\main\\java\\ticket\\booking\\localDb\\user.json";

    public UserBookingService(User userp) throws IOException {
        this.user = userp;
        File users = new File(USERS_PATH);
        userList = OBJECT_MAPPER.readValue(users,new TypeReference<List<User>>(){});
    }

    public Boolean LoginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashpassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }
        catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File userFile = new File(USERS_PATH);
        OBJECT_MAPPER.writeValue(userFile,userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public void cancelBooking(String ticketId1) throws IOException {
        Optional<Ticket> toBeRemovedTicket = user.getTicketsBooked().stream().filter(ticket -> {return ticketId1.equals(ticket);}).findFirst();
        List<Ticket> toBeRmovedFrom = user.getTicketsBooked();
        toBeRmovedFrom.remove(toBeRemovedTicket);
        saveUserListToFile();
    }
}
