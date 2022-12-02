package com.tikay.noteappkmm.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import com.tikay.noteappkmm.data.local.DatabaseDriverFactory
import com.tikay.noteappkmm.data.note.NoteDataSourceImpl
import com.tikay.noteappkmm.data.note.NoteRepositoryImpl
import com.tikay.noteappkmm.data.note.repository.NoteDataSource
import com.tikay.noteappkmm.database.NoteDatabase
import com.tikay.noteappkmm.domain.note.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(sqlDriver: SqlDriver): NoteDatabase {
        return NoteDatabase(sqlDriver)
    }


    @Provides
    @Singleton
    fun provideNoteDataSource(noteDatabase: NoteDatabase): NoteDataSource {
        return NoteDataSourceImpl(noteDatabase)
    }

    @Provides
    @Singleton
    fun provideNoteDataRepository(noteDataSource: NoteDataSource): NoteRepository {
        return NoteRepositoryImpl(noteDataSource)
    }

}