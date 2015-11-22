package it.egidiocaprino.busyplace.repository;

import it.egidiocaprino.busyplace.model.Position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(nativeQuery = true, value = "SELECT p1.* "
                                     + "FROM position p1 LEFT JOIN position p2 "
                                     + "ON (p1.device_id = p2.device_id AND p1.date < p2.date) "
                                     + "WHERE p2.id IS NULL AND p1.date >= (current_timestamp - INTERVAL '1 hour') "
                                     + "AND distance(p1.latitude, p1.longitude, ?1, ?2) <= ?3")
    List<Position> getLastHourPositions(double centerLatitude, double centerLongitude, double maxDistance);

    @Query(nativeQuery = true, value = "SELECT count(p1.*) "
                                     + "FROM position p1 LEFT JOIN position p2 "
                                     + "ON (p1.device_id = p2.device_id AND p1.date < p2.date) "
                                     + "WHERE p2.id IS NULL AND p1.date >= (current_timestamp - INTERVAL '1 hour') "
                                     + "AND distance(p1.latitude, p1.longitude, ?1, ?2) <= ?3")
    Integer getLastHourPositionsCount(double centerLatitude, double centerLongitude, double maxDistance);

}
