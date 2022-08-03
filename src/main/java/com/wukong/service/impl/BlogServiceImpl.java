package com.wukong.service.impl;

import com.wukong.dto.BlogQuery;
import com.wukong.dto.SearchBlog;
import com.wukong.exception.MyNotFoundException;
import com.wukong.mapper.BlogMapper;
import com.wukong.pojo.Blog;
import com.wukong.pojo.Tag;
import com.wukong.service.BlogService;
import com.wukong.service.TagService;
import com.wukong.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.CollationKey;
import java.util.*;

/**
 * Created By WuKong on 2022/7/12 18:55
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    TagService tagService;

    @Override
    public Blog getBlog(Long id) {
        Blog b1 = blogMapper.findById(id);

        return b1;
    }

    @Override
    public Blog getAndConvertBlog(Long id) {
        Blog blog = blogMapper.findById(id);
        if (null == blog){
            throw new MyNotFoundException("该博客不存在");
        }
        String tagIds = blog.getTagIds();

        List<Tag> tags = tagService.listTag(tagIds);
        //通过 tagids 查找对应的tag
        blog.setTags(tags);

        String content = blog.getContent();
        String markdown = MarkdownUtils.markdownToHtmlExtensions(content);

        //浏览次数自增
        blogMapper.updateViewsById(id);
        //文字评论数更新

        blog.setContent(markdown);
        return blog;
    }

    @Override
    public List<Blog> listBolg(String query) {
        return blogMapper.findAll(query);
    }

    @Override
    public List<BlogQuery> findBlogQuery() {
        return blogMapper.findBlogQuery();
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {

        //新增
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            //浏览次数
            blog.setViews(0);
            return blogMapper.save(blog);
        }else {
            blog.setUpdateTime(new Date());
            return blogMapper.updateById(blog);
        }
    }

    @Transactional()
    @Override
    public int updateBlog(Long id, Blog blog) {
        Blog b1 = blogMapper.findById(id);
        if (null == b1){
            throw new MyNotFoundException("该博客不存在");
        }

        BeanUtils.copyProperties(blog,b1);
        return blogMapper.save(b1);
    }

    @Transactional
    @Override
    public void deleteBlogById(Long id) {
        blogMapper.deleteBlogById(id);
    }

    @Override
    public List<BlogQuery> getBlogBySearch(SearchBlog searchBlog) {
        return blogMapper.getBlogBySearch(searchBlog);
    }

    @Override
    public List<Blog> listRecommendBlogTop() {
        return blogMapper.listRecommendBlogTop();
    }

    @Override
    public List<Blog> selectAllBlog() {
        List<Blog> blogs = blogMapper.selectAllBlog();
        return blogs;
    }

    @Override
    public List<Blog> listBolgByTypeId(Long id) {
        return blogMapper.listBolgByTypeId(id);
    }

    @Override
    public List<Blog> findBlogByTag(Long id) {
        return blogMapper.findBlogByTag(id);
    }

    @Override
    public TreeMap<String, List<Blog>> archiveBlog() {

        //拿到所有的年份
        List<String> years = blogMapper.findGroupByYear();

        //使用treeMap 排序
        TreeMap<String,List<Blog>> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //如果有空值，直接返回0
                if (o1 == null || o2 == null)
                    return 0;

                return o2.compareTo(o1);

            }
        });

        for (String year:years) {
            List<Blog> blogs = blogMapper.findBlogByYear(year);
            map.put(year,blogs);
        }



        return map;
    }

    @Override
    public Long countBlog() {
        return blogMapper.countBlog();
    }

}
