package com.igo.catalogDeProducts.services;

import com.igo.catalogDeProducts.DTO.UserDTO;
import com.igo.catalogDeProducts.DTO.UserInsertDTO;
import com.igo.catalogDeProducts.DTO.UserUpdateDTO;
import com.igo.catalogDeProducts.exceptions.ResourceNotFoundException;
import com.igo.catalogDeProducts.models.User;
import com.igo.catalogDeProducts.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public Page<UserDTO> findAllPage(Pageable pageable){
        Page<User> list = userRepository.findAll(pageable);
        return list.map(x -> new UserDTO(x));
    }

    public UserDTO findById(Long id){
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new UserDTO(entity);
    }

    public UserDTO insert(UserInsertDTO dto){
        User entity = new User();
        User userExiste = userRepository.findByEmail(entity.getEmail());

        if (userExiste != null){
            throw new RuntimeException("Usuario já existe com esse email");
        }
        copyDtoToEntity(dto, entity);

        entity.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        entity = userRepository.save(entity);

        return new UserDTO(entity);
    }

    public UserDTO update(Long id, UserUpdateDTO dto){
       Optional<User> userEncontrado = userRepository.findById(id);

       if (userEncontrado.isPresent()){
           User entity = userEncontrado.get();
           copyDtoToEntity(dto, entity);
           return new UserDTO(entity);
       }else {
           throw new ResourceNotFoundException(id);
       }
    }

    public void delete(Long id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity){
        entity.setFirsName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("E-mail não encontrado");
        }
        return user;
    }
}
