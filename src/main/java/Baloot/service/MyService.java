package Baloot.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    private JdbcTemplate jdbcTemplate;

    public MyService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void testConnection() {
        try {
            String sql = "SELECT 1";
            jdbcTemplate.queryForObject(sql, Integer.class);
            System.out.println("Connected to database.");
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }
}