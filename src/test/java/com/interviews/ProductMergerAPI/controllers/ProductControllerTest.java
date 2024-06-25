package com.interviews.ProductMergerAPI.controllers;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProductsByType_withValidData_ShouldReturnHttp200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products?productType=BASIC"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getProductsByType_withInvalidData_ShouldReturnHttp204() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products?productType=xxxxxxxx"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getProductsByType_withNoData_ShouldReturnHttp200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
