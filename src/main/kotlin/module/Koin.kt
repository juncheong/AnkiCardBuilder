package module

import controller.TranslatedLinesController
import controller.VocabWordsController
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.get
import org.koin.dsl.module
import service.DictionaryService
import service.PronunciationService

val appModule = module {
    single<DictionaryService> { DictionaryService() }
    single<PronunciationService> { PronunciationService() }
    single<TranslatedLinesController> { TranslatedLinesController() }
//    single<VocabWordsController> { VocabWordsController(get()) }

    singleOf(::VocabWordsController)
}

val moduleB = module {

}