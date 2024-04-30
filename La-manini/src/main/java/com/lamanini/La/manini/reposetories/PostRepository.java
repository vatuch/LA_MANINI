package com.lamanini.La.manini.reposetories;

import com.lamanini.La.manini.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
