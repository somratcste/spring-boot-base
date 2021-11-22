package info.somrat.rest.repository;

import info.somrat.rest.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Post findByTitle(String title);
    Page<Post> findByTitleContaining(String title, Pageable pageable);
}
