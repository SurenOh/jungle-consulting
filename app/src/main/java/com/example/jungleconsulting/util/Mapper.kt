package com.example.jungleconsulting.util

interface Mapper<Model, Entity> {

    fun mapModelToEntity(model: Model): Entity
    fun mapEntityToModel(entity: Entity) : Model

    fun mapListModelsToEntity(listModel: List<Model>) = listModel.map { mapModelToEntity(it) }
    fun mapListEntityToModels(listEntity: List<Entity>) = listEntity.map { mapEntityToModel(it) }

}