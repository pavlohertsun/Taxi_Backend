package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.SettingsDto;
import com.example.taxi_backend.entities.Log;
import com.example.taxi_backend.entities.Settings;
import com.example.taxi_backend.repositories.LogRepository;
import com.example.taxi_backend.repositories.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    private SettingsRepository settingsRepository;
    private LogRepository logRepository;

    @Autowired
    public SettingsController(SettingsRepository settingsRepository, LogRepository logRepository) {
        this.settingsRepository = settingsRepository;
        this.logRepository = logRepository;
    }
    @GetMapping("")
    public SettingsDto getCurrentSettings(){
        Settings settings = settingsRepository.findById(1L).get();

        return new SettingsDto(settings);
    }
    @PutMapping("")
    public boolean saveSettings(@RequestBody SettingsDto settingsDto){
        Settings settings = settingsRepository.findById(1L).get();

        settings.setRate(settingsDto.getRate());
        settings.setPercent(settingsDto.getPercent());

        settingsRepository.save(settings);

        Log log = new Log();
        log.setMessage("System settings was updated.");
        logRepository.save(log);

        return true;
    }
}
