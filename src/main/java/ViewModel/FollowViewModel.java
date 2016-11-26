package ViewModel;

/**
 * Created by waleedhassan on 26/11/16.
 */
public class FollowViewModel {

    private UserViewModel follower;
    private UserViewModel following;

    public UserViewModel getFollower() {
        return follower;
    }

    public void setFollower(UserViewModel follower) {
        this.follower = follower;
    }

    public UserViewModel getFollowing() {
        return following;
    }

    public void setFollowing(UserViewModel following) {
        this.following = following;
    }
}
