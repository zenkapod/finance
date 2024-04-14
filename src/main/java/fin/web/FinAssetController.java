package fin.web;
import fin.FinAsset;
import fin.data.BondRepository;
import fin.data.FinAssetRepository;
import fin.data.SecurityRepository;
import fin.data.TikerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/finAsset")
public class FinAssetController {

    private final FinAssetRepository finAssetRepository;
    private final TikerRepository tikerRepository;

    private final BondRepository bondRepository;

    private final SecurityRepository securityRepository;

    @Autowired
    public FinAssetController(FinAssetRepository finAssetRepository, TikerRepository tikerRepository, BondRepository bondRepository, SecurityRepository securityRepository) {
        this.finAssetRepository = finAssetRepository;
        this.tikerRepository = tikerRepository;
        this.bondRepository = bondRepository;
        this.securityRepository = securityRepository;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("finAsset", new FinAsset());
        model.addAttribute("tikers", tikerRepository.findAll()); // Получаем список всех тикеров
        return "finAssetForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("finAsset") FinAsset finAsset, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tikers", tikerRepository.findAll());
            return "finAssetForm";
        }
        finAssetRepository.save(finAsset);
        return "redirect:/finAsset/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("finAssets", finAssetRepository.findAll());
        model.addAttribute("tikers", tikerRepository.findAll());
        return "finAssetList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        FinAsset finAsset = finAssetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid finAsset Id:" + id));
        model.addAttribute("finAsset", finAsset);
        model.addAttribute("tikers", tikerRepository.findAll()); // Получаем список всех тикеров
        return "finAssetEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("finAsset") FinAsset finAsset, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tikers", tikerRepository.findAll());
            return "finAssetEditForm";
        }
        finAsset.setId(id);
        finAssetRepository.save(finAsset);
        return "redirect:/finAsset/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        if (bondRepository.existsByFinAssetId(id) || securityRepository.existsByFinAssetId(id)) {
            model.addAttribute("error", "Нельзя удалить, есть связь.");
            model.addAttribute("finAssets", finAssetRepository.findAll());
            model.addAttribute("tikers", tikerRepository.findAll());
            return "finAssetList";
        }
        finAssetRepository.deleteById(id);
        return "redirect:/finAsset/all";
    }
}

