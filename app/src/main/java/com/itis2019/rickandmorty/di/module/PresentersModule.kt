package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.bottomNavigation.mvp.*
import com.itis2019.rickandmorty.ui.characters.CharacterPresenter
import com.itis2019.rickandmorty.ui.locations.LocationPresenter
import com.itis2019.rickandmorty.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class PresentersModule {

    @Provides
    @Singleton
    fun provideCharactersPresenter(repository: Repository, router: Router): CharacterPresenter =
        CharacterPresenter(repository, router)

    @Provides
    @Singleton
    fun provideLocationsPresenter(repository: Repository): LocationPresenter =
        LocationPresenter(repository)

    @Provides
    @Singleton
    fun provideMainPresenter(router: Router): MainPresenter =
        MainPresenter(router)

    @Provides
    @Singleton
    fun provideBottomPresenter(router: Router): BottomPresenter =
        BottomPresenter(router)

    @Provides
    @Singleton
    fun provideFirstPresenter(router: Router): FirstPresenter =
        FirstPresenter(router)

    @Provides
    @Singleton
    fun provideSecondPresenter(router: Router): SecondPresenter =
        SecondPresenter(router)

    @Provides
    @Singleton
    fun provideThirdPresenter(router: Router): ThirdPresenter =
        ThirdPresenter(router)

    @Provides
    @Singleton
    fun provideFourthPresenter(router: Router): FourthPresenter =
        FourthPresenter(router)

    @Provides
    @Singleton
    fun provideFifthPresenter(router: Router): FifthPresenter =
        FifthPresenter(router)
}
