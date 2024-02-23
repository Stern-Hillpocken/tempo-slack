package firstgroup.temposlack;


import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TempoSlackServerTests {


    @Test
    void createServer() {
        User user = new User("toto", "password", "toto@gmail.com", "licorne");
        Server server = new Server("fisrtServer", user);
        assertEquals("fisrtServer", server.getName());
        assertEquals("toto", server.getUserList().get(0).getPseudo());
    }

    @Test
    void createServerRoomRemovable() {
        User user = new User("toto", "password", "toto@gmail.com", "licorne");
        Server server = new Server("fisrtServer", user);
        Room room = new Room("roomCreated");
        server.addRoom(room);
        assertTrue(server.getRoomList().get(0).isRemovable());
    }

    @Test
    void deleteRoomServer() {
        User user = new User("toto", "password", "toto@gmail.com", "licorne");
        Server server = new Server("fisrtServer", user);
        Room room1 = new Room("roomCreated1");
        Room room2 = new Room("roomCreated2");
        server.addRoom(room1);
        server.addRoom(room2);
        server.deleteRoom(room1);
        assertEquals("roomCreated2",server.getRoomList().get(0).getTitle());
    }
}

