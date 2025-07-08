package kz.petproject.gts_tz.data.network.mapper

import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.data.network.response.CreateUserResponse
import kz.petproject.gts_tz.data.network.response.PostSignInResponse

class UserMapper {
    fun fromRemoteLoginToDomain(postSignInResponse: PostSignInResponse): User{
        return User(
            id = postSignInResponse.userResponse.id,
            name = postSignInResponse.userResponse.name,
            login = postSignInResponse.userResponse.login,
            role = postSignInResponse.userResponse.role
        )
    }

    fun fromRemoteCreateToDomain(createUserResponse: CreateUserResponse): User{
        return User(
            id = createUserResponse.user.id,
            name = createUserResponse.user.name,
            login = createUserResponse.user.login,
            role = createUserResponse.user.role,
        )
    }
}