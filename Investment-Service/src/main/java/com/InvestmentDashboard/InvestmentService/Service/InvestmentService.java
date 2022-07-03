package com.InvestmentDashboard.InvestmentService.Service;

import com.InvestmentDashboard.InvestmentService.Model.Investment;
import com.InvestmentDashboard.InvestmentService.Repository.InvestmentRepository;
import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;

    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    // Return All Investments
    public List<Investment> allinvestments()
    {
        return investmentRepository.findAll();
    }

    // Create One Investment, the benefice is calculate automatically
    public Investment addinvestment(Investment investment) {

         float capital = investment.getCapital();
         float actual = investment.getActual();
         float benefice = actual - capital;

         Investment save = new Investment(investment.getName(), investment.getStart(), investment.getCapital(), investment.getActual(), benefice );
         return investmentRepository.save(save);
    }

    // Return One Investment with the ID given
    public Optional<Investment> getinvestmentbyid(int id)
    {
        return investmentRepository.findById(id);
    }

    // Return The capital of all investments
    public float allcapital()
    {
        float result = 0;
        List<Investment> liste =  investmentRepository.findAll();
        for (int j = 0; j < liste.size() ; j++) {
            result += liste.get(j).getCapital();
        }
        return result;
    }

    // Return The actual money of all investments
    public float allactual()
    {
        float result = 0;
        List<Investment> liste =  investmentRepository.findAll();
        for (int j = 0; j < liste.size() ; j++) {
            result += liste.get(j).getActual();
        }
        return result;
    }

    // Return The benefice of all investments
    public float allbenefice()
    {
        float result = 0;
        List<Investment> liste =  investmentRepository.findAll();
        for (int j = 0; j < liste.size() ; j++) {
            result += liste.get(j).getBenefice();
        }
        return result;
    }

    // Return the benefice of one investment
    public float getbenefice(int id)
    {
        return (investmentRepository.findById(id).get().getActual() - investmentRepository.findById(id).get().getCapital());
    }


    public float pourcentagebeneficeallinvestments() {
        return allbenefice() / allcapital() * 100;
    }

    public String alldata() {
        float capital = 0;
        float actual = 0;
        float benefice = 0;

        List<Investment> liste =  investmentRepository.findAll();
        for (int j = 0; j < liste.size() ; j++) {
            capital += liste.get(j).getCapital();
            actual += liste.get(j).getActual();
            benefice += liste.get(j).getBenefice();
        }

        float pourcentageallbenefice = ((actual - capital) / capital) * 100;

        return"{ \"base\":" + capital +", \"actual\":" + actual+", \"benefice\":"+benefice+", \"poucentageallbenefice\":"+pourcentageallbenefice+ "}";

    }

    public Investment updateinvestment(int id, Investment investmentDetails) {
         Investment investmentRecup = investmentRepository.getById(id);
        investmentRecup.setName(investmentDetails.getName());
        investmentRecup.setStart(investmentDetails.getStart());
        investmentRecup.setCapital(investmentDetails.getCapital());
        investmentRecup.setActual(investmentDetails.getActual());
        investmentRecup.setBenefice(investmentDetails.getActual() - investmentDetails.getCapital());

        return investmentRecup;
    }
}
