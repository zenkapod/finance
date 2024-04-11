package fin.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import fin.Deals;
import fin.data.CurrencyRepository;
import fin.data.DealPlaceRepository;
import fin.data.DealTypeRepository;
import fin.data.DealsRepository;

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

    @MockBean
    private DealPlaceRepository dealPlaceRepository;

    @MockBean
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private DealController dealController;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDealForm() throws Exception {
        mockMvc.perform(get("/deal/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("Deal"))
                .andExpect(model().attributeExists("deal", "allDealTypes", "allDealPlaces", "allCurrencies"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAllDeals() throws Exception {
        List<Deals> dealsList = new ArrayList<>();
        when(dealRepository.findAll()).thenReturn(dealsList);
        when(dealTypeRepository.findAll()).thenReturn(new ArrayList<>());
        when(dealPlaceRepository.findAll()).thenReturn(new ArrayList<>());
        when(currencyRepository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/deal/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("allDeals"))
                .andExpect(model().attributeExists("allDeals", "allDealTypes", "allDealPlaces", "allCurrencies"));
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






