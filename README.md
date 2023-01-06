# DICOM file converter

This repository contains the **sourcecode** of app **converting DICOM files to png with `kotlin`**

### Building

To build a combined JAR of project and runtime dependencies use `buildFatJar`

### To run it locally use this command

```
java -jar build/libs/dicom-converter.0.0.1.jar -d path_to_CT_or_MRI_dir/ -o out/ -e png
```