package com.itis2019.rickandmorty

import android.app.Application
import com.itis2019.rickandmorty.di.component.*
import com.itis2019.rickandmorty.di.module.*

class App : Application() {

    companion object {
        lateinit var component: AppComponent
        lateinit var repositorySComponent: RepositorySComponent
        lateinit var characterSComponent: CharactersSComponent
        lateinit var locationSComponent: LocationsSComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        repositorySComponent = component.plusRepositoryComponent(RepositoryModule())
        characterSComponent = repositorySComponent.plusCharactersSComponent(CharactersModule())
        locationSComponent = repositorySComponent.plusLocationsSComponent(LocationsModule())
    }

    private fun initDagger() {
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .serviceModule(ServiceModule())
            .roomModule(RoomModule(this))
            .build()
    }
}
