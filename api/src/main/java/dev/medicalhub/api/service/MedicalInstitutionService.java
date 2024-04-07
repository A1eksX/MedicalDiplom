package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.MedicalInstitutionEntity;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.repository.MedicalInstitutionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalInstitutionService {
    private final MedicalInstitutionRepo medicalInstitutionRepo;
    public MedicalInstitutionEntity exists(String name){
            return medicalInstitutionRepo.findByName(name)
                    .orElseThrow(()-> new NotFoundException("Медучереждение не найдено"));
    }
    public MedicalInstitutionEntity findById(Long id){
        return  medicalInstitutionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Медучереждение не найдено"));
    }

    public List<MedicalInstitutionEntity> search(String search) {
        return  medicalInstitutionRepo.findByNameLikeOrAddressLike("%"+search+"%","%"+search+"%");
    }
}
