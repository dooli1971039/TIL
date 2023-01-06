package com.example.happyplaces.database

import androidx.room.*
import com.example.happyplaces.models.HappyPlaceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface HappyPlaceDao {
	@Insert
	suspend fun insert(hpModel:HappyPlaceModel)

	@Update
	suspend fun update(hpModel:HappyPlaceModel)

	@Delete
	suspend fun delete(hpModel: HappyPlaceModel)

	@Query("Select * from `happyplace-table`")
	fun fetchAllHappyPlaces(): Flow<List<HappyPlaceModel>>
}