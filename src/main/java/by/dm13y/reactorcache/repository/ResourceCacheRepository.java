package by.dm13y.reactorcache.repository;

import by.dm13y.reactorcache.model.entity.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class ResourceCacheRepository {
    private final ResourceRepository resourceRepository;
    private final ReactiveRedisOperations<String, Resource> reactiveRedisOperations;

    public Flux<Resource> getResources(Long id) {
        return getResourceFromCache(id)
                .switchIfEmpty(resourceRepository.findById(id));
    }

    public void saveResources(Flux<Resource> resources) {
                resources.flatMap(resourceRepository::save)
                .flatMap(resource -> reactiveRedisOperations.opsForValue().set(resource.getIdAsString(), resource))
                .subscribe();
    }

    private Flux<Resource> getResourceFromCache(Long id) {
        return reactiveRedisOperations.keys(String.valueOf(id))
                .flatMap(reactiveRedisOperations.opsForValue()::get);
    }
}
