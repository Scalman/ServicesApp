package DB.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "lab1")
public class UserEntity {
    private int userId;
    private String username;
    private String password;
    private Collection<ChatMessageEntity> chatMessages;
    private Collection<PostEntity> posts;
    private Collection<FollowEntity> follow;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "sender")
    public Collection<ChatMessageEntity> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(Collection<ChatMessageEntity> chatMessagesByUserId) {
        this.chatMessages = chatMessagesByUserId;
    }

    @OneToMany(mappedBy = "user")
    public Collection<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(Collection<PostEntity> postsByUserId) {
        this.posts = postsByUserId;
    }

    @OneToMany(mappedBy = "follower")
    public Collection<FollowEntity> getFollow() {
        return follow;
    }

    public void setFollow(Collection<FollowEntity> followsByUserId) {
        this.follow = followsByUserId;
    }

}
