package gab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gab.model.AnalyzedWord;
import gab.model.Text;
import gab.service.Analyzer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({TextAnalyzerController.class, Analyzer.class})
public class TextAnalyzerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private Analyzer analyzer;

    @ParameterizedTest
    @ValueSource(strings = {"text", "different"})
    public void analyze_ExistentPathGiven_ShouldReturn200(String text) throws Exception {
        AnalyzedWord analyzedWord = new AnalyzedWord(text, 1, List.of(1));
        List<AnalyzedWord> analyzedWords = List.of(analyzedWord);
        when(analyzer.analyze(text)).thenReturn(List.of(analyzedWord));

        Text content = new Text(text);
        MvcResult mvcResult = mockMvc.perform(
                        post("/analyze")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(content)))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(analyzedWords);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    public void analyze_NullValueGiven_ShouldReturn400() throws Exception {
        Text content = new Text(null);
        mockMvc.perform(
                        post("/analyze")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(content)))
                .andExpect(status().isBadRequest());
    }
}
