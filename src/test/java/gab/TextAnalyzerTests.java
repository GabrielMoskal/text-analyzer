package gab;

import gab.controller.TextAnalyzerController;
import gab.service.Analyzer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TextAnalyzerTests {

    @Autowired
    private TextAnalyzerController controller;

    @Autowired
    private Analyzer analyzer;

    @Test
    void contextLoads() {
        assertNotNull(controller);
        assertNotNull(analyzer);
    }
}
