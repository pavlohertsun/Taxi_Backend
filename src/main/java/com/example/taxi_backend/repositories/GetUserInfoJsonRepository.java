package com.example.taxi_backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GetUserInfoJsonRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GetUserInfoJsonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getCustomerData(Long userId) {
        String sql = "SELECT get_customer_data(?)";
        Object[] params = { userId };

        String result = jdbcTemplate.queryForObject(sql, params, String.class);

        return result;
    }
    public String getDriverData(Long userId) {
        String sql = "SELECT get_driver_data(?)";
        Object[] params = { userId };

        String result = jdbcTemplate.queryForObject(sql, params, String.class);

        return result;
    }
}
