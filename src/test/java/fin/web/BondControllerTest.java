package fin.web;

import fin.Bond;
import fin.FinAsset;
import fin.data.BondRepository;
import fin.data.FinAssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BondController.class)
@AutoConfigureMockMvc
public class BondControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BondRepository bondRepository;
    @MockBean
    private FinAssetRepository finAssetRepository;
    @Test
    @WithMockUser
    public void testShowAllBonds() throws Exception {
        List<Bond> bonds = new ArrayList<>();
        when(bondRepository.findAll()).thenReturn(bonds);
        List<FinAsset> finAssets = new ArrayList<>();
        when(finAssetRepository.findAll()).thenReturn(finAssets);
        mockMvc.perform(MockMvcRequestBuilders.get("/bond/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("bondList"))
                .andExpect(model().attribute("bonds", bonds))
                .andExpect(model().attribute("finAssets", finAssets));
    }
    @Test
    @WithMockUser
    public void testShowAddBondForm() throws Exception {
        List<FinAsset> finAssets = new ArrayList<>();
        when(finAssetRepository.findAll()).thenReturn(finAssets);
        mockMvc.perform(MockMvcRequestBuilders.get("/bond/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("bondForm"))
                .andExpect(model().attributeExists("bond"))
                .andExpect(model().attribute("finAssets", finAssets));
    }
    @Test
    @WithMockUser
    public void testShowEditBondForm() throws Exception {
        Long id = 1L;
        Bond bond = new Bond();
        bond.setId(id);
        List<FinAsset> finAssets = new ArrayList<>();
        when(bondRepository.findById(id)).thenReturn(Optional.of(bond));
        when(finAssetRepository.findAll()).thenReturn(finAssets);
        mockMvc.perform(MockMvcRequestBuilders.get("/bond/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("bondEditForm"))
                .andExpect(model().attribute("bond", bond))
                .andExpect(model().attribute("finAssets", finAssets));
    }
    @Test
    @WithMockUser
    public void testDeleteBond() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/bond/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bond/all"));
        verify(bondRepository, times(1)).deleteById(id);
    }
}
