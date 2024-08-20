package controller

import domain.FileType
import domain.Language
import model.Card
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import service.DictionaryService
import service.PronunciationService
import util.printToGui
import util.toCsvFile
import java.io.File
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


class VocabWordsController (
    val dictionaryService: DictionaryService,
    val pronunciationService: PronunciationService) : KoinComponent {


//class VocabWordsController() : KoinComponent {
//
//    val dictionaryService: DictionaryService by inject()
//    val pronunciationService: PronunciationService by inject()

    // TODO: remove default value for forvoApiKey once the UI starts taking in values
    fun buildCsvFromVocabularyWords(
        file: File,
        outputDirectory: String,
        language: Language,
        forvoApiKey: String? = null
    ) {
        printToGui("Building CSV from vocabulary words.")
        forvoApiKey?.also {
            printToGui("Adding pronunciations using Forvo API Key: $forvoApiKey")
        }

        val words = mutableSetOf<String>()
        file.forEachLine { line ->
            if (line.isNotBlank()) {
                words.add(line.lowercase(Locale.getDefault()).trim())
            }
        }

        val cards = ConcurrentLinkedQueue<Card>()
        val invalidWords = ConcurrentLinkedQueue<String>()
        words.parallelStream().forEach { word ->
            dictionaryService.translate(word, language)?.also { card ->
                cards.add(card)
                forvoApiKey?.also {
                    pronunciationService.addPronunciation(card, language, forvoApiKey)
                }
            } ?: run {
                invalidWords.add(word)
            }
        }

//        cards.forEach{card ->
//            printToGui("${card.front.toString()}")
//        }

//        cards.forEach{card ->
//            printToGui("${card.back.toString()}")
//        }


        toCsvFile(cards, outputDirectory, FileType.VOCAB, language)

        printToGui("Successfully output to csv file.")
        if (!invalidWords.isEmpty()) {
            printToGui("Could not find definition for: ")
            invalidWords.forEach { word -> printToGui(word) }
        }
    }
}

