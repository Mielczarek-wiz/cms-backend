package put.poznan.files

import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile

@Service
class FileService {
    private val maxWidth = 1000
    private val maxHeight = 1000

    fun upload(file: MultipartFile, path: String): Boolean {
        val folderPath = Paths.get(path)
        Files.createDirectories(folderPath)
        if (file.originalFilename != null && file.contentType?.contains("image")!!) {
            if( file.contentType?.contains("image/svg")!!){
                return false
            }
            val uploadedTargetFilePath = folderPath.resolve(file.originalFilename!!)
            if (!uploadedTargetFilePath.exists()) {
                val destinationFile = File.createTempFile("temp-", file.originalFilename!!)
                val fileOutputStream = FileOutputStream(destinationFile)
                fileOutputStream.write(file.bytes)
                fileOutputStream.close()
                val bufferedFile = ImageIO.read(destinationFile.inputStream())
                if (bufferedFile.width > maxWidth) {
                    Thumbnails.of(file.inputStream)
                        .size(maxWidth, (maxWidth * bufferedFile.height) / bufferedFile.width)
                        .toFile(destinationFile)
                }
                if (bufferedFile.height > maxHeight) {
                    Thumbnails.of(file.inputStream)
                        .size((maxHeight * bufferedFile.width) / bufferedFile.height, maxHeight)
                        .toFile(destinationFile)
                }
                Files.copy(destinationFile.inputStream(), uploadedTargetFilePath)
            }
            if (uploadedTargetFilePath.isRegularFile()) {
                return true
            }
        }
        return false
    }

    fun download(path: String): String {
        val fileContent: ByteArray = (File(path)).readBytes()
        val encodedString = Base64.getEncoder().encodeToString(fileContent)
        return encodedString
    }
}