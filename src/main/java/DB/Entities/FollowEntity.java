package DB.Entities;

import javax.persistence.*;

@Entity
@Table(name = "follow", schema = "lab1")
public class FollowEntity {
    private int followId;
    private UserEntity follower;
    private UserEntity following;

    @Id
    @Column(name = "follow_id", nullable = false)
    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowEntity that = (FollowEntity) o;

        if (followId != that.followId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return followId;
    }

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "user_id")
    public UserEntity getFollower() {
        return follower;
    }

    public void setFollower(UserEntity userByFollowerId) {
        this.follower = userByFollowerId;
    }

    @ManyToOne
    @JoinColumn(name = "following_id", referencedColumnName = "user_id")
    public UserEntity getFollowing() {
        return following;
    }

    public void setFollowing(UserEntity userByFollowingId) {
        this.following = userByFollowingId;
    }
}
