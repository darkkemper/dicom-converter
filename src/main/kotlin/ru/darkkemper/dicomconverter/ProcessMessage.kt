package ru.darkkemper.dicomconverter

enum class ProcessMessage(var m: String) {
    NO_SUCH_DIRECTORY("An error was detected while read directory %s, the reason \"No such directory\"."),
    DICOM_OBJECT_IS_EMPTY("An error was detected while reading %s/DICOMDIR file, the reason \"DicomObject is empty\""),
    NO_SUCH_FILE_REGISTERED_IN_DICOM("An error was detected while reading DICOMDIR file, the reason \"No such file %s registered in DICOMDIR with study date %s\""),
    SUCCESS("Transcending data to image file \"%s\" succeed!"),
    UNEXPECTED_ERROR("Unexpected error")
}