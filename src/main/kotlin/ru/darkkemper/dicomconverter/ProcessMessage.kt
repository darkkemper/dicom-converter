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

enum class ProcessMessage(var m: String) {
    NO_SUCH_DIRECTORY("An error was detected while read directory %s, the reason \"No such directory\"."),
    DICOM_OBJECT_IS_EMPTY("An error was detected while reading %s/DICOMDIR file, the reason \"DicomObject is empty\""),
    NO_SUCH_FILE_REGISTERED_IN_DICOM("An error was detected while reading DICOMDIR file, the reason \"No such file %s registered in DICOMDIR with study date %s\""),
    SUCCESS("Transcending data to image file \"%s\" succeed!"),
    UNEXPECTED_ERROR("Unexpected error")
}