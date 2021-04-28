package by.dm13y.reactorcache.service.impl;

import by.dm13y.reactorcache.model.entity.Resource;
import by.dm13y.reactorcache.repository.ResourceCacheRepository;
import by.dm13y.reactorcache.repository.ResourceRepository;
import by.dm13y.reactorcache.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ReactiveRedisOperations<String, Resource> reactiveRedisOperations;
    private final ResourceCacheRepository resourceCacheRepository;

    @Override
    public Flux<Resource> getAllResourcesFromPostgres() {
        return resourceRepository.findAll();
    }

    @Override
    public void generateResourceToPostgres(Integer count) {
        Flux.range(0, count)
                .map(val -> generateResource())
                .flatMap(resourceRepository::save)
                .subscribe(resource -> log.info("Postgres. Resource with id: {} is created", resource));

    }

    @Override
    public void generateResourceToRedis(Integer count) {
        Flux.range(0, count)
                .map(item -> generateResource())
                .flatMap(resource -> reactiveRedisOperations.opsForValue().set(resource.getIdAsString(), resource))
                .subscribe(resource -> log.info("Redis. Resource with id{} is created", resource));
    }

    @Override
    public Flux<Resource> getAllResourcesFromRedis() {
        return reactiveRedisOperations.keys("*")
                .flatMap(reactiveRedisOperations.opsForValue()::get);
    }

    @Override
    public Flux<Resource> getAllResourcesFromCache(Long resourceId) {
        return resourceCacheRepository.getResources(resourceId);
    }

    @Override
    public void generateResourceToCache(Integer resourceId) {
        val resource = generateResource();
        resource.setId(resourceId);
        resourceCacheRepository.saveResources(Flux.just(resource));
    }

    private Resource generateResource() {
        val id = ThreadLocalRandom.current().nextInt();
        val name = String.valueOf(Math.random());
        return new Resource(id, name, true);
    }
}
