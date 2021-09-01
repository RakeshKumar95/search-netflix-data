package com.assessment.search.service;

import com.assessment.search.dtos.NetflixTitleDto;
import com.assessment.search.dtos.SearchRequestDto;
import com.assessment.search.dtos.SearchResponseData;
import com.assessment.search.entities.NetflixTitlesEntity;
import com.assessment.search.repositories.NetflixTitleRepository;
import com.assessment.search.util.SearchConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static com.assessment.search.util.SearchUtil.*;
import static org.springframework.data.domain.Sort.by;

@Service
public class SearchService {

    private final NetflixTitleRepository titleRepo;

    @Autowired
    SearchService(NetflixTitleRepository titleRepo) {
        this.titleRepo = titleRepo;
    }

    public List<NetflixTitleDto> getAllData(int pageIndex, Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 30;
        }

        Pageable page = PageRequest.of(pageIndex, pageSize);
        Page<NetflixTitlesEntity> entities = titleRepo.findAll(page);
        Function<NetflixTitlesEntity, NetflixTitleDto> mapper = (e) -> createCopyObject(e, NetflixTitleDto::new);
        return entities.stream().map(mapper).toList();
    }

    public SearchResponseData searchData(SearchRequestDto request) {
        PageRequest pageRequest = PageRequest.of(request.getPageIndex(), request.getPageSize(),
                by(getOrders(request.getSorts(), SearchConstants.DEFAULT_PROP)));
        Page<NetflixTitlesEntity> pages =
                titleRepo.findAll(createSpecification(request.getQuery()), pageRequest);
        Function<NetflixTitlesEntity, NetflixTitleDto> mapper = (e) -> createCopyObject(e, NetflixTitleDto::new);
        return createResponse(pages, mapper);
    }
}
