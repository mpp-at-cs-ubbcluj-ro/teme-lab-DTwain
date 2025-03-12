package ro.mpp2024.repository;

import ro.mpp2024.domain.Honey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HoneyDbRepository implements HoneyRepository {
    private static final Logger log = LogManager.getLogger(HoneyDbRepository.class);
    private String url;

    public HoneyDbRepository() {
        log.info("Initializing HoneyDbRepository...");
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            props.load(fis);
            this.url = props.getProperty("db.url");
            log.debug("Loaded db.url = {}", this.url);
        } catch (IOException e) {
            log.error("Failed to load db.properties", e);
        }
    }

    @Override
    public void add(Honey honey) {
        log.info("Adding new honey record: {}", honey);
        String sql = "INSERT INTO Honey(Sortiment, Cantitate, Concentratie_polen_la_suta, " +
                "Concentratie_glucoza_la_suta, Concentratie_fructoza_la_suta) " +
                "VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, honey.getSortiment());
            stmt.setString(2, honey.getCantitate());
            stmt.setDouble(3, honey.getConcentratiePolenLaSuta());
            stmt.setDouble(4, honey.getConcentratieGlucozaLaSuta());
            stmt.setDouble(5, honey.getConcentratieFructozaLaSuta());

            int rows = stmt.executeUpdate();
            log.info("Inserted {} row(s) into Honey table.", rows);

        } catch (SQLException e) {
            log.error("Error inserting honey: {}", honey, e);
        }
    }

    @Override
    public void update(Honey honey) {
        log.info("Updating honey record: {}", honey);
        String sql = "UPDATE Honey " +
                "SET Cantitate=?, Concentratie_polen_la_suta=? " +
                "WHERE Sortiment=?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, honey.getCantitate());
            stmt.setDouble(2, honey.getConcentratiePolenLaSuta());
            stmt.setString(3, honey.getSortiment());

            int rows = stmt.executeUpdate();
            log.info("Updated {} row(s) in Honey table for sortiment={}.", rows, honey.getSortiment());

        } catch (SQLException e) {
            log.error("Error updating honey: {}", honey, e);
        }
    }

    @Override
    public List<Honey> findAll() {
        log.info("Retrieving all honey records...");
        List<Honey> result = new ArrayList<>();
        String sql = "SELECT Sortiment, Cantitate, Concentratie_polen_la_suta, " +
                "Concentratie_glucoza_la_suta, Concentratie_fructoza_la_suta " +
                "FROM Honey";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String sortiment = rs.getString("Sortiment");
                String cantitate = rs.getString("Cantitate");
                double polen = rs.getDouble("Concentratie_polen_la_suta");
                double glucoza = rs.getDouble("Concentratie_glucoza_la_suta");
                double fructoza = rs.getDouble("Concentratie_fructoza_la_suta");
                Honey honey = new Honey(sortiment, cantitate, polen, glucoza, fructoza);
                result.add(honey);
            }
            log.info("Retrieved {} honey record(s).", result.size());

        } catch (SQLException e) {
            log.error("Error retrieving all honey records", e);
        }
        return result;
    }

    @Override
    public List<Honey> findByPolenGreaterThan(double threshold) {
        log.info("Finding honey records where polen > {}", threshold);
        List<Honey> result = new ArrayList<>();
        String sql = "SELECT Sortiment, Cantitate, Concentratie_polen_la_suta, " +
                "Concentratie_glucoza_la_suta, Concentratie_fructoza_la_suta " +
                "FROM Honey WHERE Concentratie_polen_la_suta > ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, threshold);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String sortiment = rs.getString("Sortiment");
                    String cantitate = rs.getString("Cantitate");
                    double polen = rs.getDouble("Concentratie_polen_la_suta");
                    double glucoza = rs.getDouble("Concentratie_glucoza_la_suta");
                    double fructoza = rs.getDouble("Concentratie_fructoza_la_suta");
                    Honey honey = new Honey(sortiment, cantitate, polen, glucoza, fructoza);
                    result.add(honey);
                }
            }
            log.info("Found {} honey record(s) with polen > {}.", result.size(), threshold);

        } catch (SQLException e) {
            log.error("Error finding honey by polen > {}", threshold, e);
        }
        return result;
    }
}
