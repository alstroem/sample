package dk.alstroem.sample.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dk.alstroem.data.environmemt.EnvironmentRepositoryImpl
import dk.alstroem.data.environmemt.local.EnvironmentDao
import dk.alstroem.data.environmemt.local.EnvironmentDatabase
import dk.alstroem.domain.environment.EnvironmentRepository
import dk.alstroem.core.sensor.EnvironmentSensor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideEnvironmentSensor(
        @ApplicationContext appContext: Context
    ): dk.alstroem.core.sensor.EnvironmentSensor {
        return dk.alstroem.core.sensor.EnvironmentSensor(appContext)
    }

    @Provides
    @Singleton
    fun provideEnvironmentDatabase(
        @ApplicationContext appContext: Context
    ): EnvironmentDatabase {
        return Room.databaseBuilder(
            appContext,
            EnvironmentDatabase::class.java,
            "environment_database"
        ).build()
    }

    @Provides
    fun provideEnvironmentDao(database: EnvironmentDatabase): EnvironmentDao {
        return database.environmentDao()
    }

    @Provides
    fun provideEnvironmentRepository(
        environmentDao: EnvironmentDao
    ): EnvironmentRepository {
        return EnvironmentRepositoryImpl(environmentDao)
    }
}
