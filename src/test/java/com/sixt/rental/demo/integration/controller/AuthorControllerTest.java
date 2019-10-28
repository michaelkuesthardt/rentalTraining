package com.sixt.rental.demo.integration.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@ExtendWith(SpringExtension.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listReturnsSuccess() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk());
    }

    @Test
    void getReturnsSuccess() throws Exception {
        mockMvc.perform(get("/authors/0"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(status().isOk());
    }

    @Test
    void getReturnsFailureNotFound() throws Exception {
        mockMvc.perform(get("/authors/10000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addReturnsSuccess() throws Exception {
        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": \"efb69c10-a34b-4e81-891a-da7e7e8943d8\",\n" +
                        "    \"firstName\": \"Max\",\n" +
                        "    \"lastName\": \"Mustermann\"\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void removeReturnsSuccess() throws Exception {
        mockMvc.perform(delete("/authors/0"))
                .andExpect(status().isOk());
    }

    @Test
    void removeReturnsFailure() throws Exception {
        mockMvc.perform(delete("/authors/100000"))
                .andExpect(status().isNotFound());
    }
}