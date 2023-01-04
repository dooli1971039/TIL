package com.example.roomdemo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// DAO : Data Access Object
// 인터페이스로, @Dao 키워들르 달면 된다.
// 다오 안에는, 데이터베이스 쿼리에 사용할 함수를 정의한다.

@Dao
interface EmployeeDao {
	@Insert //데이터를 저장할 때
	suspend fun insert(employeeEntity: EmployeeEntity) //코루틴 클래스를 사용해 suspend 함수를 만든다. 시간이 꽤 걸리기 때문

	@Update
	suspend fun update(employeeEntity: EmployeeEntity)

	@Delete //항목이 데이터베이스에서 영구적으로 삭제됨
	suspend fun delete(employeeEntity: EmployeeEntity)


	@Query("Select * from `employee-table`")  // employee-table의 모든 정보를 가져옴
	fun fetchAllEmployee(): Flow<List<EmployeeEntity>> //백그라운드에서 실행될 함수이지만 suspend는 아님


	@Query("Select * from `employee-table` where id=:id")  // employee-table에서 id가 일치하는 정보만 가져옴
	fun fetchEmployeeById(id:Int):Flow<EmployeeEntity>

	// Flow: Coroutines 클래스의 일부이면, 런타임에서 바뀔 수 있는 값을 가진다.
	// Flow를 사용하면 변수나 메소드에서 값을 collect 하기만 하면 된다.
	// 사용자 인터페이스에서 코드를 업데이트할 필요 X. 데이터가 변경될 때마다 데이터를 배출한다.
}