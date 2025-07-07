package com.neilsayok.bluelabs.data.portfolio

import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.domain.util.Response


sealed class FileType {
    object MDFile : FileType()
    object Other : FileType()

    fun String.getFileType(): FileType {
        return if (this.endsWith(".md")) MDFile else Other
    }
}


sealed class FolderType {
    object Jobs : FolderType()
    object Projects : FolderType()
    object Other : FolderType()

    fun String.getFolderType(): FolderType {
        return when {
            this.contains("jobs") -> Jobs
            this.contains("projects") -> Projects
            else -> Other
        }
    }
}


data class PortfolioFileContents(
    val fileType: FileType,
    val folder: FolderType,
    val fileName: String,
    val subTitle: String? = null,
    val order : Int? = null,
    val icon: String? = null,
    val content: String? = null,
    val response : Response<GithubResponse> = Response.None
)

fun String.getFileNameSubTileOrder(): Triple<String, String?, Int?> {
    val fileName = this.substringAfter('/')
    val order = try { fileName.substringBefore('.').toInt() }catch (_: Exception) {null}
    val titleSplit = fileName.substringAfter('.').substringBeforeLast('.').replace('-', ' ').replace("_", "-").split("-")
    return Triple(titleSplit[0], if (titleSplit.size == 1) null else titleSplit[1], order)
}
