package questions;

import java.util.List;

public class DesignMeetingRooms {



class Meeting {
    private List<String> participants;
    int start;
    int end;
    Room room;
}


class Room {
    int[][] bookedSlots;
    int capacity;
    int buildingNumber;

    public boolean bookSlot(int start , int end , int capacity);
    public boolean isAvailable(int start , int end);
    public void refresh();
}


class Scheduler {
    List<Room> rooms;
    public List<Room> findAvailableRooms(int start , int end , int capacity);
}

class MeetingService {
    Scheduler scheduler;
    public List<Room> findRooms(int start , int end , int capacity);
}


public static void main() {
    MeetingService teams = new MeetingService();
    List<Room> rooms = teams.findRooms(0 , 2, 5);
    rooms.get(0).bookSlot(0 , 2, 5);
}


























}
