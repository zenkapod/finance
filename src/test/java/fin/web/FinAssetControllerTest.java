package fin.web;

import fin.FinAsset;
import fin.Tiker;
import fin.data.BondRepository;
import fin.data.FinAssetRepository;
import fin.data.SecurityRepository;
import fin.data.TikerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
@WebMvcTest(FinAssetController.class)
@AutoConfigureMockMvc
public class FinAssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinAssetRepository finAssetRepository;

    @MockBean
    private TikerRepository tikerRepository;

    @MockBean
    private BondRepository bondRepository;

    @MockBean
    private SecurityRepository securityRepository;

    @Test
    @WithMockUser
    public void testShowAllFinAssets() throws Exception {
        List<FinAsset> finAssets = new ArrayList<>();
        when(finAssetRepository.findAll()).thenReturn(finAssets);

        List<Tiker> tikers = new ArrayList<>();
        when(tikerRepository.findAll()).thenReturn(tikers);

        mockMvc.perform(MockMvcRequestBuilders.get("/finAsset/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("finAssetList"))
                .andExpect(model().attribute("finAssets", finAssets))
                .andExpect(model().attribute("tikers", tikers));
    }

    @Test
    @WithMockUser
    public void testShowAddFinAssetForm() throws Exception {
        List<Tiker> tikers = new ArrayList<>();
        when(tikerRepository.findAll()).thenReturn(tikers);

        mockMvc.perform(MockMvcRequestBuilders.get("/finAsset/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("finAssetForm"))
                .andExpect(model().attributeExists("finAsset"))
                .andExpect(model().attribute("tikers", tikers));
    }

    @Test
    @WithMockUser
    public void testShowEditFinAssetForm() throws Exception {
        Long id = 1L;
        FinAsset finAsset = new FinAsset();
        finAsset.setId(id);
        List<Tiker> tikers = new ArrayList<>();
        when(finAssetRepository.findById(id)).thenReturn(Optional.of(finAsset));
        when(tikerRepository.findAll()).thenReturn(tikers);

        mockMvc.perform(MockMvcRequestBuilders.get("/finAsset/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("finAssetEditForm"))
                .andExpect(model().attribute("finAsset", finAsset))
                .andExpect(model().attribute("tikers", tikers));
    }

    @Test
    @WithMockUser
    public void testDeleteFinAsset() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/finAsset/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/finAsset/all"));
        verify(finAssetRepository, times(1)).deleteById(id);
    }
}
