package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.ContractDto;
import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.*;
import com.example.fairhandborrowing.model.constants.Constants;
import com.example.fairhandborrowing.repository.*;
import com.example.fairhandborrowing.service.LoanFundsService;
import com.example.fairhandborrowing.service.LoanService;
import com.example.fairhandborrowing.mapper.LoanMapper;
import groovy.util.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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

    @Autowired
    private LoanFundsService loanFundsService;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    @Autowired
    private LoanFundsRepository loanFundsRepository;

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
        loan.setLoanStatus(loanStatusRepository.findLoanStatusByStatusName(Constants.NOT_STARTED));
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
        List<Collateral> collaterals = loan.getCollaterals();

        collaterals.stream().forEach(collateral -> {
            collateral.setLoan(null);
            collateralRepository.saveAndFlush(collateral);
        });
        loan.setCollaterals(null);
        loanRepository.save(loan);
    }

    @Override
    public void prepareDtos(List<LoanDto> loanDtos, List<Loan> loans) {
        loans.stream().forEach(loan -> {
            LoanDto dto = LoanMapper.mapToDto(loan);
            loanDtos.add(dto);
        });

        loanDtos.stream().forEach(loanDto -> {
            StringBuilder colIdStrBuilder = new StringBuilder();
            loanDto.getCollaterals().stream().forEach(collateral -> {
                colIdStrBuilder.append(collateral.getItemName()).append(", ");
            });
            if(loanDto.getCollaterals() == null || loanDto.getCollaterals().size() == 0)
                loanDto.setCollateralIdStr("-");
            else
                loanDto.setCollateralIdStr(colIdStrBuilder.toString().substring(0, colIdStrBuilder.length() - 2));

            Double fundProgress = loanFundsService.calculateLoanFundingProgress(loanDto);
            loanDto.setFundProgress((int) Math.round(fundProgress * 100));

            // prepare contract info
            if(loanDto.getLoanStatus().getStatusName().equals(Constants.FULLY_FUNDED)) {
                loanDto.setContract(prepareContractDto(loanDto));
            }
        });
    }

    private ContractDto prepareContractDto(LoanDto loanDto) {
        ContractDto.ContractDtoBuilder contractDtoBuilder = ContractDto.builder();
        contractDtoBuilder.borrowerNameAddress(loanDto.getUser().getFirstName() + " \n" +
                loanDto.getUser().getLastName() + " " +
                "123 St. Royal Ave. New West, BC");
        List<String> lenderDetails = new ArrayList<>();
        List<Double> fundAmountDetails = new ArrayList<>();
        List<Contract> contractDetails = new ArrayList<>();

        List<LoanFunds> lfs = loanFundsRepository.findByLoanId(loanDto.getId());
        lfs.stream().forEach(lf -> {
            UserEntity lender = lf.getLender();
            lenderDetails.add(lender.getFirstName() + " " + lender.getLastName() + " \n" +
                    "123 St. Royal Ave. New West, BC" );
            fundAmountDetails.add(lf.getFundAmount());
            contractDetails.add(lf.getContract());
        });
        contractDtoBuilder.lenderNameAddress(lenderDetails);
        contractDtoBuilder.lenderFundAmount(fundAmountDetails);
        contractDtoBuilder.interestRate(loanDto.getInterestRate());
        contractDtoBuilder.term(loanDto.getMonthsToPay());
        contractDtoBuilder.startDate(contractDetails.get(0).getCreatedOn().toString());

        return contractDtoBuilder.build();
    }
}
