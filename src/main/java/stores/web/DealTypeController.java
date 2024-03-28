package stores.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import stores.DealType;
import stores.Deals;
import stores.data.DealTypeRepository;
import stores.data.DealsRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class DealTypeController {
    private final DealTypeRepository dealTypeRepository;
    private final DealsRepository dealRepository;

    public DealTypeController(DealTypeRepository dealTypeRepository, DealsRepository dealRepository) {
        this.dealTypeRepository = dealTypeRepository;
        this.dealRepository = dealRepository;
    }


    @GetMapping("/allTypes")
    public String showAllDealTypes(Model model) {
        model.addAttribute("dealTypes", dealTypeRepository.findAll());
        model.addAttribute("error", null);
        return "allTypes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addType")
    public String showAddDealTypeForm(Model model) {
        model.addAttribute("dealType", new DealType());
        return "addType";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addType")
    public String addType(@ModelAttribute("dealType") @Valid DealType dealType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addType";
        }
        dealTypeRepository.save(dealType);
        return "redirect:/allTypes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editType/{id}")
    public String showEditDealTypeForm(@PathVariable Long id, Model model) {
        DealType dealType = dealTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal type Id:" + id));
        model.addAttribute("dealType", dealType);
        return "editType";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editType/{id}")
    public String editDealType(@PathVariable Long id, @ModelAttribute("dealType") @Valid DealType updatedDealType, BindingResult result) {
        if (result.hasErrors()) {
            return "editType";
        }
        dealTypeRepository.save(updatedDealType);
        return "redirect:/allTypes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteType/{id}")
    public String deleteDealType(@PathVariable Long id, Model model) {
        Optional<Deals> dealsWithThisType = dealRepository.findById(id);
        if (!dealsWithThisType.isEmpty()) {
            model.addAttribute("error", "Сделка с этим типом существует. Нельзя удалить тип сделки.");
            model.addAttribute("dealTypes", dealTypeRepository.findAll());
            return "allTypes";
        }

        dealTypeRepository.deleteById(id);
        return "redirect:/allTypes";
    }
}


