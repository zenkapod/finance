package fin.web;
import fin.Bond;
import fin.data.BondRepository;
import fin.data.FinAssetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bond")
public class BondController {

    private final BondRepository bondRepository;
    private final FinAssetRepository finAssetRepository;

    @Autowired
    public BondController(BondRepository bondRepository, FinAssetRepository finAssetRepository) {
        this.bondRepository = bondRepository;
        this.finAssetRepository = finAssetRepository;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("bond", new Bond());
        model.addAttribute("finAssets", finAssetRepository.findAll());
        return "bondForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("bond") Bond bond, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("finAssets", finAssetRepository.findAll());
            return "bondForm";
        }
        bondRepository.save(bond);
        return "redirect:/bond/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("bonds", bondRepository.findAll());
        model.addAttribute("finAssets", finAssetRepository.findAll());
        return "bondList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Bond bond = bondRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bond Id:" + id));
        model.addAttribute("bond", bond);
        model.addAttribute("finAssets", finAssetRepository.findAll());
        return "bondEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("bond") Bond bond, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("finAssets", finAssetRepository.findAll());
            return "bondEditForm";
        }
        bond.setId(id);
        bondRepository.save(bond);
        return "redirect:/bond/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        bondRepository.deleteById(id);
        return "redirect:/bond/all";
    }
}

