# DICOM file converter

This repository contains the **sourcecode** of app **converting DICOM files to png with `kotlin`**

### Building

To build a combined JAR of project and runtime dependencies use `buildFatJar`

### To run it locally use this command

```bash
java -jar build/libs/dicom-converter.0.0.1.jar
```

| Program arguments | Default           | Description                                |
|-------------------|-------------------|--------------------------------------------|
| -d, --directory   | working directory | Directory containing DICOMDIR file         |
| -o, --out         | out/              | Output directory for converted image files |
| -e, --extension   | png               | Extension for output file [png, jpg]       |