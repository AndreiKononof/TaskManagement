package com.example.TaskManagement.repository.specification;

import com.example.TaskManagement.dto.filter.TaskFilter;
import com.example.TaskManagement.model.Task;
import org.springframework.data.jpa.domain.Specification;


public interface TaskSpecification {

    static Specification<Task> withFilter(TaskFilter taskFilter){
        return Specification.where(byAuthorId(taskFilter.getAuthorId()))
                .and(byExecutorId(taskFilter.getExecutorId()));
    }



    static Specification<Task> byAuthorId(Long authorId) {
        return (root, query, criteriaBuilder) ->{
            if(authorId == null){
                return null;
            }

            return  criteriaBuilder.equal(root.get("author").get("id"),authorId);
        };
    }

    static Specification<Task> byExecutorId(Long executorsId) {
        return (root, query, criteriaBuilder) ->{
            if(executorsId == null){
                return null;
            }

            return  criteriaBuilder.equal(root.get("executors").get("id"),executorsId);
        };
    }
}
