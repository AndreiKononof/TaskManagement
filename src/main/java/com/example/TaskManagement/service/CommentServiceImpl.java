package com.example.TaskManagement.service;

import com.example.TaskManagement.configuration.redis.propertise.AppCacheProperties;
import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.model.pagination.PageInfo;
import com.example.TaskManagement.repository.CommentRepository;
import com.example.TaskManagement.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundException(MessageFormat.format("Комментарий с ID {0} не найден", id)));
    log.info("Completed method findById comment ID {}",id);
        return comment;
    }

    @Override
    public List<Comment> findAll(PageInfo pageInfo) {
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }
        List<Comment> comments = commentRepository.findAll(PageRequest.of(pageInfo.getNumber(), pageInfo.getSize())).getContent();
        log.info("Completed method findAll comment");
        return comments;
    }
    @Override
    public List<Comment> findAllByTask(Task task) {
        List<Comment> comments = commentRepository.findAllByTask(task);
        log.info("Completed method findAll comment by task ID - {}, name - {}",task.getId(), task.getTitle());
        return comments;
    }

    @Override
    public List<Comment> findAllByTask(Task task, PageInfo pageInfo) {
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }
        List<Comment> comments = commentRepository.findAllByTask(task, PageRequest.of(pageInfo.getNumber(), pageInfo.getSize())).getContent();
        log.info("Completed method findAll comment by task ID - {}, name - {}",task.getId(), task.getTitle());
        return comments;
    }

    @Override
    public List<Comment> findAllByAuthor(User author) {
        List<Comment> comments = commentRepository.findAllByAuthor(author);
        log.info("Completed method findAll comment by author name - {}, ID -{}",
                author.getName(), author.getId());
        return comments;
    }

    @Override
    public List<Comment> findAllByAuthor(User author, PageInfo pageInfo) {
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }
        List<Comment> comments = commentRepository.findAllByAuthor(author, PageRequest.of(pageInfo.getNumber(), pageInfo.getSize())).getContent();
        log.info("Completed method findAll comment by author name - {}, ID -{}",
                author.getName(), author.getId());
        return comments;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.CACHE_GET_ALL_TASK, allEntries = true, beforeInvocation = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.CACHE_GET_ALL_TASK_USER, allEntries = true, beforeInvocation = true)
    })
    public Comment save(Comment comment) {
        Comment commentSave = commentRepository.save(comment);
        log.info("Save comment ID - {}",comment.getId());
        return commentSave;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.CACHE_GET_ALL_TASK, allEntries = true, beforeInvocation = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.CACHE_GET_ALL_TASK_USER, allEntries = true, beforeInvocation = true)
    })
    public Comment update(Comment comment) {
        Comment commentUpdate = commentRepository.save(comment);
        log.info("Update comment ID {}",commentUpdate.getId());
        return commentUpdate;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.CACHE_GET_ALL_TASK, allEntries = true, beforeInvocation = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.CACHE_GET_ALL_TASK_USER, allEntries = true, beforeInvocation = true)
    })
    public void delete(Long id) {
    commentRepository.deleteById(id);
    log.info("Delete comment ID {}", id);
    }
}
