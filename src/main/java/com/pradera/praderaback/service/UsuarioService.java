package com.pradera.praderaback.service;

import com.pradera.praderaback.dto.UsuarioDTO;
import com.pradera.praderaback.model.UsuarioModel;
import com.pradera.praderaback.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public List<UsuarioDTO> listar(){
        List<UsuarioModel> data = repository.findAll();
        List<UsuarioDTO> listadto = new ArrayList<>();
        for (UsuarioModel usuario : data){
            UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
            listadto.add(dto);
        }
        return listadto;
    }

    public UsuarioDTO obtener(Long id){
        return modelMapper.map(repository.findById(id).orElse(null), UsuarioDTO.class);
    }

    private void registrar(UsuarioDTO usuarioDTO){
        usuarioDTO.setPassword(new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()));
        repository.save(modelMapper.map(usuarioDTO, UsuarioModel.class));
    }

    private void actualizar(UsuarioDTO usuarioDTO){
        UsuarioModel usuarioModel = repository.findById(usuarioDTO.getId()).orElse(null);
        if(!usuarioModel.getPassword().equals(usuarioDTO.getPassword())){
            usuarioDTO.setPassword(new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()));
        }
        repository.save(modelMapper.map(usuarioDTO, UsuarioModel.class));
    }

    public void guardar(UsuarioDTO usuarioDTO){
        if(usuarioDTO.getId() == null){
            registrar(usuarioDTO);
        } else{
            actualizar(usuarioDTO);
        }
    }

    public void eliminar(Long id){
        repository.deleteById(id);
    }

    public boolean existsByUsername(String username){
        return repository.existsByUsername(username);
    }

    public boolean avaibleUsername(UsuarioDTO usuarioDTO){
        UsuarioModel usuarioModel = repository.findUserByUsername(usuarioDTO.getUsername(), usuarioDTO.getId()).orElse(null);
        return usuarioModel == null;
    }
}
