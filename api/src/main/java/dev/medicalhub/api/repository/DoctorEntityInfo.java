package dev.medicalhub.api.repository;

import dev.medicalhub.api.entity.ReceptionEntity;

import java.util.List;

/**
 * Projection for {@link dev.medicalhub.api.entity.DoctorEntity}
 */
public interface DoctorEntityInfo {
    List<ReceptionEntity> getHistoryReception();
}