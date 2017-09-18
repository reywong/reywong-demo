package test;

import cn.com.yto.reywong.tool.springboot.test.App;
import cn.com.yto.reywong.tool.springboot.test.Swagger2;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.GroupBy;
import org.asciidoctor.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import springfox.documentation.staticdocs.Swagger2MarkupResultHandler;

import java.io.File;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "src/docs/asciidoc/snippets")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class, Swagger2.class})
@WebAppConfiguration
public class TestDocumentation {
    private String snippetDir = "src/docs/asciidoc/snippets";
    private String outputDir = "src/docs/asciidoc/generated";
    @Autowired
    private MockMvc mockMvc;

    @After
    public void Test() throws Exception {
        mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
                .andDo(Swagger2MarkupResultHandler.outputDirectory(outputDir)
                        .withExamples(snippetDir)
                        .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                        .build())
                .andExpect(status().isOk())
                .andReturn();


        // 转成asciiDoc，并加入Example
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        Attributes attributes = new Attributes();
        attributes.setCopyCss(true);
        attributes.setLinkCss(false);
        attributes.setSectNumLevels(3);
        attributes.setAnchors(true);
        attributes.setSectionNumbers(true);
        attributes.setHardbreaks(true);
        attributes.setTableOfContents(Placement.LEFT);
        attributes.setAttribute("generated", "generated");
        OptionsBuilder optionsBuilder = OptionsBuilder
                .options()
                .backend("html5")
                .docType("book")
                .eruby("")
                .inPlace(true)
                .safe(SafeMode.UNSAFE)
                .attributes(attributes);
        String asciiInputFile = "src/docs/asciidoc/index.adoc";
        asciidoctor.convertFile(new File(asciiInputFile), optionsBuilder.get());

    }

    @Test
    public void TestApi() throws Exception {
        mockMvc.perform(get("/userInfo/getUserInfo/1" )
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("获取用户列表", preprocessResponse(prettyPrint())));
    }


}
