package comp4097.comp.hkbu.edu.hk.Infoday.data

import androidx.room.*
@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)
    @Query("Select * from event where deptID = :deptID")
    suspend fun findEventsByDeptID(deptID: String): List<Event>
    @Query("Select * from event where bookmarked = 1")
    suspend fun findAllBookmarkedEvents(): List<Event>
    @Delete
    suspend fun delete(vararg events: Event)
    @Update
    suspend fun update(events: Event)
}
