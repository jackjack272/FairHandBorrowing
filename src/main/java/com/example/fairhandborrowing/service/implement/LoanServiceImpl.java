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

//        ListIterator itr = collaterals.listIterator();
//        while (itr.hasNext()) {
//            Collateral c = (Collateral) itr.next();
//            c.setLoan(loan);
//            collateralRepository.saveAndFlush(c);
//        }

//        collaterals.map(collateral -> {
////            collateral.setInUse(true);
//            collateral.setLoan(loan);
//            collateralRepository.save(collateral);
//        });
////
//        collaterals.forEach(collateral -> {
//            collateral.setLoan(loan);
//            collateralRepository.saveAndFlush(collateral);
//        });

//        List<Collateral> collaterals = loan.getCollaterals();
//        collaterals.stream().forEach(collateral -> {
//            collateral.setInUse(true);
//            collateral.setLoan(loan);
//            collateralRepository.save(collateral);
//        });

//        return loanResult;
    }
}
