package geekbrains.ru.translator.view.main

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.datasource.DataSourceLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.presenter.Presenter
import geekbrains.ru.translator.rx.SchedulerProvider
import geekbrains.ru.translator.view.base.ViewInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainPresenterImpl<T : AppState, V : ViewInterface>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        val check = checkData(word)
        check?.let {
            if (check) {
                compositeDisposable.add(
                    interactor.getData(word, isOnline)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                        .subscribeWith(getObserver())
                )
            }
            else currentView?.showError(
            "Пожалуйсте, введите слово, состоящее по крайней мере из двух символов (разрешены только латинские буквы)"
            )
        }
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }

    override fun checkData(word: String) : Boolean? = currentView?.checkData(word)
}
