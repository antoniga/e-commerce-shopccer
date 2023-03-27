package com.shopccer.admin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileLoadUtil {

	public static void saveFile(String dirSubida, String nombreArchivo, MultipartFile multipartFile)
			throws IOException {

		Path pathSubida = Paths.get(dirSubida);

		if (!Files.exists(pathSubida)) {
			Files.createDirectories(pathSubida);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path pathArchivo = pathSubida.resolve(nombreArchivo);
			Files.copy(inputStream, pathArchivo, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("No se puede guardar el archivo: " + nombreArchivo, e);
		}
	}
	
	public static void cleanDir(String dir) {

		Path dirPath = Paths.get(dir);

		try {
			Files.list(dirPath).forEach(archivo -> {

				if (!Files.isDirectory(archivo)) {
					try {
						Files.delete(archivo);
					} catch (IOException e) {
						System.out.println("No se pudo eliminar el archivo: " + archivo);
					}

				}
			});
		} catch (IOException e) {
			System.out.println("No se pudo listar el directorio: " + dirPath);
		}
	}

}
