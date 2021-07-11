package az.zero.azshop.di

import android.app.Application
import androidx.room.Room
import az.zero.azshop.dp.ProductDatabase
import az.zero.azshop.utils.DATABASE_NAME
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
    fun providesDatabase(
        app: Application
    ) = Room.databaseBuilder(app, ProductDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideTaskDao(db: ProductDatabase) = db.productDao()


}