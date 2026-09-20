package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.IncidentReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidentDao {
  @Query("SELECT * FROM incident_reports ORDER BY timestamp DESC")
  fun getAllReports(): Flow<List<IncidentReportEntity>>

  @Query("SELECT * FROM incident_reports WHERE category = :category ORDER BY timestamp DESC")
  fun getReportsByCategory(category: String): Flow<List<IncidentReportEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertReport(report: IncidentReportEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertReports(reports: List<IncidentReportEntity>)

  @Update
  suspend fun updateReport(report: IncidentReportEntity)

  @Query("UPDATE incident_reports SET upvotes = upvotes + 1 WHERE id = :id")
  suspend fun upvoteReport(id: Long)

  @Query("DELETE FROM incident_reports WHERE id = :id")
  suspend fun deleteReportById(id: Long)

  @Query("SELECT COUNT(*) FROM incident_reports")
  suspend fun getReportCount(): Int
}
