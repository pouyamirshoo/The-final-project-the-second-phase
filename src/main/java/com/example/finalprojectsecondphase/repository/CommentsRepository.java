package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
}
