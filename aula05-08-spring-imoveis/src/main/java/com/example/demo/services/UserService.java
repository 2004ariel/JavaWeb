package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.UserDTO;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> find(Long id) {
        return userRepository.findById(id);
    }

    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    public Optional<UserModel> update(Long id, UserModel user) {
        Optional<UserModel> usuarioExiste = find(id);
        if (usuarioExiste.isPresent()) {
            UserModel existingUser = usuarioExiste.get();
            existingUser.setNome(user.getNome());
            existingUser.setEmail(user.getEmail());
            existingUser.setSenha(user.getSenha());
            existingUser.setTipo(user.getTipo());
            return Optional.of(userRepository.save(existingUser));
        }
        return Optional.empty();
    }

    public UserModel insert(UserDTO dto) {
        UserModel model = new UserModel();
        model.setNome(dto.getNome());
        model.setEmail(dto.getEmail());
        model.setTipo(dto.getTipo());
        model.setSenha(dto.getSenha());
        return userRepository.save(model);
    }

    public UserModel updateFromDto(Long id, UserDTO dto) {
        Optional<UserModel> existente = find(id);
        if (existente.isEmpty()) return null;
        UserModel model = existente.get();
        if (dto.getNome() != null) model.setNome(dto.getNome());
        if (dto.getEmail() != null) model.setEmail(dto.getEmail());
        if (dto.getTipo() != null) model.setTipo(dto.getTipo());
        if (dto.getSenha() != null) model.setSenha(dto.getSenha());
        return userRepository.save(model);
    }

    public boolean delete(Long id) {
        Optional<UserModel> usuarioExiste = find(id);
        if (usuarioExiste.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
