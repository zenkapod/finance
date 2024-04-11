package fin.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import fin.Deals;
import fin.data.CurrencyRepository;
import fin.data.DealPlaceRepository;
import fin.data.DealTypeRepository;
import fin.data.DealsRepository;

@Controller
@RequestMapping("/deal")
public class DealController {
    private final DealsRepository dealRepository;
    private final DealTypeRepository dealTypeRepository;
    private final DealPlaceRepository dealPlaceRepository;
    private final CurrencyRepository currencyRepository;

    public DealController(DealsRepository dealRepository, DealTypeRepository dealTypeRepository,
                          DealPlaceRepository dealPlaceRepository, CurrencyRepository currencyRepository) {
        this.dealRepository = dealRepository;
        this.dealTypeRepository = dealTypeRepository;
        this.dealPlaceRepository = dealPlaceRepository;
        this.currencyRepository = currencyRepository;
    }

    @GetMapping("/create")
    public String dealForm(Model model) {
        model.addAttribute("deal", new Deals());
        model.addAttribute("allDealTypes", dealTypeRepository.findAll());
        model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
        model.addAttribute("allCurrencies", currencyRepository.findAll());
        return "Deal";
    }

    @GetMapping("/all")
    public String allDeals(Model model) {
        model.addAttribute("allDeals", dealRepository.findAll());
        model.addAttribute("allDealTypes", dealTypeRepository.findAll());
        model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
        model.addAttribute("allCurrencies", currencyRepository.findAll());
        return "allDeals";
    }

    @PostMapping("/submit")
    public String processDeal(@Valid @ModelAttribute("deal") Deals deal, Errors errors,
                              SessionStatus sessionStatus, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("allDealTypes", dealTypeRepository.findAll());
            model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
            model.addAttribute("allCurrencies", currencyRepository.findAll());
            return "Deal";
        }

        dealRepository.save(deal);
        sessionStatus.setComplete();
        return "redirect:/deal/all";
    }

    @GetMapping("/edit/{id}")
    public String editDeal(@PathVariable Long id, Model model) {
        Deals deal = dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal Id:" + id));

        model.addAttribute("deal", deal);
        model.addAttribute("allDealTypes", dealTypeRepository.findAll());
        model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
        model.addAttribute("allCurrencies", currencyRepository.findAll());
        return "DealEdit";
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id, @Valid @ModelAttribute("deal") Deals deal,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allDealTypes", dealTypeRepository.findAll());
            model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
            model.addAttribute("allCurrencies", currencyRepository.findAll());
            return "DealEdit";
        }

        dealRepository.save(deal);
        return "redirect:/deal/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteDeal(@PathVariable Long id) {
        dealRepository.deleteById(id);
        return "redirect:/deal/all";
    }
}









