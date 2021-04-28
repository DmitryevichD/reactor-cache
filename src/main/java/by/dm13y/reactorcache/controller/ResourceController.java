package by.dm13y.reactorcache.controller;

import by.dm13y.reactorcache.model.entity.Resource;
import by.dm13y.reactorcache.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping("/postgres")
    public Flux<Resource> getResourceFromPostgres() {
        return resourceService.getAllResourcesFromPostgres();
    }

    @PostMapping("/postgres/generate/{count}")
    public void generateResourcesToPostgres(@PathVariable Integer count) {
        resourceService.generateResourceToPostgres(count);
    }

    @GetMapping("/redis")
    public Flux<Resource> getResourceFromRedis() {
        return resourceService.getAllResourcesFromRedis();
    }

    @PostMapping("/redis/generate/{count}")
    public void generateResourcesToRedis(@PathVariable Integer count) {
        resourceService.generateResourceToRedis(count);
    }

    @GetMapping("/cache/{resourceId}")
    public Flux<Resource> getResourcesFromCache(@PathVariable Long resourceId) {
        return resourceService.getAllResourcesFromCache(resourceId);
    }

    @PostMapping("/cache/{resourceId}")
    public void generateResourcesToCache(@PathVariable Integer resourceId) {
        resourceService.generateResourceToCache(resourceId);
    }
}
