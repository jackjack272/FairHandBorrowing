package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.repository.CollateralRepository;
import com.example.fairhandborrowing.repository.LoanRepository;
import com.example.fairhandborrowing.repository.UserRepository;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.mapper.LoanMapper;
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
