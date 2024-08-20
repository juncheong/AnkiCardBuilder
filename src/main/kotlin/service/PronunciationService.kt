package service

import com.google.gson.Gson
import domain.Language
import model.Card
import model.ForvoResponse
import model.Item
import util.printToGui
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class PronunciationService {

    private val httpClient = HttpClient.newBuilder().build()
    private val gson = Gson()
    private val fileOutputPath = "" // TODO:

    fun addPronunciation(card: Card, language: Language, apiKey: String): Card {
        card.front?.also { word ->
            val item = getFromForvoApi(word, language, apiKey).items.maxByOrNull { it.num_positive_votes }!!

            downloadMp3(item, language, fileOutputPath)?.also {
                card.back += "\n [sound:$it.mp3]"
            } ?: run {
                printToGui("Unable to fetch pronunciation for $word")
            }
        } ?: run {
            printToGui("Front of card is null.")
            throw NullPointerException()
        }

        return card
    }

    // TODO: Need to properly send request
    private fun getFromForvoApi(word: String, language: Language, apikey: String): ForvoResponse {
        val encodedWord = URLEncoder.encode(word, "UTF-8")
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://en.wiktionary.org/w/api.php?titles=$encodedWord&action=query&prop=extracts&format=json"))
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return gson.fromJson(response.body(), ForvoResponse::class.java)
    }

    private fun downloadMp3(item: Item, language: Language, fileOutputPath: String): String? {
        val fileName = "${language.code}_${item.word}"
        // TODO: make API call to download the file from item.pathmp3

        // TODO: create a new file using try-with-resources

        // TODO: save the file

        // TODO: on success return fileName

        // TODO else return null
        return null
    }
}