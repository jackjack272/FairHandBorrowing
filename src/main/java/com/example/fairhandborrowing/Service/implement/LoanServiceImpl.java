package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.DTO.LoanDto;
import com.example.fairhandborrowing.Model.Collateral;
import com.example.fairhandborrowing.Model.Loan;
import com.example.fairhandborrowing.Model.UserEntity;
import com.example.fairhandborrowing.Repository.CollateralRepository;
import com.example.fairhandborrowing.Repository.LoanRepository;
import com.example.fairhandborrowing.Repository.UserRepository;
import com.example.fairhandborrowing.Service.LoanService;
import com.example.fairhandborrowing.mapper.LoanMapper;
import com.example.fairhandborrowing.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollateralRepository collateralRepository;

    @Override
    public List<LoanDto> getAllLoansByUserId(Long userId) {

        List<Loan> loans = loanRepository.findAllByUserUserId(userId).get();
        return loans.stream().map(loan -> LoanMapper.mapToDto(loan)).collect(Collectors.toList());
    }

    @Override
    public LoanDto createLoan(String userName, LoanDto loanDto) {
        UserEntity user = userRepository.findFirstByUsername(userName);

        Loan loan = LoanMapper.mapToModel(loanDto);
        loan.setUser(user);

    List<Collateral> collaterals =
        loan.getCollateralList().stream()
            .map(collateral -> collateralRepository.findById(collateral.getId()).get()).collect(Collectors.toList());

    collaterals.stream().forEach(collateral -> {
        collateral.setInUse(true);
        collateralRepository.save(collateral);
    });
    loan.setCollateralList(collaterals);
        Loan loanResult = loanRepository.save(loan);
        return LoanMapper.mapToDto(loanResult);
    }
}
