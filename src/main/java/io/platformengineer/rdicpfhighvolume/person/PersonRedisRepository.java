package io.platformengineer.rdicpfhighvolume.person;

import com.redis.om.spring.repository.RedisDocumentRepository;

public interface PersonRedisRepository extends RedisDocumentRepository<PersonRedis,String> {


}
