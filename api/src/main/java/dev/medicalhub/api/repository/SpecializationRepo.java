package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.SpecializationEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationRepo extends CrudRepository<SpecializationEntity,String> {
    Optional<List<SpecializationEntity>> findByNameLike(String name);
}
