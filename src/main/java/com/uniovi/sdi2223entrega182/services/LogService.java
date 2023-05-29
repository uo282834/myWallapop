package com.uniovi.sdi2223entrega182.services;

import com.uniovi.sdi2223entrega182.entities.Log;
import com.uniovi.sdi2223entrega182.entities.Offer;
import com.uniovi.sdi2223entrega182.repositories.LogRepository;
import com.uniovi.sdi2223entrega182.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogRepository repository;

    /**
     * Método que añade un log en el sistema
     *
     * @param log que se quiere añadir
     */
    public void addLog(Log log) {
        repository.save(log);
    }

    public void deleteLog(Long id) {
        repository.deleteById(id);
    }

    public Page<Log> getLogs(Pageable pageable) {
        Page<Log> list = repository.findAll(pageable);
        return list;
    }

    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<Log>();
        repository.findAll().forEach(logs::add);
        return logs;
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
