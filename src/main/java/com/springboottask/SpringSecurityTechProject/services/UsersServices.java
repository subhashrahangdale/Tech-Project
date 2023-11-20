package com.springboottask.SpringSecurityTechProject.services;

import com.springboottask.SpringSecurityTechProject.entity.Users;
import com.springboottask.SpringSecurityTechProject.repository.UsersRepository;
import com.springboottask.SpringSecurityTechProject.utils.PasswordUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UsersServices implements UserDetailsService {
    private @NonNull UsersRepository usersRepository;

    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public void saveAll(List<Users> usersList) {
        usersRepository.saveAll(usersList);
    }

    public Optional<Users> getUsersById(Long id) {
        return usersRepository.findById(id);
    }
    public List<Users> getAllUser() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserByUserName(String userName) {
        return usersRepository.findUsersByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> usersByUserName = usersRepository.findUsersByUserName(username);
        return  usersByUserName.orElse(null);
    }
}
