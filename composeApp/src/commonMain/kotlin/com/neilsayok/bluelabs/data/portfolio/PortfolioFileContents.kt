package com.neilsayok.bluelabs.data.portfolio

import com.arkivanov.decompose.value.MutableValue
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.domain.util.Response
import kotlinx.coroutines.flow.Flow


sealed class FileType {
    object MDFile : FileType()
    object Image : FileType()
    object Other : FileType()

    fun String.getFileType(): FileType {
        return when {
            this.endsWith(".md") -> MDFile
            this.endsWith(".png") || this.endsWith(".jpg") || this.endsWith(".jpeg") || this.endsWith(
                ".gif"
            ) || this.endsWith(".webp") || this.endsWith(".svg") -> Image

            else -> Other
        }


    }
}


sealed class FolderType(val folderName: String) {
    object Jobs : FolderType("jobs")
    object JobsIcon : FolderType("jobs/icons")
    object Projects : FolderType("projects")
    object ProjectIcon : FolderType("projects/icons")
    object Other : FolderType("")

    fun String.getFolderType(): FolderType {
        return when {
            this.contains(JobsIcon.folderName) -> JobsIcon
            this.contains(ProjectIcon.folderName) -> ProjectIcon
            this.contains(Projects.folderName) -> Projects
            this.contains(Jobs.folderName) -> Jobs
            else -> Other
        }
    }
}


data class PortfolioFileContents(
    val fileType: FileType,
    val folder: FolderType,
    val fileName: String,
    val subTitle: String? = null,
    val order: Int? = null,
    val icon: String? = null,
    var content: String? = null,
    val response: Response<GithubResponse>
)

fun String.getFileNameSubTileOrder(): Triple<String, String?, Int?> {
    val fileName = this.substringAfter('/')
    val order = try {
        fileName.substringBefore('.').toInt()
    } catch (_: Exception) {
        null
    }
    val titleSplit =
        fileName.substringAfter('.').substringBeforeLast('.').replace('-', ' ').replace("_", "-")
            .split("-")
    return Triple(titleSplit[0], if (titleSplit.size == 1) null else titleSplit[1], order)
}


fun PortfolioFileContents.projectFileValidator(): Boolean =
    (this.folder == FolderType.Projects)
            && (this.fileType == FileType.MDFile)


fun PortfolioFileContents.jobFileValidator(): Boolean =
    (this.folder == FolderType.Jobs )
            && (this.fileType == FileType.MDFile)