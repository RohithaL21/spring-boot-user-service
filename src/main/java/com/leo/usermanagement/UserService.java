package com.leo.usermanagement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<UserDTO> getUsers() {
    List<UserEntity> users = userRepository.findAll();
    List<UserDTO> response = new ArrayList<>();

    for (UserEntity user : users) {
      UserDTO userDTO = new UserDTO(
          user.getId(),
          user.getName(),
          user.getEmail());

      response.add(userDTO);
    }

    return response;
  }

  @Transactional
  public UserDTO saveUser(UserDTO userDTO) {
    UserEntity user = UserEntity.builder()
        .name(userDTO.name())
        .email(userDTO.email())
        .build();

    UserEntity savedUser = userRepository.save(user);

    return new UserDTO(
        savedUser.getId(),
        savedUser.getName(),
        savedUser.getEmail());
  }

  @Transactional
  public UserDTO updateUser(UserDTO userDTO) {
    UserEntity user = userRepository.findById(userDTO.id())
        .orElseThrow(() -> new RuntimeException("User not found"));

    user.setName(userDTO.name());
    user.setEmail(userDTO.email());

    UserEntity savedUser = userRepository.save(user);

    return new UserDTO(
        savedUser.getId(),
        savedUser.getName(),
        savedUser.getEmail());
  }

  @Transactional
  public String deleteUser(Long id) {
    userRepository.deleteById(id);
    return "User with id " + id + "successfully deleted";
  }
}
