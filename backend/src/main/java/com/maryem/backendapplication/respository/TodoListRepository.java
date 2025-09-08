package com.maryem.backendapplication.respository;

import com.maryem.backendapplication.model.TodoList;
import com.maryem.backendapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findByUser(User user);
}