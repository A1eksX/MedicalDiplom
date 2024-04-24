package dev.medicalhub.api.entity;

import dev.medicalhub.api.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reception")
public class ReceptionEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private String data;
    private StatusReception status;
    @ManyToOne
    @JoinColumn(name = "doctor_diploma_number")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;


    public ReceptionModel toDTOModel() {
        return (ReceptionModel) new ReceptionModel()
                .setDoctorDiplomaNumber(doctor.getDiplomaNumber())
                .setPatientId(patient.getId())
                .setDateTime(dateTime)
                .setData(data)
                .setId(id);
    }
    public ReceptionHistoryModel toDTOHistoryModel() {
        return (ReceptionHistoryModel) new ReceptionHistoryModel()
                .setPatientId(patient.getId())
                .setStatus(status)
                .setFullName(patient.getFullName())
                .setDateTime(dateTime)
                .setData(data)
                .setId(id);
    }

    public ReceptionWithPatient toDTOWithPatient() {
        return (ReceptionWithPatient) new ReceptionWithPatient()
                .setPatient(patient.toDTOModel())
                .setDateTime(dateTime)
                .setData(data)
                .setId(id);
    }

    public ReceptionWithPatientModel toDTOWithPatientModel() {
        return (ReceptionWithPatientModel) new ReceptionWithPatientModel()
                .setDoctorDiplomaNumber(doctor.getDiplomaNumber())
                .setPatient(patient.toDTOModel())
                .setDateTime(dateTime)
                .setData(data)
                .setId(id);
    }

    public ReceptionWithDoctor toReceptionWithDoctor() {
        return (ReceptionWithDoctor) new ReceptionWithDoctor()
                .setDoctor(doctor.toDTOWithMedicalInstitution())
                .setDateTime(dateTime)
                .setData(data)
                .setId(id);
    }
}
