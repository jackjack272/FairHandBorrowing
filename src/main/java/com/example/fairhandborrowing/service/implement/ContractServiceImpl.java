package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.mapper.CollateralMapper;
import com.example.fairhandborrowing.model.*;
import com.example.fairhandborrowing.repository.CollateralRepository;
import com.example.fairhandborrowing.repository.LoanFundsRepository;
import com.example.fairhandborrowing.repository.UserRepository;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private LoanFundsRepository loanFundsRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void signContract(String userName, Long loanId) {
        List<LoanFunds> loanFunds = loanFundsRepository.findByAcceptedAndLoanId(true, loanId);
        UserEntity user = userRepository.findFirstByUsername(userName);

        loanFunds.forEach(lf -> {
            Contract contract = lf.getContract();
            if(user.getProfileType().getTypeName().equals("BORROWER")) {
                contract.setBorrowerSigned(true);
                contract.setBorrowerSignedDate(new Timestamp(new Date().getTime()));
            } else {
                if(user.getUserId() == lf.getLender().getUserId()) {
                    contract.setLenderSigned(true);
                    contract.setLenderSignedDate(new Timestamp(new Date().getTime()));
                }
            }
            loanFundsRepository.save(lf);
        });
    }
}
