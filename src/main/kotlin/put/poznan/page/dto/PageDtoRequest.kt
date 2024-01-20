package put.poznan.page.dto

import put.poznan.page.Page

data class PageDtoRequest(
        val name: String,
        val link: String,
        val hidden: Boolean,
        val user: String,
        val page: Page
)