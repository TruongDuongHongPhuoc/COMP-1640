package com.example.comp1640.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

	@Autowired
	public StorageProperties storageProperties = new StorageProperties();
	private final Path rootLocation = Paths.get(storageProperties.getLocation());

//	@Autowired
//	public FileSystemStorageService(StorageProperties properties) {
//		this.rootLocation = Paths.get(properties.getLocation());
//	}

	@Override
	public void store(MultipartFile file) {
		try {
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
					.normalize().toAbsolutePath();
			System.out.println(destinationFile);

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new RuntimeException();
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			return resource;
		}
		catch (MalformedURLException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	@Override
	public void deleteFile(String filename) {
		try {
			Path fileToDelete = rootLocation.resolve(filename);
			Files.deleteIfExists(fileToDelete);
		} catch (IOException e) {
			throw new RuntimeException("Failed to delete file: " + filename, e);
		}
	}
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
