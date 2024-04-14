package fin.web;

import fin.Security;
import fin.data.FinAssetRepository;
import fin.data.SecurityRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/security")
public class SecurityController {

    private final SecurityRepository securityRepository;
    private final FinAssetRepository finAssetRepository;

    @Autowired
    public SecurityController(SecurityRepository securityRepository, FinAssetRepository finAssetRepository) {
        this.securityRepository = securityRepository;
        this.finAssetRepository = finAssetRepository;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("security", new Security());
        model.addAttribute("finAssets", finAssetRepository.findAll()); // Получаем список всех FinAsset
        return "securityForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("security") Security security, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("finAssets", finAssetRepository.findAll());
            return "securityForm";
        }
        securityRepository.save(security);
        return "redirect:/security/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("securities", securityRepository.findAll());
        model.addAttribute("finAssets", finAssetRepository.findAll());
        return "securityList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Security security = securityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid security Id:" + id));
        model.addAttribute("security", security);
        model.addAttribute("finAssets", finAssetRepository.findAll()); // Получаем список всех FinAsset
        return "securityEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("security") Security security,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("finAssets", finAssetRepository.findAll());
            return "securityEditForm";
        }
        security.setId(id);
        securityRepository.save(security);
        return "redirect:/security/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        securityRepository.deleteById(id);
        return "redirect:/security/all";
    }
}
