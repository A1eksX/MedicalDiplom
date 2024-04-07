package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.SpecializationEntity;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.repository.SpecializationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SpecializationRepo specializationRepo;

    public List<SpecializationEntity> search(String name){
        return  specializationRepo.findByNameLike("%"+name+"%")
                .orElseThrow(()->new NotFoundException("Специализация не найдена"));
    }
    public SpecializationEntity exists(String name){
        if (specializationRepo.existsById(name))
            return specializationRepo.findById(name)
                    .orElseThrow(()->new NotFoundException("Специализация не найдена"));
        SpecializationEntity specialization = new SpecializationEntity();
        specialization.setName(name);
        return specializationRepo.save(specialization);

    }
}
