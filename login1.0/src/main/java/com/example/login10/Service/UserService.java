package com.example.login10.Service;

import com.example.login10.DTO.UserDTO;
import com.example.login10.Exception.UserEmailAlreadyRegistered;
import com.example.login10.Exception.UserNotFound;
import com.example.login10.Repository.UserRepository;
import com.example.login10.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean verifyIfEmailIsRegistered(String email){
        Optional<User> userOptional= userRepository.findByEmail(email);
        if(userOptional.isPresent()) return false;
        else return true;
    }

    public String save(UserDTO userDTO) throws UserEmailAlreadyRegistered {
        if(verifyIfEmailIsRegistered(userDTO.getEmail())){
        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
        userRepository.save(user);
        return "Usuário Salvo com sucesso";}
        else {
            throw new UserEmailAlreadyRegistered();
        }

    }


    public UserDTO findUserByEmail(String email) throws UserNotFound {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
        return UserDTO.builder()
                .id(user.get().getId())
                .name(user.get().getName())
                .email(user.get().getEmail())
                .password((user.get().getPassword()))
                .build();}
        else{
            throw new UserNotFound("Usuário com email "+ email+" não existe");
        }

    }

    public String updatePassword(String email, String password) throws  UserNotFound{

        UserDTO userDTO = findUserByEmail(email);
        userDTO.setPassword(password);
        User user = new User(userDTO.getId(),userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
        userRepository.save(user);
       return "Senha atualizada com sucesso";
    }
}
