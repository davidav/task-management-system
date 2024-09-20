package org.example.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private final User user = User.builder().id(1L).username("user").build();

    @Test
    public void testFindByIdSuccess() {

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        User returnedUser = userService.findById(1L);

        assertThat(returnedUser.getId()).isEqualTo(1L);
        assertThat(returnedUser.getUsername()).isEqualTo("user");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdFail() {

        given(userRepository.findById(1L)).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> userService.findById(1L));

        assertThat(thrown).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User with id 1 not found");
        verify(userRepository, times(1)).findById(1L);

    }

    @Test
    public void testFindAll() {
        User user1 = User.builder().id(2L).username("user1").build();
        List<User> users = List.of(user, user1);
        given(userRepository.findAll()).willReturn(users);

        List<User> returnedUsers = userService.findAll();

        assertThat(returnedUsers.get(0).getId()).isEqualTo(1L);
        assertThat(returnedUsers.get(0).getUsername()).isEqualTo("user");
        assertThat(returnedUsers.get(1).getId()).isEqualTo(2L);
        assertThat(returnedUsers.get(1).getUsername()).isEqualTo("user1");

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testCreateUser() {

        given(userRepository.save(user)).willReturn(user);

        User savedUser = userService.create(user);

        assertThat(savedUser.getId()).isEqualTo(1L);
        assertThat(savedUser.getUsername()).isEqualTo("user");
        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void testUpdateUser() {

        User updateUser = User.builder().id(1L).username("UserUpdate").build();
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(updateUser);

        User savedUser = userService.update(1L, updateUser);

        assertThat(savedUser.getId()).isEqualTo(1L);
        assertThat(savedUser.getUsername()).isEqualTo("UserUpdate");
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void testDeleteById() {
        userService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteById_ThrowsExceptionWhenUserNotFound() {

        doThrow(new EmptyResultDataAccessException(1)).when(userRepository).deleteById(1L);

        assertThrows(EmptyResultDataAccessException.class, () -> userService.deleteById(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }
}