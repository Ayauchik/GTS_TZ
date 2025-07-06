package kz.petproject.gts_tz.data.network.mapper

import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.data.network.response.PostSignInResponse

class UserMapper {
    fun fromRemoteToDomain(postSignInResponse: PostSignInResponse): User{
        return User(
            id = postSignInResponse.userResponse.id,
            name = postSignInResponse.userResponse.name,
            login = postSignInResponse.userResponse.login,
            role = postSignInResponse.userResponse.role
        )
    }
}