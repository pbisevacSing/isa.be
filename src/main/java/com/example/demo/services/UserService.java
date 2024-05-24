package com.example.demo.services;

import com.example.demo.exceptions.user.UserAlreadyExistException;
import com.example.demo.exceptions.user.UserException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.mappers.UserProductsMapper;
import com.example.demo.models.UserModel;
import com.example.demo.models.UserPageModel;
import com.example.demo.models.UserProductsModel;
import com.example.demo.repositories.IUserProductsRepository;
import com.example.demo.repositories.IUserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IUserProductsRepository userProductsRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserModel> findAll() {
        var result = userRepository.findAll();
        return UserMapper.toModelList(result);
    }

    @Override
    public UserPageModel findPagedList(PageRequest pageRequest) {
        var result = userRepository.findAll(pageRequest);
        return UserMapper.toModelPagedList(result);
    }

    @Override
    public UserModel create(UserModel model) {
        var entity = UserMapper.toEntity(model, passwordEncoder);

        var result = userRepository.save(entity);

        return UserMapper.toModel(result);
    }

    @Override
    public UserModel update(UserModel model) {
        var entity = UserMapper.toEntity(model, passwordEncoder);
        try {
            var result = userRepository.save(entity);
            return UserMapper.toModel(result);
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public List<UserProductsModel> findUserProductsAll() {
        var result = userProductsRepository.findAll();
        return UserProductsMapper.toModelList(result);
    }
}
