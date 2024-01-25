package put.poznan.files

import org.springframework.stereotype.Service
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.isRegularFile

@Service
class FileService {
    private val uploadsFolderPath: Path = Paths.get("resources/files")

    fun upload(file: MultipartFile, path: Path = uploadsFolderPath): String? {
        Files.createDirectories(uploadsFolderPath)
        if (file.originalFilename != null && file.contentType?.contains("image")!!) {
            val uploadedTargetFilePath = uploadsFolderPath.resolve(file.originalFilename!!)
            Files.copy(file.inputStream, uploadedTargetFilePath)
            if (uploadedTargetFilePath.isRegularFile()) {
                return uploadedTargetFilePath.toString()
            }
        }
        return null
    }

    fun download(filename: String, path: Path = uploadsFolderPath): Resource? {
        val file: Path = uploadsFolderPath.resolve(filename)
        val resource: Resource = UrlResource(file.toUri())
        if (resource.exists() || resource.isReadable) {
            return resource
        }
        return null
    }
}