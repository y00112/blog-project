package com.wukong.service;

import com.wukong.dto.BlogQuery;
import com.wukong.dto.SearchBlog;
import com.wukong.pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * Created By WuKong on 2022/7/12 18:44
 **/
public interface BlogService {

    /**
     *通过id查询单个blog
     * @param id
     * @return
     */
    Blog getBlog(Long id);

    Blog getAndConvertBlog(Long id);

    /**
     * 分页查询
     * @param
     * @return
     */
    List<Blog> listBolg(String query);


    /**
     * 分页查询
     * @return
     */
    List<BlogQuery> findBlogQuery();

    /**
     * 新增
     * @param blog
     * @return
     */
    int saveBlog(Blog blog);

    /**
     * 修改blog
     * @param id
     * @param blog
     * @return
     */
    int updateBlog(Long id,Blog blog);

    /**
     * 删除blog
     * @param id
     */
    void deleteBlogById(Long id);

    /**
     * 搜索blog
     * @param searchBlog
     * @return
     */
    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);

    /**
     * 获取推荐列表
     * @return
     */
    List<Blog> listRecommendBlogTop();


    List<Blog> selectAllBlog();

    List<Blog> listBolgByTypeId(Long id);

    List<Blog> findBlogByTag(Long id);

    //归档处理
    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
