package io.platformengineer.rdicpfhighvolume.person;

import com.redis.om.spring.repository.RedisDocumentRepository;

import java.util.List;

public interface PersonRedisRepository extends RedisDocumentRepository<PersonRedis,String> {

}
