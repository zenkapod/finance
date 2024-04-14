package fin.web;

import fin.Tiker;
import fin.data.TikerRepository;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TikerController.class)
@AutoConfigureMockMvc
public class TikerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TikerRepository tikerRepository;

    @Test
    @WithMockUser
    public void testShowAllTikers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tiker/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("tikerList"))
                .andExpect(model().attributeExists("tikers"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowAddTikerForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tiker/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("tikerForm"))
                .andExpect(model().attributeExists("tiker"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowEditTikerForm() throws Exception {
        Long id = 1L;
        Tiker tiker = new Tiker();
        tiker.setId(id);
        when(tikerRepository.findById(id)).thenReturn(Optional.of(tiker));

        mockMvc.perform(MockMvcRequestBuilders.get("/tiker/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("tikerEditForm"))
                .andExpect(model().attributeExists("tiker"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteTiker() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/tiker/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tiker/all"));
        verify(tikerRepository, times(1)).deleteById(id);
    }
}


