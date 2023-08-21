package com.example.jungleconsulting.mapper

import com.example.jungleconsulting.db.user.UserDataEntity
import com.example.jungleconsulting.model.UserDataModel
import com.example.jungleconsulting.util.Mapper

class EntityMapper : Mapper<UserDataModel, UserDataEntity> {

    override fun mapModelToEntity(model: UserDataModel) = UserDataEntity (
        login = model.login,
        image = model.image
    )

    override fun mapEntityToModel(entity: UserDataEntity)= UserDataModel (
        login = entity.login,
        image = entity.image
    )
}