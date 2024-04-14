package fin.web;

import fin.FinAsset;
import fin.Security;
import fin.data.FinAssetRepository;
import fin.data.SecurityRepository;
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
@WebMvcTest(SecurityController.class)
@AutoConfigureMockMvc
public class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityRepository securityRepository;

    @MockBean
    private FinAssetRepository finAssetRepository;

    @Test
    @WithMockUser
    public void testShowAllSecurities() throws Exception {
        List<Security> securities = new ArrayList<>();
        when(securityRepository.findAll()).thenReturn(securities);

        mockMvc.perform(MockMvcRequestBuilders.get("/security/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("securityList"))
                .andExpect(model().attribute("securities", securities))
                .andExpect(model().attributeExists("finAssets"));
    }

    @Test
    @WithMockUser
    public void testShowAddSecurityForm() throws Exception {
        List<FinAsset> finAssets = new ArrayList<>();
        when(finAssetRepository.findAll()).thenReturn(finAssets);

        mockMvc.perform(MockMvcRequestBuilders.get("/security/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("securityForm"))
                .andExpect(model().attributeExists("security"))
                .andExpect(model().attribute("finAssets", finAssets));
    }

    @Test
    @WithMockUser
    public void testShowEditSecurityForm() throws Exception {
        Long id = 1L;
        Security security = new Security();
        security.setId(id);
        List<FinAsset> finAssets = new ArrayList<>();
        when(securityRepository.findById(id)).thenReturn(Optional.of(security));
        when(finAssetRepository.findAll()).thenReturn(finAssets);

        mockMvc.perform(MockMvcRequestBuilders.get("/security/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("securityEditForm"))
                .andExpect(model().attribute("security", security))
                .andExpect(model().attribute("finAssets", finAssets));
    }

    @Test
    @WithMockUser
    public void testDeleteSecurity() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/security/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/security/all"));
        verify(securityRepository, times(1)).deleteById(id);
    }

}
