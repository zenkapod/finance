package fin.web;
import fin.Tiker;
import fin.data.FinAssetRepository;
import fin.data.TikerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tiker")
public class TikerController {

    private final TikerRepository tikerRepository;
    private final FinAssetRepository finAssetRepository;

    @Autowired
    public TikerController(TikerRepository tikerRepository, FinAssetRepository finAssetRepository) {
        this.tikerRepository = tikerRepository;
        this.finAssetRepository = finAssetRepository;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("tiker", new Tiker());
        return "tikerForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("tiker") Tiker tiker, BindingResult result) {
        if (result.hasErrors()) {
            return "tikerForm";
        }
        tikerRepository.save(tiker);
        return "redirect:/tiker/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("tikers", tikerRepository.findAll());
        return "tikerList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Tiker tiker = tikerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tiker Id:" + id));
        model.addAttribute("tiker", tiker);
        return "tikerEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("tiker") Tiker tiker, BindingResult result) {
        if (result.hasErrors()) {
            return "tikerEditForm";
        }
        tiker.setId(id);
        tikerRepository.save(tiker);
        return "redirect:/tiker/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        if (finAssetRepository.existsByTikerId(id)) {
            model.addAttribute("error", "Нельзя удалить, есть связь.");
            model.addAttribute("tikers", tikerRepository.findAll());
            return "tikerList";
        }
        tikerRepository.deleteById(id);
        return "redirect:/tiker/all";
    }
}

