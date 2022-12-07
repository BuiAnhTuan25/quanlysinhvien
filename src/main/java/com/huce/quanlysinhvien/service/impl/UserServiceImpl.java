package com.huce.quanlysinhvien.service.impl;

import com.huce.quanlysinhvien.constains.StatusEnum;
import com.huce.quanlysinhvien.model.dto.UpdatePasswordDto;
import com.huce.quanlysinhvien.model.dto.UsersDto;
import com.huce.quanlysinhvien.model.entity.UsersEntity;
import com.huce.quanlysinhvien.model.request.UserSearchRequest;
import com.huce.quanlysinhvien.model.response.Data;
import com.huce.quanlysinhvien.model.response.ListData;
import com.huce.quanlysinhvien.model.response.Pagination;
import com.huce.quanlysinhvien.model.response.Response;
import com.huce.quanlysinhvien.repository.UserRepository;
import com.huce.quanlysinhvien.repository.custom.UserRepositoryCustom;
import com.huce.quanlysinhvien.security.CustomUserDetails;
import com.huce.quanlysinhvien.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final ModelMapper mapper;
    private final Response response;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Data getById(Long id){
        Optional<UsersEntity> user = userRepository.findByIdAndStatus(id, StatusEnum.ACTIVE);

        return user.map(data -> response.responseData("Get user successfully", mapper.map(data, UsersDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData search(UserSearchRequest request, int page, int pageSize) {
        List<UsersEntity> users = userRepositoryCustom.search(request, page, pageSize);
        List<UsersDto> usersDto = users.stream().map(p -> this.mapper.map(p, UsersDto.class)).collect(Collectors.toList());
        int total = userRepositoryCustom.count(request).intValue();
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        return new ListData(true, "Search user successfully",200, usersDto, new Pagination(page, pageSize, totalPage, total));
    }

    @Override
    public ListData getAll(int page, int pageSize) {
        Page<UsersEntity> users = userRepository.getAll(PageRequest.of(page, pageSize));

        return response.responseListData(users.getContent(), new Pagination(users.getNumber(), users.getSize(), users.getTotalPages(),
                (int) users.getTotalElements()));
    }

    @Override
    public Data createUser(UsersDto user){
        Optional<UsersEntity> optional = userRepository.findByUsername(user.getUsername());
        if (optional.isPresent()) return response.responseError("Username already exists");

        UsersEntity userEntity = new UsersEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setStatus(StatusEnum.ACTIVE);

        return response.responseData("Create successfully", mapper.map(userRepository.save(userEntity), UsersDto.class));
    }

    @Override
    public Data updateUser(UsersDto user, Long id){
        Optional<UsersEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) return response.responseError("User not found");
        UsersEntity userUpdate = userEntity.get().mapper(user);
        userUpdate.setId(id);
        return response.responseData("Update successfully", mapper.map(userRepository.save(userUpdate), UsersDto.class));
    }

    @Override
    public Data deleteUser(Long id) {
        Optional<UsersEntity> userEntity = userRepository.findById(id);

        return userEntity.map(data -> {
            data.setStatus(StatusEnum.INACTIVE);
            userRepository.save(data);
            return response.responseData("Delete user successfully", mapper.map(data, UsersDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UsersEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }

    // JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(Long id) {
        UsersEntity user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }

    @Override
    public Data changePassword(Long id, UpdatePasswordDto passwordDto) {
        Optional<UsersEntity> usersEntity = userRepository.findById(id);

        return usersEntity.map(data -> {
            if (!passwordEncoder.matches(passwordDto.getOldPassword(), data.getPassword()))
                return response.responseError("Incorrect password");
            if (passwordDto.getOldPassword().equals(passwordDto.getNewPassword()))
                return response.responseError("The new password must be different from the old password");

            data.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));

            return response.responseData("Change password successfully", mapper.map(userRepository.save(data), UsersDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }

}
