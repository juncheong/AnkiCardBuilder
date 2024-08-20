package util

import domain.FileType
import domain.Language
import model.Card
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd")

fun toCsvFile(cards: Iterable<Card>, outputPath: String, fileType: FileType, language: Language) {
    val outputFileName = "${simpleDateFormat.format(Date())}_${fileType}_${language.code}"
    CSVPrinter(FileWriter("$outputPath\\$outputFileName.csv"), CSVFormat.DEFAULT).use { printer ->
        cards.forEach { card ->
            printer.printRecord(card.front, card.back)
        }
    }
}