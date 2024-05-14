package tn.esprit.services;


import tn.esprit.entities.Event;
import tn.esprit.entities.Groupe;
import tn.esprit.interfaces.IServiceFatma;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventService implements IServiceFatma<Event> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Event event) {
        String req = "INSERT INTO `event` ( `groupe_id`, `nom`, `desc_event`, `date_d`, `date_f`)VALUES ('" + event.getGroupe_id() + "','" + event.getNom() + "','" + event.getDesc_event() + "','" + event.getDate_d() + "','" + event.getDate_f() + "')";
        try {
            GroupService gs = new GroupService();
            Groupe groupe = gs.getOne(event.getGroupe_id());
            if (groupe != null) {
                Statement st = cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("event added successfully");
                groupe.events.add(event);
            } else
                System.out.println("group not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addWithId(Event event) {
        String req = "INSERT INTO `event` ( `groupe_id`, `nom`, `desc_event`, `date_d`, `date_f`,`user_id` )VALUES ('" + event.getGroupe_id() + "','" + event.getNom() + "','" + event.getDesc_event() + "','" + event.getDate_d() + "','" + event.getDate_f() + "','" + event.getUser_id() + "')";
        try {
            GroupService gs = new GroupService();
            Groupe groupe = gs.getOne(event.getGroupe_id());
            if (groupe != null) {
                Statement st = cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("event added successfully");
                groupe.events.add(event);
            } else
                System.out.println("group not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Event event) {
        String req = "UPDATE `event` SET `nom`='" + event.getNom() + "',`desc_event`='" + event.getDesc_event() + "',`date_d`='" + event.getDate_d() + "', `date_f`='" + event.getDate_f() + "', where `id`='" + event.getId() + "'";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("event updated successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Event event) {
        String req = "DELETE FROM `event` WHERE `id`=?";
        try {
            GroupService gs = new GroupService();
            Groupe groupe = gs.getOne(event.getGroupe_id());
            if (groupe != null) {
                groupe.events.remove(event);
            }
            PreparedStatement statement = cnx.prepareStatement(req);
            statement.setInt(1, event.getId());
            statement.executeUpdate();
            System.out.println("event deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        String req = "SELECT * FROM `event`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Event event = new Event();
                event.setId(res.getInt("id"));
                event.setNom(res.getString("nom"));
                event.setDesc_event(res.getString("desc_event"));
                event.setDate_d(res.getDate("date_d").toLocalDate());
                event.setDate_f(res.getDate("date_f").toLocalDate());
                event.setGroupe_id(res.getInt("groupe_id"));
                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public Event getOne(int id) {
        return null;
    }

    public Duration getTotalDuration() {
        List<Event> events = getAll();
        Duration totalDuration = Duration.ZERO;
        for (Event event : events) {
            totalDuration = totalDuration.plus(Duration.between(event.getDate_d().atStartOfDay(), event.getDate_f().atStartOfDay()));
        }
        return totalDuration;
    }

    public Duration getMaxDuration() {
        List<Event> events = getAll();
        Duration maxDuration = Duration.ZERO;
        for (Event event : events) {
            Duration duration = Duration.between(event.getDate_d().atStartOfDay(), event.getDate_f().atStartOfDay());
            if (duration.compareTo(maxDuration) > 0) {
                maxDuration = duration;
            }
        }
        return maxDuration;
    }

    public long getMinDuration() {
        List<Event> events = getAll();
        if (events.isEmpty()) {
            return 0;
        }

        LocalDate minStart = events.get(0).getDate_d();
        LocalDate minEnd = events.get(0).getDate_f();
        long minDurationInDays = Duration.between(minStart.atStartOfDay(), minEnd.atStartOfDay()).toDays();

        for (Event event : events) {
            LocalDate start = event.getDate_d();
            LocalDate end = event.getDate_f();
            long durationInDays = Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();

            if (durationInDays < minDurationInDays) {
                minDurationInDays = durationInDays;
            }
        }

        return minDurationInDays;
    }

    public Map<Month, Integer> getEventsPerMonth() {
        Map<Month, Integer> eventsPerMonth = new HashMap<>();
        String req = "SELECT MONTH(date_d) AS month, COUNT(*) AS count FROM event GROUP BY month";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int monthValue = res.getInt("month");
                int count = res.getInt("count");
                Month month = Month.of(monthValue);
                eventsPerMonth.put(month, count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eventsPerMonth;
    }
}
