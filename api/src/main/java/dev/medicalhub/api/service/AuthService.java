package dev.medicalhub.api.service;

import dev.medicalhub.api.entity.DoctorEntity;
import dev.medicalhub.api.entity.MedicalInstitutionEntity;
import dev.medicalhub.api.entity.SpecializationEntity;
import dev.medicalhub.api.exception.BadRequestException;
import dev.medicalhub.api.exception.NotFoundException;
import dev.medicalhub.api.model.JwtResponse;
import dev.medicalhub.api.exception.AuthException;
import dev.medicalhub.api.model.RegistrationDoctor;
import dev.medicalhub.api.repository.DoctorRepo;
import dev.medicalhub.api.repository.MedicalInstitutionRepo;
import dev.medicalhub.api.repository.SpecializationRepo;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import dev.medicalhub.api.domain.JwtAuthentication;
import dev.medicalhub.api.model.JwtRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final DoctorService doctorService;
    private final DoctorRepo doctorRepo;
    private final MedicalInstitutionRepo medicalInstitutionRepo;
    private final SpecializationRepo specializationRepo;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final DoctorEntity doctor = getDoctorById(authRequest.getDiplomaNumber());

        if (!doctor.getPassword().equals(authRequest.getPassword()))
            throw new AuthException("Неправильный пароль");

        final String accessToken = jwtProvider.generateAccessToken(doctor);
        final String refreshToken = jwtProvider.generateRefreshToken(doctor);
        refreshStorage.put(String.valueOf(doctor.getDiplomaNumber()), refreshToken);
        return new JwtResponse(accessToken, refreshToken);

    }
    public JwtResponse registration(@NonNull RegistrationDoctor registrationDoctor) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setPassword(registrationDoctor.getPassword());
        doctorEntity.setFullName(registrationDoctor.getFullName());
        doctorEntity.setDiplomaNumber(registrationDoctor.getDiplomaNumber());

        if (!specializationRepo.existsById(registrationDoctor.getSpecialization()))
            throw new BadRequestException("Данной специальности нет в базе");

        doctorEntity.setSpecialization(
                new SpecializationEntity(registrationDoctor.getSpecialization()));

        if (!medicalInstitutionRepo.existsById(registrationDoctor.getMedicalInstitutionId()))
            throw new BadRequestException("Данного мед учреждения нет в базе");

        doctorEntity.setMedicalInstitution(
                new MedicalInstitutionEntity(registrationDoctor.getMedicalInstitutionId()));

       doctorEntity = doctorService.create(doctorEntity);
        JwtRequest authRequest = new JwtRequest();
        authRequest.setDiplomaNumber(doctorEntity.getDiplomaNumber());
        authRequest.setPassword(doctorEntity.getPassword());
        return login(authRequest);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final long diplomaNumber = Long.parseLong(claims.getSubject());
            final String saveRefreshToken = refreshStorage.get(diplomaNumber);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final DoctorEntity doctor = getDoctorById(diplomaNumber);
                final String accessToken = jwtProvider.generateAccessToken(doctor);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final long diplomaNumber = Long.parseLong(claims.getSubject());
            final String saveRefreshToken = refreshStorage.get(diplomaNumber);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final DoctorEntity doctor = getDoctorById(diplomaNumber);
                final String accessToken = jwtProvider.generateAccessToken(doctor);
                final String newRefreshToken = jwtProvider.generateRefreshToken(doctor);
                refreshStorage.put(String.valueOf(doctor.getDiplomaNumber()), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
    public DoctorEntity getDoctor(){
        return getDoctorById(Long.parseLong(getAuthInfo().getUsername()));
    }
    public DoctorEntity getDoctorById(long id){
        return doctorRepo.findByDiplomaNumber(id)
                .orElseThrow(()-> new NotFoundException("Доктор не найден"));
    }

}
