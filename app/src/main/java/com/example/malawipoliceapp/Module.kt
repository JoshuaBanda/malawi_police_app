package com.example.malawipoliceapp
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import jakarta.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideUserApi(): UserApi {
//        return UserApiImpl()
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserRepository(
//        api: UserApi
//    ): UserRepository {
//        return UserRepository(api)
//    }
//}
