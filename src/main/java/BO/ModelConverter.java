package BO;

import DB.Entities.ChatMessageEntity;
import DB.Entities.FollowEntity;
import DB.Entities.PostEntity;
import DB.Entities.UserEntity;
import ViewModel.ChatMessageViewModel;
import ViewModel.FollowViewModel;
import ViewModel.PostViewModel;
import ViewModel.UserViewModel;


public class ModelConverter {


    static UserViewModel convertToUserViewModel(UserEntity u){
        UserViewModel user = new UserViewModel();
        user.setUserId(u.getUserId());
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        return user;
    }

    public static UserEntity convertToUserEntity(UserViewModel u){
        UserEntity user = new UserEntity();
        user.setUserId(u.getUserId());
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        return user;
    }
    public static PostEntity convertToPostEntity(PostViewModel p){
        PostEntity post = new PostEntity();
        post.setBody(p.getBody());
        post.setUser(convertToUserEntity(p.getUser()));
        return post;
    }

    static PostViewModel convertToPostViewModel(PostEntity p){
        PostViewModel post = new PostViewModel();
        post.setBody(p.getBody());
        post.setUser(convertToUserViewModel(p.getUser()));
        post.setCreationDate(p.getCreationDate());
        return post;
    }

    public static ChatMessageEntity convertToChatMessageEntity(ChatMessageViewModel c){
        ChatMessageEntity chat = new ChatMessageEntity();
        chat.setMessage(c.getMessage());
        chat.setSender(convertToUserEntity(c.getSender()));
        chat.setReceiver(convertToUserEntity(c.getReceiver()));
        chat.setSendDate(c.getSendDate());
        return chat;
    }

    static ChatMessageViewModel convertToChatMessageViewModel(ChatMessageEntity c){
        ChatMessageViewModel chat = new ChatMessageViewModel();
        chat.setMessage(c.getMessage());
        chat.setSender(convertToUserViewModel(c.getSender()));
        chat.setReceiver(convertToUserViewModel(c.getReceiver()));
        chat.setSendDate(c.getSendDate());
        return chat;
    }

    static FollowViewModel convertToFollowViewModel(FollowEntity f){
        FollowViewModel follow = new FollowViewModel();
        follow.setFollower(convertToUserViewModel(f.getFollower()));
        follow.setFollowing(convertToUserViewModel(f.getFollowing()));
        return follow;
    }
    public static FollowEntity convertToFollowEntity(FollowViewModel f){
        FollowEntity follow = new FollowEntity();
        follow.setFollower(convertToUserEntity(f.getFollower()));
        follow.setFollowing(convertToUserEntity(f.getFollowing()));
        return follow;
    }

}
