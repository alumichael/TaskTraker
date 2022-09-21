package com.michaelau.tasktraker.di

import android.content.Context
import androidx.room.Room
import com.michaelau.tasktraker.Repository
import com.michaelau.tasktraker.data.localdb.TaskDao
import com.michaelau.tasktraker.data.localdb.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext appContext: Context): TaskDatabase {
        return Room.databaseBuilder(
            appContext,
            TaskDatabase::class.java,
            "task.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTaskDao(appDatabase: TaskDatabase): TaskDao {
        return appDatabase.taskDao()
    }

}