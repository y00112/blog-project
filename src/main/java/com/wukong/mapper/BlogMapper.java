package com.wukong.mapper;

import com.wukong.dto.BlogQuery;
import com.wukong.dto.SearchBlog;
import com.wukong.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By WuKong on 2022/7/12 18:55
 **/
@Mapper
public interface BlogMapper {

    Blog findById(Long id);

    int save(Blog blog);


    List<Blog> findAll(String query);

    List<BlogQuery> findBlogQuery();

    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);

    int updateById(Blog blog);

    void deleteBlogById(Long id);

    List<Blog> listRecommendBlogTop();


    List<Blog> selectAllBlog();

    @Transactional
    int updateViewsById(Long id);

    List<Blog> findBlogByTag(Long id);

    List<Blog> listBolgByTypeId(Long id);

    List<String> findGroupByYear();

    List<Blog> findBlogByYear(String year);

    Long countBlog();
}
