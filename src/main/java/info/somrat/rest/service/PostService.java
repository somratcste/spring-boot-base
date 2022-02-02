package info.somrat.rest.service;

import info.somrat.rest.models.Post;
import info.somrat.rest.request.PostCreateRequest;
import info.somrat.rest.request.PostUpdateRequest;
import org.springframework.data.domain.Page;

public interface PostService {

     Post create(PostCreateRequest post);

     Post update(int id, PostUpdateRequest post);

     boolean delete(long id);

     Post getById(long id);

     boolean deleteAll();

     Page<Post> getAll(int pageNo, int pageSize, String sortBy, String title);

     Page getAllPostsByUser(int pageNo, int pageSize, long userId);
}
