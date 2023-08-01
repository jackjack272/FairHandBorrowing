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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private static Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollateralRepository collateralRepository;

    @Override
    public List<Loan> getAllLoansByUserId(Long userId) {
        List<Loan> loans = loanRepository.findAllByUserUserId(userId).get();
        return loans;
    }

    @Override
    public List<Loan> getAllNonArchivedLoansByUserId(Long userId) {
        List<Loan> loans = getAllLoansByUserId(userId);

        List<Loan> filteredLoans = loans.stream().filter(loan -> !loan.isArchived()).collect(Collectors.toList());
        return filteredLoans;
    }

    @Override
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId).get();
    }

    @Override
    public void createLoan(String userName, LoanDto loanDto) {
        UserEntity user = userRepository.findFirstByUsername(userName);
        Loan loan = LoanMapper.mapToModel(loanDto);
        loan.setUser(user);
        loanRepository.save(loan);

        List<Collateral> collaterals = loan.getCollaterals();

        for(int i = 0; i < collaterals.size(); i++) {
            Collateral c = collaterals.get(i);
            c.setLoan(loan);
            collateralRepository.saveAndFlush(c);
        }
    }

    @Override
    public void editLoan(Long loanId, LoanDto loanDto, String[] collaterals) {
        Loan loan = getLoanById(loanId);
        loan.setDescription(loanDto.getDescription());
        loan.setAmountBorrowed(loanDto.getAmountBorrowed());
        loan.setInterestRate(loanDto.getInterestRate());
        loan.setMonthsToPay(loanDto.getMonthsToPay());
        List<Collateral> collateralAccumulator = new ArrayList<>();

        if(collaterals != null) {
            for(String col : collaterals) {
                Collateral collateral = collateralRepository.findById(Long.valueOf(col)).get();
                collateral.setLoan(loan);
                collateralAccumulator.add(collateral);
            }
            loan.setCollaterals(collateralAccumulator);
        }

        loanRepository.save(loan);
    }

    @Override
    public void archiveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).get();
        loan.setArchived(true);

        loanRepository.save(loan);
    }
}
