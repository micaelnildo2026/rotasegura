package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.model.IncidentReportEntity

@Database(entities = [IncidentReportEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun incidentDao(): IncidentDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "rotasegura_database"
        )
          .fallbackToDestructiveMigration(true)
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
