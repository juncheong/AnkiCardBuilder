package model

data class WiktionaryResponse(
    var batchComplete: String? = null,
    var warnings: Warning? = null,
    var query: Query? = null
)

data class Warning(var extracts: Map<String, String>? = null)

data class Query(var pages: Map<String, Page>? = null)

data class Page(var pageId: Int? = null, var ns: Int? = null, var title: String? = null, var extract: String? = null)
