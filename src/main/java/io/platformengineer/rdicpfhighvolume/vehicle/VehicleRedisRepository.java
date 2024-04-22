package io.platformengineer.rdicpfhighvolume.vehicle;

import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.awt.*;
import java.util.List;

public interface VehicleRedisRepository extends RedisDocumentRepository<VehicleRedis, Long> {
    List<VehicleRedis> findByLocationNear(Point location, Distance distance);

}
