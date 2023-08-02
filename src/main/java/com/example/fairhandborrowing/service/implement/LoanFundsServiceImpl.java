package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.mapper.LoanMapper;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.repository.LoanFundsRepository;
import com.example.fairhandborrowing.service.LoanFundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    public List<LoanFunds> getPendingRequestsForUser(UserEntity user) {
        return loanFundsRepository.findByAcceptedAndLender(false, user);
    }

    @Override
    public void acceptFundRequest(Long fundLoanId, Double fundAmount) {
        LoanFunds loanFunds = loanFundsRepository.findById(fundLoanId).get();
        loanFunds.setAccepted(true);
        loanFunds.setFundAmount(fundAmount);
        loanFundsRepository.save(loanFunds);
    }

    @Override
    public Double calculateLoanFundingProgress(LoanDto loanDto) {
        List<LoanFunds> loanFunds = loanFundsRepository.findByLoanId(loanDto.getId());
        Double fundingProgress = 0.0;
        Double totalFundingNeeded = loanDto.getAmountBorrowed();
        AtomicReference<Double> totalFunded = new AtomicReference<>(0.0);

        loanFunds.stream().forEach(lf -> {
            if(lf.getFundAmount() != null)
                totalFunded.updateAndGet(v -> v + lf.getFundAmount());
        });

        if(totalFunded.get() == 0.0 || totalFundingNeeded == 0.0) return totalFunded.get();

        return totalFunded.get()/totalFundingNeeded;
    }
}
