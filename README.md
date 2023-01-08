<p align="center">
  <img src="https://sun9-53.userapi.com/impg/egurcw-f66Gg5DT71D36PjIHQ5GBe7PmOjkJnQ/cIC-7It75N8.jpg?size=800x200&quality=96&sign=7670b96954f2542e131ed199663c67c2&type=album" width="100%">
</p>

# DICOM file converter 
[![License](https://img.shields.io/:license-Apache%202-blue.svg?style=flat-square)](https://github.com/darkkemper/dicom-converter/blob/master/LICENSE)

This repository contains the **sourcecode** of app **converting DICOM files to png with `kotlin`**

## Building

To build a combined JAR of project and runtime dependencies use `buildFatJar`

## To run it locally use this command

```bash
java -jar build/libs/dicom-converter.0.0.1.jar
```

| Program arguments | Default           | Description                                |
|-------------------|-------------------|--------------------------------------------|
| -d, --directory   | working directory | Directory containing DICOMDIR file         |
| -o, --out         | out/              | Output directory for converted image files |
| -e, --extension   | png               | Extension for output file [png, jpg]       |
