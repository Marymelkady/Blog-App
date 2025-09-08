package com.maryem.backendapplication.respository;

import com.maryem.backendapplication.model.TodoItem;
import com.maryem.backendapplication.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    // This method finds all items in a specific list
    List<TodoItem> findByList(TodoList list);
}