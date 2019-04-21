package com.itis2019.rickandmorty

import android.app.Application
import com.itis2019.rickandmorty.di.component.AppComponent
import com.itis2019.rickandmorty.di.component.DaggerAppComponent
import com.itis2019.rickandmorty.di.module.*

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .serviceModule(ServiceModule())
            .roomModule(RoomModule(this))
            .presentersModule(PresentersModule())
            .repositoryModule(RepositoryModule())
            .navigationModule(NavigationModule())
            .build()
    }
}
