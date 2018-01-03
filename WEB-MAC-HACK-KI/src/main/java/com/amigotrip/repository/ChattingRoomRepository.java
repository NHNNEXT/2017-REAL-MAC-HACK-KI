package com.amigotrip.repository;

import com.amigotrip.domain.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by NEXT on 2018. 1. 4..
 */
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long>{
    List<ChattingRoom> findByUsers_Id(Long Id); // find chatting rooms containing certain user
}
