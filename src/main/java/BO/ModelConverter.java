package BO;

import DB.Entities.UserEntity;
import ViewModel.UserViewModel;

/**
 * Created by waleedhassan on 26/11/16.
 * This Class converts from and to ViewModel & Entities
 */
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

}
