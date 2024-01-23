package put.poznan.page.dto

import put.poznan.page.Page

data class PageDtoResponse(
        val id: Long,
        val name: String,
        val link: String,
        val parentPage: String,
        val sections: List<String>,
        val user: String,
        val hidden: Boolean
)