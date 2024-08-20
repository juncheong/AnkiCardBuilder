package service

import com.google.gson.Gson
import domain.Language
import model.Card
import model.WiktionaryResponse
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import util.printToGui
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class DictionaryService {

    private val httpClient = HttpClient.newBuilder().build()
    private val gson = Gson()

    fun translate(word: String, language: Language): Card? {
        val elements = getFromWiktionaryApi(word)

        return when (language) {
            Language.SVENSKA -> {
                getDefinition(word, elements, Language.SVENSKA) ?:
                getDefinition(word.replaceFirst(word[0], word[0].toUpperCase()), elements, Language.SVENSKA)
            }
            Language.DEUTSCH -> {
                getDefinition(word, elements, Language.DEUTSCH) ?:
                getDefinition(word.replaceFirst(word[0], word[0].toUpperCase()), elements, Language.DEUTSCH)
            }

            Language.KOREAN -> {
                getDefinition(word, elements, Language.KOREAN) ?:
                getDefinition(word.replaceFirst(word[0], word[0].toUpperCase()), elements, Language.KOREAN)
            }
        }
    }

    private fun getFromWiktionaryApi(word: String): String? {
        val encodedWord = URLEncoder.encode(word, "UTF-8")
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://en.wiktionary.org/w/api.php?titles=$encodedWord&action=query&prop=extracts&format=json"))
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val htmlResponse = gson.fromJson(response.body(), WiktionaryResponse::class.java)
            .query!!
            .pages!!
            .iterator()
            .next()
            .value
            .extract

        if (htmlResponse != null) {
            val replaced = htmlResponse.toString().replace("\n", "")
            if (replaced.isNotBlank()) {
                printToGui("***********************")
                printToGui(replaced)
//                val parsed = Jsoup.parse(replaced).select("*")

                // TODO: Try to remove empty elements
//                for (element in parsed) {
//                    if (!element.hasText() && element.isBlock()) {
//                        element.remove()
//                    }
//                }
                return replaced
            } else {
                return null
            }
        } else {
            return null
        }
    }

    // TODO: perhaps in the future, it might make sense to return to parsing wiktionary data based on the chosen language
    private fun getDefinition(word: String, elements: String?, language: Language): Card? {
        if (elements.isNullOrEmpty()) {
            return null
        }

        val definitions = StringBuilder()
//        definitions.append(elements.html())
        definitions.append(elements.toString())

        return if (definitions.isBlank()) null else Card(word.replaceFirst(word[0], word[0].toUpperCase()), definitions.toString())
    }
}