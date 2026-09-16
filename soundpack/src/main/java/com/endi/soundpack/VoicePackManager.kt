package com.endi.soundpack

import android.content.Context
import com.endi.soundpack.model.Voice
import java.io.File
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.util.zip.ZipInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Endi
 * @version 1.0
 * @since 9/16/2026
 */

object VoicePackManager {

    private const val ROOT_DIR = "soundpack"

    private const val MALE_PACK_VERSION = "1.0.0"

    private const val MALE_PACK_URL =
        "https://github.com/endid7/NotificationSoundPack/releases/download/v1.1.0/soundpack-male-1.0.0.zip"

    /*
     * SHA-256 dari soundpack-male-1.0.0.zip
     *
     * Ambil nilai lengkap dari GitHub Release.
     */
    private const val MALE_PACK_SHA256 =
        "8043fa277bc5eeda66a04c68265b5d6918899603eb1104eadd60ee23cd94bfea"

    private val maleRequiredFiles = listOf(
        "nol.mp3",
        "satu.mp3",
        "dua.mp3",
        "tiga.mp3",
        "empat.mp3",
        "lima.mp3",
        "enam.mp3",
        "tujuh.mp3",
        "delapan.mp3",
        "sembilan.mp3",
        "sepuluh.mp3",
        "sebelas.mp3",
        "belas.mp3",
        "puluh.mp3",
        "ratus.mp3",
        "seratus.mp3",
        "ribu.mp3",
        "seribu.mp3",
        "juta.mp3",
        "miliar.mp3",
        "triliun.mp3",
        "rupiah.mp3",
        "payment_received.mp3",
        "payment_received_indopay.mp3",
        "payment_received_myindopay.mp3",
        "custom_notification_sound.mp3"
    )

    fun getVoiceDirectory(
        context: Context,
        voice: Voice
    ): File {
        return File(
            context.applicationContext.filesDir,
            "$ROOT_DIR/${voice.name.lowercase()}"
        )
    }

    fun isVoicePackAvailable(
        context: Context,
        voice: Voice
    ): Boolean {
        if (voice == Voice.FEMALE) {
            return true
        }

        val directory = getVoiceDirectory(context, voice)

        return directory.exists() &&
                directory.isDirectory &&
                maleRequiredFiles.all { fileName ->
                    File(directory, fileName).isFile
                }
    }

    fun getAudioFile(
        context: Context,
        voice: Voice,
        resourceName: String
    ): File? {
        if (voice == Voice.FEMALE) {
            return null
        }

        val file = File(
            getVoiceDirectory(context, voice),
            "$resourceName.mp3"
        )

        return file.takeIf {
            it.exists() && it.isFile
        }
    }

    fun getMalePackVersion(): String {
        return MALE_PACK_VERSION
    }

    suspend fun downloadMalePack(
        context: Context
    ) {
        withContext(Dispatchers.IO) {
            val zipFile = File(
                context.applicationContext.cacheDir,
                "soundpack-male-$MALE_PACK_VERSION.zip"
            )

            try {
                downloadFile(
                    url = MALE_PACK_URL,
                    destination = zipFile
                )

                verifySha256(
                    file = zipFile,
                    expectedSha256 = MALE_PACK_SHA256
                )

                installMalePack(
                    context = context,
                    zipFile = zipFile
                )
            } finally {
                zipFile.delete()
            }
        }
    }

    private fun downloadFile(
        url: String,
        destination: File
    ) {
        val connection = URL(url).openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 30_000
            connection.readTimeout = 30_000
            connection.instanceFollowRedirects = true

            connection.connect()

            if (connection.responseCode !in 200..299) {
                throw IllegalStateException(
                    "Failed to download voice pack. HTTP ${connection.responseCode}"
                )
            }

            connection.inputStream.use { input ->
                destination.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } finally {
            connection.disconnect()
        }
    }

    private fun verifySha256(
        file: File,
        expectedSha256: String
    ) {
        val actualSha256 = sha256(file)

        require(
            actualSha256.equals(
                expectedSha256,
                ignoreCase = true
            )
        ) {
            "Voice pack SHA-256 mismatch. " +
                    "Expected=$expectedSha256, Actual=$actualSha256"
        }
    }

    private fun sha256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")

        FileInputStream(file).use { input ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

            while (true) {
                val count = input.read(buffer)

                if (count == -1) {
                    break
                }

                digest.update(buffer, 0, count)
            }
        }

        return digest.digest()
            .joinToString("") { byte ->
                "%02x".format(byte)
            }
    }

    private fun installMalePack(
        context: Context,
        zipFile: File
    ) {
        val targetDirectory = getVoiceDirectory(
            context = context,
            voice = Voice.MALE
        )

        val temporaryDirectory = File(
            context.applicationContext.cacheDir,
            "soundpack-male-installing"
        )

        if (temporaryDirectory.exists()) {
            temporaryDirectory.deleteRecursively()
        }

        temporaryDirectory.mkdirs()

        unzip(
            zipFile = zipFile,
            destination = temporaryDirectory
        )

        val extractedMaleDirectory = File(
            temporaryDirectory,
            "male"
        )

        require(extractedMaleDirectory.isDirectory) {
            "Invalid voice pack structure. Missing male directory."
        }

        validateMalePack(extractedMaleDirectory)

        if (targetDirectory.exists()) {
            targetDirectory.deleteRecursively()
        }

        targetDirectory.parentFile?.mkdirs()

        if (!extractedMaleDirectory.renameTo(targetDirectory)) {
            extractedMaleDirectory.copyRecursively(
                targetDirectory,
                overwrite = true
            )
        }

        temporaryDirectory.deleteRecursively()

        require(
            isVoicePackAvailable(
                context = context,
                voice = Voice.MALE
            )
        ) {
            "Male voice pack installation failed."
        }
    }

    private fun validateMalePack(
        directory: File
    ) {
        maleRequiredFiles.forEach { fileName ->
            require(
                File(directory, fileName).isFile
            ) {
                "Male voice pack is missing: $fileName"
            }
        }
    }

    private fun unzip(
        zipFile: File,
        destination: File
    ) {
        ZipInputStream(
            zipFile.inputStream().buffered()
        ).use { zipInputStream ->

            while (true) {
                val entry = zipInputStream.nextEntry
                    ?: break

                val outputFile = File(
                    destination,
                    entry.name
                )

                val destinationPath =
                    destination.canonicalPath + File.separator

                require(
                    outputFile.canonicalPath.startsWith(destinationPath)
                ) {
                    "Invalid ZIP entry: ${entry.name}"
                }

                if (entry.isDirectory) {
                    outputFile.mkdirs()
                } else {
                    outputFile.parentFile?.mkdirs()

                    outputFile.outputStream().use { output ->
                        zipInputStream.copyTo(output)
                    }
                }

                zipInputStream.closeEntry()
            }
        }
    }
}