package by.dm13y.reactorcache.service;

import by.dm13y.reactorcache.model.entity.Resource;
import reactor.core.publisher.Flux;

public interface ResourceService {
    Flux<Resource> getAllResourcesFromPostgres();

    void generateResourceToPostgres(Integer count);

    void generateResourceToRedis(Integer count);

    Flux<Resource> getAllResourcesFromRedis();

    Flux<Resource> getAllResourcesFromCache(Long resourceId);

    void generateResourceToCache(Integer count);
}
