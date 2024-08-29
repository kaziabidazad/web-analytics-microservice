package com.kaziabid.learn.wams.solr.query.webclient.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaziabid.learn.wams.common.dto.Page;
import com.kaziabid.learn.wams.common.dto.PagedResponse;
import com.kaziabid.learn.wams.common.dto.solr.WikipediaPageDto;
import com.kaziabid.learn.wams.exceptions.api.WamsRestServiceException;
import com.kaziabid.learn.wams.solr.query.webclient.dto.QueryRequest;

/**
 * @author Kazi Abid
 */
@Controller
@RequestMapping(path = "/query")
public class SolrWebClientQueryController {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SolrWebClientQueryController.class);

    @GetMapping
    public String query(Model model) {
        model.addAttribute("queryRequestModel", QueryRequest.build());
        return "query";
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8")
    public String query(QueryRequest queryRequest, Model model) {
        try {
            LOGGER.info("Request for query: {} ", queryRequest);
            PagedResponse<WikipediaPageDto> data = new PagedResponse<>(Page.build(), List
                    .of(new WikipediaPageDto("1", "super", null, null, "Jenna Dewan",
                            null, null, null, "2024-1-03: 13:22:12",
                            "Jenna Lee Dewan is an American actress and dancer. She started her career as a backup dancer for Janet Jackson, and later worked with artists including Christina Aguilera, Pink, and Missy Elliott. She is known for her role as Nora Clark in the 2006 film Step Up. She has also starred on the short-lived NBC series The Playboy Club and had a recurring role on the FX series American Horror Story: Asylum. She portrayed Freya Beauchamp on the Lifetime series Witches of East End, Lucy Lane in The CW series Supergirl and Superman & Lois, and Joanna in Soundtrack on Netflix. Dewan has hosted the reality television shows World of Dance and Flirty Dancing and served as a judge on Come Dance with Me. She currently stars as Bailey Nune on ABC's The Rookie. She also had a recurring role on the FOX medical drama The Resident.",
                            null, "Jenna Dewan",
                            "https://upload.wikimedia.org/wikipedia/commons/0/03/Jenna_Dewan_2012.jpg",
                            null, "https://en.wikipedia.org/wiki/Jenna_Dewan", null),
                            new WikipediaPageDto("2", "super", null, null,
                                    "Kristina and Karissa Shannon", null, null, null,
                                    "2024-1-03: 13:22:12",
                                    "Kristina and Karissa Shannon are American Playboy Playmates, porn stars and twin sisters.",
                                    null, "Kristina and Karissa Shannon",
                                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Karissa_and_Kristina_Shannon_2008.jpg/320px-Karissa_and_Kristina_Shannon_2008.jpg",
                                    null,
                                    "https://en.wikipedia.org/wiki/Kristina_and_Karissa_Shannon",
                                    null)));
            model.addAttribute("queryRequestModel", queryRequest);
            model.addAttribute("queryResponseModel", data);
            return "query";
        } catch (Exception e) {
            throw new WamsRestServiceException(500, e.getMessage());
        }
    }

}
