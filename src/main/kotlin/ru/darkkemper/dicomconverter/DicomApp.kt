/*
 * Copyright 2023 Evgeniy Tatarinov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.darkkemper.dicomconverter

import org.apache.logging.log4j.kotlin.Logging
import org.dcm4che2.data.Tag
import org.dcm4che2.io.DicomInputStream
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.io.File
import java.nio.file.Paths
import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream
import kotlin.system.exitProcess

/**
 * Main entry point into program used to extract info data from DICOMDIR file.
 *
 * @author Evgeniy Tatarinov (darkkemper@gmail.com)
 * @version 0.0.1
 */
@Command(
    name = "run", mixinStandardHelpOptions = true, version = ["0.0.1"],
    description = ["Convert images from DICOM to user chosen image format"]
)
class DicomApp : Runnable, Logging {

    /**
     * Directory containing DICOMDIR file
     */
    @Option(names = ["-d", "--directory"], description = ["Directory containing DICOMDIR file"])
    var dir = Paths.get("").toAbsolutePath().toString()

    /**
     * Output directory for converted image files
     */
    @Option(names = ["-o", "--out"], description = ["Output directory for converted image files"])
    var out = "out"

    /**
     * Extension for output file
     */
    @Option(names = ["-e", "--extension"], description = ["Extension for output file [png, jpg]"])
    var extension = "png"

    /**
     * Init
     */
    override fun run() {
        // clear inputs
        val format = "/*$".toRegex()
        dir = dir.replace(format, "")
        out = out.replace(format, "")

        File(dir).run {
            if (isDirectory) {
                walk().forEach { it ->
                    if (it.isFile && it.name == "DICOMDIR") {
                        try {
                            process(it, out)
                        } catch (e: Exception) {
                            logger.error(e)
                        }
                    }
                }
            } else {
                logger.error("An error was detected while read directory $dir, the reason \"No such directory\".")
            }
        }
    }

    /**
     * Task to convert images from DICOM to user format
     */
    private fun process(dicomDir: File, out: String) {
        DicomInputStream(dicomDir).run {
            val dicomObject = readDicomObject()
            if (dicomObject.isEmpty) {
                logger.error("An error was detected while reading ${dicomDir.parent}/DICOMDIR file, the reason \"DicomObject is empty\"")
            }
            dicomObject.datasetIterator().forEach {
                if (it.hasItems()) {
                    var date = ""
                    var output = ""
                    for (i in 0 until it.countItems()) {
                        val em = it.getDicomObject(i).getString(Tag.DirectoryRecordType)
                        val currentDicomObject = it.getDicomObject(i)
                        when (em) {
                            "STUDY" -> {
                                date = currentDicomObject.getString(Tag.StudyDate)
                                output = "$out/${date}"
                            }

                            "IMAGE" -> {
                                val input = dicomDir.parent.plus("/")
                                    .plus(
                                        String(currentDicomObject.getBytes(Tag.ReferencedFileID)).replace(
                                            "\\",
                                            "/"
                                        )
                                    ).trim()

                                val fileName = "/([a-zA-Z\\d]+$)".toRegex().find(input)
                                    ?.destructured
                                    ?.component1()

                                if (!File(input).isFile) {
                                    logger.error("An error was detected while reading DICOMDIR file, the reason \"No such file $input registered in DICOMDIR with study date $date\"")
                                    continue
                                }

                                val iss: ImageInputStream = ImageIO.createImageInputStream(File(input))
                                try {
                                    val iterator = ImageIO.getImageReaders(iss)
                                    if (!iterator.hasNext()) {
                                        continue
                                    }
                                    val reader = iterator.next()
                                    try {
                                        reader.input = iss
                                        if (reader.formatName == "dicom" && reader.getNumImages(true) > 0) {
                                            val image = reader.read(0)
                                            val file =
                                                File("$output/${image.width}x${image.height}/${fileName}.$extension")
                                                    .also { file ->
                                                        file.parentFile.mkdirs()
                                                    }
                                            ImageIO.write(
                                                reader.read(0),
                                                "png",
                                                file.outputStream()
                                            ).also { result ->
                                                when (result) {
                                                    true -> logger.info("Transcending data to image file \"$output/${image.width}x${image.height}/${fileName}.png\" succeed!")
                                                    else -> logger.error("Unexpected error")
                                                }
                                            }
                                        }
                                    } catch (e: Exception) {
                                        logger.error(e)
                                    } finally {
                                        reader.dispose()
                                    }
                                } catch (e: Exception) {
                                    logger.error(e)
                                } finally {
                                    iss.close()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>): Unit = exitProcess(CommandLine(DicomApp()).execute(*args))
