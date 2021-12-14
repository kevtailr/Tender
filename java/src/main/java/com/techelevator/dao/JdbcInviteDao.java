package com.techelevator.dao;

import com.techelevator.Service.DTO.InviteDTO;
import com.techelevator.Service.DTO.RestaurantDTO;
import com.techelevator.model.Invite;
import com.techelevator.model.Restaurant;
import com.techelevator.model.RestaurantInviteDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcInviteDao implements InviteDao {

    private final RestaurantDao restaurantDao;
    private final JdbcTemplate jdbcTemplate;

    public JdbcInviteDao(RestaurantDao restaurantDao, JdbcTemplate jdbcTemplate) {
        this.restaurantDao = restaurantDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invite getInviteByInviteId(int inviteId) {

        Invite invite = null;

        String sql = "SELECT invite_id, sender_user_id, closing_date, closing_time, unique_link " +
                "FROM invites WHERE invite_id = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, inviteId);
        while (results.next()){
            invite = mapRowToInvite(results);
        }
        return invite;
    };

    @Override
    public Invite getInviteBySenderId(int senderUserId) {
        Invite invite = null;

        String sql = "SELECT invite_id, closing_date, closing_time, unique_link " +
                "FROM invites WHERE sender_user_id = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, senderUserId);
        if (results.next()){
            invite = mapRowToInvite(results);
        }
        return invite;
    };

    @Override
    public List<Invite> getAllInvitesBySenderId(int senderUserId) {
        List<Invite> invites = new ArrayList<>();

        String sql = "SELECT invite_id, sender_user_id, closing_date, closing_time, unique_link " +
                "FROM invites WHERE sender_user_id = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, senderUserId);
        while (results.next()){
            Invite invite = mapRowToInvite(results);
            invites.add(invite);
        }
        return invites;
    };

    @Override
    public Invite getInviteByUniqueLink(String uniqueLink) {
        Invite invite = null;

        String sql = "SELECT invite_id, sender_user_id, closing_date, closing_time, unique_link " +
                "FROM invites WHERE unique_link = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, uniqueLink);
        if (results.next()){
            invite = mapRowToInvite(results);
        }
        return invite;
    };

    @Override
    public Invite createInvite(Invite invite) {

        String sql = "INSERT INTO invites (sender_user_id, closing_date, closing_time, unique_link) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, invite.getSenderUserId(), invite.getClosingDate(), invite.getClosingTime(), invite.getUniqueLink());

        Invite createdInvite = getInviteByUniqueLink(invite.getUniqueLink());

        return createdInvite;

    }


    public void addRestaurantToInvite(RestaurantInviteDTO restaurantInviteBundle) {

        //process a for loop of an array of these to bulk process

        int restaurantId = restaurantDao.createRestaurant(restaurantInviteBundle.getRestaurant());

        boolean defaultVeto = false;

        String sql = "INSERT INTO invite_restaurant (invite_id, restaurant_id, vetoed) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, restaurantInviteBundle.getInviteId(), restaurantId, defaultVeto);
    }

    private Invite mapRowToInvite(SqlRowSet rs) {

        Invite invite = new Invite();

        invite.setInviteId(rs.getInt("invite_id"));
        invite.setSenderUserId(rs.getInt("sender_user_id"));
        invite.setClosingDate(rs.getDate("closing_date"));
        invite.setClosingTime(rs.getTime("closing_time"));
        invite.setUniqueLink(rs.getString("unique_link"));

        return invite;
    }
}
