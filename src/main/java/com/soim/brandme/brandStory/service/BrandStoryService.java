package com.soim.brandme.brandStory.service;

import com.soim.brandme.brandStory.repo.BrandStoryRepo;
import com.soim.brandme.chatRoom.entity.ChatRoom;
import com.soim.brandme.user.entity.User;
import com.soim.brandme.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandStoryService {
    private final BrandStoryRepo brandStoryRepo;
    private final UserRepo userRepo;

//   public List<String> addResource(Long userId, Long chatRoomId,List<String> resources){
//       Optional<User> user = userRepo.findById(userId);
//       if(user.isPresent()){
//           User u = user.get();
//           Optional<ChatRoom> chatRoom = u.getChatRooms().stream()
//                   .filter(c -> c.getChatRoomId().equals(chatRoomId)).findFirst();
//              if(chatRoom.isPresent(){
//                  ChatRoom c = chatRoom.get();
//                  c.s
//           }
//       }
//   }
}
