package com.assessment.meetingrooms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MeetingRoomsApplication {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        List<String> meetingRecords = Files.readAllLines(path);

        //Reading meeting values into a 2D Int array
        int[][] meetings = new int[meetingRecords.size()][2];

        for (int i = 0; i < meetingRecords.size(); i++) {
            String[] meetingTimes = meetingRecords.get(i).split("-");
            meetings[i][0] = Integer.parseInt(meetingTimes[0].replace(":", ""));
            meetings[i][1] = Integer.parseInt(meetingTimes[1].replace(":", ""));
        }

        //Sorting the array by start time
        Arrays.sort(meetings, Comparator.comparing((int[] mt) -> mt[0]));

        //Each element in the queue is a meeting room and represents the time when the last meeting is finished
        PriorityQueue<Integer> meetingRooms = new PriorityQueue<>();
        int requiredMeetingRoomsCount = 0;

        for (int[] meeting : meetings) {
            //This is the fist meeting
            if (meetingRooms.isEmpty()) {
                requiredMeetingRoomsCount++;
                meetingRooms.offer(meeting[1]);
                continue;
            }

            //If the meeting starts after the earliest finishing meeting, free up the meeting room
            //Else request a new meeting room
            if (meeting[0] >= meetingRooms.peek()) {
                meetingRooms.poll();
            } else {
                requiredMeetingRoomsCount++;
            }

            meetingRooms.offer(meeting[1]);
        }

        System.out.println(requiredMeetingRoomsCount);
    }
}
