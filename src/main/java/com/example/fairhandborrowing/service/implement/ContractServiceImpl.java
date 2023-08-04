package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.model.*;
import com.example.fairhandborrowing.model.constants.Constants;
import com.example.fairhandborrowing.repository.LoanFundsRepository;
import com.example.fairhandborrowing.repository.LoanRepository;
import com.example.fairhandborrowing.repository.LoanStatusRepository;
import com.example.fairhandborrowing.repository.UserRepository;
import com.example.fairhandborrowing.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private LoanFundsRepository loanFundsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;


    @Override
    public void signContract(String userName, Long loanId) {
        List<LoanFunds> loanFunds = loanFundsRepository.findByAcceptedAndLoanId(true, loanId);
        UserEntity user = userRepository.findFirstByUsername(userName);

        AtomicBoolean completed = new AtomicBoolean(true);

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

            completed.set(completed.get() && contract.isBorrowerSigned() && contract.isLenderSigned());
        });

        if(completed.get()) {
            Optional<Loan> loan = loanRepository.findById(loanId);
            loan.get().setLoanStatus(loanStatusRepository.findLoanStatusByStatusName(Constants.ACTIVE));
            loanRepository.save(loan.get());
        }
    }
}
