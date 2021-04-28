package by.dm13y.reactorcache.repository;

import by.dm13y.reactorcache.model.entity.Resource;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ResourceRepository extends R2dbcRepository<Resource, Long> {
}
