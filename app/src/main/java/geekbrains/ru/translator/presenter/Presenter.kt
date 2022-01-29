package geekbrains.ru.translator.presenter

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.view.base.ViewInterface

interface Presenter<T : AppState, V : ViewInterface> {
    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
