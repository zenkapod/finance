package stores.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import stores.Deals;
import stores.data.DealTypeRepository;
import stores.data.DealsRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(DealController.class)
@ExtendWith(MockitoExtension.class)
public class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealsRepository dealRepository;

    @MockBean
    private DealTypeRepository dealTypeRepository;

    @InjectMocks
    private DealController dealController;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDealForm() throws Exception {
        mockMvc.perform(get("/deal/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("Deal"))
                .andExpect(model().attributeExists("deal", "allDealTypes"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAllDeals() throws Exception {
        List<Deals> dealsList = new ArrayList<>();
        when(dealRepository.findAll()).thenReturn(dealsList);

        mockMvc.perform(get("/deal/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("allDeals"))
                .andExpect(model().attributeExists("allDeals", "allDealTypes"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteDeal() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/deal/delete/{id}", id))
                .andExpect(status().is3xxRedirection());
        verify(dealRepository, times(1)).deleteById(id);
    }
}





