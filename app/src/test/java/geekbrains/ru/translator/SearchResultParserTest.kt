package geekbrains.ru.translator

import geekbrains.ru.model.data.AppState
import geekbrains.ru.model.data.userdata.DataModel
import geekbrains.ru.model.data.userdata.Meaning
import geekbrains.ru.model.data.userdata.TranslatedMeaning
import geekbrains.ru.repository.convertDataModelSuccessToEntity
import geekbrains.ru.translator.utils.getSuccessResultData
import org.junit.Assert.*
import org.junit.Test

class SearchResultParserTest {
    private val searchHeaderFirst = "equal"
    private val searchMeaningsFirst = listOf(
        meaningConstructor("одинаковый"),
        meaningConstructor("равный"),
        meaningConstructor("соответствующий"),
        meaningConstructor("равняться"),
        meaningConstructor("уравнивать"),
        meaningConstructor("ровня")
    )
    private val searchResultFirst = DataModel(searchHeaderFirst, searchMeaningsFirst)

    private val searchHeaderSecond = "equally"
    private val searchMeaningsSecond = listOf(
        meaningConstructor("в равной степени"),
        meaningConstructor("поровну"),
        meaningConstructor("одинаково")
    )
    private val searchResultSecond = DataModel(searchHeaderSecond, searchMeaningsSecond)

    private val searchDataModels: List<DataModel> = listOf(searchResultFirst, searchResultSecond)

    private var newSearchDataModels = ArrayList<DataModel>()

    private fun meaningConstructor(meaning: String) : Meaning = Meaning(TranslatedMeaning(meaning))

    @Test
    fun isResultDataSuccess_WithConnection() {
        getSuccessResultData(AppState.Success(searchDataModels), true, newSearchDataModels)
        assertArrayEquals(newSearchDataModels.toArray(), searchDataModels.toTypedArray())
    }

    @Test
    fun isMeaningsDataNull_WithoutConnection() {
        newSearchDataModels = ArrayList()
        getSuccessResultData(AppState.Success(searchDataModels), false, newSearchDataModels)
        assertNull(newSearchDataModels.getOrNull(0)?.meanings?.getOrNull(0))
    }

    @Test
    fun isHeaderExists_WithoutConnection() {
        newSearchDataModels = ArrayList()
        getSuccessResultData(AppState.Success(searchDataModels), false, newSearchDataModels)
        assertNotNull(newSearchDataModels.getOrNull(0))
    }

    @Test
    fun assertSameResult_OnConvertDataModelSuccess_WithoutSuccess() {
        assertSame(
            convertDataModelSuccessToEntity(AppState.Loading(0)),
            convertDataModelSuccessToEntity(AppState.Error(Throwable()))
        )
    }
}
