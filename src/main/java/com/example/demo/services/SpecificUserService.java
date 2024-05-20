package com.example.demo.services;

import com.example.demo.mappers.UserMapper;
import com.example.demo.mappers.UserProductsMapper;
import com.example.demo.models.UserModel;
import com.example.demo.models.UserPageModel;
import com.example.demo.models.UserProductsModel;
import com.example.demo.repositories.IUserProductsRepository;
import com.example.demo.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecificUserService implements IUserService {
    private final IUserRepository userRepository;
    private final IUserProductsRepository userProductsRepository;

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
        var entity = UserMapper.toEntity(model);

        var result = userRepository.save(entity);

        return UserMapper.toModel(result);
    }

    @Override
    public List<UserProductsModel> findUserProductsAll() {
        var result = userProductsRepository.findAll();
        return UserProductsMapper.toModelList(result);
    }

    @Override
    public String getNameOfImplementer() {
        return "THIS SPECIFIC USER SERVICE";
    }
}
