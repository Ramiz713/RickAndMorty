package com.itis2019.rickandmorty.di.component

import com.itis2019.rickandmorty.di.module.*
import com.itis2019.rickandmorty.ui.bottomNavigation.*
import com.itis2019.rickandmorty.ui.characters.CharacterFragment
import com.itis2019.rickandmorty.ui.locations.LocationFragment
import com.itis2019.rickandmorty.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        ServiceModule::class,
        RoomModule::class,
        NavigationModule::class,
        PresentersModule::class,
        RepositoryModule::class]
)
interface AppComponent {

    fun inject(characterFragment: CharacterFragment)

    fun inject(characterFragment: LocationFragment)

    fun inject(mainActivity: MainActivity)

    fun inject(bottomActivity: BottomNavigationActivity)

    fun inject(firstFragment: FirstFragment)

    fun inject(secondFragment: SecondFragment)

    fun inject(thirdFragment: ThirdFragment)

    fun inject(fourthFragment: FourthFragment)

    fun inject(fifthFragment: FifthFragment)
}
