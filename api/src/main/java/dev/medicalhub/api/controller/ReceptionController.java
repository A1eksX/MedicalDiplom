package dev.medicalhub.api.controller;

import dev.medicalhub.api.model.ReceptionHistoryModel;
import dev.medicalhub.api.model.ReceptionModel;
import dev.medicalhub.api.model.ReceptionWithPatientModel;
import dev.medicalhub.api.service.AuthService;
import dev.medicalhub.api.service.DoctorService;
import dev.medicalhub.api.service.ReceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("api/reception")
@RequiredArgsConstructor
public class ReceptionController {
    private final ReceptionService receptionService;

    private final AuthService authService;
    @PostMapping
    public ReceptionModel create(@RequestBody ReceptionModel receptionModel) {
            return receptionService.create(receptionModel);
    }
    @PatchMapping("/{id}")
    public void End(@PathVariable long id, String data, boolean isCancelled) {
        var authServiceAuthInfo  = authService.getAuthInfo();
        receptionService.end(id, Long.parseLong(authServiceAuthInfo.getUsername()),isCancelled,data);
    }

    @GetMapping
    public List<ReceptionWithPatientModel> getIncomplete() {
        var authServiceAuthInfo  = authService.getAuthInfo();
        return receptionService.getIncomplete(Long.parseLong(authServiceAuthInfo.getUsername()));
    }

    @GetMapping("/history")
    public List<ReceptionHistoryModel> getHistory() {
            var authServiceAuthInfo  = authService.getAuthInfo();
            return receptionService.getHistory(Long.parseLong(authServiceAuthInfo.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        receptionService.deleteReception(id);
        return ResponseEntity.noContent().build();
    }
}
