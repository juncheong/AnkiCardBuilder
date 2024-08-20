package controller

import domain.FileType
import domain.Language
import model.Card
import util.toCsvFile
import java.io.File

class TranslatedLinesController {
    fun buildCsvFromLines(file: File, outputDirectory: String, language: Language) {
        val cards = mutableListOf<Card>()
        var card: Card? = null
        file.forEachLine { line ->
            if (line.isBlank() && card != null) {
                cards.add(card!!)
                card = null
            } else {
                if (card == null) {
                    card = Card()
                }
                if (card!!.front == null) {
                    card!!.front = line
                } else {
                    card!!.back = line
                }
            }
        }

        card?.let { cards.add(it) }

        toCsvFile(cards, outputDirectory, FileType.LINES, language)
    }
}

