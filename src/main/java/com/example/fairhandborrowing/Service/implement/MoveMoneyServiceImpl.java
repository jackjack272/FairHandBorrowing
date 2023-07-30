package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.Controller.HomeController;
import com.example.fairhandborrowing.DTO.MoveMoneyDto;
import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.MoveMoney;
import com.example.fairhandborrowing.Repository.MoveMoneyRepository;
import com.example.fairhandborrowing.Service.MoveMoneyService;
import com.example.fairhandborrowing.Service.UserService;
import com.example.fairhandborrowing.mapper.MoveMoneyMapper;
import com.example.fairhandborrowing.mapper.UserMapper;
import com.example.fairhandborrowing.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MoveMoneyServiceImpl implements MoveMoneyService {
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    MoveMoneyRepository repository;
    MoveMoneyMapper mapper;
    UserService userService;


    public MoveMoneyServiceImpl(MoveMoneyRepository repository, MoveMoneyMapper mapper, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public void save(MoveMoneyDto money) {
        String username = SecurityUtil.getSessionUser();
        UserRegistrationDto user=  userService.findByUserName(username);
        LOGGER.info(user.getUserName());

        money.setUser(UserMapper.mapToModel(user));
        MoveMoney money_=mapper.mapToEntity(money);
//        repository.save(money_);
    }


}
