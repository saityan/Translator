package geekbrains.ru.translator.view.base

import geekbrains.ru.translator.model.data.AppState

interface ViewInterface {
    fun renderData(appState: AppState)

    fun checkData(word: String) : Boolean

    fun showError(error: String)
}
