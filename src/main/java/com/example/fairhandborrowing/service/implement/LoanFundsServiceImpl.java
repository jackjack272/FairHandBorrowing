package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.repository.LoanFundsRepository;
import com.example.fairhandborrowing.service.LoanFundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanFundsServiceImpl implements LoanFundsService {

    private final LoanFundsRepository loanFundsRepository;

    @Autowired
    public LoanFundsServiceImpl(LoanFundsRepository loanFundsRepository) {
        this.loanFundsRepository = loanFundsRepository;
    }

    @Override
    public void sendFundRequest(UserEntity borrower, UserEntity lender, Loan loan) {
        LoanFunds fund = new LoanFunds();
        fund.setBorrower(borrower);
        fund.setLender(lender);
        fund.setLoan(loan);
        fund.setAccepted(false);

        loanFundsRepository.save(fund);
    }

    @Override
    public void acceptFundRequest(LoanFunds fund) {
        fund.setAccepted(true);

        loanFundsRepository.save(fund);
    }

    @Override
    public List<LoanFunds> getPendingRequestsForUser(UserEntity user) {
        return loanFundsRepository.findByAcceptedAndLender(false, user);
    }

}
