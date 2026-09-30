package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.ImovelModel;
import com.example.demo.repositories.ImovelRepository;

@Service
public class ImovelService {

    private final ImovelRepository repository;

    public ImovelService(ImovelRepository repository) {
        this.repository = repository;
    }

    public List<ImovelModel> getAll() {
        return repository.findAll();
    }

    public ImovelModel find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public ImovelModel insert(ImovelModel model) {
        return repository.save(model);
    }

    public ImovelModel update(ImovelModel model) {
        if (find(model.getId()) != null) {
            return repository.save(model);
        }
        return null;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
