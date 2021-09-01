package com.assessment.search.controller;

import com.assessment.search.dtos.NetflixTitleDto;
import com.assessment.search.dtos.SearchRequestDto;
import com.assessment.search.dtos.SearchResponseData;
import com.assessment.search.service.SearchService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/data")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping({"/{pageIndex}", "/{pageIndex}/{pageSize}"})
    public ResponseEntity<List<NetflixTitleDto>> getAllData(
            @PathVariable("pageIndex") int pageIndex,
            @PathVariable(value = "pageSize", required = false) Integer pageSize
    ) throws NotFoundException {
        if (pageIndex < 0 || (pageSize != null && pageSize < 0)) {
            throw new IllegalArgumentException("Page  Number/Page Size cannot be less than 0");
        }
        List<NetflixTitleDto> response = searchService.getAllData(pageIndex, pageSize);
        if (null == response) {
            throw new NotFoundException("No Data Configured");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponseData> searchData(@RequestBody SearchRequestDto request) {
        SearchResponseData responseData;
        responseData = searchService.searchData(request);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
